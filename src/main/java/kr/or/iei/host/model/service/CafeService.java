package kr.or.iei.host.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.host.model.dao.CafeDao;
import kr.or.iei.host.model.vo.Cafe;


public class CafeService {

	private CafeDao dao;
	public CafeService() {
		dao = new CafeDao();
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
	    int result3 = 0;

	    if (result1 > 0) {
	    	result2 = dao.updateCafeStatus(conn, cafe.getCafeNo()); // tbl_cafe 상태 변경
	        result3 = dao.updateHostRequestStatus(conn, cafe.getCafeNo()); 
	    }

	    if (result1 > 0 && result2 > 0 && result3 > 0) {
	        JDBCTemplate.commit(conn);
	    } else {
	        JDBCTemplate.rollback(conn);
	    }

	    JDBCTemplate.close(conn);

	    return (result1 > 0 && result2 > 0 && result3 > 0) ? 1 : 0;
	}
}
