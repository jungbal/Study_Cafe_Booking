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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Member loginMember = (Member) session.getAttribute("loginMember");

		if (loginMember == null) {
			response.sendRedirect(request.getContextPath() + "/member/loginFrm");
			return;
		}

		String userId = loginMember.getUserId();
		String type = request.getParameter("type"); // "RVW" 또는 "QNA"

		if (type == null || (!type.equals("RV") && !type.equals("QNA"))) {
			request.setAttribute("title", "잘못된 접근");
			request.setAttribute("msg", "리뷰 또는 Q&A 유형이 올바르지 않습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			return;
		}

		CommentService service = new CommentService();
		List<Comment> commentList = service.selectMyCommentsByType(userId, type);

		request.setAttribute("commentList", commentList);

		String targetPage = type.equals("RV") ? "/WEB-INF/views/member/myReview.jsp"
		                                       : "/WEB-INF/views/member/myQa.jsp";

		request.getRequestDispatcher(targetPage).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
