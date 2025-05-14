package kr.or.iei.member.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.or.iei.common.KhRenamePolicy;
import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;

@WebServlet("/myInfo/update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String toDay = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String rootPath = request.getServletContext().getRealPath("/");
		String savePath = rootPath + "resources/upload/" + toDay + "/";
		int maxSize = 1024 * 1024 * 10; // 10MB

		File dir = new File(savePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		MultipartRequest mRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new KhRenamePolicy());

		String userId = mRequest.getParameter("userId");
		String userPhone = mRequest.getParameter("userPhone");
		String originImage = mRequest.getParameter("originImage");

		// 새 이미지 처리
		String fileName = mRequest.getFilesystemName("profileImg");
		String filePath = (fileName != null) ? toDay + "/" + fileName : originImage;

		// 기존 이미지 삭제
		if (fileName != null && originImage != null && !originImage.equals("")) {
			File oldFile = new File(rootPath + "resources/upload/" + originImage);

			System.out.println("[삭제 시도] 경로: " + oldFile.getAbsolutePath());
			System.out.println("[삭제 시도] 존재 여부: " + oldFile.exists());

			if (oldFile.exists()) {
				boolean deleted = oldFile.delete();
				System.out.println("[삭제 결과] " + deleted);
			}
		}

		HttpSession session = request.getSession(false);
		Member loginMember = (Member) session.getAttribute("loginMember");

		Member updMember = new Member();
		updMember.setUserId(userId);
		updMember.setUserPhone(userPhone);
		updMember.setUserPw(loginMember.getUserPw()); // 비밀번호는 유지
		updMember.setUserImage(filePath);

		MemberService service = new MemberService();
		int result = service.updateMember(updMember);

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");

		if (result > 0) {
			request.setAttribute("title", "성공");
			request.setAttribute("msg", "회원 정보가 수정되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/myPage/myInfo");

			// 세션 갱신
			loginMember.setUserPhone(userPhone);
			loginMember.setUserImage(filePath);
		} else {
			request.setAttribute("title", "실패");
			request.setAttribute("msg", "회원 정보 수정 중 오류가 발생했습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/myPage/myInfo");
		}

		view.forward(request, response);
	}
}
