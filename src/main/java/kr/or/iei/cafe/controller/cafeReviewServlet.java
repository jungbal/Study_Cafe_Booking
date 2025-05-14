package kr.or.iei.cafe.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.cafe.model.dao.CafeDao;
import kr.or.iei.cafe.model.service.CafeService;
import kr.or.iei.cafe.model.service.CommentService;
import kr.or.iei.cafe.model.vo.Comment;

/**
 * Servlet implementation class cafeReview
 */
@WebServlet("/cafeDetail/review")
public class cafeReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cafeReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 인코딩 - 필터 : x
		// 2. 값 추출 : cafeNo, RVQA(dao의 sql문에서 리뷰인지 Q&A 인지 구분하기 위해 RV 값을 담아 전달)
		String cafeNo = request.getParameter("cafeNo");
		String RVQA = request.getParameter("RVQA");
		// 3. 로직 - cafeNo로 리뷰 조회 (select * from tbl_comment where comment_cafe_no = ?)
		CommentService service = new CommentService();
		ArrayList<Comment> reviewList = service.selectComment(cafeNo, RVQA);
		// 4. 결과 처리
		
			// 4.1. 이동할 JSP 페이지 지정
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/cafe/cafeReview.jsp");
			// 4.2. 화면 구현에 필요한 데이터 등록 
		request.setAttribute("reviewList", reviewList); 
		
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
