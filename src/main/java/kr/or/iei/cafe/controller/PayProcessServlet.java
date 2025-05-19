package kr.or.iei.cafe.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.cafe.model.service.CafeService;
import kr.or.iei.cafe.model.vo.Ticket;

/**
 * Servlet implementation class Pay
 */
@WebServlet("/payInfo/payProcess")
public class PayProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayProcessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 인코딩 - 필터 : x
		// 2. 값 추출 : cafeNo
		
		String ticketId = request.getParameter("ticketId");
		String ticketPrice = request.getParameter("ticketPrice");
		String ticketHour = request.getParameter("ticketHour");
		String cafeNo = request.getParameter("cafeNo");
		String seatNo = request.getParameter("seatNo");
		String userId = request.getParameter("userId");
		
		
		CafeService cafeService = new CafeService();
		int result = cafeService.insertPayAndHistory(ticketId, ticketPrice, ticketHour, cafeNo, seatNo, userId);
		
		
		// 결과 처리
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		
		if (result > 0) { // 성공
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "결제가 완료되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/cafeDetail?cafeNo=" + cafeNo); // 임시 페이지 이동 처리 (마이페이지 결제내역으로 갈 것임)

		} else { // 실패
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "결제 중 오류가 발생하였습니다");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/cafeDetail?cafeNo=" + cafeNo);
		}
			//4.3. 페이지 이동
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
