<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업체 리스트</title>
<style>
	#pageNavi{
		margin-top : 30px;
	}
</style>
</head>
<body>
	<div class = 'wrap'>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<main class = 'content'>
		<section class = 'section notice-list-wrap'>
			<div class = 'page-title'>업체 목록</div>
			<div class = 'list-content'>
				<table class = 'tbl tbl-hover'>
					<tr>
						<th style = "width:55%;">업체명</th>
						<th style = "width:15%;">호스트ID</th>
						<th style = "width:20%;">주소</th>
						<th style = "width:20%;">상태</th>
						<th style = "width:20%;">상태변경</th>
					</tr>
					<c:forEach var = "cafe" items = "${cafeList}">
						<tr>
							<td>${cafe.cafeNo}</td>
							<td>${cafe.hostId}</td>
							<td>${cafe.cafeAddr}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div id ='pageNavi'>${pageNavi}</div>
		</section>
	</main>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	</div>
</body>
</html>