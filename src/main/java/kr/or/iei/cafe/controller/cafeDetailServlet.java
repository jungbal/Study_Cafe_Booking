package kr.or.iei.cafe.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.Login.model.vo.Login;
import kr.or.iei.cafe.model.service.CafeService;
import kr.or.iei.cafe.model.service.TicketService;
import kr.or.iei.cafe.model.vo.Cafe;
import kr.or.iei.cafe.model.vo.History;
import kr.or.iei.cafe.model.vo.Ticket;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class cafeDetail
 */
@WebServlet("/cafeDetail")
public class cafeDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 1. 값 추출 : cafeList.jsp에서 request로 보낸 카페 번호
	    String cafeNo = request.getParameter("cafeNo");

	    // 2. 서비스 객체 생성
	    CafeService service = new CafeService();
	    TicketService ticketService = new TicketService();

	    // 3. 카페 정보 가져오기
	    Cafe cafe = service.selectCafeByNo(cafeNo); // selectCafeByNo: cafe_no로 Cafe 객체를 가져옴

	    // 4. 이용권 정보 가져오기
	    ArrayList<Ticket> ticketList = ticketService.selectCafeTicket(cafeNo); // selectCafeTicket: cafeNo로 해당 카페의 이용권 리스트를 가져옴

	    // 5. 현재 시간 기준으로 사용중인 좌석 리스트 조회
	    ArrayList<History> seatList = service.isSeatAvailable(cafeNo); // isSeatAvailable: cafeNo로 해당 카페에서 현재 이용상태인 좌석 번호 조회해서 리스트로 가져옴

	    // 6. 로그인 세션에서 로그인 회원 정보 가져오기
	    HttpSession session = request.getSession(false);
	    History history = null;

	    if (session != null) {
	        Login loginCafe = (Login) session.getAttribute("loginCafe");
	        if (loginCafe != null) {
	            String loginId = loginCafe.getLoginId();
	            // 로그인한 회원이 현재 좌석 사용중인지 조회
	            history = service.isUserAvailable(loginId);
	        }
	    }

	    // 7. 결과 request에 세팅
	    request.setAttribute("cafe", cafe);
	    request.setAttribute("ticketList", ticketList);
	    request.setAttribute("seatList", seatList);
	    request.setAttribute("currentUsage", history); // JSP에서 currentUsage로 받도록 수정 권장

	    // 8. JSP 포워드
	    RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/cafe/cafeDetail.jsp");
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
