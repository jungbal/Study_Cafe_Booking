package kr.or.iei.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.common.ListData;
import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.MemberReport;

/**
 * Servlet implementation class UserReportServlet
 */
@WebServlet("/manager/chkReport")
public class UserReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 인코딩
		//2. 값 추출
		int reqPage = Integer.parseInt(request.getParameter("reqPage"));
		//3. 로직
		MemberService service = new MemberService();
		ListData<MemberReport> reportList = service.selectReportList(reqPage);
		//4. 결과 처리
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/manager/manageReport.jsp");
		
		request.setAttribute("reportList", reportList.getList());
		request.setAttribute("pageNavi", reportList.getPageNavi());
		
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
