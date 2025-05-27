package kr.or.iei.host.model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import kr.or.iei.Login.model.dao.LoginDao;
import kr.or.iei.cafe.model.vo.Ticket;
import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.host.model.dao.HostDao;
import kr.or.iei.host.model.vo.Cafe;

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

	public Cafe selectCafeByNo(String hostId) {
		Connection conn = JDBCTemplate.getConnection();
		Cafe cafe = dao.selecetCafeByNo(conn,hostId);
		JDBCTemplate.close(conn);
		return cafe;
	}
	
	
	public int changeCafe(Cafe cafe) {
	    Connection conn = JDBCTemplate.getConnection();

	    int result1 = dao.changeCafe(conn, cafe);  // tbl_cafe 업데이트
	    int result2 = 0;
	    //int result3 = 0;
	    int result4 = 0;
	    int result5 = 0; // 이미지 update 실패 시 rollback 되게 설정

	    if (result1 > 0) {
	        result2 = dao.updateCafeStatus(conn, cafe.getCafeNo()); // 상태 변경
	        //result3 = dao.updateHostRequestStatus(conn, cafe.getCafeNo()); 
	    }

	    if (result1 > 0 && result2 > 0) {
	        result4 = dao.insertHostRequest(conn, cafe.getCafeNo());

	        // 무조건 update만 수행 (이미지가 무조건 존재하므로)
	        result5 = dao.updateCafeImage(conn, cafe);
	    }

	    if (result1 > 0 && result2 > 0 &&  result4 > 0 && result5 > 0) {
	        JDBCTemplate.commit(conn);
	    } else {
	        JDBCTemplate.rollback(conn);
	    }
	    
	    System.out.println("result1 : " + result1);
	    System.out.println("result2 : " + result2);
	    //System.out.println("result3 : " + result3);
	    System.out.println("result4 : " + result4);
	    System.out.println("result5 : " + result5);

	    JDBCTemplate.close(conn);

	    return (result1 > 0 && result2 > 0 && result4 > 0 && result5 > 0) ? 1 : 0;
	}

}
