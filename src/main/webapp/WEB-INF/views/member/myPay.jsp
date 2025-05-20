<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>결제 내역</title>
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<h2>결제 내역</h2>
	<table border="1">
		<tr>
			<th>결제번호</th>
			<th>결제일자</th>
			<th>결제수단</th>
			<th>결제금액</th>
			<th>상태</th>
		</tr>
		<c:forEach var="p" items="${sessionScope.payList}">
			<tr>
				<td>${p.payId}</td>
				<td>${p.payTime}</td>
				<td>${p.payMethod}</td>
				<td>${p.payAmount}원</td>
				<td>${p.payStatus}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>