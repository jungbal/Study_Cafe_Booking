package kr.or.iei.member.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.or.iei.cafe.model.vo.Code;
import kr.or.iei.cafe.model.vo.Comment;
import kr.or.iei.member.model.service.MemberService;

/**
 * Servlet implementation class ReportReviewServlet
 */
@WebServlet("/cafeDetail/comment/report")
public class ReportCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String commentId = request.getParameter("commentId");
		String reporterId = request.getParameter("reporterId"); // 신고하는 사람 ID
		
		// 로직 : 
		//1. commentId 로 comment 테이블에서 select 해오기
		MemberService service = new MemberService();
		Comment comment = service.selectCommentById(commentId);
		
		
		//2. 신고 코드 조회
		ArrayList<Code> codeList = service.selectReportCodeById();
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/cafe/commentReport.jsp");
		request.setAttribute("comment", comment);
		request.setAttribute("codeList", codeList);
		
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
