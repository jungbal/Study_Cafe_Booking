<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신청 정보</title>
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<h2>카페 신청 정보</h2>
	<table>
		<tr>
			<th>카페 이름</th>
			<td>${cafe.cafeName}</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>${cafe.cafeAddr}</td>
		</tr>
		<tr>
			<th>전화번호</th>
			<td>${cafe.cafePhone}</td>
		</tr>
		<tr>
			<th>사업자번호</th>
			<td>${cafe.cafeBiznum}</td>
		</tr>
		<tr>
			<th>영업시간</th>
			<td>${cafe.cafeStartHour} ~ ${cafe.cafeEndHour}</td>
		</tr>
		<tr>
			<th>호스트 ID</th>
			<td>${cafe.hostId}</td>
		</tr>
		<tr>
			<th>카페 소개</th>
			<td>${cafe.cafeIntroduce}</td>
		</tr>
		<tr>
			<th>상세 소개</th>
			<td>${cafe.cafeIntroDetail}</td>
		</tr>
	</table>

	<br>
	<button onclick="window.close();">닫기</button>
</body>
</html>