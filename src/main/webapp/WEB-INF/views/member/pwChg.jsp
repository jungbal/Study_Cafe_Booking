<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="/css/common.css">
<link type="text/css" rel="stylesheet" href="/resources/css/mypage.css" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/resources/js/sweetalert.min.js"></script>
</head>
<body>
	<div class="chkPw-wrapper">
					<form id="pwChgForm" action="/member/pwChg" method="post" onsubmit="return validateForm()">
								<label for="memberPw">현재 비밀번호</label>
								<%-- 서블릿에서 세션에 등록된 회원의 비밀번호와 일치하는지 검증하기 위해, name 속성 작성하여 서버로 전송 --%>
								<input type="password" id="memberPw" name="memberPw" placeholder="현재 비밀번호" required>
								<label for="newMemberPw">새 비밀번호</label>
								<input type="password" id="newMemberPw" name="newMemberPw" placeholder="영어, 숫자, 특수문자(!,@,#,$) 6~30글자">
						
								<label for="newMemberPwRe">새 비밀번호 확인</label>
								<input type="password" id="newMemberPwRe" placeholder="새 비밀번호 확인">
						
							<button type="submit" class="primary">변경하기</button>
							<button type="button" onclick="closeFn()" class="danger" style="margin-top : 10px;">닫기</button>
							
					</form>
	</div>
	<script>
	//닫기 버튼 클릭 시
	function closeFn(){
		self.close();
	}
	
	
	function validateForm(){
		//현재 비밀번호 검증(사용자가 입력한 기존 비밀번호와 세션에 저장된 회원의 비밀번호 비교)
		if($('#memberPw').val() != '${loginMember.userPw}'){
			swal({
				title : "알림",
				text : "현재 비밀번호가 일치하지 않습니다.",
				icon : "warning"
			});
			
			return false;
		}
		
		//새 비밀번호가 정규표현식 패턴에 만족하는지?
		const regExp = /^[a-zA-Z0-9!@#$]{6,30}$/; //영어, 숫자, 특수문자 (!@#$) 6~30글자
		
		if(!regExp.test($('#newMemberPw').val())){
			swal({
				title : "알림",
				text : "새 비밀번호가 유효하지 않습니다.",
				icon : "warning"
			});
			
			return false;
		}
		
		//새 비밀번호와 새 비밀번호 확인값이 같은지?
				
		if($('#memberPw').val() == $('#newMemberPw').val()){
			swal({
				title : "알림",
				text : "기존 비밀번호와 새비밀번호가 같습니다. 다른 비밀번호를 사용해주세요.",
				icon : "warning"
			});
			return false;
		}
				
		if($('#newMemberPw').val() != $('#newMemberPwRe').val()){
			swal({
				title : "알림",
				text : "새 비밀번호와 새 비밀번호 확인값이 일치하지 않습니다.",
				icon : "warning"
			});
			
			return false;
		}
		
	}
	</script>
</body>
</html>