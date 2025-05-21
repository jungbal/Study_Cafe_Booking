<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>결제 내역</title>
<link type="text/css" rel="stylesheet" href="/resources/css/mypage.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<div class="pay-tab-wrap">
  <button class="pay-tab-btn active">전체 결제내역</button>
</div>

	<table class="pay-table tab-visible">
	<thead>
		<tr>
			<th>결제번호</th>
			<th>결제일자</th>
			<th>결제수단</th>
			<th>결제금액</th>
			<th>상태</th>
			<th>카페명</th> <%-- 추가 --%>
			<th>좌석번호</th>
			<th>예약시작시간</th>
			<th>예약종료시간</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="p" items="${sessionScope.payList}">
			<tr>
				<td>${p.payId}</td>
				<td>${p.payTime}</td>
				<td>${p.payMethod}</td>
				<td>${p.payAmount}원</td>
				<td>${p.payStatus}</td>
				<th>${p.payCafeName}</th>
				<th>${p.payHistorySeatNo}</th>
				<th>${p.payHistoryStart}</th>
				<th>${p.payHistoryEnd}</th>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>