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
 * Servlet implementation class cafeDetail
 */
@WebServlet("/cafeDetail")
public class cafeDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cafeDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 값 추출 : 카페 번호
	    String cafeNo = request.getParameter("cafeNo");

	    // 2. 로직 : 카페 정보 가져오기, 이용권 정보 가져오기.
	    CafeService service = new CafeService();
	    Cafe cafe = service.selectcafeNo(cafeNo);
	    
	    TicketService ticketService = new TicketService();
	    ArrayList<Ticket> ticketList = ticketService.selectCafeTicket(cafeNo);

	    // 3. 결과 처리
	    request.setAttribute("cafe", cafe); 
	    request.setAttribute("ticketList", ticketList);
	    

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
