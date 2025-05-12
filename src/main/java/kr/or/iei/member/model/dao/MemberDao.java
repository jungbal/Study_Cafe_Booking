package kr.or.iei.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.member.model.vo.Member;

public class MemberDao {
	
	public Member selectOneMember(Connection conn, String userId, String userPw) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member m = null;
		
		String query = "SELECT * FROM TBL_USER WHERE USER_ID = ? AND USER_PW = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				m = new Member();
				m.setUserId(rset.getString("USER_ID"));
				m.setUserPw(rset.getString("USER_PW"));
				m.setUserRole(rset.getInt("USER_ROLE"));
				m.setUserPhone(rset.getString("USER_PHONE"));
				m.setUserStatus(rset.getString("USER_STATUS"));
				m.setUserImage(rset.getString("USER_IMAGE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return m;
	}


	public int updateMember(Connection conn, Member updMember) {
		PreparedStatement pstmt = null;

		int result = 0;

		String query = "update tbl_user set user_phone = ?, user_pw = ?, user_image = ? where user_id = ?";

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, updMember.getUserPhone());
			pstmt.setString(2, updMember.getUserPw());
			pstmt.setString(3, updMember.getUserImage());
			pstmt.setString(4, updMember.getUserId());
			

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}
}
