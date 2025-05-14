package kr.or.iei.Login.model.service;

import java.sql.Connection;

import kr.or.iei.Login.model.dao.LoginDao;
import kr.or.iei.Login.model.vo.Login;
import kr.or.iei.common.JDBCTemplate;

public class LoginService {
	
	private LoginDao dao;
	
	public LoginService() {
		dao = new LoginDao();
	}

	public Login cafeLogin(String loginId, String loginPw) {
		Connection conn = JDBCTemplate.getConnection();
		Login loginCafe = dao.cafeLogin(conn, loginId, loginPw);
		JDBCTemplate.close(conn);
		return loginCafe;
	}

	public int memberJoin(Login joinInfo) {
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.memberJoin(conn ,joinInfo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}
	
	public int idDuplChk(String memberId) {
		Connection conn = JDBCTemplate.getConnection();
		int cnt = dao.idDuplChk(conn, memberId);
		JDBCTemplate.close(conn);
		return cnt;
	}

}
