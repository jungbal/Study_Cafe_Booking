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

import com.oreilly.servlet.MultipartRequest;

import kr.or.iei.common.KhRenamePolicy;
import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class ProfileImgUpdateServlet
 */
@WebServlet("/mypage/updateProfileImg")
public class ProfileImgUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileImgUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String toDay = new SimpleDateFormat("yyyyMMdd").format(new Date());
	      String rootPath = request.getSession().getServletContext().getRealPath("/");
	      String savePath = rootPath + "resources/upload/" + toDay + "/";
	      int maxSize = 1024 * 1024 * 10; // 10MB

	      File dir = new File(savePath);
	      if (!dir.exists()) {
	         dir.mkdirs();
	      }

	      MultipartRequest mRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new KhRenamePolicy());

	      String userId = mRequest.getParameter("userId");
	      String oriName = mRequest.getOriginalFileName("profileImg"); // form의 input name="profileImg"
	      String fileName = mRequest.getFilesystemName("profileImg");

	      String filePath = null;
	      if (fileName != null) {
	         filePath = toDay + "/" + fileName;
	      }


	      // DB에 경로 업데이트
	      MemberService service = new MemberService();
	      int result = service.updateProfileImg(userId, filePath);

	      // 결과 처리
	      RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
	      if (result > 0) {
	         request.setAttribute("title", "성공");
	         request.setAttribute("msg", "프로필 이미지가 변경되었습니다.");
	         request.setAttribute("icon", "success");

	         // 세션 갱신
	         Member loginMember = service.selectOneMember(userId);
	         request.getSession().setAttribute("loginMember", loginMember);
	         request.setAttribute("loc", "/myPage/myInfo");

	      } else {
	         request.setAttribute("title", "실패");
	         request.setAttribute("msg", "이미지 변경 중 오류 발생");
	         request.setAttribute("icon", "error");
	         request.setAttribute("loc", "/myPage/myInfo");
	      }

	      view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
