package kr.or.iei.host.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cafe.model.vo.Ticket;
import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.host.model.vo.Cafe;

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

	public Cafe selecetCafeByNo(Connection conn,String hostId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		Cafe cafe = null;
		
		String query = "select * from tbl_cafe where host_id = ?";
		
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, hostId);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				cafe = new Cafe();
				cafe.setCafeNo(rset.getString("cafe_no"));
				cafe.setCafeName(rset.getString("cafe_name"));
				cafe.setCafePhone(rset.getString("cafe_phone"));
				cafe.setCafeAddr(rset.getString("cafe_addr"));
				cafe.setCafeBiznum(rset.getString("cafe_biznum"));
				cafe.setCafeIntroduce(rset.getString("cafe_introduce"));
				cafe.setCafeStartHour(rset.getString("cafe_start_hour"));
				cafe.setCafeEndHour(rset.getString("cafe_end_hour"));
				cafe.setCafeStatus(rset.getString("cafe_status"));
				cafe.setCafeIntroDetail(rset.getString("cafe_intro_detail"));
				cafe.setHostId(hostId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return cafe;
	}

	public int changeCafe(Connection conn, Cafe cafe) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = "UPDATE tbl_cafe SET " +
                "cafe_name = ?, " +
                "cafe_phone = ?, " +
                "cafe_addr = ?, " +
                "cafe_biznum = ?, " +
                "cafe_introduce = ?, " +
                "cafe_start_hour = ?, " +
                "cafe_end_hour = ?, " +
                "cafe_status = 'N', " +
                "cafe_intro_detail = ? " +
                "WHERE cafe_no = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cafe.getCafeName());
			pstmt.setString(2, cafe.getCafePhone());
			pstmt.setString(3, cafe.getCafeAddr());
			pstmt.setString(4, cafe.getCafeBiznum());
			pstmt.setString(5, cafe.getCafeIntroduce());
			pstmt.setString(6, cafe.getCafeStartHour());
			pstmt.setString(7, cafe.getCafeEndHour());
			//pstmt.setString(8, cafe.getCafeStatus());
			pstmt.setString(8, cafe.getCafeIntroDetail());
			pstmt.setString(9, cafe.getCafeNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
	
		return result;
	}

	public int updateHostRequestStatus(Connection conn, String cafeNo) {
	    PreparedStatement pstmt = null;
	    int result = 0;
	    String query = "UPDATE tbl_host_request SET status = 'N' WHERE host_no = ?";
	             

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, cafeNo);
	        result = pstmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

	public int updateCafeStatus(Connection conn, String cafeNo) {
		 PreparedStatement pstmt = null;
		    int result = 0;
		    String query = "UPDATE tbl_cafe SET cafe_status = 'N' WHERE cafe_no = ?";

		    try {
		        pstmt = conn.prepareStatement(query);
		        pstmt.setString(1, cafeNo);
		        result = pstmt.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        JDBCTemplate.close(pstmt);
		    }

		    return result;
}

	// 호스트 신청 내역 테이블 insert
		public int insertHostRequest(Connection conn, String cafeNo) {
			PreparedStatement pstmt = null;
			int result = 0;
			
			String query = "insert into tbl_host_request (apply_date, status, host_no)\r\n"
					+ "values (sysdate, 'N', ?)";
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1,	cafeNo);
				result= pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(pstmt);
			}
			return result;
		}

		public int updateCafeImage(Connection conn, Cafe cafe) {
			PreparedStatement pstmt = null;
			int result = 0;
			
			String query = "update tbl_image set image_name= ?, image_path= ? where cafe_no = ?";
			
			try {
				pstmt = conn.prepareStatement(query);
				  System.out.println("이미지 이름: " + cafe.getCafeImageName());
				  System.out.println("이미지 경로: " + cafe.getCafeImagePath());
				  System.out.println("카페 번호: " + cafe.getCafeNo());
				pstmt.setString(1, cafe.getCafeImageName());
				pstmt.setString(2, cafe.getCafeImagePath());
				pstmt.setString(3, cafe.getCafeNo());
				
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(pstmt);
			}
			return result;
		}

}
