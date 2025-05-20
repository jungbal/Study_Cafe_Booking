package kr.or.iei.cafe.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.cafe.model.service.CafeService;
import kr.or.iei.cafe.model.vo.Cafe;

/**
 * Servlet implementation class Search
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 인코딩 - 필터
		//2. 값 추출 - 클라이언트가 검색창에 입력한 값
		String srchStr = request.getParameter("srchStr");
		
		//3. 로직 - 상세 정보 조회
		CafeService service = new CafeService();
		ArrayList<Cafe> list = service.srchCafe(srchStr);
		
		//4. 결과 처리
			//4.1. 이동할 jsp 페이지 경로 지정
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/cafe/cafeList.jsp");
			//4.2. 화면 구현에 필요한 데이터 등록 (카페 정보 리스트)
		request.setAttribute("list", list);
		request.setAttribute("srchStr", srchStr);
			//4.3. 페이지 이동
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
