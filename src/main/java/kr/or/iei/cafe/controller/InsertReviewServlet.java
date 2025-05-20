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
 * Servlet implementation class UpdateComment
 */
@WebServlet("/cafeDetail/review/updateComment") 
public class InsertReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 인코딩 - 필터 : x
		// 2. 값 추출 : 
		String cafeNo = request.getParameter("cafeNo");
	    String writerId = request.getParameter("writerId");
	    String content = request.getParameter("content");
	    String parentId = request.getParameter("parentId"); // 답글일 경우만 존재
	    String RVQA = "RV"; // 리뷰 타입이라고 알려주기 위한 변수 설정
		
	   
	    
	    // 3. 로직 
	    Comment comment = new Comment();
	    comment.setCommentCafeNo(cafeNo);
	    comment.setCommentUserId(writerId);
	    comment.setContent(content);
	    
	    if (parentId != null && !parentId.isEmpty()) {
	        comment.setCommentParent(parentId);
	    }
		
	    int result = new CommentService().insertComment(comment, RVQA);
		
	  //4.결과 처리
        //4.1 이동할 페이지 경로 지정
     RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
        //4.2 화면 구현에 필요한 데이터 등록
     if(result > 0) {
        request.setAttribute("title", "성공");
        request.setAttribute("msg", "작성 완료되었습니다");
        request.setAttribute("icon", "success");
        
     }else {
        request.setAttribute("title", "실패");
        request.setAttribute("msg", "작성 중 오류가 발생했습니다");
        request.setAttribute("icon", "error");
        
     }
     
     request.setAttribute("loc", "/cafeDetail?cafeNo=" + cafeNo+"&tab=review");
     //4.3 페이지 이동
     view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
