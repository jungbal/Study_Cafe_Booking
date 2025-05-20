package kr.or.iei.host.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.host.model.vo.Cafe;

public class CafeDao {

	public Cafe selecetCafeByNo(Connection conn,String hostId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		Cafe cafe = null;
		
		String query = "select * from tbl_cafe where host_id = ?";
		
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, hostId);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				cafe = new Cafe();
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
				cafe.setHostId(hostId);
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

	public int changeCafe(Connection conn, Cafe cafe) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = "UPDATE tbl_cafe SET " +
                "cafe_name = ?, " +
                "cafe_phone = ?, " +
                "cafe_addr = ?, " +
                "cafe_biznum = ?, " +
                "cafe_introduce = ?, " +
                "cafe_start_hour = ?, " +
                "cafe_end_hour = ?, " +
                "cafe_status = 'N', " +
                "cafe_intro_detail = ? " +
                "WHERE cafe_no = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cafe.getCafeName());
			pstmt.setString(2, cafe.getCafePhone());
			pstmt.setString(3, cafe.getCafeAddr());
			pstmt.setString(4, cafe.getCafeBiznum());
			pstmt.setString(5, cafe.getCafeIntroduce());
			pstmt.setString(6, cafe.getCafeStartHour());
			pstmt.setString(7, cafe.getCafeEndHour());
			//pstmt.setString(8, cafe.getCafeStatus());
			pstmt.setString(8, cafe.getCafeIntroDetail());
			pstmt.setString(9, cafe.getCafeNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
	
		return result;
	}

	public int updateHostRequestStatus(Connection conn, String cafeNo) {
	    PreparedStatement pstmt = null;
	    int result = 0;
	    String query = "UPDATE tbl_host_request SET status = 'N' WHERE host_no = ?";
	             

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, cafeNo);
	        result = pstmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

	public int updateCafeStatus(Connection conn, String cafeNo) {
		 PreparedStatement pstmt = null;
		    int result = 0;
		    String query = "UPDATE tbl_cafe SET cafe_status = 'N' WHERE cafe_no = ?";

		    try {
		        pstmt = conn.prepareStatement(query);
		        pstmt.setString(1, cafeNo);
		        result = pstmt.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        JDBCTemplate.close(pstmt);
		    }

		    return result;
}

	
}
