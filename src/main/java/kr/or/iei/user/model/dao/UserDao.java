package kr.or.iei.user.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.user.model.vo.User;

public class UserDao {

	public ArrayList<User> selectAllUser(Connection conn, int start, int end) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		ArrayList<User> list = new ArrayList<User>();
		
		String query = "select * from (select ROWNUM RNUM, A.*from ( select * from tbl_user A ) A) where RNUM >=? and RNUM <=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				User user = new User();
				user.setUserId(rset.getString("user_id"));
				user.setUserPw(rset.getString("user_pw"));
				user.setUserRole(rset.getInt("user_role"));
				user.setUserPhone(rset.getString("user_phone"));
				user.setUserStatus(rset.getString("user_status"));
				user.setUserImage(rset.getString("user_image"));
				
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public int selectTotalCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int totCnt = 0;
		
		String query = "select count(*) as cnt from tbl_user";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			rset = pstmt.executeQuery();
			rset.next();
			totCnt = rset.getInt("cnt");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return totCnt;
	}

}
