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

		String query = "SELECT \r\n"
				+ "    PH.*, \r\n"
				+ "    H.HISTORY_SEAT_NO, \r\n"
				+ "    H.HISTORY_CAFE_NO, \r\n"
				+ "    C.CAFE_NAME,\r\n"
				+ "    TO_CHAR(H.HISTORY_START, 'YYYY-MM-DD HH24:MI:SS') AS HISTORY_START,\r\n"
				+ "    TO_CHAR(H.HISTORY_END, 'YYYY-MM-DD HH24:MI:SS') AS HISTORY_END\r\n"
				+ "FROM \r\n"
				+ "    TBL_PAY_HISTORY PH\r\n"
				+ "JOIN \r\n"
				+ "    TBL_HISTORY H ON H.HISTORY_USER_ID = PH.PAY_USER_ID\r\n"
				+ "JOIN \r\n"
				+ "    TBL_CAFE C ON H.HISTORY_CAFE_NO = C.CAFE_NO\r\n"
				+ "WHERE \r\n"
				+ "    PH.PAY_USER_ID = ?\r\n"
				+ "ORDER BY \r\n"
				+ "    PH.PAY_TIME DESC";

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
				
				// 추가 (김은서)
				p.setPayCafeName(rset.getString("CAFE_NAME")); // 예약한 스터디카페 이름 추가
				p.setPayHistorySeatNo(rset.getString("HISTORY_SEAT_NO")); // 예약 좌석 추가
				p.setPayHistoryStart(rset.getString("HISTORY_START")); // 예약 시작 시간 추가
				p.setPayHistoryEnd(rset.getString("HISTORY_END")); // 예약 종료 시간 추가
				
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
