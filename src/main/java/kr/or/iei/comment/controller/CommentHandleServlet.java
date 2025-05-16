package kr.or.iei.comment.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import kr.or.iei.comment.model.service.CommentService;
import kr.or.iei.comment.model.vo.Comment;

@WebServlet("/comment/handle")
public class CommentHandleServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private CommentService service = new CommentService();

  // 수정폼 표시, 삭제 처리
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String action    = request.getParameter("action");
    String commentId = request.getParameter("commentId");
    String type      = request.getParameter("type"); // RV 또는 QNA

    if ("update".equals(action)) {
      // 1) 수정 폼
      Comment c = service.selectCommentById(commentId);
      request.setAttribute("comment", c);
      request.setAttribute("type", type);
      request.getRequestDispatcher("/WEB-INF/views/member/commentUpdate.jsp")
             .forward(request, response);
    }
    else if ("delete".equals(action)) {
      // 2) 삭제 후 내 리뷰/Q&A로 복귀
      int result = service.deleteComment(commentId);
      request.setAttribute("title", result>0?"삭제 성공":"삭제 실패");
      request.setAttribute("msg",   result>0?"댓글이 삭제되었습니다.":"삭제 중 오류가 발생했습니다.");
      request.setAttribute("icon",  result>0?"success":"error");
      request.setAttribute("loc", request.getContextPath()+"/myPage/myInfo?tab=review");
      request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp")
             .forward(request, response);
    }
    else {
      response.sendError(404);
    }
  }

  // 수정 폼에서 POST 전송된 데이터 업데이트
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String action    = request.getParameter("action");
    String commentId = request.getParameter("commentId");
    String content   = request.getParameter("content");
    String type      = request.getParameter("type");

    if ("update".equals(action)) {
      int result = service.updateComment(commentId, content);
      request.setAttribute("title", result>0?"수정 성공":"수정 실패");
      request.setAttribute("msg",   result>0?"댓글이 수정되었습니다.":"수정 중 오류가 발생했습니다.");
      request.setAttribute("icon",  result>0?"success":"error");
      request.setAttribute("loc", request.getContextPath()+"/myPage/myInfo?tab=review");
      request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp")
             .forward(request, response);
    }
    else {
      response.sendError(405);
    }
  }
}
