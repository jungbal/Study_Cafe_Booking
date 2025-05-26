package kr.or.iei.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.member.model.service.MemberService;

/**
 * Servlet implementation class SubmitReportServlet
 */
@WebServlet("/cafeDetail/review/submitReport")
public class SubmitReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 값 추출 : reporterId(신고하는 사람 id), commentId(신고당하는 댓글/답글의 id), reasonCode(신고자가 선택한 신고 사유 코드id)
		String reporterId = request.getParameter("reporterId");
		String commentId = request.getParameter("commentId");
		String reasonCode = request.getParameter("reasonCode");
		
		// 2. 로직 : tbl_user_report로 insert 하기
		MemberService memberService = new MemberService();
		int result = memberService.insertReport(reporterId, commentId, reasonCode); // insertReport : reportId, commentId, reasonCode로 tbl_user_report로 insert 하기
		
		// 결과 처리
		response.setContentType("application/json;charset=UTF-8");
	    PrintWriter out = response.getWriter();

	    if(result > 0) {
	        out.print("{\"success\": true, \"message\": \"신고 완료되었습니다.\"}");
	    } else {
	        out.print("{\"success\": false, \"message\": \"신고 중 오류가 발생했습니다.\"}");
	    }
	    out.flush();
	    out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
