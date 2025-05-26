package kr.or.iei.cafe.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.cafe.model.service.CommentService;
import kr.or.iei.cafe.model.vo.Comment;

/**
 * Servlet implementation class DeleteCommentServlet
 */
@WebServlet("/cafeDetail/review/deleteComment")
public class DeleteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 값 추출 : cafeNo(해당 카페 번호), commnetId (삭제할 댓글 번호)
		String cafeNo = request.getParameter("cafeNo");
		String commentId = request.getParameter("commentId");
		
		CommentService commentService = new CommentService();
		int result = commentService.deleteComment(commentId); // deleteComment : commentId로 해당 댓글 삭제
		
		//4.결과 처리
        //4.1 이동할 페이지 경로 지정
     RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
        //4.2 화면 구현에 필요한 데이터 등록
     if(result>0) {
        request.setAttribute("title", "성공");
        request.setAttribute("msg", "삭제 완료되었습니다");
        request.setAttribute("icon", "success");
        
     }else {
        request.setAttribute("title", "실패");
        request.setAttribute("msg", "삭제 중 오류가 발생했습니다");
        request.setAttribute("icon", "error");
        
     }
     request.setAttribute("loc", "/cafeDetail?cafeNo=" + cafeNo+"&tab=review"); // 성공 or 실패했을 경우, 메시지 출력 후 다시 리뷰 탭으로 이동됨
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
