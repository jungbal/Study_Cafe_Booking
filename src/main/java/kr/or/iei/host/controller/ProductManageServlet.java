package kr.or.iei.host.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.Login.model.vo.Login;
import kr.or.iei.cafe.model.vo.History;
import kr.or.iei.cafe.model.vo.Ticket;
import kr.or.iei.host.model.service.HostService;

/**
 * Servlet implementation class ProductManageServlet
 */
@WebServlet("/host/productManage")
public class ProductManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductManageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
	        Object cafeNoObj = session.getAttribute("cafeNo");
	        if (cafeNoObj != null) {
	            String cafeNo = cafeNoObj.toString();  // 혹은 (String) cafeNoObj로 직접 캐스팅
	            System.out.println("ProductManagerServlet.java : cafeNo 세션 값 체크 - " + cafeNo);
	            
	            ArrayList<Ticket> ticketList = new HostService().selectTicketByCafeNo(cafeNo);
	            request.setAttribute("ticketList", ticketList);
	            request.getRequestDispatcher("/WEB-INF/views/host/productManage.jsp").forward(request, response);
	            return; // 포워드 후 종료
	        } else {
	            System.out.println("ProductManagerServlet.java : cafeNo가 세션에 없음");
	        }
	    } else {
	        System.out.println("ProductManagerServlet.java : 세션이 없음");
	    }
	    
	    // 세션이 없거나 cafeNo가 없을 때 기본 처리 (예: 로그인 페이지 리다이렉트 또는 에러 페이지)
		System.out.println("세션이 없거나 cafeNo가 없을 때 기본 처리 (예: 로그인 페이지 리다이렉트 또는 에러 페이지)");
	    response.sendRedirect(request.getContextPath() + "/login"); // 예시: 로그인 페이지로 이동
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
