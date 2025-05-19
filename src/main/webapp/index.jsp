<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
</head>
<body>

<!-- 검색 폼 -->
<form action="/search" method="get">
    <input type="text" name="srchStr" placeholder="검색어를 입력해주세요.">
    <button type="submit">검색</button>
</form>

<br><br><br>
<hr>

		<%-- "정원님 테스트용"--%>
			 
			<c:if test="${empty sessionScope.loginMember}">
			<a href="${pageContext.request.contextPath}/member/loginFrm">로그인</a> 
			</c:if>
			
			<c:if test="${not empty sessionScope.loginMember}">
			<a href="${pageContext.request.contextPath}/member/loginFrm">로그인</a>
			<a href="/LogoutServlet">로그아웃</a> 
			<a href="/myPage/chkPwFrm">마이페이지</a>
			</c:if>
			 
			 
<%-- 로그인 안된 상태 --%>
	<%-- <c:if test="${empty sessionScope.loginCafe}">
		<a href="/loginFrm">로그인</a>
		<a href="/joinFrm">회원가입</a>
	</c:if> --%>		 
	
<%-- 로그인 되어있는 상태 --%>
	<c:if test="${not empty sessionScope.loginCafe}">
		<a href="/LogoutServlet">로그아웃</a> 
		<a href="#">마이페이지</a>
		<a href="#">내 리뷰/Q&A</a><br>
		
	<%-- 일반이용자 메뉴 --%>
		<c:if test="${3 == sessionScope.role}">
			<%-- 여기서 분기 한 번 더 해야함 호스트 신청 한 상태면 수정만 나오게--%>
			<a href="/applyCafe">업체(호스트) 신청</a>
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
</body>
</html>
