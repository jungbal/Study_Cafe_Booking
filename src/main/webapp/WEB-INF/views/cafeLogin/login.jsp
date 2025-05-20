<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
body {
  margin: 0;
  padding: 0;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f0f4f8;
}

main {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
}

.login-contatiner {
  background-color: #ffffff;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  width: 350px;
}

.input-title {
  font-size: 14px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 6px;
  margin-top: 16px;
}

.input-item input {
  width: 100%;
  padding: 10px 12px;
  font-size: 14px;
  border: 1px solid #cccccc;
  border-radius: 6px;
  transition: border-color 0.3s;
}

.input-item input:focus {
  border-color: #007bff;
  outline: none;
}

.login-button-box {
  margin-top: 24px;
  text-align: center;
}

.btn-primary {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 12px 24px;
  font-size: 16px;
  font-weight: bold;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-primary:hover {
  background-color: #0056b3;
}
</style>

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