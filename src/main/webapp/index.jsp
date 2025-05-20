<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <% // 메인 페이지 접속 시, 카페리스트 정보 가져오기 위해 설정
    RequestDispatcher rd = request.getRequestDispatcher("/main");
    rd.forward(request, response);
%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
<style>
    .cafe-list {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 20px;
        justify-items: center;
        padding: 20px;
    }

    .cafe-item {
        width: 200px;
        border: 1px solid #ddd;
        border-radius: 10px;
        padding: 15px;
        box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1);
        text-align: center;
        background-color: #fff;
        transition: transform 0.2s, box-shadow 0.2s;
    }

    .cafe-item:hover {
        transform: translateY(-5px);
        box-shadow: 4px 4px 12px rgba(0, 0, 0, 0.2);
    }

    .cafe-item img {
        max-width: 100%;
        height: auto;
        border-radius: 8px;
    }

    .cafe-item p {
        margin: 10px 0 0;
        font-size: 14px;
    }
</style>
</head>
<body>

<!-- 검색 폼 -->
<form action="/search" method="get">
    <input type="text" name="srchStr" placeholder="검색어를 입력해주세요.">
    <button type="submit">검색</button>
</form>

<br><br><br>
<hr>

		<%-- "정원님 테스트용"
			 
<<<<<<< HEAD
		<%-- 	<c:if test="${empty sessionScope.loginMember}">
=======
			 <%-- 
			<c:if test="${empty sessionScope.loginMember}">
>>>>>>> 26ae708544f9a46520d340e9c112325502fd3277
			<a href="${pageContext.request.contextPath}/member/loginFrm">로그인</a> 
			</c:if>
			
			<c:if test="${not empty sessionScope.loginMember}">
			<a href="${pageContext.request.contextPath}/member/loginFrm">로그인</a>
			<a href="/LogoutServlet">로그아웃</a>
			<a href="/myPage/chkPwFrm">마이페이지</a>
<<<<<<< HEAD
			</c:if> --%> 
			 
			 
<%-- 로그인 안된 상태 --%>
	 <c:if test="${empty sessionScope.loginCafe}">
		<a href="/loginFrm">로그인</a>
		<a href="/joinFrm">회원가입</a>
	</c:if> 	 
=======
			</c:if>
			 --%>
			 
<%-- 로그인 안된 상태 --%>

	 <c:if test="${empty sessionScope.loginCafe}">
		<a href="/loginFrm">로그인</a>
		<a href="/joinFrm">회원가입</a>
	</c:if>	 
>>>>>>> 26ae708544f9a46520d340e9c112325502fd3277
	
<%-- 로그인 되어있는 상태 --%>
	<c:if test="${not empty sessionScope.loginCafe}">
		<a href="/LogoutServlet">로그아웃</a> 
		<a href="/myPage/chkPwFrm">마이페이지</a>
		<a href="#">내 리뷰/Q&A</a><br>
		
	<%-- 일반이용자 메뉴 --%>
		<c:if test="${3 == sessionScope.role}">
			<%-- 여기서 분기 한 번 더 해야함 호스트 신청 한 상태면 수정만 나오게
			호스트 신청을 했다면 로그인한 세션 아이디와 호스트 신청내역 id가 같은 
			  --%>
			<a href="/applyCafe">업체(호스트) 신청</a>
			
			<a href="/editCafeFrm">업체(호스트) 수정</a>
			
		</c:if>
		
	<%-- 호스트 메뉴 --%>
		<c:if test="${2 == sessionScope.role}">
			<a href="#">카페 관리</a><br>
			<a href="#">카페 리뷰/Q&A</a>
		</c:if>
		
	<%-- 관리자 메뉴 --%>
		<c:if test="${1 == sessionScope.role}">
			<a href="/manager/userManage?reqPage=1">이용자 관리</a><br>
			<a href="/manager/cafeManage?reqPage=1">업체 관리</a><br>
			<a href="/manger/cafeApplyChk">업체신청정보열람</a><br>
			<a href="/manager/chkReport">신고접수열람</a>
		</c:if>
		
	</c:if>



<br><br><br>
<hr>
<a><관리자 페이지 메뉴></a><br> <%-- 휘훈님 개발 --%>
<a href="/manager/userManage?reqPage=1">이용자 관리</a>
<a href="/manager/cafeManage?reqPage=1">업체 관리</a>
<a href="/manger/cafeApplyChk">업체신청정보열람</a>
<a href="/manager/chkReport?reqPage=1">신고접수열람</a>

<br><br><br>
<hr>

	<%-- 인기많은 카페 top6 (리뷰/Q&A 달린 기준) --%>
<div class="cafe-list">
    <c:forEach var="cafe" items="${cafeList}">
        <div class="cafe-item">
            <img src="${cafe.cafeImagePath}" alt="${cafe.cafeName}">
            <p><strong>${cafe.cafeName}</strong></p>
            <p>${cafe.cafeAddr}</p>
        </div>
    </c:forEach>
</div>

</body>
</html>
