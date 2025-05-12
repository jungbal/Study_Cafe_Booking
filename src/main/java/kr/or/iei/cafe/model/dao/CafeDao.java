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
				cafe.setCafeName(rset.getString("cafeName"));
				cafe.setCafeAddr(rset.getString("cafeAddr"));
				cafe.setCafeNo(rset.getString("cafeNo"));
				
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
	
	

}
