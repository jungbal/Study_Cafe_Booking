<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<main class = 'content'>
		<section class = 'section notice-list-wrap'>
			<div class = 'page-title'>신고 목록</div>
			<div class = 'list-content'>
				<table class = 'tbl tbl-hover'>
					<tr>
						<th style = 'width:10%;'>신고 번호</th>
						<th style = 'width:10%;'>신고한 사용자ID</th>
						<th style = 'width:10%;'>신고된 사용자ID</th>
						<th style = 'width:10%;'>신고 사유</th>
					</tr>
					<c:forEach var = 'report' items = "${reportList}">
						<tr>
							<th>${report.reportId}</th>
							<th>${report.reporterId}</th>
							<th>${report.targetCommentId}</th>
							<th>${report.reportCodeId}</th>
						</tr>
					</c:forEach>
				</table>
			</div>
		</section>
	</main>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
</body>
</html>