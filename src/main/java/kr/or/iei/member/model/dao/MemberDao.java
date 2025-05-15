package kr.or.iei.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.member.model.vo.Member;


public class MemberDao {
	
	//이정원
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

	//이정원 오버로딩: 단순 조회용 (userId만 필요)
	public Member selectOneMember(Connection conn, String userId) {
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    Member m = null;

	    String query = "SELECT * FROM TBL_USER WHERE USER_ID = ?";

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, userId);
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

	//이정원
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
	
	//이정원
	public int updateMemberPw(Connection conn, String userId, String newMemberPw) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = "update tbl_user set user_pw = ? where user_id = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, newMemberPw);
			pstmt.setString(2, userId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	//이정원
	public int updateProfileImg(Connection conn, String userId, String filePath) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = "UPDATE TBL_USER SET USER_IMAGE = ? WHERE USER_ID = ?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, filePath);
            pstmt.setString(2, userId);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
    }

	
	//정휘훈 파트
	public ArrayList<Member> selectAllUser(Connection conn, int start, int end) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		ArrayList<Member> list = new ArrayList<Member>();
		
		String query = "select * from (select ROWNUM RNUM, A.*from ( select * from tbl_user A ) A) where RNUM >=? and RNUM <=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Member user = new Member();
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

	public int deleteOneUser(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = "delete from tbl_user where user_id = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(conn);
		}
		
		return result;
	}
}
