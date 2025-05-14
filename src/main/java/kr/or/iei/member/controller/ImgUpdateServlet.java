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

/**
 * Servlet implementation class ImgUpdateServlet
 */
@WebServlet("/member/updateImg")
public class ImgUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_PATH = "/upload/profile";
	private static final int MAX_SIZE = 100 * 1024 * 1024; // 10MB

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImgUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(" [ImgUpdateServlet] POST 요청 들어옴 (폼 제출 성공)");

		String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String root = request.getSession().getServletContext().getRealPath("/");
		String savePath = root + "upload/profile/" + today + "/";
		System.out.println("파일 저장 경로: " + savePath);

		int maxSize = 1024 * 1024 * 100;

		File dir = new File(savePath);
		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			System.out.println(" 디렉토리 생성됨: " + created);
		} else {
			System.out.println(" 디렉토리 이미 존재함");
		}

		MultipartRequest mRequest = null;
		try {
			mRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new KhRenamePolicy());
			System.out.println("MultipartRequest 생성 성공");
		} catch (Exception e) {
			System.out.println(" MultipartRequest 생성 실패");
			e.printStackTrace();
			response.sendRedirect("/member/myPage");
			return;
		}

		String filename = mRequest.getFilesystemName("profileImg");
		System.out.println("📸 업로드된 파일 이름: " + filename);

		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		MemberService service = new MemberService();

		if (filename != null) {
			String dbPath = "/upload/profile/" + today + "/" + filename;
			System.out.println(" DB에 저장될 경로: " + dbPath);

			int result = service.updateProfileImg(loginMember.getUserId(), dbPath);
			System.out.println(" DB 업데이트 결과: " + result);

			if (result > 0) {
				loginMember.setUserImage(dbPath);
				session.setAttribute("loginMember", loginMember);
				System.out.println(" 세션 userImage 경로 갱신됨");
			} else {
				System.out.println(" DB 업데이트 실패");
			}
		} else {
			System.out.println(" 파일이 업로드되지 않음 (filename == null)");
		}

		response.sendRedirect(request.getContextPath() + "/myPage/myInfo");
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
