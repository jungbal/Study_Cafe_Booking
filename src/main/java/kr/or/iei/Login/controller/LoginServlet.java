package kr.or.iei.Login.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.Login.model.service.LoginService;
import kr.or.iei.Login.model.vo.Login;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		
		LoginService service = new LoginService();
		Login loginCafe = service.cafeLogin(loginId, loginPw);
		
		RequestDispatcher view = null;
		
		if(loginCafe == null) {
			view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "아이디 또는 비밀번호를 확인하세요");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/loginFrm");
		}else {
			view = request.getRequestDispatcher("/index.jsp");
			
			HttpSession session = request.getSession();
			session.setAttribute("loginCafe", loginCafe);
			session.setMaxInactiveInterval(600);
		}
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
