package kr.or.iei.Login.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.or.iei.Login.model.vo.Login;
import kr.or.iei.common.JDBCTemplate;

public class LoginDao {

//	로그인 메소드
	public Login cafeLogin(Connection conn, String loginId, String loginPw) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		Login loginCafe = null;
		
		String query = "select * from tbl_user where user_id =? and user_pw = ?";
		
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
	
//  회원 가입 메소드
	public int memberJoin(Connection conn, Login joinInfo) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query =
		"INSERT INTO TBL_USER (user_id, user_pw, user_phone) VALUES (?, ?, ?)";
	// (USER_ID, USER_PW, USER_ROLE, USER_PHONE, USER_STATUS, USER_IMAGE)
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, joinInfo.getLoginId());
			pstmt.setString(2, joinInfo.getLoginPw());
			pstmt.setString(3, joinInfo.getMemberPhone());
			
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
