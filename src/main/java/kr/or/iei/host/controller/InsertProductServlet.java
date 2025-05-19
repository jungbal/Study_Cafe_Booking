package kr.or.iei.host.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.host.model.service.HostService;

/**
 * Servlet implementation class InsertProductServlet
 */
@WebServlet("/productManage/insertProduct")
public class InsertProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ticketType/ticketPrice 값 가져오기
		String ticketHour = request.getParameter("ticketHour");
		String ticketPrice = request.getParameter("ticketPrice");
		
		HttpSession session = request.getSession(false);
		String cafeNo = (String) session.getAttribute("cafeNo");
		
		// service에 insert 하는 메소드
		HostService hostService = new HostService();
		int result = hostService.insertProduct(cafeNo, ticketHour, ticketPrice);
		
		//4.결과 처리
        //4.1 이동할 페이지 경로 지정
     RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
        //4.2 화면 구현에 필요한 데이터 등록
     if(result>0) {
        request.setAttribute("title", "성공");
        request.setAttribute("msg", "추가가 완료되었습니다");
        request.setAttribute("icon", "success");
        
     }else {
        request.setAttribute("title", "실패");
        request.setAttribute("msg", "추가 작업 중 오류가 발생했습니다");
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
