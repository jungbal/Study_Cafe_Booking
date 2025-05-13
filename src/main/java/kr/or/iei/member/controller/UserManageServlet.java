package kr.or.iei.member.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.common.ListData;
import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;



/**
 * Servlet implementation class UserManageServlet
 */
@WebServlet("/manager/userManage")
public class UserManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserManageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 인코딩 - 필터
		//2. 값 추출
		int reqPage =  Integer.parseInt(request.getParameter("reqPage"));
		//3. 로직
		MemberService service = new MemberService();
		ListData<Member> userList =service.selectAllUser(reqPage);
		//4. 결과 처리
			//4.1 이동할 페이지 경로 등록
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/manager/manageUser.jsp");
		
			//4.2 화면 구현에 필요한 데이터 등록
		request.setAttribute("userList", userList.getList());
		request.setAttribute("pageNavi", userList.getPageNavi());
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
