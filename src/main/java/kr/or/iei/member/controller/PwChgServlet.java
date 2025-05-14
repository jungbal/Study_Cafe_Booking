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
 * Servlet implementation class PwChgServlet
 */
@WebServlet("/member/pwChg")
public class PwChgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PwChgServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 인코딩 - 필터
		//2. 값 추출
		String memberPw = request.getParameter("memberPw");			//현재 비밀번호
		String newMemberPw = request.getParameter("newMemberPw");	//새 비밀번호
		
		//3. 로직
		
		HttpSession session = request.getSession(false); //세션 객체가 존재하면, 존재하는 객체 반환. 없으면 null 반환
		if(session != null) {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			
			//로그인 회원 정보
			Member loginMember = (Member) session.getAttribute("loginMember");
			
			//사용자가 입력한 현재 비밀번호와 세션에 등록된 회원의 비밀번호 다른 경우
			if(!loginMember.getUserPw().equals(memberPw)) {
				request.setAttribute("title", "알림");
				request.setAttribute("msg", "현재 비밀번호가 일치하지 않습니다.");
				request.setAttribute("icon", "warning");
				request.setAttribute("callback", "self.close();");
				
				view.forward(request, response);
				return;
			}
			
			MemberService service = new MemberService();
			int result = service.updateMemberPw(loginMember.getUserId(), newMemberPw);
			
			//4. 결과 처리
			if(result > 0) {
				request.setAttribute("title", "성공");
				request.setAttribute("msg", "비밀번호가 변경되었습니다. 변경된 비밀번호로 다시 로그인하세요.");
				request.setAttribute("icon", "success");
				
				//비밀번호 변경 성공 시, 팝업창 닫고 부모창에서 로그인 페이지 이동 처리
				request.setAttribute("callback", "self.close(); window.opener.location.href=\"/member/loginFrm\";");
				
				//다시 로그인할 수 있도록 세션 파기
				session.invalidate();
				
				
			}else {
				request.setAttribute("title", "실패");
				request.setAttribute("msg", "비밀번호 변경 처리 중, 오류가 발생하였습니다.");
				request.setAttribute("icon", "error");
				request.setAttribute("callback", "self.close();");
			}
			
			view.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
