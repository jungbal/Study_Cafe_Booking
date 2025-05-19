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

import kr.or.iei.comment.model.service.CommentService;
import kr.or.iei.comment.model.vo.Comment;
import kr.or.iei.member.model.service.PayService;
import kr.or.iei.member.model.vo.Member;
import kr.or.iei.member.model.vo.PayHistory;

@WebServlet("/myPage/myInfo")
public class MypageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");

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
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/member/myPage.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}