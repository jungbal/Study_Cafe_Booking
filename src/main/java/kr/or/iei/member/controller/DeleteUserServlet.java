package kr.or.iei.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.member.model.service.MemberService;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/user/deleteUser")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 인코딩 - 필터
		//2. 값 추출
		String userId = request.getParameter("userId");
		//3. 로직
		MemberService service = new MemberService();
		int result = service.deleteOneUser(userId);
		//4. 결과 처리 
		RequestDispatcher view = null;
		if(result > 0) {	//정상적으로 삭제 되었을 때
			view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			request.setAttribute("title", "회원 삭제");
			request.setAttribute("msg", "회원이 정상적으로 삭제되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/manager/userManage?reqPage=1");
			view.forward(request, response);
			
		}else { //삭제 실패 되었을 때
			view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			request.setAttribute("title", "삭제 실패");
			request.setAttribute("msg","회원 삭제 중, 오류가 발생하였습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/WEB-INF/views/manager/manageUser.jsp");
			view.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
