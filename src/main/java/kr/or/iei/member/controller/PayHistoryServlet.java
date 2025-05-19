package kr.or.iei.member.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.member.model.service.PayService;
import kr.or.iei.member.model.vo.Member;
import kr.or.iei.member.model.vo.PayHistory;

/**
 * Servlet implementation class PayHistoryServlet
 */
@WebServlet("/myPage/payHistory")
public class PayHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayHistoryServlet() {
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
			response.sendRedirect("/member/loginFrm");
			return;
		}

		String userId = loginMember.getUserId();
		PayService service = new PayService();
		ArrayList<PayHistory> list = service.selectPayHistoryByUser(userId);

		session.setAttribute("payList", list); //(JSP에서 sessionScope로 접근하게 함)
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/member/myPay.jsp");
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
