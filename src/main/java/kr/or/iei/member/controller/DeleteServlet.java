package kr.or.iei.member.controller;

import java.io.File;
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
//이정원 - 마이페이지 회원 탈퇴
@WebServlet("/member/delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		// 1. 세션에서 사용자 정보 가져오기 (파일 경로 확인용)
		HttpSession session = request.getSession(false);
		Member loginMember = (session != null) ? (Member) session.getAttribute("loginMember") : null;
		Login loginCafe = (session != null) ? (Login) session.getAttribute("loginCafe") : null;
		
		String userId = null;
		String userImage = null;
		
		MemberService service = new MemberService();
		
		if (loginMember != null) {
			userId = loginMember.getUserId();
			userImage = loginMember.getUserImage();
		} else if (loginCafe != null) {
			userId = loginCafe.getLoginId();
			// 필요하다면 loginCafe의 이미지 경로도 추출할 수 있도록 DB에서 Member 조회
			Member member = service.selectOneMember(userId);
			if (member != null) {
				userImage = member.getUserImage();
			}
		}

		// 2. DB에서 회원 삭제
		int result = service.deleteOneUser(userId);

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");

		if (result > 0) {
			// 3. 프로필 이미지 파일 삭제
			if (userImage != null && !userImage.trim().equals("")) {
				String rootPath = request.getServletContext().getRealPath("/");
				File profileImgFile = new File(rootPath + "resources/upload/" + userImage);
				if (profileImgFile.exists()) {
					boolean deleted = profileImgFile.delete();
					System.out.println("[이미지 삭제 여부] " + deleted);
				}
			}

			// 4. 세션 파기
			if (session != null) {
				session.invalidate();
			}

			// 5. 성공 메시지
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "회원 탈퇴가 완료되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/loginFrm");

		} else {
			// 실패 메시지
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "회원 탈퇴 중 오류가 발생하였습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/myPage/myInfo");
		}

		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
