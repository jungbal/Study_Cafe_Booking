package kr.or.iei.cafe.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cafe.model.vo.Ticket;
import kr.or.iei.common.JDBCTemplate;

public class TicketDao {
	
	// 이용권 리스트 출력
	public ArrayList<Ticket> selectCafeTicket (Connection conn, String cafeNo){
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
		
		String query = "select * from tbl_ticket where cafe_no=? order by ticket_price desc";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cafeNo);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Ticket ticket = new Ticket();
				ticket.setTicketId(rset.getString("ticket_id"));
				ticket.setTicketType(rset.getString("ticket_type"));
				ticket.setTicketHour(rset.getString("ticket_hour"));
				ticket.setTicketPrice(rset.getString("ticket_price"));
				ticket.setTicketRegDate(rset.getString("ticket_reg_date"));
				ticket.setCafeNo(rset.getString("cafe_no"));
			
				ticketList.add(ticket);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
	        JDBCTemplate.close(rset);
	        JDBCTemplate.close(pstmt);
	    }
		return ticketList;
	}
	
	
	// ticket_id로 해당 이용권의 정보 가져오기
	public Ticket selectOneTicket (Connection conn, String ticketId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		Ticket ticket = null;
		
		String query = "select * from tbl_ticket where ticket_id= ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ticketId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				ticket = new Ticket();
				ticket.setTicketId(ticketId);
				ticket.setTicketPrice(rset.getString("ticket_price"));
				ticket.setTicketHour(rset.getString("ticket_hour"));
				ticket.setTicketType(rset.getString("ticket_type"));
				ticket.setTicketRegDate(rset.getString("ticket_reg_date"));
				ticket.setCafeNo(rset.getString("cafe_no"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ticket;
	}

}
