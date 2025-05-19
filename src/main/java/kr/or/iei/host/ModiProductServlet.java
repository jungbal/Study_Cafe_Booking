package kr.or.iei.host;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.or.iei.cafe.model.vo.Ticket;
import kr.or.iei.host.model.service.HostService;

/**
 * Servlet implementation class ModiProductServlet
 */
@WebServlet("/productManage/modiProduct")
public class ModiProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModiProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// JSON 형태로 데이터 받기
		StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = request.getReader().readLine()) != null) {
	        sb.append(line);
	    }
	    String jsonData = sb.toString();

	    // JSON 파싱 (Gson 버전 문제 해결 후 적용)
	    JsonParser parser = new JsonParser();
	    JsonObject jsonObj = parser.parse(jsonData).getAsJsonObject();
	    JsonArray ticketArray = jsonObj.getAsJsonArray("tickets");

	    HostService hostService = new HostService();
	    int successCount = 0;

	    for (JsonElement el : ticketArray) {
	        JsonObject t = el.getAsJsonObject();
	        String ticketId = t.get("ticketId").getAsString();
	        String ticketType = t.get("ticketType").getAsString();
	        String ticketHour = t.get("ticketHour").getAsString();
	        String ticketPrice = t.get("ticketPrice").getAsString();

	        Ticket ticket = new Ticket(ticketId, ticketType, ticketHour, ticketPrice, null, null);
	        int result = hostService.updateTicketById(ticket);
	        if (result > 0) successCount++;
	    }

	    response.setContentType("application/json; charset=UTF-8");
	    JsonObject resJson = new JsonObject();
	    if (successCount == ticketArray.size()) {
	        resJson.addProperty("success", true);
	        resJson.addProperty("message", "수정 완료되었습니다.");
	    } else {
	        resJson.addProperty("success", false);
	        resJson.addProperty("message", "일부 항목 수정 실패.");
	    }
	    response.getWriter().write(resJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
