package kr.or.iei.cafe.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PayInfoServlet
 */
@WebServlet("/payInfo")
public class PayInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 필터 - x
		//2. 값 추출 - cafeNo, seatNo (좌석번호), ticketId(티켓 아이디), userID(세션으로 받기)
		String cafeNo = request.getParameter("cafeNo");
		String seatNo = request.getParameter("seatNo");
	    String ticketId = request.getParameter("ticketId");
	    //3. 로직 : 결제 테이블에 저장하기 (결제 상태 : 미완료)
	    
	    // 4. 토스 결제 api로 결제 정보 보내기
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
