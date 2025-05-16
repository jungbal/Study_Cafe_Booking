package kr.or.iei.cafe.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.cafe.model.dao.TicketDao;
import kr.or.iei.cafe.model.vo.Ticket;
import kr.or.iei.common.JDBCTemplate;

public class TicketService {
	
	private TicketDao dao;
	
	public TicketService() {
		dao = new TicketDao();
	}
	
	// 카페 고유 번호로 이용권 리스트 얻어오는 메소드
	public ArrayList<Ticket> selectCafeTicket (String cafeNo){
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Ticket> ticketList = dao.selectCafeTicket(conn, cafeNo);
		JDBCTemplate.close(conn);
	      return ticketList;
	}
	
	// ticket_id로 해당 이용권의 정보 가져오기
	public Ticket selectOneTicket (String ticketId) {
		Connection conn = JDBCTemplate.getConnection();
		Ticket ticket = dao.selectOneTicket(conn, ticketId);
		JDBCTemplate.close(conn);
	      return ticket;
	}

}
