<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>비밀번호 확인</title></head>
<body>
	<h2>비밀번호 확인</h2>
	<form action="/myPage/chkPw" method="post">
		<label for="userPw">비밀번호:</label>
		<input type="password" name="userPw" required>
		<button type="submit">확인</button>
	</form>
</body>
</html>
