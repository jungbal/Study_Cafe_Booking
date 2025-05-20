package kr.or.iei.host.model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import kr.or.iei.Login.model.dao.LoginDao;
import kr.or.iei.cafe.model.vo.Ticket;
import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.host.model.dao.HostDao;

public class HostService {
	
	private HostDao dao;
	
	public HostService() {
		dao = new HostDao();
	}

	public ArrayList<Ticket> selectTicketByCafeNo(String cafeNo) {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Ticket> ticketList = dao.selectTicketByCafeNo(conn, cafeNo);
		JDBCTemplate.close(conn);
		return ticketList;
	}

	public int insertProduct(String cafeNo, String ticketHour, String ticketPrice) {
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.insertProduct(conn, cafeNo, ticketHour, ticketPrice);
		if(result>0) {
	         JDBCTemplate.commit(conn);
	      }else {
	         JDBCTemplate.rollback(conn);
	      }
	      JDBCTemplate.close(conn);
		JDBCTemplate.close(conn);
		return result;
	}

	public int deleteTicketById(Ticket ticket) {
	    Connection conn = JDBCTemplate.getConnection();
	    String ticketId = ticket.getTicketId();
	   
	    int insertResult = dao.insertTicketHist(conn, ticket);  // 1단계: 히스토리 저장

	    int deleteResult = 0;
	    if (insertResult > 0) {
	        deleteResult = dao.deleteTicket(conn, ticket);      // 2단계: 실제 삭제
	    }
	    
	    System.out.println("insert result: " + insertResult);
	    System.out.println("delete result: " + deleteResult);


	    if (insertResult > 0 && deleteResult > 0) {
	        JDBCTemplate.commit(conn);
	    } else {
	        JDBCTemplate.rollback(conn);
	    }

	    JDBCTemplate.close(conn);

	    return (insertResult > 0 && deleteResult > 0) ? 1 : 0;  // 성공 시 1 반환
	}

	
	// 이용권 정보 수정
	public int updateTicketById(Ticket ticket) {
		Connection conn = JDBCTemplate.getConnection();
	    String ticketId = ticket.getTicketId();
	   
	    int insertResult = dao.insertTicketHist(conn, ticket);  // tbl_ticket_hist 테이블에서 수정대상정보 insert

	    int updateResult = 0;
	    if (insertResult > 0) {
	    	updateResult = dao.updateTicket(conn, ticket);      // tbl_ticket 테이블에서 수정대상정보 update
	    }


	    if (insertResult > 0 && updateResult > 0) {
	        JDBCTemplate.commit(conn);
	    } else {
	        JDBCTemplate.rollback(conn);
	    }

	    JDBCTemplate.close(conn);
	    
	    
	    return (insertResult > 0 && updateResult > 0) ? 1 : 0;
	}


}
