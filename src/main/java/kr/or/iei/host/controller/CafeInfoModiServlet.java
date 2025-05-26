package kr.or.iei.host.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.Login.model.vo.Login;
import kr.or.iei.comment.model.service.CommentService;
import kr.or.iei.comment.model.vo.Comment;

import kr.or.iei.host.model.service.HostService;
import kr.or.iei.host.model.vo.Cafe;
import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.service.PayService;
import kr.or.iei.member.model.vo.Member;
import kr.or.iei.member.model.vo.PayHistory;

/**
 * Servlet implementation class CafeInfoModiServlet
 */
@WebServlet("/host/cafeInfoModi")
public class CafeInfoModiServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CafeInfoModiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // Member 세션 가져오기
      //HttpSession session = request.getSession(false);
        //Member loginMember = (Member) session.getAttribute("loginMember");
      HttpSession session = request.getSession(false);
      
      //cafe 로그인 정보만 있고 member 정보가 없는 경우 -> member 정보 조회 후 세션에 저장
      if (session != null && session.getAttribute("loginCafe") != null && session.getAttribute("loginMember") == null) {
          Login loginCafe = (Login) session.getAttribute("loginCafe");
          String userId = loginCafe.getLoginId();

          Member loginMember = new MemberService().selectOneMember(userId);
          session.setAttribute("loginMember", loginMember);
          
      }
      
      //세션에서 로그인 회원 정보 꺼내기 
      Member loginMember = (Member) session.getAttribute("loginMember");
      
      	//로그인 정보 없으면 로그인 페이지 이동 
        if (loginMember == null) {
            response.sendRedirect(request.getContextPath() + "/member/loginFrm");
            return;
        }

        String userId = loginMember.getUserId();
        
        // 탭 정보 파라미터 받기 (account, review, payment)
        String tab = request.getParameter("tab");
        if (tab == null || !(tab.equals("account") || tab.equals("review") || tab.equals("payment"))) {
            tab = "account";// 기본값 account
        }
        // 탭 정보를 JSP에서 조건 분기로 사용하기 위해 request에 등록
        request.setAttribute("tab", tab);

        //리뷰 탭일 경우 : 리뷰, QNA 불러오기 
        if (tab.equals("review")) {
            CommentService service = new CommentService();
            List<Comment> reviewList = service.selectMyCommentsByType(userId, "RV");
            List<Comment> qnaList = service.selectMyCommentsByType(userId, "QNA");
            request.setAttribute("reviewList", reviewList);
            request.setAttribute("qnaList", qnaList);
        }
        
        // 결제 내역 탭이라면 데이터 조회
       if ("payment".equals(tab) && loginMember != null) {
          PayService pService = new PayService();
          ArrayList<PayHistory> list = pService.selectPayHistoryByUser(userId);
          session.setAttribute("payList", list);
       }
       
       // myPage.jsp로 포워딩
       HostService service = new HostService();
       Cafe cafe = service.selectCafeByNo(userId);
       
       request.setAttribute("cafe", cafe);
       
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/host/cafeInfoModi.jsp");
        view.forward(request, response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // TODO Auto-generated method stub
      doGet(request, response);
   }

}
