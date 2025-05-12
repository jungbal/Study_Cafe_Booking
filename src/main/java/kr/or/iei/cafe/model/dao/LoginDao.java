package kr.or.iei.cafe.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.or.iei.cafe.model.vo.Login;
import kr.or.iei.common.JDBCTemplate;

public class LoginDao {

	public Login cafeLogin(Connection conn, String loginId, String loginPw) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		Login loginCafe = null;
		
		String query = "select * tbl_user where user_id =? and user_pw ";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, loginId);
			pstmt.setString(2, loginPw);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				loginCafe = new Login();
				
				loginCafe.setLoginId(loginId);
				loginCafe.setLoginPw(loginPw);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return loginCafe;
	}

	
}
