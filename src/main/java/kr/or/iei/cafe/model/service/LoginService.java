package kr.or.iei.cafe.model.service;

import java.sql.Connection;

import kr.or.iei.cafe.model.dao.CafeDao;
import kr.or.iei.cafe.model.dao.LoginDao;
import kr.or.iei.cafe.model.vo.Login;
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
	

}
