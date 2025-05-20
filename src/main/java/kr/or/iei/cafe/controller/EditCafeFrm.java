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
import kr.or.iei.cafe.model.vo.Cafe;

/**
 * Servlet implementation class EditCafe
 */
@WebServlet("/editCafeFrm")
public class EditCafeFrm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCafeFrm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CafeService service = new CafeService();

		 HttpSession session = request.getSession();
		   Login loginCafe = (Login) session.getAttribute("loginCafe");
		   String loginId = loginCafe.getLoginId();
		
		Cafe cafeInfo = service.selectOneCafe(loginId);
		
		request.setAttribute("cafeInfo", cafeInfo);
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/cafe/editCafe.jsp");
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
