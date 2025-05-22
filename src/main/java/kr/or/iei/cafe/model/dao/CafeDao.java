package kr.or.iei.cafe.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import kr.or.iei.Login.model.vo.Login;
import kr.or.iei.cafe.model.vo.Cafe;
import kr.or.iei.cafe.model.vo.Code;
import kr.or.iei.cafe.model.vo.History;
import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.member.model.vo.Member;

public class CafeDao {

	public ArrayList<Cafe> memberJoin(Connection conn, String srchStr) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		ArrayList<Cafe> cafeList = new ArrayList<Cafe>();
		
		// 카페명과 카페 주소에서 동시에 검색어 찾기.
		String query = "SELECT \r\n"
				+ "    c.*, \r\n"
				+ "    i.image_no, \r\n"
				+ "    i.image_name, \r\n"
				+ "    i.image_path\r\n"
				+ "FROM \r\n"
				+ "    tbl_cafe c\r\n"
				+ "LEFT JOIN \r\n"
				+ "    tbl_image i ON c.cafe_no = i.cafe_no\r\n"
				+ "WHERE \r\n"
				+ "    c.cafe_name LIKE ? OR c.cafe_addr LIKE ?";
		
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
				cafe.setCafeEndHour(rset.getString("cafe_end_hour"));
				cafe.setCafeStatus(rset.getString("cafe_status"));
				cafe.setCafeIntroDetail(rset.getString("cafe_intro_detail"));
				cafe.setHostId(rset.getString("host_id"));
				cafe.setCafeImageNo(rset.getString("image_no"));
				cafe.setCafeImageName(rset.getString("image_name"));
				cafe.setCafeImagePath(rset.getString("image_path"));
				
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
		String query = "SELECT \r\n"
				+ "    c.*, \r\n"
				+ "    i.image_no, \r\n"
				+ "    i.image_name, \r\n"
				+ "    i.image_path\r\n"
				+ "FROM \r\n"
				+ "    tbl_cafe c\r\n"
				+ "LEFT JOIN \r\n"
				+ "    tbl_image i ON c.cafe_no = i.cafe_no\r\n"
				+ "WHERE \r\n"
				+ "    c.cafe_no = ?\r\n"
				+ "";
		
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
				cafe.setCafeImageNo(rset.getString("image_no"));
				cafe.setCafeImageName(rset.getString("image_name"));
				cafe.setCafeImagePath(rset.getString("image_path"));
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
		
		String query =  "SELECT *\r\n"
				+ "FROM (\r\n"
				+ "    SELECT ROWNUM AS RNUM, A.*\r\n"
				+ "    FROM (\r\n"
				+ "        SELECT\r\n"
				+ "            sub.cafe_no,\r\n"
				+ "            sub.cafe_name,\r\n"
				+ "            sub.cafe_phone,\r\n"
				+ "            sub.cafe_addr,\r\n"
				+ "            sub.cafe_biznum,\r\n"
				+ "            sub.cafe_introduce,\r\n"
				+ "            sub.cafe_start_hour,\r\n"
				+ "            sub.cafe_end_hour,\r\n"
				+ "            sub.cafe_status,\r\n"
				+ "            sub.cafe_intro_detail,\r\n"
				+ "            sub.host_id,\r\n"
				+ "            sub.status AS host_request_status,\r\n"
				+ "            sub.apply_date,\r\n"
				+ "            sub.reject_id,\r\n"
				+ "            sub.user_role,\r\n"
				+ "            sub.user_status,\r\n"
				+ "            CASE\r\n"
				+ "                WHEN sub.cafe_status = 'N' AND sub.status = 'N' AND sub.user_role = '3' AND sub.user_status = 'Y' THEN '등록대기'\r\n"
				+ "                WHEN sub.cafe_status = 'N' AND sub.status = 'N' AND sub.user_role = '2' AND sub.user_status = 'Y' THEN '수정대기'\r\n"
				+ "                WHEN sub.cafe_status = 'Y' AND sub.status = 'Y' THEN '승인'\r\n"
				+ "            END AS cafe_manage_status\r\n"
				+ "        FROM (\r\n"
				+ "            SELECT\r\n"
				+ "                c.cafe_no,\r\n"
				+ "                c.cafe_name,\r\n"
				+ "                c.cafe_phone,\r\n"
				+ "                c.cafe_addr,\r\n"
				+ "                c.cafe_biznum,\r\n"
				+ "                c.cafe_introduce,\r\n"
				+ "                c.cafe_start_hour,\r\n"
				+ "                c.cafe_end_hour,\r\n"
				+ "                c.cafe_status,\r\n"
				+ "                c.cafe_intro_detail,\r\n"
				+ "                c.host_id,\r\n"
				+ "                h.status,\r\n"
				+ "                h.apply_date,\r\n"
				+ "                h.reject_id,\r\n"
				+ "                u.user_role,\r\n"
				+ "                u.user_status,\r\n"
				+ "                ROW_NUMBER() OVER (PARTITION BY c.cafe_no ORDER BY h.apply_date ASC) AS rn\r\n"
				+ "            FROM tbl_cafe c\r\n"
				+ "            JOIN tbl_host_request h ON c.cafe_no = h.host_no\r\n"
				+ "            JOIN tbl_user u ON c.host_id = u.user_id\r\n"
				+ "            WHERE \r\n"
				+ "                (c.cafe_status = 'N' AND h.status = 'N' AND u.user_role = '3' AND u.user_status = 'Y')\r\n"
				+ "                OR (c.cafe_status = 'N' AND h.status = 'N' AND u.user_role = '2' AND u.user_status = 'Y')\r\n"
				+ "                OR (c.cafe_status = 'Y' AND h.status = 'Y')\r\n"
				+ "        ) sub\r\n"
				+ "        WHERE sub.rn = 1\r\n"
				+ "    ) A\r\n"
				+ ")\r\n"
				+ "WHERE RNUM >= ? AND RNUM <= ?";
		
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
				cafe.setCafeRejectReason(rset.getString("HOST_REQUEST_STATUS"));
				cafe.setCafeApplyStatus(rset.getString("reject_id"));
				cafe.setCafeManageStatus(rset.getString("cafe_manage_status"));		// Cafe.java에서 미리 선언해둔 cafeManageStatus를 이용 
				
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
			JDBCTemplate.close(pstmt);
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

	public int insertCafe(Connection conn, Cafe cafeInfo, String loginId) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
//		쿼리문에 user_id를 타 패키지 메소드
		
		String query = 
"insert into tbl_cafe (cafe_no, cafe_name, cafe_phone, cafe_addr, cafe_biznum, cafe_introduce, cafe_start_hour, cafe_end_hour, cafe_status, cafe_intro_detail, host_id)"
+ "values (SEQ_TBL_CAFE.nextval, ?, ?, ?, ?, ?, ?, ?, default, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cafeInfo.getCafeName());
			pstmt.setString(2, cafeInfo.getCafePhone());
			pstmt.setString(3, cafeInfo.getCafeAddr());
			pstmt.setString(4, cafeInfo.getCafeBiznum());
			pstmt.setString(5, cafeInfo.getCafeIntroduce());
			pstmt.setString(6, cafeInfo.getCafeStartHour());
			pstmt.setString(7, cafeInfo.getCafeEndHour());
			pstmt.setString(8, cafeInfo.getCafeIntroDetail());
			pstmt.setString(9, loginId);
			
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
	        String userId, String payId) {
	    PreparedStatement pstmt = null;
	    int result = 0;

	    // SYSDATE + ticketHour (시간 더하기) : Oracle에서는 DATE + 숫자 = 날짜 + 일수이므로, 시간으로 변환하려면 ticketHour / 24
	    String query = "INSERT INTO TBL_HISTORY (" +
	            "HISTORY_ID, HISTORY_START, HISTORY_END, HISTORY_CAFE_NO, HISTORY_SEAT_NO, HISTORY_TICKET_ID, HISTORY_USER_ID, PAY_ID" +
	            ") VALUES (" +
	            "TO_CHAR(SEQ_TBL_PAY_HISTORY.NEXTVAL), SYSDATE, SYSDATE + (? / 24), ?, ?, ?, ?, ?" +
	            ")";

	    try {
	        pstmt = conn.prepareStatement(query);

	        // ticketHour는 시간 숫자로 변환해서 넣어야 함
	        pstmt.setInt(1, Integer.parseInt(ticketHour)); // SYSDATE + (ticketHour/24)
	        pstmt.setString(2, cafeNo);
	        pstmt.setString(3, seatNo);
	        pstmt.setString(4, ticketId);
	        pstmt.setString(5, userId);
	        pstmt.setString(6, payId);

	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

	// 해당 카페에 리뷰 내역 있는지 확인
	public Member isReviewHistory(Connection conn, String userId, String cafeNo) {
		PreparedStatement pstmt = null;
	      ResultSet rset = null;
	      Member reviewMember = null;
	      
	      String query = "select * from tbl_history where history_user_id = ? and history_cafe_no = ?"; //and 연산자 사용하여 아이디와 비밀번호 둘 다 일치하는 회원정보 가져오기위해서 and.
	      
	      try {
	         pstmt = conn.prepareStatement(query);
	         pstmt.setString(1, userId);
	         pstmt.setString(2, cafeNo);
	         
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
	        }

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

	public int updateWait(Connection conn, String cafeNo) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			// tbl cafe 업데이트
			pstmt=conn.prepareStatement(
					"update tbl_cafe set cafe_status = 'Y' " + 
					"where cafe_no =?"
			);
			pstmt.setString(1, cafeNo);
			result += pstmt.executeUpdate();
			
			pstmt= conn.prepareStatement(
					"update tbl_host_request set status= 'Y' " + 
					"where host_no = ?"
					);
			pstmt.setString(1, cafeNo);
			result += pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int insertWait(Connection conn, String cafeNo) {
	    PreparedStatement pstmt = null;
	    int result = 0;

	    try {
	        // 1. tbl_cafe 승인 처리
	        pstmt = conn.prepareStatement(
	            "UPDATE tbl_cafe SET cafe_status = 'Y' WHERE cafe_no = ?"
	        );
	        pstmt.setString(1, cafeNo);
	        result += pstmt.executeUpdate();
	        pstmt.close();

	        // 2. tbl_host_request 승인 처리
	        pstmt = conn.prepareStatement(
	            "UPDATE tbl_host_request SET status = 'Y' WHERE host_no = ?"
	        );
	        pstmt.setString(1, cafeNo);
	        result += pstmt.executeUpdate();
	        pstmt.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	        // ❗conn은 서비스에서 닫습니다
	    }

	    return result;
	}




	public Cafe selectOneCafe(Connection conn, String loginId) {
		
		PreparedStatement pstmt= null;
		ResultSet rset = null;
		Cafe cafeInfo = null;
		
		String query = "SELECT *\r\n"
				+ "FROM (\r\n"
				+ "    SELECT \r\n"
				+ "        c.*,\r\n"
				+ "        hr.status AS apply_status,\r\n"
				+ "        cd.code_name AS reject_reason,\r\n"
				+ "        hr.apply_date,\r\n"
				+ "        ROW_NUMBER() OVER (PARTITION BY c.cafe_no ORDER BY hr.apply_date DESC) AS rn\r\n"
				+ "    FROM \r\n"
				+ "        tbl_cafe c\r\n"
				+ "    JOIN \r\n"
				+ "        tbl_host_request hr ON c.cafe_no = hr.host_no\r\n"
				+ "    LEFT JOIN \r\n"
				+ "        tbl_code cd ON hr.reject_id = cd.code_id\r\n"
				+ "    WHERE \r\n"
				+ "        c.host_id = ?\r\n"
				+ ") sub\r\n"
				+ "WHERE rn = 1\r\n"
				+ "ORDER BY apply_date DESC;";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, loginId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				cafeInfo = new Cafe();
				
				cafeInfo.setCafeName(rset.getString("cafe_name"));
				cafeInfo.setCafeAddr(rset.getString("cafe_addr"));
				cafeInfo.setCafePhone(rset.getString("cafe_phone"));
				cafeInfo.setCafeBiznum(rset.getString("cafe_biznum"));
				cafeInfo.setCafeIntroduce(rset.getString("cafe_introduce"));
				cafeInfo.setCafeStartHour(rset.getString("cafe_start_hour"));
				cafeInfo.setCafeEndHour(rset.getString("cafe_end_hour"));
				cafeInfo.setCafeStatus(rset.getString("cafe_status"));
				cafeInfo.setCafeIntroDetail(rset.getString("cafe_intro_detail"));
				cafeInfo.setCafeApplyStatus(rset.getString("apply_status"));
				cafeInfo.setCafeRejectReason(rset.getString("reject_reason"));
				
			}
		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(rset);
				JDBCTemplate.close(pstmt);
			}
					
			return cafeInfo;
		}


	// 메인 페이지에 보여질 카페 (리뷰 / Q&A 많은 순서대로 6개)
	public ArrayList<Cafe> selectMainCafes(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		ArrayList<Cafe> cafeList = new ArrayList<Cafe>();
		
		String query = "SELECT * FROM (\r\n"
				+ "    SELECT \r\n"
				+ "        c.*,\r\n"
				+ "        NVL(cm.comment_count, 0) AS comment_count,\r\n"
				+ "        i.image_path\r\n"
				+ "    FROM \r\n"
				+ "        tbl_Cafe c\r\n"
				+ "    LEFT JOIN (\r\n"
				+ "        SELECT \r\n"
				+ "            comment_cafe_no,\r\n"
				+ "            COUNT(*) AS comment_count\r\n"
				+ "        FROM \r\n"
				+ "            tbl_comment\r\n"
				+ "        GROUP BY \r\n"
				+ "            comment_cafe_no\r\n"
				+ "    ) cm ON c.cafe_no = cm.comment_cafe_no\r\n"
				+ "    LEFT JOIN (\r\n"
				+ "        SELECT \r\n"
				+ "            cafe_no, image_path\r\n"
				+ "        FROM (\r\n"
				+ "            SELECT \r\n"
				+ "                cafe_no, image_path,\r\n"
				+ "                ROW_NUMBER() OVER (PARTITION BY cafe_no ORDER BY image_no) AS rn\r\n"
				+ "            FROM tbl_image\r\n"
				+ "        ) WHERE rn = 1\r\n"
				+ "    ) i ON c.cafe_no = i.cafe_no\r\n"
				+ "    ORDER BY \r\n"
				+ "        cm.comment_count DESC NULLS LAST\r\n"
				+ ") WHERE ROWNUM <= 8\r\n"
				+ "";
		
		try {
			pstmt = conn.prepareStatement(query);
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
				cafe.setCafeImagePath(rset.getString("image_path"));
				
				cafeList.add(cafe);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return cafeList;
	}


	public int insertHostRqst(Connection conn, Cafe cafeInfo) {
		PreparedStatement pstmt= null;
		PreparedStatement pstmt2 = null;
		ResultSet rset = null;
		int hostRqstResult = 0;
		
		String query = "insert into tbl_host_request(apply_no, apply_date, status, reject_id, host_no) "
				+ "values(SEQ_TBL_HOST_REQUEST.nextval, sysdate, default, null, ?)";
				
		
		String sql = "SELECT SEQ_TBL_CAFE.CURRVAL FROM dual";
	
		
		String cafeNo = null;
		
		try {
			pstmt2 = conn.prepareStatement(sql);
			rset = pstmt2.executeQuery();
				if (rset.next()) {
				    cafeNo = rset.getString(1);
				}
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cafeNo);
			hostRqstResult = pstmt.executeUpdate();
			
		

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(pstmt2);
			JDBCTemplate.close(rset);
		}
		
		return hostRqstResult;

		}
	
	public int insertHostRqstV2(Connection conn, String loginId, Cafe cafeInfo) {
	PreparedStatement pstmt= null;
	PreparedStatement pstmt2 = null;
	ResultSet rset = null;
	int hostRqstResultV2 = 0;
	
	String query = "insert into tbl_host_request(apply_no, apply_date, status, reject_id, host_no) "
			+ "values(SEQ_TBL_HOST_REQUEST.nextval, sysdate, default, null, ?)";
			
	String sql = "SELECT cafe_no from tbl_cafe where host_id = ? ";
	
	String cafeNo = null;
	
	try {
		pstmt2 = conn.prepareStatement(sql);
		pstmt2.setString(1, loginId);
		rset = pstmt2.executeQuery();
			if (rset.next()) {
			    cafeNo = rset.getString(1);
			}
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, cafeNo);
		hostRqstResultV2  = pstmt.executeUpdate();
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		JDBCTemplate.close(pstmt);
		JDBCTemplate.close(pstmt2);
		JDBCTemplate.close(rset);
	}
	
	return hostRqstResultV2 ;

	}

	// 호스트 신청 내역 테이블 insert
	public int insertHostRequest(Connection conn, Cafe cafe) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "insert into tbl_host_request (apply_date, status, host_no)\r\n"
				+ "values (sysdate, 'N', ?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cafe.getHostId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public String matchHostId(Connection conn, String loginId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String hostId = null;
		
		String query = "select host_id from tbl_cafe where host_id = ? ";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, loginId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				 hostId = rset.getString("host_id");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return hostId;
	}
	public int updateUserStatus(Connection conn, String loginId) {
	    PreparedStatement pstmt = null;
	    int result = 0;

	    String sql = "UPDATE tbl_user SET user_status = 'Y' WHERE user_id = ?";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, loginId);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

	//권한 바꾸기 일반사용자 -> 호스트
	public int updateUserRoleToHost(Connection conn, String cafeNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "update tbl_user set user_role = 2 where user_id in (select host_id from tbl_cafe where cafe_no = ?)";
		
		try {
			pstmt=conn.prepareStatement(query);
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



	// userId 로 payId 가져오기 (결제내역이 가장 최신인 것)
	public String selectPayIdByUserId(Connection conn, String userId) {
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    String payId = null;
	    
	    // 최신 결제내역을 가져오기 위해 pay_id를 내림차순 정렬, 1건만 조회
	    String query = "SELECT pay_id\r\n"
	    		+ "FROM (\r\n"
	    		+ "    SELECT pay_id\r\n"
	    		+ "    FROM tbl_pay_history\r\n"
	    		+ "    WHERE pay_user_id = ?\r\n"
	    		+ "    ORDER BY pay_id DESC\r\n"
	    		+ ")\r\n"
	    		+ "WHERE ROWNUM = 1\r\n"
	    		+ "";
	    
	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, userId);
	        rset = pstmt.executeQuery();
	        
	        if (rset.next()) {
	            payId = rset.getString("pay_id");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
	    
	    return payId;
	}

	public int editResult(Connection conn, String loginId, Cafe cafeInfo) {
		int editResult = 0;
		PreparedStatement pstmt = null;
		String query = "update tbl_cafe set "
				+ "cafe_name = ?, "
				+ "cafe_phone = ?, "
				+ "cafe_addr = ?, "
				+ "cafe_biznum = ?, "
				+ "cafe_introduce = ?, "
				+ "cafe_start_hour = ?, "
				+ "cafe_end_hour = ?, "
				+ "cafe_intro_detail = ? "
				+ "where host_id = ? ";
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cafeInfo.getCafeName());
			pstmt.setString(2, cafeInfo.getCafePhone());
			pstmt.setString(3, cafeInfo.getCafeAddr());
			pstmt.setString(4, cafeInfo.getCafeBiznum());
			pstmt.setString(5, cafeInfo.getCafeIntroduce());
			pstmt.setString(6, cafeInfo.getCafeStartHour());
			pstmt.setString(7, cafeInfo.getCafeEndHour());
			pstmt.setString(8, cafeInfo.getCafeIntroDetail());
			pstmt.setString(9, loginId);
			
			editResult = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return editResult;
	}
	
	public int insertDefaultImage(Connection conn, Cafe cafeInfo) {
		 PreparedStatement pstmt = null;
		 int result = 0;
		    
		    // 최신 결제내역을 가져오기 위해 pay_id를 내림차순 정렬, 1건만 조회
		    String query = "Insert into tbl_image  (cafe_no, image_name, image_path) values (?, 'defaultCafe.png', '/resources/cafeImage/defaultCafe.png')";
		    
		    try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, cafeInfo.getCafeNo());
				
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

		    String query = "UPDATE user_tbl SET user_status = 'Y' WHERE host_no = ?";

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

	public int insertCodeName(Connection conn, String cafeNo, String rejectCode) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "update tbl_host_request set reject_id =? where host_no = ?";
		
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, rejectCode);
			pstmt.setString(2, cafeNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<Code> selectAllCodeId(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
        ArrayList<Code> codeList = new ArrayList<Code>();
		
        String query = "select * from tbl_code where code_parent='A1'";
        
        try {
			pstmt=conn.prepareStatement(query);
			rset=pstmt.executeQuery();
				
			while(rset.next()) {
				Code code = new Code();
				
                code.setCodeId(rset.getString("code_id"));
                code.setCodeName(rset.getString("code_name"));
                code.setCodeParent(rset.getString("code_parent"));
                
                codeList.add(code);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return codeList;
	}


}
