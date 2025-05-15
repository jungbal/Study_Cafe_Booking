package kr.or.iei.comment.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.comment.model.service.CommentService;

/**
 * Servlet implementation class CommentHandleServlet
 */
@WebServlet("/comment/handle")
public class CommentHandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentHandleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");           // "update" 또는 "delete"
        String commentId = request.getParameter("commentId");
        CommentService service = new CommentService();
        int result = 0;

        if ("update".equals(action)) {
            String content = request.getParameter("content");
            result = service.updateComment(commentId, content);

            request.setAttribute("title", result > 0 ? "수정 성공" : "수정 실패");
            request.setAttribute("msg", result > 0 ? "댓글이 수정되었습니다." : "수정 중 오류가 발생했습니다.");
            request.setAttribute("icon", result > 0 ? "success" : "error");

        } else if ("delete".equals(action)) {
            result = service.deleteComment(commentId);

            request.setAttribute("title", result > 0 ? "삭제 성공" : "삭제 실패");
            request.setAttribute("msg", result > 0 ? "댓글이 삭제되었습니다." : "삭제 중 오류가 발생했습니다.");
            request.setAttribute("icon", result > 0 ? "success" : "error");
        }

        // ★ 현재 페이지로 돌아가기
        String referer = request.getHeader("referer");
        request.setAttribute("loc", referer);

        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
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
