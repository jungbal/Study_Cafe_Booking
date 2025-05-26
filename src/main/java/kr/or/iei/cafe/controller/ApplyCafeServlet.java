package kr.or.iei.cafe.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.Login.model.vo.Login;
import kr.or.iei.cafe.model.service.CafeService;
import kr.or.iei.cafe.model.vo.Cafe;

/**
 * Servlet implementation class ApplyCafeServlet
 */
@WebServlet("/ApplyCafeServlet")
public class ApplyCafeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyCafeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		업체 등록 jsp에서 사용자 input태그 입력값을 가져옴
		String cafeName = request.getParameter("cafeName");
		String cafeAddr = request.getParameter("cafeAddr");
		String cafePhone = request.getParameter("cafePhone");
		String cafeBiznum = request.getParameter("cafeBiznum");
		String cafeIntroduce = request.getParameter("cafeIntroduce");
		String cafeIntroDetail = request.getParameter("cafeIntroDetail");
		String cafeStartHour = request.getParameter("cafeStartHour");
		String cafeEndHour = request.getParameter("cafeEndHour");
		
//		cafeInfo 객체에 입력값 저장
		Cafe cafeInfo = new Cafe();
		
		cafeInfo.setCafeName(cafeName);
		cafeInfo.setCafeAddr(cafeAddr);
		cafeInfo.setCafePhone(cafePhone);
		cafeInfo.setCafeBiznum(cafeBiznum);
		cafeInfo.setCafeIntroduce(cafeIntroduce);
		cafeInfo.setCafeIntroDetail(cafeIntroDetail);
		cafeInfo.setCafeStartHour(cafeStartHour);
		cafeInfo.setCafeEndHour(cafeEndHour);
		
//		쿼리문에 필요한 식별값인 loginId를 세션에서 가져옴
	   HttpSession session = request.getSession();
	   Login loginCafe = (Login) session.getAttribute("loginCafe");
	   String loginId = loginCafe.getLoginId();

//	   업체 정보가 DB에 입력되도록 업체정보 입력 메소드 호출 
		CafeService service = new CafeService();
		int result = service.insertCafe(cafeInfo, loginId);
		
//		
		CafeService cafeservice = new CafeService();
		String hostId = cafeservice.matchHostId(loginId);
		 session.setAttribute("hostId", hostId);

		RequestDispatcher view = null;
		if(result > 0) { // 업체 신청이 됐을 때
			view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "업체(호스트) 신청이 완료되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/main");
			
		}else { // 업체 신청이 안됐을 때
			view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "오류가 발생했습니다. 다시 신청해주세요.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/applyCafe");
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
