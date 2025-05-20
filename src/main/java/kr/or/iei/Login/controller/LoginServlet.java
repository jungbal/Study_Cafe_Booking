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
import kr.or.iei.cafe.model.service.CafeService;

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
//<<<<<<< HEAD
//		String loginId = request.getParameter("loginId");
//		String loginPw = request.getParameter("loginPw");
//		
//		LoginService service = new LoginService();
//		CafeService cafeservice = null;
//		
//		Login loginCafe = service.cafeLogin(loginId, loginPw); 
//		int role = service.chkUserRole(loginId);
//		
//		RequestDispatcher view = null;
//		
//		if(loginCafe == null || role == 0) {
//			view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
//			
//			request.setAttribute("title", "알림");
//			request.setAttribute("msg", "아이디 또는 비밀번호를 확인하세요");
//			request.setAttribute("icon", "error");
//			request.setAttribute("loc", "/loginFrm");
//		}else {
//			view = request.getRequestDispatcher("/index.jsp");
//			
//			HttpSession session = request.getSession();
//			session.setAttribute("loginCafe", loginCafe);
//			session.setAttribute("role", role);
//			session.setMaxInactiveInterval(600);
//			
//			cafeservice = new CafeService();
//			String hostId = cafeservice.matchHostId(loginId);
//			request.setAttribute("hostId", hostId);
//=======
		 	String loginId = request.getParameter("loginId");
		    String loginPw = request.getParameter("loginPw");

		    LoginService service = new LoginService();
		    CafeService cafeservice = null;
		    
		    Login loginCafe = service.cafeLogin(loginId, loginPw);
		    int role = service.chkUserRole(loginId);
		    
		    RequestDispatcher view = null;

		    if (loginCafe == null || role == 0) {
		        // 로그인 실패 시만 forward 처리
		        view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		        
		        request.setAttribute("title", "알림");
		        request.setAttribute("msg", "아이디 또는 비밀번호를 확인하세요");
		        request.setAttribute("icon", "error");
		        request.setAttribute("loc", "/loginFrm");
		        
		        view.forward(request, response);
		    } else {
		        // 로그인 성공 시 redirect 처리 (view 변수 선언 금지)
		        HttpSession session = request.getSession();
		        session.setAttribute("loginCafe", loginCafe);
		        session.setAttribute("role", role);
		        session.setMaxInactiveInterval(600); // 10분 세션 유지

		        response.sendRedirect(request.getContextPath() + "/main");
		        // redirect 이후엔 절대 forward 호출 금지!
		        
		        cafeservice = new CafeService();
				String hostId = cafeservice.matchHostId(loginId);
				request.setAttribute("hostId", hostId);
		        
		    }
//>>>>>>> 60d7f3933aea2519ce2aee62af6eb8ccdf3a5dc7
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
