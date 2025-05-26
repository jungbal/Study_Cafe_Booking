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
import kr.or.iei.cafe.model.service.CommentService;
import kr.or.iei.cafe.model.vo.Cafe;
import kr.or.iei.cafe.model.vo.Comment;

/**
 * Servlet implementation class cafeQA
 */
@WebServlet("/cafeDetail/qna")
public class CafeQAServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CafeQAServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 인코딩 - 필터 : x
		// 2. 값 추출 : cafeNo, RVQA(dao의 sql문에서 리뷰인지 Q&A 인지 구분하기 위해 QA 값을 담아 전달)
		String cafeNo = request.getParameter("cafeNo");
		String RVQA = request.getParameter("RVQA");
		// 3. 로직 - cafeNo로 Q&A 리스트 조회
		CommentService service = new CommentService();
		ArrayList<Comment> qaList = service.selectComment(cafeNo, RVQA);
		
		// hostId값 가져오기 위해서 cafe 객체 가져오기
		CafeService cafeService = new CafeService();
		Cafe cafe = cafeService.selectCafeByNo(cafeNo);
		// 4. 결과 처리
			// 4.1. 이동할 JSP 페이지 지정
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/cafe/cafeQA.jsp");
			// 4.2. 화면 구현에 필요한 데이터 등록
		request.setAttribute("qaList", qaList); 	
		request.setAttribute("cafe", cafe);
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
