<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

</head>
<body>
<h1>회원가입</h1>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
 <form action='/joinServlet' method='post' autocomplete="off">
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
      	 <button type="submit" class="btn-primary lg">회원가입</button>
      </div>
     
  </form>
  
  <script>
  // 아이디 유효성 검사
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
		  idMessage.text('유효하지 않은 아이디입니다');
		  checkObj.memberId = false;
	  }
  });
  
  // 아이디 중복 체크
  const idDuplChkBtn = $('#idDuplChkBtn');
  
  idDuplChkBtn.on('click', function(){
	 if(!checkObj.memberId){
		swal({
			title : "알림",
			text : "유효한 ID를 입력하고, 중복체크를 진행하세요.",
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
					swal({
						title : "알림",
						text : "사용 가능한 ID 입니다.",
						icon : "success"
					});
					
					checkObj.idDuplChk = true;
				}else{
					swal({
						title : "알림",
						text : "중복된 ID 입니다.",
						icon : "warning"
					});
					
					checkObj.idDuplChk = false;
				}
			},
			error : function(){		
			}
		});
  });
	//비밀번호 유효성 검사
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
			$(pwMessage).text('비밀번호 형식이 유효하지 않습니다.');
		}
	});
	
	//비밀번호 확인 유효성 검사
	$(memberPwRe).on('input', checkPw);
	
	function checkPw(){
		
		//입력한 비밀번호와 값이 같은지만 검증
		if($(memberPw).val() == $(memberPwRe).val()){
			$(pwMessage).text('');
			checkObj.memberPwRe = true;
		}else{
			$(pwMessage).text('비밀번호가 일치하지 않습니다.');
			checkObj.memberPwRe = false;
		}
	}
	
	//전화번호 유효성 검사
	const memberPhone = $('#memberPhone');
	const phoneMessage = $('#phoneMessage');
	
	$(memberPhone).on('input', function(){
		
		const regExp = /^010-\d{3,4}-\d{4}/;
		
		if(regExp.test($(this).val())){
			$(phoneMessage).text('');
			checkObj.memberPhone = true;
		}else{
			$(phoneMessage).text('전화번호 형식이 올바르지 않습니다.');
			checkObj.memberPhone = false;
		}
	});
  
  
  
  </script>
</body>
</html>