package kr.or.iei.Login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.Login.model.service.LoginService;

/**
 * Servlet implementation class IdDuplChkServlet
 */
@WebServlet("/idDuplChk")
public class IdDuplChkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IdDuplChkServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

//	사용자 입력 id를 가져와 중복체크하는 메소드 호출하며 전달 및 cnt를 브라우저에 작성
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ajax에서 key로 요청한 memberId 
		String memberId = request.getParameter("memberId");
		
		LoginService service = new LoginService();
		
		int cnt = service.idDuplChk(memberId);
		
		response.getWriter().print(cnt);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
