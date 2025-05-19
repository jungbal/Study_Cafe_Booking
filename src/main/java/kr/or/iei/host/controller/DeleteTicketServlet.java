package kr.or.iei.host.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.cafe.model.vo.Ticket;
import kr.or.iei.host.model.service.HostService;

/**
 * Servlet implementation class DeleteTicketServlet
 */
@WebServlet("/productManage/deleteProduct")
public class DeleteTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteTicketServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ticketId = request.getParameter("ticketId");
		System.out.println("ticketId : "+ticketId);
		String ticketType = request.getParameter("ticketType");
		String ticketHour = request.getParameter("ticketHour");
		String ticketPrice = request.getParameter("ticketPrice");
		
		Ticket ticket = new Ticket(ticketId, ticketType, ticketHour, ticketPrice, null, null);
		
		HostService hostService = new HostService();
	    int result = hostService.deleteTicketById(ticket);
	    
	    RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
        //4.2 화면 구현에 필요한 데이터 등록
     if(result>0) {
        request.setAttribute("title", "성공");
        request.setAttribute("msg", "삭제 완료되었습니다");
        request.setAttribute("icon", "success");
        
     }else {
        request.setAttribute("title", "실패");
        request.setAttribute("msg", "삭제 중 오류가 발생했습니다");
        request.setAttribute("icon", "error");
        
     }
     request.setAttribute("loc", "/host/productManage");
     //4.3 페이지 이동
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
