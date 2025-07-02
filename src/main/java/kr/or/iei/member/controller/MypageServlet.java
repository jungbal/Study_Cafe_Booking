package kr.or.iei.member.controller;

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
import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.service.PayService;
import kr.or.iei.member.model.vo.Member;
import kr.or.iei.member.model.vo.PayHistory;

@WebServlet("/myPage/myInfo")
public class MypageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	//세션 가져오기
        HttpSession session = request.getSession(false);
        
        // loginUser가 세션에 있다면 → loginMember로 변환해서 세션에 저장
        if (session != null && session.getAttribute("loginUser") != null) {
            Login loginUser = (Login) session.getAttribute("loginUser");
            String userId = loginUser.getLoginId();

            MemberService memberService = new MemberService();
            Member member = memberService.selectOneMember(userId);

            if (member != null) {
                session.setAttribute("loginMember", member);
            }
        }
        // 최종 loginMember 세션 가져오기
        Member loginMember = (Member) session.getAttribute("loginMember");

        //로그인 안된 경우 -> 로그인 페이지로 리다이렉트
        if (loginMember == null) {
            response.sendRedirect(request.getContextPath() + "/loginFrm");
            return;
        }

        String userId = loginMember.getUserId();
        
        // 탭 정보 파라미터 받기 (account, review, payment, reservation)
        String tab = request.getParameter("tab");
        if (tab == null || !(tab.equals("account") || tab.equals("review") || tab.equals("payment") || tab.equals("reservation"))) {
            tab = "account";// 기본값 account
        }
        // 탭 정보를 JSP에서 조건 분기로 사용하기 위해 request에 등록
        request.setAttribute("tab", tab);

        if (tab.equals("review")) {
            CommentService service = new CommentService();
            List<Comment> reviewList = service.selectMyCommentsByType(userId, "RV");
            List<Comment> qnaList = service.selectMyCommentsByType(userId, "QA");
            request.setAttribute("reviewList", reviewList);
            request.setAttribute("qnaList", qnaList);
        }
        
        // 결제 내역 또는 예약 내역 탭이라면 데이터 조회
        if (("payment".equals(tab) || "reservation".equals(tab)) && loginMember != null) {
                PayService pService = new PayService();
                ArrayList<PayHistory> list = pService.selectPayHistoryByUser(userId);
                session.setAttribute("payList", list);
        }
    	
    	// myPage.jsp로 포워딩
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/member/myPage.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}