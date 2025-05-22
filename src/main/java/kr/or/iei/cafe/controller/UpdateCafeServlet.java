package kr.or.iei.cafe.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.or.iei.cafe.model.service.CafeService;

/**
 * Servlet implementation class UpdateCafeServlet
 */
@WebServlet("/updateCafe")
public class UpdateCafeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCafeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, String> updateMap = new HashMap<>();

        // selectedStatusJson 처리
        String selectedStatusJson = request.getParameter("selectedStatusJson");
        if (selectedStatusJson != null && !selectedStatusJson.isEmpty()) {
            // selectedStatusJson에서 반려 사유 값 파싱 (예: 3_B2)
            // 파라미터 예시: {"105":"3_B2"}
            System.out.println("selectedStatusJson: " + selectedStatusJson);

            // JSON 파싱을 위해 Gson 사용
            Gson gson = new Gson();
            Map<String, String> statusMap = gson.fromJson(selectedStatusJson, Map.class);

            // 파싱된 값을 updateMap에 추가
            for (Map.Entry<String, String> entry : statusMap.entrySet()) {
                String cafeNo = entry.getKey();
                String statusValue = entry.getValue();
                
                // 상태 값이 "3"인 경우, 반려 사유가 포함되어 있으므로 업데이트
                if (statusValue.startsWith("3") && statusValue.contains("_")) {
                    // 반려 사유가 있는 상태이므로 그대로 상태 값을 업데이트
                    updateMap.put(cafeNo, statusValue);
                    System.out.println("CafeNo: " + cafeNo + ", Status: " + statusValue);
                } else {
                    // 그 외 상태 값 처리 (필요에 따라 처리 로직 추가)
                    updateMap.put(cafeNo, statusValue);
                    System.out.println("CafeNo: " + cafeNo + ", Status: " + statusValue);
                }
            }
        }

        // 상태 업데이트 서비스 호출
        CafeService service = new CafeService();
        Map<String, String> resultMap = service.updateCafeStatus(updateMap);

        // 결과 메시지 생성
        StringBuilder msgBuilder = new StringBuilder();
        boolean hasFailure = false;
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            System.out.println("CafeNo: " + entry.getKey() + ", Result: " + entry.getValue()); // CafeNo: 105, Result: 반려 사유 입력 완료

            msgBuilder.append("카페번호").append(entry.getKey())
                      .append(": ").append(entry.getValue()).append("\\n");
            if (entry.getValue().contains("실패")) {
                hasFailure = true;
            }
        }

        System.out.println("Message: " + msgBuilder.toString()); // Message: 카페번호105: 반려 사유 입력 완료\n
        System.out.println("Has failure: " + hasFailure); // Has failure: false

        // 알림 메시지 설정
        request.setAttribute("title", hasFailure ? "일부 실패" : "성공");
        request.setAttribute("msg", msgBuilder.toString());
        request.setAttribute("icon", hasFailure ? "error" : "success");
        request.setAttribute("loc", "/manager/cafeManage?reqPage=1");

        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
        view.forward(request, response);
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
