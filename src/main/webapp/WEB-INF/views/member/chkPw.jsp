<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>비밀번호 확인</title>
<link rel="stylesheet" href="/resources/css/common.css" />
<link type="text/css" rel="stylesheet" href="/resources/css/mypage.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<div class="chkPw-wrapper">
	<h2>비밀번호 확인</h2>
	<form action="/myPage/chkPw" method="post">
		<input type="password" name="userPw" placeholder="비밀번호를 입력하세요" required>
		<button type="submit">확인</button>
	</form>
</div>
</body>
</html>
