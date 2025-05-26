package kr.or.iei.cafe.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.cafe.model.service.CafeService;
import kr.or.iei.cafe.model.service.TicketService;
import kr.or.iei.cafe.model.vo.Cafe;
import kr.or.iei.cafe.model.vo.Ticket;

/**
 * Servlet implementation class PayInfoServlet
 */
@WebServlet("/payInfo")
public class PayInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PayInfoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 파라미터 추출 : cafeNo, ticketId, seatNo를 request에서 받음.
		String cafeNo = request.getParameter("cafeNo");
		String ticketId = request.getParameter("ticketId");
		String seatNo = request.getParameter("seatNo");

		// 필수 파라미터 체크 : 카페번호, 이용권 아이디, 좌석번호 중 하나라도 값이 전달이 안 됐을 경우 error 처리
		if (cafeNo == null || ticketId == null || seatNo == null) {
			response.sendRedirect("/errorPage.jsp");
			return;
		}

		// 2. 비즈니스 로직
		CafeService cafeService = new CafeService();
		Cafe cafe = cafeService.selectCafeByNo(cafeNo); // selectCafeByNo : cafeNo로 해당 카페 객체를 가져온다

		TicketService ticketService = new TicketService();
		Ticket ticket = ticketService.selectOneTicket(ticketId); //selectOneTicket : ticketId로 해당 이용권의 객체 가져온다

		// 3. 화면 처리 : /WEB-INF/views/cafe/payInfo.jsp로 포워딩하면서 cafe, seatNo, ticket을 전달.
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/cafe/payInfo.jsp");
		request.setAttribute("cafe", cafe);
		request.setAttribute("seatNo", seatNo);
		request.setAttribute("ticket", ticket);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
