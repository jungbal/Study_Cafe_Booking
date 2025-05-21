package kr.or.iei.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/member/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 인코딩
		request.setCharacterEncoding("UTF-8");

		// 2. 값 추출
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");

		// 3. 비즈니스 로직 - 로그인 처리
		MemberService service = new MemberService();
		Member loginMember = service.selectOneMember(userId, userPw);

		// 4. 결과 처리
		
		RequestDispatcher view = null;
		
		if (loginMember != null) {
			// 로그인 성공 → 세션에 등록
			HttpSession session = request.getSession();
			session.setAttribute("loginMember", loginMember);
			//response.sendRedirect(request.getContextPath() + "/index.jsp");
			response.sendRedirect(request.getContextPath() + "/main"); // 수정 (김은서)
		} else {
			// 로그인 실패
			view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			request.setAttribute("title", "로그인 실패");
			request.setAttribute("msg", "아이디 또는 비밀번호를 확인해주세요.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/loginFrm");
			view.forward(request, response);
		}
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
