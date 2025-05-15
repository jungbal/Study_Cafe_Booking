package kr.or.iei.cafe.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.net.httpserver.Authenticator.Result;

import kr.or.iei.cafe.model.service.CafeService;
import kr.or.iei.cafe.model.vo.Cafe;

/**
 * Servlet implementation class DeleteHostServlet
 */
@WebServlet("/deleteHost")
public class DeleteHostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteHostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 인코딩 - 필터
		//2. 값추출
		String cafeNo = request.getParameter("cafeNo");
		
		//3. 로직
		CafeService service = new CafeService();
		int result = service.selectCafeList(cafeNo);
		//4. 결과 처리
			//4.1 이동할 JSP 페이지 경로 지정
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/manager/manageCafe.jsp");
			//4.2 화면 구현에 필요한 데이터 등록
		if(result> 0) {	//정상적으로 수행 됐을 떄
			response.sendRedirect("/manager/cafeManage?reqPage=1");
		}else { //정상적으로 수행 되지 않았을 때
			//4.3 페이지 이동
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
