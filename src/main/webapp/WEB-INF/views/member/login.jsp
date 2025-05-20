<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
	.login-wrap {
		width: 300px;
		margin: 100px auto;
		padding: 30px;
		border: 1px solid #ccc;
		border-radius: 10px;
	}
	.login-wrap input {
		width: 100%;
		padding: 10px;
		margin-bottom: 15px;
	}
</style>
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<div class="login-wrap">
		<h2>로그인</h2>
		<form action="/member/login" method="post">
			<input type="text" name="userId" placeholder="아이디 입력" required>
			<input type="password" name="userPw" placeholder="비밀번호 입력" required>
			<button type="submit">로그인</button>
		</form>
	</div>
</body>
</html>
