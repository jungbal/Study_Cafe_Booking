package kr.or.iei.cafe.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.cafe.model.service.CafeService;
import kr.or.iei.cafe.model.vo.Cafe;

/**
 * Servlet implementation class CafeRequestDetailServlet
 */
@WebServlet("/admin/cafeRequestDetail")
public class CafeRequestDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CafeRequestDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cafeNo = request.getParameter("cafeNo");
		
		CafeService service = new CafeService();
		Cafe cafe = service.selectCafeByNo(cafeNo);
		
		request.setAttribute("cafe", cafe);
		request.getRequestDispatcher("/WEB-INF/views/manager/cafeRequestDetail.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
