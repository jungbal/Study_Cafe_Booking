package kr.or.iei.host.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cafe.model.vo.Ticket;
import kr.or.iei.common.JDBCTemplate;

public class HostDao {

	public ArrayList<Ticket> selectTicketByCafeNo(Connection conn, String cafeNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
		
		String query = "select * from tbl_ticket where cafe_no = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cafeNo);
			rset = pstmt.executeQuery();
			while(rset.next()) {
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

	public int insertProduct(Connection conn, String cafeNo, String ticketHour, String ticketPrice) {
	    PreparedStatement pstmt = null;
	    int result = 0;

	    String query = "INSERT INTO tbl_ticket " +
	                   "(ticket_id, ticket_type, ticket_hour, ticket_price, ticket_reg_date, cafe_no) " +
	                   "VALUES (TO_CHAR(SEQ_TBL_TICKET.NEXTVAL), ?, ?, ?, SYSDATE, ?)";

	    try {
	        pstmt = conn.prepareStatement(query);
	        String ticketType = ticketHour + "시간권";
	        
	        pstmt.setString(1, ticketType);
	        pstmt.setString(2, ticketHour);
	        pstmt.setString(3, ticketPrice);
	        pstmt.setString(4, cafeNo);

	        result = pstmt.executeUpdate(); 

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

	public int insertTicketHist(Connection conn, Ticket ticket) {
		PreparedStatement pstmt = null;
	    int result = 0;
	    
	    String query = "insert into tbl_ticket_hist\r\n"
	    		+ "    (hist_ticket_price, hist_ticket_time, hist_ticket_change_date, hist_ticket_id)\r\n"
	    		+ "values (?, ?, sysdate, ?)";
	    try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ticket.getTicketPrice());
			pstmt.setString(2, ticket.getTicketHour());
			pstmt.setString(3, ticket.getTicketId());
			
			result = pstmt.executeUpdate(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

	public int deleteTicket(Connection conn, Ticket ticket) {
		PreparedStatement pstmt = null;
	    int result = 0;
	    
	    String query = "delete from tbl_ticket where ticket_id = ?";
	    try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ticket.getTicketId());
			result = pstmt.executeUpdate(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
	        JDBCTemplate.close(pstmt);
	    }
	    return result;
	}

	public int updateTicket(Connection conn, Ticket ticket) {
	    PreparedStatement pstmt = null;
	    int result = 0;
	    String query = "UPDATE tbl_ticket\r\n"
	                 + "SET ticket_type = ?,\r\n"
	                 + "    ticket_hour = ?,\r\n"
	                 + "    ticket_price = ?,\r\n"
	                 + "    ticket_reg_date = SYSDATE\r\n"
	                 + "WHERE ticket_id = ?";

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, ticket.getTicketType());
	        pstmt.setString(2, ticket.getTicketHour());
	        pstmt.setString(3, ticket.getTicketPrice());
	        pstmt.setString(4, ticket.getTicketId());

	        result = pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}



}
