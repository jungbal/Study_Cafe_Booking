<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <form action='/JoinServlet' method='post' autocomplete="off">
 	<div class="input-wrap">
	     <div class="input-title">
	      	<label for="memberId">아이디</label>
	     </div>
	      <div class="input-item">
	      	<input type="text" id="memberId" name="loginId" placeholder="영어, 숫자 8~20글자" maxlength="20">
	      	<%-- 아이디 중복체크 버튼! form 태그 내에서 button 태그 타입 미지정 시, submit 기능을 가짐 --%>
	      	<button type="button" class="btn-primary" id="idDuplChkBtn">중복체크</button>
	     </div>
	      <p id="idMessage" class="input-msg"></p>
     </div>
     
     <div class="input-wrap">
     	  <div class="input-title">
     	  	<label for="memberPw">비밀번호</label>
     	  </div>
     	  <div class="input-item">
     	  	<input type="password" id="memberPw" name="loginPw" placeholder="영어, 숫자, 특수문자(!,@,#,$) 6~30글자" maxlength="30">
     	  </div>
     </div>
     
     <div class="input-wrap">
     	  <div class="input-title">
     	  	<label for="memberPwRe">비밀번호 확인</label>
     	  </div>
     	  <div class="input-item">
     	  	<input type="password" id="memberPwRe" maxlength="30">
     	  </div>
     	  <p id="pwMessage" class="input-msg"></p>
     </div>
     
     <div class="input-wrap">
     	  <div class="input-title">
     	  	<label for="memberPhone">전화번호</label>
     	  </div>
     	  <div class="input-item">
     	  	<input type="text" id="memberPhone" name="memberPhone" placeholder="전화번호 (010-0000-0000)" maxlength="13">
     	  </div>
     	  <p id="phoneMessage" class="input-msg"></p>
     </div>
     
      <div class="join-button-box">
      	 <button type="submit" class="btn-primary lg">회원가입</button>
      </div>
     
  </form>
  
  <script>
  const checkObj = {
			"memberId"	: false,
			"memberPw"  : false,
			"memberPwRe" : false,		
			"memberPhone": false,
			"idDuplChk" : false
			};
  
  const memberId = $('#memberId');
  const idMessage = $('#idMessage');
  
  memberId.on('input', function(){
	  const regExp = /^[a-zA-Z0-9]{8,20}$/;
	  
	  if(regExp.test($(this).val()))
  });
  
  
  </script>
</body>
</html>