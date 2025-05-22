package kr.or.iei.cafe.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.or.iei.cafe.model.dao.CafeDao;
import kr.or.iei.cafe.model.vo.Cafe;
import kr.or.iei.cafe.model.vo.Code;
import kr.or.iei.cafe.model.vo.History;
import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.common.ListData;
import kr.or.iei.member.model.vo.Member;

public class CafeService {
   
   private CafeDao dao;
   
   public CafeService() {
      dao = new CafeDao();
   }
   
   // 검색창 메소드
   public ArrayList<Cafe> srchCafe(String srchStr) {
      Connection conn = JDBCTemplate.getConnection();
         ArrayList<Cafe> list = dao.memberJoin(conn, srchStr);
         
         JDBCTemplate.close(conn);
         return list;
   }

   // 
   public Cafe selectCafeByNo(String cafeNo) {
      Connection conn = JDBCTemplate.getConnection();
      Cafe cafe = dao.selectCafeByNo(conn, cafeNo);
      JDBCTemplate.close(conn);
      return cafe;
   }

   
   
   //정휘훈 파트 
   public ListData<Cafe> selectAllCafe(int reqPage) {
	    Connection conn = JDBCTemplate.getConnection();

	    int viewNoticeCnt = 10; // 한 페이지당 보여줄 업체 수
	    int end = viewNoticeCnt * reqPage;	// ex) 10*페이지 수(2) => 20
	    int start = end - viewNoticeCnt + 1;// ex) 20 - 10 + 1 => 11 시작

	    ArrayList<Cafe> list = dao.selectAllCafe(conn, start, end);

	    
	    int totCnt = dao.selectTotalCount(conn); // 전체 업체 조회(totCnt) 
	    
	    
	    int totPage = 0;
	    if(totCnt % viewNoticeCnt > 0) {	//나눈 나머지가 0보다 클 경우 
	    	totPage = totCnt / viewNoticeCnt + 1;	// 한 페이지 더 보여질 수 있게 설정
	    }else {								//나눈 나머지가 0일 경우
	    	totPage = totCnt/viewNoticeCnt;		//추가할 페이지가 없다.
	    }

	    int pageNaviSize = 5;	//페이지 네이션 5개만 보일 수 있게 설정
	    int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;		//시작번호(pageNo)를 나타내주는 변수 ex) (reqPage(7)-1) / 5 ) * 5 + 1 => 6=> 시작페이지가 6으로 된다. 

	 // 페이지 하단에 보여줄 페이지네이션 HTML 코드 작성
	 // 페이지 하단에 보여줄 페이지네이션 HTML 코드 작성
	    String pageNavi = "<ul class='flex justify-center items-center space-x-2 mt-4'>";

	    // 이전 버튼
	    if (pageNo != 1) {
	        pageNavi += "<li>";
	        pageNavi += "<a class='px-3 py-1 bg-gray-200 rounded hover:bg-gray-300 transition' href='/manager/cafeManage?reqPage=" + (pageNo - 1) + "'>";
	        pageNavi += "&laquo;";
	        pageNavi += "</a></li>";
	    }

	    // 페이지 번호 버튼
	    for (int i = 0; i < pageNaviSize; i++) {
	        pageNavi += "<li>";

	        if (pageNo == reqPage) {
	            // 현재 페이지
	            pageNavi += "<a class='px-3 py-1 bg-blue-500 text-white rounded font-bold' href='/manager/cafeManage?reqPage=" + pageNo + "'>";
	        } else {
	            // 다른 페이지
	            pageNavi += "<a class='px-3 py-1 bg-gray-200 rounded hover:bg-gray-300 transition' href='/manager/cafeManage?reqPage=" + pageNo + "'>";
	        }

	        pageNavi += pageNo;
	        pageNavi += "</a></li>";
	        pageNo++;

	        // 마지막 페이지를 넘어서면 반복 중지
	        if (pageNo > totPage) {
	            break;
	        }
	    }

	    // 다음 버튼
	    if (pageNo <= totPage) {
	        pageNavi += "<li>";
	        pageNavi += "<a class='px-3 py-1 bg-gray-200 rounded hover:bg-gray-300 transition' href='/manager/cafeManage?reqPage=" + pageNo + "'>";
	        pageNavi += "&raquo;";
	        pageNavi += "</a></li>";
	    }

	    pageNavi += "</ul>";
	      
	      ListData<Cafe> listData = new ListData<Cafe>();
	      listData.setList(list);
	      listData.setPageNavi(pageNavi);
	      
	      
	      JDBCTemplate.close(conn);
	      return listData;
	   }

   /*
   public int selectCafeList(String cafeNo) {
      Connection conn = JDBCTemplate.getConnection();
      
      
      
      //1. DB 유저 권한 수정
      int rst = dao.updateRole(conn,cafeNo);
      
      int result = 0;
      
      if(rst > 0) {
         //2. 수정 뒤 카페 삭제
          result = dao.deleteHost(conn,cafeNo);
         
         if(result > 0) {
            JDBCTemplate.commit(conn);
         }else {
            JDBCTemplate.rollback(conn);
         }
      }else {
         JDBCTemplate.rollback(conn);
      }
      
      JDBCTemplate.close(conn);
      
      return result;
   }
   */

	public int insertPayAndHistory(String ticketId, String ticketPrice, String ticketHour, String cafeNo, String seatNo, String userId) {
	    Connection conn = JDBCTemplate.getConnection();
	    int result = 0;

	    try {
	        // 1. 결제 테이블 업데이트하기
	        int payResult = dao.insertPayHistory(conn, ticketPrice, userId);
	        
	        //2. 결제 테이블에서 pay_id 가져오기
	        String payId = dao.selectPayIdByUserId(conn, userId);

	        // 2. 결제가 성공했을 경우에만 이용내역 업데이트 시도
	        int historyResult = 0;
	        if (payResult > 0) {
	            historyResult = dao.insertHistory(conn, ticketId, ticketPrice, ticketHour, cafeNo, seatNo, userId, payId);
	        }

	        // 3. 두 작업 모두 성공하면 커밋, 아니면 롤백
	        if (payResult > 0 && historyResult > 0) {
	            JDBCTemplate.commit(conn);
	            result = 1; // 성공
	        } else {
	            JDBCTemplate.rollback(conn);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JDBCTemplate.rollback(conn);
	    } finally {
	        JDBCTemplate.close(conn);
	    }

	    return result;
	}

	// 리뷰 이력이 있는지 select 하는 메소드
	public Member isReviewHistory(String userId, String cafeNo) {
		Connection conn = JDBCTemplate.getConnection();
		Member member = dao.isReviewHistory(conn, userId, cafeNo);
		
		return member;
	}


	public ArrayList<History> isSeatAvailable(String cafeNo) {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<History> seatList = dao.isSeatAvailable(conn, cafeNo);
		
		if (seatList == null) {
	        System.out.println("service에서 seatList가 null 입니다.");
	        seatList = new ArrayList<>(); // null 방지용 빈 리스트 생성
	    } else {
	        for(History h : seatList) {
	            System.out.println("service에서 사용중 좌석 번호: " + h.getHistorySeatNo());
	        }
	    }
		
		return seatList;
	}

	public History isUserAvailable(String loginId) {
		Connection conn = JDBCTemplate.getConnection();
		History history = dao.isUserAvailable(conn, loginId);
		
		return history;
	}

   // 업체 관리 승인/반려 시 
	public Map<String, String> updateCafeStatus(Map<String, String> updateMap) {
	    Connection conn = JDBCTemplate.getConnection();
	    boolean isAllSuccess = true;
	    Map<String, String> resultMap = new HashMap<>();

	    for (Map.Entry<String, String> entry : updateMap.entrySet()) {
	        String cafeNo = entry.getKey(); //105
	        String statusValue = entry.getValue(); //3 (3_B2이 안 옴)
	        int result = 0;
	        
	     // 반려일 경우: statusValue = "3_B1" 형식
	        String statusValue2 = statusValue;
	        String rejectCode = null;

	        if (statusValue.startsWith("3_")) {
	            String[] parts = statusValue.split("_", 2);
	            if (parts.length == 2) {
	                statusValue2 = parts[0];   // "3"
	                rejectCode = parts[1];     // "B1"
	            }
	        }

	        switch (statusValue2) { // statusValue을 statusValue2에 넣기
	            case "1": // 수정대기 승인
	                result = dao.updateWait(conn, cafeNo);
	                resultMap.put(cafeNo, result > 0 ? "수정 승인 완료" : "수정 승인 실패");
	                break;

	            case "2": // 등록대기 승인
	            	 result = dao.insertWait(conn, cafeNo);

		                if (result > 0) {
		                    int roleResult = dao.updateUserRoleToHost(conn, cafeNo);
		                    if (roleResult > 0) {
		                        resultMap.put(cafeNo, "등록 승인 완료 (권한 변경 완료)");
		                    } else {
		                        resultMap.put(cafeNo, "등록 승인 됐지만 권한 변경 실패");
		                        isAllSuccess = false;
		                    }
		                } else {
		                    resultMap.put(cafeNo, "등록 승인 실패");
		                    isAllSuccess = false;
		                }
		                break;

	            case "3":  // 반려일 경우
	            	result  = dao.insertCodeName(conn, cafeNo, rejectCode);
	            	resultMap.put(cafeNo, result > 0 ? "반려 사유 입력 완료" : "반려 사유 입력 실패");
	            	break;

	            case "4": // 삭제 처리
	                int rst = dao.updateRole(conn, cafeNo); // 권한 먼저 바꿈
	                if (rst > 0) {
	                    result = dao.deleteHost(conn, cafeNo); // host 삭제
	                }
	                resultMap.put(cafeNo, result > 0 ? "삭제 완료" : "삭제 실패");
	                break;

	            case "0": // 아무 것도 선택 안 함
	                continue;

	            default: // 유효하지 않은 값
	                isAllSuccess = false;
	                resultMap.put(cafeNo, "잘못된 상태값");
	                break;
	        }

	        if (result == 0) {
	            System.err.println("처리 실패: cafeNo = " + cafeNo + ", status = " + statusValue);
	            isAllSuccess = false;
	            break;
	        }
	    }

	    if (isAllSuccess) {
	        JDBCTemplate.commit(conn);
	    } else {
	        JDBCTemplate.rollback(conn);
	    }

	    JDBCTemplate.close(conn);
	    return resultMap;
	}


       


	public int insertCafe(Cafe cafeInfo, String loginId) {
	    Connection conn = JDBCTemplate.getConnection();

	    int result = dao.insertCafe(conn, cafeInfo, loginId);
	    int hostRequestResult = dao.insertHostRqst(conn, cafeInfo);
	    Cafe cafe = dao.selectOneCafe(conn, loginId);
	    System.out.println("cafeService에서 cafe.getCafeNo(): " + cafe.getCafeNo());
	    int insertImageResult = dao.insertDefaultImage(conn, cafe.getCafeNo());

	    int updateUserStatusResult = 0;
	    if (result > 0 && hostRequestResult > 0 && insertImageResult > 0) {
	        updateUserStatusResult = dao.updateUserStatus(conn, loginId);
	    }

	    if (result > 0 && hostRequestResult > 0 && insertImageResult > 0 && updateUserStatusResult > 0) {
	        JDBCTemplate.commit(conn);
	    } else {
	        JDBCTemplate.rollback(conn);
	    }

	    JDBCTemplate.close(conn);

	    return result;
	}
	

	public ArrayList<Cafe> selectMainCafes() {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Cafe> cafeList = dao.selectMainCafes(conn);
		
		return cafeList;
	}


	public Cafe selectOneCafe(String loginId) {
		Connection conn = JDBCTemplate.getConnection();
		Cafe cafeInfo = dao.selectOneCafe(conn, loginId);
		JDBCTemplate.close(conn);
		return cafeInfo;
	}

	public String matchHostId(String loginId) {

		Connection conn = JDBCTemplate.getConnection();
		String hostId = dao.matchHostId(conn, loginId);
		JDBCTemplate.close(conn);
		return hostId;
	}

	public int editCafe(String loginId, Cafe cafeInfo) {
		Connection conn = JDBCTemplate.getConnection();
		int editResult = dao.editResult(conn, loginId, cafeInfo);
		int hostRqstResultV2  = dao.insertHostRqstV2(conn, loginId, cafeInfo);
		
		if(editResult>0 && hostRqstResultV2 >0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return editResult;
	}

	public ArrayList<Code> selectAllCodeId() {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Code> codeList = dao.selectAllCodeId(conn);
		JDBCTemplate.close(conn);
		return codeList;
	}
}
