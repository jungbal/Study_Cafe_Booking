package kr.or.iei.cafe.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.or.iei.cafe.model.dao.CafeDao;
import kr.or.iei.cafe.model.vo.Cafe;
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

	    int viewNoticeCnt = 10; // 한 페이지당 보여줄 글 수
	    int end = viewNoticeCnt * reqPage;
	    int start = end - viewNoticeCnt + 1;

	    ArrayList<Cafe> list = dao.selectAllCafe(conn, start, end);

	    // 전체 글 수 조회
	    int totCnt = dao.selectTotalCount(conn);
	    int totPage = (totCnt % viewNoticeCnt == 0) ? (totCnt / viewNoticeCnt) : (totCnt / viewNoticeCnt + 1);

	    int pageNaviSize = 5;
	    int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

	    StringBuilder pageNavi = new StringBuilder();
	    pageNavi.append("<ul class='flex justify-center items-center space-x-2 mt-4'>");

	    // 이전 버튼
	    if (pageNo != 1) {
	        pageNavi.append("<li>");
	        pageNavi.append("<a href='/manager/cafeManage?reqPage=")
	                .append(pageNo - 1)
	                .append("' class='px-3 py-1 rounded-md border border-gray-300 text-gray-700 hover:bg-gray-100 transition'>&larr;</a>");
	        pageNavi.append("</li>");
	    }

	    // 페이지 번호들
	    for (int i = 0; i < pageNaviSize && pageNo <= totPage; i++) {
	        pageNavi.append("<li>");
	        if (pageNo == reqPage) {
	            pageNavi.append("<a href='/manager/cafeManage?reqPage=")
	                    .append(pageNo)
	                    .append("' class='px-3 py-1 rounded-md border border-blue-500 bg-blue-500 text-white font-semibold'>");
	        } else {
	            pageNavi.append("<a href='/manager/cafeManage?reqPage=")
	                    .append(pageNo)
	                    .append("' class='px-3 py-1 rounded-md border border-gray-300 text-gray-700 hover:bg-gray-100 transition'>");
	        }
	        pageNavi.append(pageNo);
	        pageNavi.append("</a></li>");
	        pageNo++;
	    }

	    // 다음 버튼
	    if (pageNo <= totPage) {
	        pageNavi.append("<li>");
	        pageNavi.append("<a href='/manager/cafeManage?reqPage=")
	                .append(pageNo)
	                .append("' class='px-3 py-1 rounded-md border border-gray-300 text-gray-700 hover:bg-gray-100 transition'>&rarr;</a>");
	        pageNavi.append("</li>");
	    }

	    pageNavi.append("</ul>");

	    ListData<Cafe> listData = new ListData<>();
	    listData.setList(list);
	    listData.setPageNavi(pageNavi.toString());

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
       boolean isAllSuccess = true;	//isAllSuccess(boolena 자료형)로 반환해주게 되면 어떤 작업이 성공했는지 실패했는지 알림창을 보여줄 구체적인 메시지 정보를 만들 수 없다.
       Map<String, String> resultMap = new HashMap<String, String>();

       for (Map.Entry<String, String> entry : updateMap.entrySet()) {
           String cafeNo = entry.getKey();
           String statusValue = entry.getValue();
           int result = 0;

           switch (statusValue) {
               case "1": // 수정대기 승인

                  result = dao.updateWait(conn, cafeNo);
                  resultMap.put(cafeNo, result > 0 ? "수정 승인 완료" : "수정 승인 실패");

                //  result = dao.updateWait(conn, cafeNo);

                   break;
               case "2": // 등록대기 승인
                   result = dao.insertWait(conn, cafeNo);
                   resultMap.put(cafeNo, result > 0 ? "등록 승인 완료" : "등록 승인 실패");
                   break;
               case "3": // 반려: 처리 안 함
                   continue;
               case "4": // 삭제 처리
                   int rst = dao.updateRole(conn, cafeNo);
                   if (rst > 0) {
                       result = dao.deleteHost(conn, cafeNo);
                   }
                   resultMap.put(cafeNo, result > 0 ? "삭제완료 " : "삭제 실패");
                   break;
               default: // 유효하지 않은 값
                   isAllSuccess = false;
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
    	   }else {
    		   JDBCTemplate.rollback(conn);
    	   }
	       JDBCTemplate.close(conn);
	       return resultMap;
	   }
       

	// 업체(호스트) 신청
	public int insertCafe(Cafe cafeInfo, String loginId) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.insertCafe(conn, cafeInfo, loginId);
		
		int hostRequestResult = dao.insertHostRqst(conn, cafeInfo);
		
		if(result>0 && hostRequestResult>0) {
			JDBCTemplate.commit(conn);
		}else {
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



}
