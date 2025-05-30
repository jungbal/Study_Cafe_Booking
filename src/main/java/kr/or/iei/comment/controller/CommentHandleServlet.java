package kr.or.iei.comment.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import kr.or.iei.comment.model.service.CommentService;
import kr.or.iei.comment.model.vo.Comment;

// /comment/handle 경로로 들어오는 요청을 처리할 서블릿
@WebServlet("/comment/handle")
public class CommentHandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 댓글 관련 서비스 객체 생성 (비즈니스 로직 처리)
	private CommentService service = new CommentService();

	// GET 요청 처리: 수정폼 표시 또는 삭제 처리
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 요청 한글 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		
		// 요청 파라미터에서 action 값 추출 (update 또는 delete)
		String action = request.getParameter("action");
		
		// 요청 파라미터에서 commentId 값 추출 (수정/삭제 대상 댓글 ID)
		String commentId = request.getParameter("commentId");
		System.out.println("commentId = " + commentId);
		
		// 요청 파라미터에서 type 값 추출 (RV: 리뷰, QNA: 질문답변 구분용)
		String type = request.getParameter("type"); // RV 또는 QNA

		// action 값이 "update"일 때 → 수정 폼 보여주기
		if ("update".equals(action)) {
			// 해당 댓글 ID로 댓글 조회
			Comment c = service.selectCommentById(commentId);
			System.out.println("조회된 comment = " + c);
			
			// 조회된 댓글과 타입을 request에 담아서
			request.setAttribute("comment", c);
			request.setAttribute("type", type);
			
			// 수정폼 JSP로 forward 이동
			request.getRequestDispatcher("/WEB-INF/views/member/commentUpdate.jsp").forward(request, response);
			
			// action 값이 "delete"일 때 → 댓글 삭제 처리
		} else if ("delete".equals(action)) {
			// 댓글 삭제 서비스 호출
			int result = service.deleteComment(commentId);
			
			// 삭제 결과에 따라 alert 메시지 구성
			request.setAttribute("title", result > 0 ? "삭제 성공" : "삭제 실패");
			request.setAttribute("msg", result > 0 ? "댓글이 삭제되었습니다." : "삭제 중 오류가 발생했습니다.");
			request.setAttribute("icon", result > 0 ? "success" : "error");
			
			// 삭제 후 내 리뷰/Q&A 목록으로 이동 (탭은 review로 고정)
			request.setAttribute("loc", request.getContextPath() + "/myPage/myInfo?tab=review");
			// 메시지 JSP로 forward 이동
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			
			// 그 외 action 값 → 404 에러
		} else {
			response.sendError(404);
		}
	}

	// POST 요청 처리: 수정 폼에서 전송된 댓글 내용 업데이트
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 요청 한글 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		
		// 요청 파라미터 추출
		String action = request.getParameter("action");
		String commentId = request.getParameter("commentId");
		String content = request.getParameter("content");

		// action 값이 "update"일 때 → 댓글 업데이트 처리
		if ("update".equals(action)) {
			// 댓글 업데이트 서비스 호출
			int result = service.updateComment(commentId, content);

			// 수정 성공 시 → 부모창 새로고침하고 팝업창 닫기
			if (result > 0) {
				// 수정성공
				request.setAttribute("title", "수정 성공");
				request.setAttribute("msg", "댓글이 성공적으로 수정되었습니다.");
				request.setAttribute("icon", "success");
				
				// 자바스크립트로 부모창 새로고침 후 팝업창 닫기
				request.setAttribute("loc", "javascript:window.opener.location.reload();window.close();");
				
				request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
				
				// 수정 실패 → msg.jsp로 alert 띄우고 팝업창 닫기
			} else {
				request.setAttribute("title", "수정 실패");
				request.setAttribute("msg", "댓글 수정에 실패했습니다. 다시 시도해주세요.");
				request.setAttribute("icon", "error");
				
				// 팝업창만 닫기
				request.setAttribute("loc", "javascript:window.close();");
				request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			}
			// 그 외 action 값 → 405 메서드 에러
		} else {
			response.sendError(405);
		}
	}
}
