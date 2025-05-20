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
      
      //한 페이지에서 보여줄 회원 수 
      int viewNoticeCnt = 10;
      
      int end = viewNoticeCnt * reqPage;   // 10 * 회원 수 
      int start = end - viewNoticeCnt + 1;   // 총 페이지 수 - 10 + 1
      
      ArrayList<Cafe> list = dao.selectAllCafe(conn,start,end);
      
      // 전체 회원 수 조회 (totCnt)
      int totcnt = dao.selectTotalCount(conn);
      
      int totPage = 0;
      
      if(totPage % viewNoticeCnt > 0) {
         totPage = totcnt / viewNoticeCnt + 1;
      }else {
         totPage = totcnt / viewNoticeCnt;
      }
      
      // 페이지네이션 사이즈 10으로 지정 따라서 < 1 2 3 4 5 6 7 8 9 10>
      int pageNaviSize = 5;
      
      //(총 페이지 수 -1 /10) * 10 + 1 = ex) 총페이지 수 (5-1) => 4 *10 = 40 + 1 = 41(pageNo) 
      int pageNo = ((reqPage-1)/pageNaviSize)* pageNaviSize + 1;
      
      String pageNavi = "<ul class = 'pagination circle-style'>";
      
      
      // 이전 버튼
      if(pageNo != 1) {
         pageNavi += "<li>";
         pageNavi += "<a class='page-item' href = '/manager/cafeManage?reqPage=" +(pageNo -1)+"'>";
         pageNavi += "<span class = 'material-icons'>chevron_left</span>";
         pageNavi += "</a></li>";
   }
      for(int i=0; i<pageNaviSize; i++) {
        pageNavi += "<li>";
        
        // 페이지 번호 작성 중, 사용자가 요청한 페이지 일 때 클래스를 다르게 지정하여 시각적인 효과
        if(pageNo == reqPage) {// 현재 번호가 사용자가 요청한 번호일 때
           pageNavi += "<a class='page-item active-page' href='/manager/cafeManage?reqPage="+ pageNo +"'>";
        }else { // 현재 번호가 사용자가 요청한 번호가 아닐 때
           pageNavi += "<a class='page-item' href='/manager/cafeManage?reqPage="+ pageNo +"'>";
        }
        
        pageNavi += pageNo; // 시작태그와 종료태그 사이에 작성되는 값
        pageNavi += "</a></li>";
        pageNo++;
        
        // 설정한 pageNaviSize만큼 항상 그리지 않고, 마지막 페이지 그렸으면 더 이상 생성하지 않도록 처리
        // ex) 총 게시글 갯수가 130개면, 13개 페이지가 그려져야 함. 근데 pageNaviSize만큼 반복문 처리하면 11, 12, 13, 14, 15까지 모두 그려진다.
        //      => 13까지만 그리고 반복문 종료.
        if(pageNo > totPage) {
           break;
        }
     }
    // 다음 버튼
    if(pageNo <= totPage) {
       pageNavi += "<li>";
       pageNavi += "<a class='page-item' href='/manager/cafeManage?reqPage="+ pageNo +"'>";
       pageNavi += "<span class='material-icons'>chevron_right</span>";
       pageNavi += "</a></li>";
    }
    
    pageNavi += "</ul>"; // servlet에 pageNavi 보내줘야 함
    
    /*
     * 서블릿으로 리턴해야되는 값 => 게시글 리스트(list)와 페이지 하단에 보여줄 페이지네이션(pageNavi)
     * 자바에서 메소드를 하나의 값만을 리턴할 수 있음 => 두 개의 값을 저장할 수 있는 객체 ListData 생성
     * */
    
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

	        // 2. 결제가 성공했을 경우에만 이용내역 업데이트 시도
	        int historyResult = 0;
	        if (payResult > 0) {
	            historyResult = dao.insertHistory(conn, ticketId, ticketPrice, ticketHour, cafeNo, seatNo, userId);
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


       if (isAllSuccess) {
    	   int result1 = dao.insertHostRequest(conn, cafeNo);
    	   if(result1>0) {
    		   JDBCTemplate.commit(conn); 
    	   }else {
    		   JDBCTemplate.rollback(conn);
    	   }
       } else {
           JDBCTemplate.rollback(conn);
       }

       JDBCTemplate.close(conn);
       return resultMap;
   }
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



}
