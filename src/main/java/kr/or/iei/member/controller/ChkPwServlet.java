package kr.or.iei.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.Login.model.vo.Login;
import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class ChkPwServlet
 */
@WebServlet("/myPage/chkPw")
public class ChkPwServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChkPwServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 이 페이지는 POST 전용입니다
		response.sendRedirect(request.getContextPath() + "/myPage/chkPwFrm");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		// loginCafe 세션이 존재하고, 아직 loginMember 세션이 없다면
		if (session != null && session.getAttribute("loginCafe") != null && session.getAttribute("loginMember") == null) {
		    Login loginCafe = (Login) session.getAttribute("loginCafe");
		    String userId = loginCafe.getLoginId();

		    Member member = new MemberService().selectOneMember(userId);
		    session.setAttribute("loginMember", member);
		}
		
		Member loginMember = (Member) session.getAttribute("loginMember");

		if (loginMember == null) {
			response.sendRedirect("/loginFrm");
			return;
		}

		String userPw = request.getParameter("userPw");
		if(userPw != null) {
			userPw = userPw.trim();
		}
		String userId = loginMember.getUserId();

		MemberService service = new MemberService();
		Member checked = service.selectOneMember(userId, userPw);

		RequestDispatcher view = null;
		
		if (checked != null) {
			 // 비밀번호 확인 성공 시 → 계정관리 탭으로 진입
			if(loginMember.getUserRole()==2) {
				response.sendRedirect(request.getContextPath() + "/myPage/myInfo?tab=account");
			}else if(loginMember.getUserRole()==3) {
				response.sendRedirect(request.getContextPath() + "/myPage/myInfo?tab=account");
			}else if(loginMember.getUserRole()==1) { // 수정 : 관리자일 경우 리다이렉트 주소 추가
				response.sendRedirect(request.getContextPath() + "/myPage/myInfo?tab=account");
			}
		} else {
			request.setAttribute("title", "비밀번호 오류");
			request.setAttribute("msg", "비밀번호가 일치하지 않습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/myPage/chkPwFrm");
			view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			view.forward(request, response);
		}
	}

}
