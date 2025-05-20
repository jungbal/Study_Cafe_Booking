package kr.or.iei.host.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.host.model.service.CafeService;
import kr.or.iei.host.model.vo.Cafe;

/**
 * Servlet implementation class ChangeCafeServlet
 */
@WebServlet("/host/update")
public class ChangeCafeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeCafeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cafeNo = request.getParameter("cafeNo");
		String cafeName = request.getParameter("cafeName");
		String cafePhone = request.getParameter("cafePhone");
		String cafeAddr = request.getParameter("cafeAddr");
		String cafeBiznum = request.getParameter("cafeBiznum");
		String cafeIntroduce = request.getParameter("cafeIntroduce");
		String cafeStartHour = request.getParameter("cafeStartHour");
		String cafeEndHour = request.getParameter("cafeEndHour");
		String cafeStatus = request.getParameter("cafeStatus");
		String cafeIntroDetail = request.getParameter("cafeIntroDetail");
		String hostId = request.getParameter("hostId");
		
		CafeService service = new CafeService();

		
		Cafe cafe = new Cafe(cafeNo, cafeName, cafePhone, cafeAddr, cafeBiznum, cafeIntroduce, cafeStartHour, cafeEndHour, cafeStatus, cafeIntroDetail, hostId);
		
		int result = service.changeCafe(cafe);
		
		
		RequestDispatcher view = null;
		if(result > 0) {
			view=request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			request.setAttribute("title", "업체 수정");
			request.setAttribute("msg", "업체가 정상적으로 수정되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/host/cafeInfoModi");
			view.forward(request, response);
			
		}else {
			view=request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			request.setAttribute("title", "수정 실패");
			request.setAttribute("msg", "업체 수정 중, 오류가 발생하였습니다. ");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/host/cafeInfoModi");
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
