<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>검색 결과</title>
</head>
<body>
<%--아직 db에 이미지 저장 안 해놨으므로 default 이미지 사진으로 해놓기--%>

<div class="list-content">
	<table class="tbl tbl-hover">
		<tr>
			<th style="width:55%">카페 이미지</th>
			<th style="width:15%">카페명</th>
			<th style="width:20%">카페주소</th>
		</tr>

		<c:choose>
			<c:when test="${empty list}">
				<tr>
					<td colspan="3" style="text-align:center;">검색 결과가 없습니다.</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="list" items="${list}">
					<tr>
						<td><a href='/cafeDetail?cafeNo=${list.cafeNo}'>카페이미지(예시)</a></td>
						<td>${list.cafeName}</td>
						<td>${list.cafeAddr}</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>

	</table>
</div>
</body>
</html>
