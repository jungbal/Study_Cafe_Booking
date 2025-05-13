<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이용자 목록</title>
<style>
	.pageNavi{
		margin-top : 30px;
	}
	.list-content{
		heigth : 500px;
	}
</style>
</head>
<body>
	<div class = 'wrap'>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<main class = 'content'>
		<section class = 'section notice-List-wrap'>
			<div class = 'page-title'>이용자 목록</div>
			<div class = 'list-content'>
				<table class = 'tbl tbl-hover'>
					<tr>
						<th style = "width:30%;">이용자ID</th>
						<th style = "width:30%;">권한</th>
						<th style = "width:30%;">휴대폰번호</th>
						<th style = "width:30%;">회원삭제</th>
					</tr>
					<c:forEach var = "user" items = "${userList}">	<%-- userManageServlet에서 userList로 가져옴 --%>
					<tr>
						<td>${user.userId}</td>
						<td>${user.userRole}</td>
						<td>${user.userPhone }</td>
						
					</tr>
					</c:forEach>
				</table>
			</div>
			<div id = 'pageNavi'>${pageNavi}</div>
		</section>
	</main>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	</div>
</body>
</html>