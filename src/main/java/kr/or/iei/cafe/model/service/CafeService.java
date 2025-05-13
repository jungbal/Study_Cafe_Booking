package kr.or.iei.cafe.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.cafe.model.dao.CafeDao;
import kr.or.iei.cafe.model.vo.Cafe;
import kr.or.iei.common.JDBCTemplate;

public class CafeService {
	
	private CafeDao dao;
	
	public CafeService() {
		dao = new CafeDao();
	}
	
	// 검색창 메소드
	public ArrayList<Cafe> srchCafe(String srchStr) {
		Connection conn = JDBCTemplate.getConnection();
	      ArrayList<Cafe> list = dao.memberJoin(conn, srchStr);
	      
	      JDBCTemplate.close(conn);
	      return list;
	}

	// 
	public Cafe selectcafeNo(String cafeNo) {
		Connection conn = JDBCTemplate.getConnection();
		Cafe cafe = dao.selectNocafe(conn, cafeNo);
		JDBCTemplate.close(conn);
		return cafe;
	}

}
