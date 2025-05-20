package kr.or.iei.cafe.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        for (String paramName : paramMap.keySet()) {
            if (paramName.equals("selectedStatusJson")) {
                continue;
            }

            String cafeNo = paramName;
            String statusValue = request.getParameter(paramName);
            updateMap.put(cafeNo, statusValue);
        }

        CafeService service = new CafeService();
        Map<String, String> resultMap = service.updateCafeStatus(updateMap);

        
        //결과 메시지 생성
        StringBuilder msgBuilder = new StringBuilder();
        boolean hasFailure = false;
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            msgBuilder.append("카페번호").append(entry.getKey())
                      .append(": ").append(entry.getValue()).append("\\n");
            if (entry.getValue().contains("실패")) {
                hasFailure = true;
            }
        }

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
