package kr.or.iei.cafe.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.cafe.model.dao.CafeDao;
import kr.or.iei.cafe.model.vo.Cafe;
import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.common.ListData;

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
		
		int end = viewNoticeCnt * reqPage;	// 10 * 회원 수 
		int start = end - viewNoticeCnt + 1;	// 총 페이지 수 - 10 + 1
		
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

	
	

}
