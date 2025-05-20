<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<main>
		<section class="login-contatiner">
			
			<form action="/login" method="post" >	
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
				
			</form>
		</section>
	</main>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
</body>
</html>