package kr.or.iei.member.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

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

		// 1. 파일 업로드 경로 설정
		String savePath = getServletContext().getRealPath("/resources/upload");
		File folder = new File(savePath);
		if (!folder.exists()) {
			folder.mkdirs(); // 폴더 없으면 생성
		}

		// 2. MultipartRequest 객체 생성 (cos.jar 사용)
		int maxSize = 10 * 1024 * 1024; // 10MB
		MultipartRequest mRequest = new MultipartRequest(
			request,
			savePath,
			maxSize,
			"UTF-8",
			new KhRenamePolicy() // 또는 DefaultFileRenamePolicy
		);

		// 3. 데이터 추출
		String userId = mRequest.getParameter("userId");
		String userPw = mRequest.getParameter("userPw");
		String userPhone = mRequest.getParameter("userPhone");
		
		

		// 4. 파일 이름 처리
		String userImage = mRequest.getFilesystemName("userImage");

		// 기존 로그인 정보
		HttpSession session = request.getSession(false);
		Member loginMember = (Member) session.getAttribute("loginMember");

		
		// 새 이미지 등록 시 기존 이미지 삭제
		if (userImage != null && loginMember.getUserImage() != null) {
		    File delFile = new File(savePath + "/" + loginMember.getUserImage());
		    if (delFile.exists()) {
		        delFile.delete(); // 기존 이미지 삭제
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

		// 5. VO 객체로 묶기
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPw(userPw);
		m.setUserPhone(userPhone);
		m.setUserImage(userImage);

		// 6. 서비스 호출
		int result = new MemberService().updateMember(m);

		// 7. 결과 처리
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		if (result > 0) {
			request.setAttribute("title", "수정 완료");
			request.setAttribute("msg", "회원정보가 성공적으로 수정되었습니다.");
			request.setAttribute("icon", "success");

			// 세션의 회원정보 갱신
			loginMember.setUserPw(userPw);
			loginMember.setUserPhone(userPhone);
			loginMember.setUserImage(userImage);

		} else {
			request.setAttribute("title", "수정 실패");
			request.setAttribute("msg", "정보 수정 중 오류가 발생했습니다.");
			request.setAttribute("icon", "error");
		}
		request.setAttribute("loc", "/myPage/myInfo?tab=account");
		view.forward(request, response);
	}
}
