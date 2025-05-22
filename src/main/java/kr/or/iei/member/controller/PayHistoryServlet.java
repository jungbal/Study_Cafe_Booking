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

import kr.or.iei.Login.model.vo.Login;
import kr.or.iei.member.model.service.MemberService;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		// 세션이 없거나 loginUser가 없는 경우 로그인 페이지로 이동
		if (session == null || session.getAttribute("loginUser") == null) {
			response.sendRedirect(request.getContextPath() + "/loginFrm");
			return;
		}

		// loginUser에서 userId 추출 → loginMember 객체 갱신
		Login loginUser = (Login) session.getAttribute("loginUser");
		String userId = loginUser.getLoginId();

		MemberService memberService = new MemberService();
		Member member = memberService.selectOneMember(userId);

		if (member == null) {
			response.sendRedirect(request.getContextPath() + "/loginFrm");
			return;
		}

		session.setAttribute("loginMember", member); // 세션에 최신 member 저장
		
		//결재내역 조회
		PayService service = new PayService();
		ArrayList<PayHistory> list = service.selectPayHistoryByUser(userId);

		//세션에 결제 목록 저장
		session.setAttribute("payList", list); // (JSP에서 sessionScope로 접근하게 함)
		
		//jsp 포워딩
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/member/myPay.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
