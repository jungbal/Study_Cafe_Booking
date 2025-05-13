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
<!-- 상세 페이지(/cafeDetail/info)로 가는 버튼 만들기. -->
<a href="/cafeDetail">상세 페이지</a>

<!-- 검색 폼 -->
<form action="/search" method="get">
    <input type="text" name="srchStr" placeholder="검색어를 입력해주세요.">
    <button type="submit">검색</button>
</form>

<br><br><br>
<hr>
<%-- 성우님 개발 --%>
<a href="/member/loginFrm">로그인</a> <br>
<a href="/join">회원가입</a>


<br><br><br>
<hr>
<a><관리자 페이지 메뉴></a><br> <%-- 휘훈님 개발 --%>
<a href="/manager/userManage?reqPage=1">이용자 관리</a>
<a href="/manager/cafeManage">업체 관리</a>
<a href="/manger/cafeApplyChk">업체신청정보열람</a>
<a href="/manager/chkReport">신고접수열람</a>

</body>
</html>
