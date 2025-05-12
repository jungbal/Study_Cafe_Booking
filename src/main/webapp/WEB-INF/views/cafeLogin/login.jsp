<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<main>
		<section class="login-contatiner">
			<div>로그인</div>
			<form action="/login/login" method="post" >	
				<div class="input-title">
					<label for="loginId">아이디</label>
				</div>
				<div class="input-item">
					<input type="text" id="loginId" name="loginId">
				</div>
				
				<div class="input-wrap">
					<div class="input-title">
						<label for="loginPw">비밀번호</label>
					</div>
					<div class="input-item">
						<input type="password" id="loginPw" name="loginPw">
					</div>
				</div>
				
				<div class="login-button-box">
					<button type="submit" class="btn-primary lg">로그인</button>
				</div>
				
				<div class="member-link-box">
					<a href="/member/joinFrm">회원가입</a> 
					<a href="#">비밀번호 찾기</a>
				</div>
			</form>
		</section>
	</main>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
</body>
</html>