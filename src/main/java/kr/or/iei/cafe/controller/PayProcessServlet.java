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
		// 2. 값 추출 : 
		
		String ticketId = request.getParameter("ticketId"); // (수정 사항) 티켓은 ticket 객체에 묶어서 보내기
		String ticketPrice = request.getParameter("ticketPrice");
		String ticketHour = request.getParameter("ticketHour");
		String cafeNo = request.getParameter("cafeNo");
		String seatNo = request.getParameter("seatNo");
		String userId = request.getParameter("userId"); // (수정할 사항) userId는 세션으로 보내면 되는데 왜 이렇게 했지?
		
		
		CafeService cafeService = new CafeService();
		// insertPayAndHistory : tbl_history와 tbl_pay_history 에 insert 하는 메소드
		int result = cafeService.insertPayAndHistory(ticketId, ticketPrice, ticketHour, cafeNo, seatNo, userId);
		
		
		// 결과 처리
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		
		if (result > 0) { // insertPayAndHistory 메서드 결과가 성공일 경우
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "결제가 완료되었습니다.");
			request.setAttribute("icon", "success");
			// 성공했을 경우, 카페 상세 페이지로 돌아감
			request.setAttribute("loc", "/cafeDetail?cafeNo=" + cafeNo); // (수정할 사항) 마이페이지의 결제내역으로 가기

		} else { // insertPayAndHistory 메서드 결과가 실패일 경우
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "결제 중 오류가 발생하였습니다");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/cafeDetail?cafeNo=" + cafeNo); // 실패했을 경우, 카페 상세 페이지로 돌아감
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
