package kr.or.iei.cafe.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.or.iei.cafe.model.service.CafeService;
import kr.or.iei.cafe.model.vo.Cafe;

/**
 * Servlet implementation class MapServlet
 */
@WebServlet("/map")
public class MapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CLIENT_ID = "cwtyjal38c";
	private static final String CLIENT_SECRET = "7BL7bkPIcrXFYtjmGKZp7bBFYSGtHpJqomKKWaQG";

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("==== MapServlet 실행됨 ====");

        String cafeNo = request.getParameter("cafeNo");
        System.out.println("파라미터로 간 cafeNo값: " + cafeNo); // null 확인

        // 1. DB에서 주소 가져오기 (CafeService 이용)
        CafeService cafeservice = new CafeService();
        Cafe cafe = cafeservice.selectCafeByNo(cafeNo); // 예: "서울 종로구 세종대로 110"
        System.out.println("cafe.getCafeAddr() : " + cafe.getCafeAddr()); // null 확인

        if (cafe.getCafeAddr() == null || cafe.getCafeAddr().isEmpty()) {
            System.out.println("[DEBUG] 주소가 없음");
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("주소 없음");
            return;
        }

        String rawAddress = cafe.getCafeAddr();
        System.out.println("원본 주소: " + rawAddress);

        // 정규표현식으로 주소 정리 (층, 호, 호실 등을 제거)
        String cleanAddress = rawAddress
                .replaceAll("\\s*\\d+층", "")       // "8층", "2층" 등 제거
                .replaceAll("\\s*\\d+호", "")       // "202호" 등 제거
                .replaceAll("\\s*\\d+호실", "")     // "301호실" 등 제거
                .replaceAll("\\s*\\([^\\)]*\\)", "") // 괄호 내용 제거 (예: "(용성빌딩)")
                .trim();                             // 앞뒤 공백 제거

        System.out.println("전처리된 주소: " + cleanAddress);

        // ==========================================================

        // 2. 주소 → 좌표 변환
        double[] coords = getCoordinatesFromAddress(cleanAddress);
        System.out.println("좌표: " + Arrays.toString(coords));

        if (coords == null) {
            System.out.println("[DEBUG] 좌표 변환 실패");
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("좌표 변환 실패");
            return;
        }

        double lat = coords[0];
        double lng = coords[1];
        System.out.println("coords[0] : " + coords[0]);
        System.out.println("coords[1] : " + coords[1]);

     // 3. Static Map API 요청
        String apiUrl = "https://maps.apigw.ntruss.com/map-static/v2/raster"
                + "?center=" + lng + "," + lat  // 중심 좌표 (경도, 위도)
                + "&level=16"                   // 줌 레벨
                + "&w=600&h=400"                // 지도 이미지 크기 (가로 600px, 세로 400px)
                + "&markers=type:d|size:mid|pos:" + lng + "," + lat;  // 마커 설정 (중간 크기, 기본 마커, 위치: lng, lat)


        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", CLIENT_SECRET);

        if (conn.getResponseCode() == 200) {
            response.setContentType("image/png");
            try (InputStream in = conn.getInputStream(); OutputStream out = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }

        } else {
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("지도 불러오기 실패");
            System.out.println("[DEBUG] 지도 이미지 불러오기 실패, 응답코드: " + conn.getResponseCode());
        }

	}
    private double[] getCoordinatesFromAddress(String address) throws IOException {
        // 1. API URL 준비 (주소 파라미터 추가)
        String apiURL = "https://maps.apigw.ntruss.com/map-geocode/v2/geocode?query=" + URLEncoder.encode(address, "UTF-8");

        // 2. URL 객체 생성
        URL url = new URL(apiURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 3. 요청 방식 설정
        conn.setRequestMethod("GET");

        // 4. 요청 헤더 설정 (Accept 헤더와 API 키 포함)
        conn.setRequestProperty("x-ncp-apigw-api-key-id", CLIENT_ID);
        conn.setRequestProperty("x-ncp-apigw-api-key", CLIENT_SECRET);
        conn.setRequestProperty("Accept", "application/json");  // 응답 포맷을 JSON으로 요청

        // 5. 응답 코드 확인
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            // 6. 응답 데이터 읽기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            // 7. 응답 JSON 파싱
            String json = sb.toString();
            System.out.println("응답 JSON: " + json);  // 응답 JSON 출력

            // 8. JSON에서 경도 (x)와 위도 (y) 추출
            int xIndex = json.indexOf("\"x\":\"");
            int yIndex = json.indexOf("\"y\":\"");

            if (xIndex > -1 && yIndex > -1) {
                double lng = Double.parseDouble(json.substring(xIndex + 5, json.indexOf("\"", xIndex + 5)));
                double lat = Double.parseDouble(json.substring(yIndex + 5, json.indexOf("\"", yIndex + 5)));
                return new double[]{lat, lng};
            }
        }

        // 9. 좌표를 찾을 수 없는 경우 null 반환
        return null;
    }
}