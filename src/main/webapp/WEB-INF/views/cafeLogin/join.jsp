<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style>
	body {
  margin: 0;
  padding: 0;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f0f4f8;
}

form {
  background-color: #ffffff;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  width: 400px;
}

.input-wrap {
  margin-bottom: 18px;
}

.input-title {
  font-size: 14px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 6px;
}

.input-item {
   display: block;
}

.input-item input {
  flex: 1;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #cccccc;
  border-radius: 6px;
  transition: border-color 0.3s;
}

.input-item input:focus {
  border-color: #007bff;
  outline: none;
}

.input-msg {
  font-size: 13px;
  color: #dc3545; /* 붉은색으로 에러 메시지 표시 */
  margin-top: 6px;
  margin-left: 2px;
}

.btn-primary {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 18px;
  font-size: 14px;
  font-weight: bold;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  height: 40px;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-primary.lg {
  width: 100%;
  font-size: 16px;
  padding: 12px 0;
}

.join-button-box {
  margin-top: 30px;
}

main {
  display: flex;
  justify-content: center;
  align-items: center;
  width : 100%;
  min-height: calc(100vh - 140px); /* 헤더+푸터 높이 빼줌 */
  padding-top: 70px; /* 헤더 높이 */
  padding-bottom: 70px; /* 푸터 높이 */
}

</style>

<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

<main>
 <form action='/joinServlet' id='formSubmit' method='post' autocomplete="off"> 
 <div class="form-wrap">
 	<div class="input-wrap">
	     <div class="input-title">
	      	<label for="memberId">아이디</label>
	     </div>
	      <div class="input-item">
	      	<input type="text" id="memberId" name="loginId" placeholder="영문, 숫자 혼합 4~10글자" maxlength="20">
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
     	  	<input type="password" id="memberPw" name="loginPw" placeholder="영문, 숫자, 특수문자 4~10글자" maxlength="30">
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
      	<button type="submit" id="submit" class="btn-primary lg">회원가입</button>
      	<p id="submitMsg" class="submit-msg"></p>
     </div>
 </div>    
 </form>
 </main> 
  
  <script>
  <%-- 아이디 유효성 검사 --%> 
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
	  checkObj.idDuplChk = false;
	  const regExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,10}$/;
	  
	  if(regExp.test($(this).val())){
		  idMessage.text('');
		  checkObj.memberId = true;
	  }else{
		  idMessage.text('유효한 값을 입력하세요(숫자포함 10자이내)');
		  checkObj.memberId = false;
	  }
  });
  
  <%-- 아이디 중복 체크 --%> 
  const idDuplChkBtn = $('#idDuplChkBtn');
  
  idDuplChkBtn.on('click', function(){
	  
	 if(!checkObj.memberId){
		swal.fire({
			title : "알림",
			text : "유효한 ID를 입력하고, 중복체크를 진행하세요",
			icon : "warning"
		});
	
		return;
	 } 
	 
	 $.ajax({
			url : "/idDuplChk",							
			data : {'memberId' : memberId.val()},	
			type : "get",								
			success : function(res){ // res는 서블릿에서 getWriter() 한 cnt값이 들어있음			
				if(res == 0){ //중복 X  == 회원가입 가능
					swal.fire({
						title : "알림",
						text : "사용 가능한 ID 입니다",
						icon : "success"
					});
					
					checkObj.idDuplChk = true;
				}else{
					swal.fire({
						title : "알림",
						text : "중복된 ID 입니다",
						icon : "warning"
					});
					
					checkObj.idDuplChk = false;
				}
			},
			error : function(){		
			}
		});
  });
	<%-- 비밀번호 유효성 검사 --%>
	const memberPw = $('#memberPw');
	const pwMessage = $('#pwMessage');
	const memberPwRe = $('#memberPwRe');
	
	$(memberPw).on('input', function(){
		
		const regExp = /^[a-zA-Z0-9!@#$]{4,10}$/; //영문, 숫자, 특수문자 4~10글자
		
		if(regExp.test($(memberPw).val())){
			checkObj.memberPw = true;
			
			//비밀번호 확인값이 입력이 되었을 때
			if($(memberPwRe).val().length > 0){
				checkPw(); //비밀번호와 확인값 일치하는지 체크
			}else{				
				$(pwMessage).text('');
			}
			
		}else{
			checkObj.memberPw = false;
			$(pwMessage).text('비밀번호 형식이 유효하지 않습니다');
		}
	});
	
	<%-- 비밀번호 확인 유효성 검사 --%>
	$(memberPwRe).on('input', checkPw);
	
	function checkPw(){
		
		//입력한 비밀번호와 값이 같은지만 검증
		if($(memberPw).val() == $(memberPwRe).val()){
			$(pwMessage).text('');
			checkObj.memberPwRe = true;
		}else{
			$(pwMessage).text('비밀번호가 일치하지 않습니다');
			checkObj.memberPwRe = false;
		}
	}
	
	<%-- 전화번호 유효성 검사 --%>
	const memberPhone = $('#memberPhone');
	const phoneMessage = $('#phoneMessage');
	
	$(memberPhone).on('input', function(){
		
		const regExp = /^010-\d{3,4}-\d{4}/;
		
		if(regExp.test($(this).val())){
			$(phoneMessage).text('');
			checkObj.memberPhone = true;
		}else{
			$(phoneMessage).text('전화번호 형식이 올바르지 않습니다');
			checkObj.memberPhone = false;
		}
	});
  

	
	$('#formSubmit').on('submit', function(e) {
		const allVal = Object.values(checkObj).every(value => Boolean(value));
		
		  if (!checkObj.idDuplChk) {
		    e.preventDefault(); 
		    $('#submitMsg').text('아이디 중복 체크를 진행하세요');
		  }
		
		  if(!allVal) {
		    e.preventDefault(); 
		    $('#submitMsg').text('모든 값을 정확히 입력하세요.');
		  }
		});
	
	
  </script>
</body>
</html>