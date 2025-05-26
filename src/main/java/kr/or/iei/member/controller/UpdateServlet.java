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

import com.oreilly.servlet.MultipartRequest;

import kr.or.iei.Login.model.vo.Login;
import kr.or.iei.common.KhRenamePolicy;
import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;

@WebServlet("/member/update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 파일 업로드 경로 설정
		String basePath = getServletContext().getRealPath("/resources/upload"); // resources/upload 실제 서버 경로
		String today = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()); // 오늘 날짜 yyyymmdd 형식
		String savePath = basePath + File.separator + today; //최종 저장 경로(폴더별로 날짜 구분)
		File folder = new File(savePath);
		if (!folder.exists()) {
			folder.mkdirs(); // 폴더 없으면 생성
		}

		// MultipartRequest 객체 생성 (cos.jar 사용, 파일 업로드 처리)
		int maxSize = 10 * 1024 * 1024; // 최대 업로드 크기 : 10MB
		MultipartRequest mRequest = new MultipartRequest(
			request,
			savePath,
			maxSize,
			"UTF-8",
			new KhRenamePolicy() // 또는 파일명 리네임 정책
		);

		// 데이터 추출
		String userId = mRequest.getParameter("userId");
		String userPw = mRequest.getParameter("userPw");
		String userPhone = mRequest.getParameter("userPhone");
		
		

		String originalFile = mRequest.getFilesystemName("userImage"); // 업로드된 파일명
		String userImage = (originalFile != null) ? today + "/" + originalFile : null; //업로드된 파일 있으면 경로 포함, 없으면 null

		// 기존 로그인 정보 가져오기
		HttpSession session = request.getSession(false);
		
		// loginCafe 세션이 존재하고, 아직 loginMember 세션이 없다면, loginMember 세팅
		if (session != null && session.getAttribute("loginCafe") != null && session.getAttribute("loginMember") == null) {
		    Login loginCafe = (Login) session.getAttribute("loginCafe");
		    String loginId = loginCafe.getLoginId();

		    Member member = new MemberService().selectOneMember(loginId);
		    session.setAttribute("loginMember", member);
		}
		Member loginMember = (Member) session.getAttribute("loginMember");

		// 기존 이미지가 있으면 서버에서 삭제
		if (originalFile != null && loginMember.getUserImage() != null) {
		    File delFile = new File(basePath + File.separator + loginMember.getUserImage());
		    if (delFile.exists()) {
		        delFile.delete();
		    }
		}
		
		// 이미지 파일 선택 안 했으면 기존 이미지 사용
		if (userImage == null) {
			userImage = loginMember.getUserImage();
		}
		
		// 비밀번호가 null이면 기존 값 유지
		if (userPw == null || userPw.trim().equals("")) {
		    userPw = loginMember.getUserPw();
		}

		// VO 객체에 데이터 세팅
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPw(userPw);
		m.setUserPhone(userPhone);
		m.setUserImage(userImage);

		// 서비스 호출
		MemberService service = new MemberService();
		int result = service.updateMember(m);

		// 결과 처리
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		//성공시
		if (result > 0) {
			request.setAttribute("title", "수정 완료");
			request.setAttribute("msg", "회원정보가 성공적으로 수정되었습니다.");
			request.setAttribute("icon", "success");

			// 세션의 회원정보 갱신
			loginMember.setUserPw(userPw);
			loginMember.setUserPhone(userPhone);
			loginMember.setUserImage(userImage);
			
		} else { //실패시
			request.setAttribute("title", "수정 실패");
			request.setAttribute("msg", "정보 수정 중 오류가 발생했습니다.");
			request.setAttribute("icon", "error");
		}
		
		//공통 이동 경로
		request.setAttribute("loc", "/myPage/myInfo?tab=account");
		view.forward(request, response);
	}
}
