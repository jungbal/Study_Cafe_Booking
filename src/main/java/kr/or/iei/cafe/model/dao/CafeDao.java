package kr.or.iei.cafe.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cafe.model.vo.Cafe;
import kr.or.iei.cafe.model.vo.History;
import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.member.model.vo.Member;

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
		
		String query = "UPDATE tbl_user SET user_role = 3 WHERE user_id IN (SELECT host_id FROM tbl_cafe WHERE cafe_no = ?)";
		
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

	// 결제내역 테이블 업데이트
	public int insertPayHistory(Connection conn, String ticketPrice, String userId) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		// 일단 결제 수단을 자동으로 '카드'로 입력되어 설정
		String query = "INSERT INTO TBL_PAY_HISTORY (\r\n"
				+ "    PAY_ID, PAY_TIME, PAY_STATUS, PAY_AMOUNT, PAY_METHOD, PAY_USER_ID\r\n"
				+ ") \r\n"
				+ "VALUES (\r\n"
				+ "    TO_CHAR(SEQ_TBL_PAY_HISTORY.NEXTVAL), SYSDATE, '완료', ?, '카드', ?\r\n"
				+ ")";
				
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ticketPrice);
			pstmt.setString(2, userId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	// 이용내역 테이블 업데이트
	public int insertHistory(Connection conn, String ticketId, String ticketPrice, String ticketHour, String cafeNo, String seatNo,
	        String userId) {
	    PreparedStatement pstmt = null;
	    int result = 0;

	    // SYSDATE + ticketHour (시간 더하기) : Oracle에서는 DATE + 숫자 = 날짜 + 일수이므로, 시간으로 변환하려면 ticketHour / 24
	    String query = "INSERT INTO TBL_HISTORY (" +
	            "HISTORY_ID, HISTORY_START, HISTORY_END, HISTORY_CAFE_NO, HISTORY_SEAT_NO, HISTORY_TICKET_ID, HISTORY_USER_ID" +
	            ") VALUES (" +
	            "TO_CHAR(SEQ_TBL_PAY_HISTORY.NEXTVAL), SYSDATE, SYSDATE + (? / 24), ?, ?, ?, ?" +
	            ")";

	    try {
	        pstmt = conn.prepareStatement(query);

	        // ticketHour는 시간 숫자로 변환해서 넣어야 함
	        pstmt.setInt(1, Integer.parseInt(ticketHour)); // SYSDATE + (ticketHour/24)
	        pstmt.setString(2, cafeNo);
	        pstmt.setString(3, seatNo);
	        pstmt.setString(4, ticketId);
	        pstmt.setString(5, userId);

	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

	// 리뷰 내역 있는지 확인
	public Member isReviewHistory(Connection conn, String userId) {
		PreparedStatement pstmt = null;
	      ResultSet rset = null;
	      Member reviewMember = null;
	      
	      String query = "select * from tbl_history where history_user_id = ?"; //and 연산자 사용하여 아이디와 비밀번호 둘 다 일치하는 회원정보 가져오기위해서 and.
	      
	      try {
	         pstmt = conn.prepareStatement(query);
	         pstmt.setString(1, userId);
	         
	         rset = pstmt.executeQuery();
	         
	         //조회되는 행의 갯수 하나 멤버 하나만 일치할것.
	         if(rset.next()) {
	        	 reviewMember = new Member();
	        	 reviewMember.setUserId(userId);

	         }
	         
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }finally {
	         JDBCTemplate.close(rset);
	         JDBCTemplate.close(pstmt);
	      }
	      return reviewMember;
	}

	// 좌석 사용 여부 판단
	public ArrayList<History> isSeatAvailable(Connection conn, String cafeNo) {
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    ArrayList<History> seatList = new ArrayList<History>();

	    String query = "SELECT * "
	                 + "FROM tbl_history "
	                 + "WHERE history_cafe_no = ? "
	                 + "AND SYSDATE BETWEEN history_start AND history_end";

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, cafeNo);
	        rset = pstmt.executeQuery();

	        while (rset.next()) {
	            History history = new History();
	            // History 객체에 ResultSet 데이터 매핑
	            // 예: history.setSeatNo(rset.getString("seat_no"));
	            // 필요한 컬럼을 여기에 세팅
	            history.setHistorySeatNo(rset.getString("history_seat_no"));
	            seatList.add(history);
	            
	            System.out.println("Added History seatNo: " + history.getHistorySeatNo());
	        }
	        
	        System.out.println("seatList size after query: " + seatList.size());

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(rset);
	        JDBCTemplate.close(pstmt);
	    }

	    return seatList;
	}

	public History isUserAvailable(Connection conn, String loginId) {
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    History history = null;

	    String query = "SELECT * FROM tbl_history "
	                 + "WHERE history_user_id = ? "
	                 + "AND SYSDATE BETWEEN history_start AND history_end";

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, loginId);

	        rset = pstmt.executeQuery();

	        if (rset.next()) {
	            history = new History();
	            history.setHistoryUserId(loginId);
	            history.setHistoryCafeNo(rset.getString("history_cafe_no"));
	            history.setHistoryStart(rset.getString("history_start"));  // Timestamp 타입으로 받기
	            history.setHistoryEnd(rset.getString("history_end"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(rset);
	        JDBCTemplate.close(pstmt);
	    }
	    return history;
	}


}
