package kr.or.iei.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.member.model.vo.PayHistory;

public class PayDao {

	public ArrayList<PayHistory> selectPayHistoryByUser(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<PayHistory> list = new ArrayList<>();

		String query = "SELECT * FROM TBL_PAY_HISTORY WHERE PAY_USER_ID = ? ORDER BY PAY_TIME DESC";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				PayHistory p = new PayHistory();
				p.setPayId(rset.getInt("PAY_ID"));
				p.setPayTime(rset.getDate("PAY_TIME"));
				p.setPayStatus(rset.getString("PAY_STATUS"));
				p.setPayAmount(rset.getString("PAY_AMOUNT"));
				p.setPayMethod(rset.getString("PAY_METHOD"));
				p.setPayUserId(rset.getString("PAY_USER_ID"));
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}
}
