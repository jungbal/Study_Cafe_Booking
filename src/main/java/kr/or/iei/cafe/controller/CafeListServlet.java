package kr.or.iei.cafe.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.cafe.model.service.CafeService;
import kr.or.iei.cafe.model.vo.Cafe;
import kr.or.iei.common.ListData;

/**
 * Servlet implementation class CafeListServlet
 */
@WebServlet("/manager/cafeManage")
public class CafeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CafeListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 인코딩 - 필터
		//2. 값 추출
		int reqPage = Integer.parseInt(request.getParameter("reqPage"));
		
		//3. 로직
		CafeService service = new CafeService();
		ListData<Cafe> cafeList = service.selectAllCafe(reqPage);
		//4. 결과 처리
			//4.1 이동할 페이지 경로 등록
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/manager/manageCafe.jsp");
			
			//4.2 화면 구현에 필요한 데이트 등록
		request.setAttribute("cafeList", cafeList.getList());
		request.setAttribute("pageNavi", cafeList.getPageNavi());
		
			//4.3 페이지 이동
		view.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
