package kr.or.iei.comment.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.comment.model.service.CommentService;
import kr.or.iei.comment.model.vo.Comment;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class CommentListServlet
 */
@WebServlet("/myPage/reply")
public class CommentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Member loginMember = (Member) session.getAttribute("loginMember");

		if (loginMember == null) {
			response.sendRedirect(request.getContextPath() + "/member/loginFrm");
			return;
		}

		String userId = loginMember.getUserId();
		String type = request.getParameter("type"); // "RVW" 또는 "QNA"
		CommentService service = new CommentService();
		
		// 리뷰와 Q&A 둘 다 가져오기
		List<Comment> reviewList = service.selectMyCommentsByType(userId, "RV");
		List<Comment> qnaList = service.selectMyCommentsByType(userId, "QNA");

		// 넘겨주기
		request.setAttribute("reviewList", reviewList);
		request.setAttribute("qnaList", qnaList);

		// 통합된 jsp 사용 (리뷰/Q&A 동일 페이지에서 분기)
		request.getRequestDispatcher("/WEB-INF/views/member/reviewQa.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
