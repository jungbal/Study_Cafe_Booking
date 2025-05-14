package kr.or.iei.Login.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.Login.model.service.LoginService;
import kr.or.iei.Login.model.vo.Login;

/**
 * Servlet implementation class JoinServlet
 */
@WebServlet("/JoinServlet")
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		String memberPhone = request.getParameter("memberPhone");
		
		Login joinInfo = new Login();
		joinInfo.setLoginId(loginId);
		joinInfo.setLoginPw(loginPw);
		joinInfo.setMemberPhone(memberPhone);
		
		LoginService service = new LoginService();
		int result = service.memberJoin(joinInfo);
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		
		if(result>0) {
			request.setAttribute("title", "성공");
			request.setAttribute("msg", "회원가입이 완료되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/");
		}else {
			request.setAttribute("title", "실패");
			request.setAttribute("msg", "회원가입 중 오류가 발생하였습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/");
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
