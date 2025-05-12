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
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/myInfo/update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 세션에서 로그인한 사용자 정보 가져오기
		HttpSession session = request.getSession(false);
		Member loginMember = (Member) session.getAttribute("loginMember");

		// 클라이언트가 입력한 값 가져오기
		String userPhone = request.getParameter("userPhone");
		String userPw = request.getParameter("userPw");
		String userImage = request.getParameter("userImage");
		String userId = request.getParameter("userId");

		// 비밀번호가 입력되지 않은 경우 기존 값 유지
		if (userPw == null || userPw.trim().equals("")) {
			userPw = loginMember.getUserPw();
		}

		// 수정할 회원 정보 객체 생성
		Member updMember = new Member();
		updMember.setUserId(userId);
		updMember.setUserPhone(userPhone);
		updMember.setUserPw(userPw);
		updMember.setUserImage(userImage);

		// 서비스 호출
		MemberService service = new MemberService();
		int result = service.updateMember(updMember);

		// 메시지 페이지로 이동
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");

		if (result > 0) {
			// 성공 메시지
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "회원정보가 수정되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/myPage/myInfo");

			// 세션에 저장된 회원 정보도 갱신
			if (session != null) {
				loginMember.setUserPhone(userPhone);
				loginMember.setUserPw(userPw);
				loginMember.setUserImage(userImage);
			}
		} else {
			// 실패 메시지
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "회원 정보 수정 중, 오류가 발생하였습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/myPage/myInfo");
		}

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
