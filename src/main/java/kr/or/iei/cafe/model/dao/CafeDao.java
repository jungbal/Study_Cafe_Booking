package kr.or.iei.cafe.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cafe.model.vo.Cafe;
import kr.or.iei.common.JDBCTemplate;

public class CafeDao {

	public ArrayList<Cafe> memberJoin(Connection conn, String srchStr) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		ArrayList<Cafe> cafeList = new ArrayList<Cafe>();
		
		// 카페명과 카페 주소에서 동시에 검색어 찾기.
		String query = "SELECT * FROM tbl_cafe WHERE cafe_name LIKE ? OR cafe_addr LIKE ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + srchStr + "%");
			pstmt.setString(2, "%" + srchStr + "%");
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Cafe cafe = new Cafe();
				cafe.setCafeNo(rset.getString("cafe_No"));
				cafe.setCafeName(rset.getString("cafe_name"));
				cafe.setCafeAddr(rset.getString("cafe_addr"));
				cafe.setCafeBiznum(rset.getString("cafe_biznum"));
				cafe.setCafeIntroduce(rset.getString("cafe_introduce"));
				cafe.setCafeStartHour(rset.getString("cafe_start_hour"));
				cafe.setCafeEndHour(rset.getString("cafe_start_hour"));
				cafe.setCafeStatus(rset.getString("cafe_status"));
				cafe.setCafeIntroDetail(rset.getString("cafe_intro_detail"));
				cafe.setHostId(rset.getString("host_id"));
				
				cafeList.add(cafe);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		// TODO Auto-generated method stub
		return cafeList;
	}

	public Cafe selectCafeByNo(Connection conn, String cafeNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		Cafe cafe = null;
		
		// cafe 테이블 전체 정보 select
		String query = "SELECT * FROM tbl_cafe WHERE cafe_no = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cafeNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				cafe = new Cafe();
				cafe.setCafeNo(cafeNo);
				cafe.setCafeName(rset.getString("cafe_name"));
				cafe.setCafePhone(rset.getString("cafe_phone"));
				cafe.setCafeAddr(rset.getString("cafe_addr"));
				cafe.setCafeBiznum(rset.getString("cafe_biznum"));
				cafe.setCafeIntroduce(rset.getString("cafe_introduce"));
				cafe.setCafeStartHour(rset.getString("cafe_start_hour"));
				cafe.setCafeEndHour(rset.getString("cafe_end_hour"));
				cafe.setCafeStatus(rset.getString("cafe_status"));
				cafe.setCafeIntroDetail(rset.getString("cafe_intro_detail"));
				cafe.setHostId(rset.getString("host_id"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
			
		}
		return cafe;
	}

	
	//정휘훈 파트
	public ArrayList<Cafe> selectAllCafe(Connection conn, int start, int end) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		ArrayList<Cafe> list = new ArrayList<Cafe>();
		
		String query = "select * from (select ROWNUM RNUM, A.* from (SELECT \r\n"
				+ "    c.*,\r\n"
				+ "    h.status AS host_request_status,\r\n"
				+ "    CASE \r\n"
				+ "        WHEN c.cafe_status = 'N' AND h.status = 'N' THEN '등록대기'\r\n"
				+ "        WHEN c.cafe_status = 'N' AND h.status = 'Y' THEN '수정대기'\r\n"
				+ "        WHEN c.cafe_status = 'Y' AND h.status = 'Y' THEN '승인'\r\n"
				+ "        ELSE '기타'\r\n"
				+ "    END AS cafe_manage_status\r\n"
				+ "FROM tbl_cafe c\r\n"
				+ "JOIN tbl_host_request h ON c.cafe_no = h.host_no\r\n"
				+ "WHERE \r\n"
				+ "    (c.cafe_status = 'N' AND h.status IN ('N', 'Y'))\r\n"
				+ "    OR (c.cafe_status = 'Y' AND h.status = 'Y')) A) where RNUM >=? and RNUM <=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Cafe cafe = new Cafe();
				
				cafe.setCafeNo(rset.getString("cafe_no"));
				cafe.setCafeName(rset.getString("cafe_name"));
				cafe.setCafePhone(rset.getString("cafe_phone"));
				cafe.setCafeAddr(rset.getString("cafe_addr"));
				cafe.setCafeBiznum(rset.getString("cafe_biznum"));
				cafe.setCafeIntroduce(rset.getString("cafe_introduce"));
				cafe.setCafeStartHour(rset.getString("cafe_start_hour"));
				cafe.setCafeEndHour(rset.getString("cafe_end_hour"));
				cafe.setCafeStatus(rset.getString("cafe_status"));
				cafe.setCafeIntroDetail(rset.getString("cafe_intro_detail"));
				cafe.setHostId(rset.getString("host_id"));
				cafe.setCafeManageStatus(rset.getString("cafe_manage_status"));
				
				list.add(cafe);
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
		
		String query = "select count(*) as cnt from tbl_cafe";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			rset=pstmt.executeQuery();
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

	


	public int deleteHost(Connection conn, String cafeNo) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = "delete from tbl_cafe where cafe_no =?";
		
		try {
			pstmt=conn.prepareStatement(query);
			
			pstmt.setString(1, cafeNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}

	public int updateRole(Connection conn, String cafeNo) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = "UPDATE tbl_user SET user_roll = '3' WHERE user_id IN (SELECT host_id FROM tbl_cafe WHERE cafe_no = ?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cafeNo);
			
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
