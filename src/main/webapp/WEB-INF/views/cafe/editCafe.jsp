<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업체(신청서식) 수정</title>
<style>
	body {
  margin: 0;
  padding: 0;
  background-color: #f0f4f8;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

main {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 140px);
  padding-top: 70px;
  padding-bottom: 70px;
}

form {
  background-color: #ffffff;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  width: 500px;
  box-sizing: border-box;
}

.input-wrap {
  margin-bottom: 20px;
}

.input-title {
  font-size: 14px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 6px;
}

input[type="text"] {
  width: 100%;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #cccccc;
  border-radius: 6px;
  transition: border-color 0.3s;
}

input[type="text"]:focus {
  border-color: #007bff;
  outline: none;
}

p {
  font-size: 13px;
  color: #dc3545;
  margin-top: 6px;
  margin-left: 2px;
}

.submit-wrap {
  margin-top: 30px;
  text-align: center;
}

.submit-btn1 {
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

.submit-btn1:hover {
  background-color: #0056b3;
}
</style>
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	
	<main>
	<form action= "/editCafe" method="post">
		<div class="input-wrap">
			<div class="input-title">
				<label for="cafeName">업체명</label>
			</div>
			<div>
				<input type="text" id="cafeName" name="cafeName" value="${cafeInfo.cafeName}">
			</div>
		</div>
		
		<div class="input-wrap">
			<div class="input-title">
				<label for="cafeAddr">업체 주소</label>
			</div>
			<div>
				<input type="text" id="cafeAddr" name="cafeAddr" value="${cafeInfo.cafeAddr}">
			</div>
			<p id="addrMsg"></p>
		</div>
		
		<div class="input-wrap">
			<div class="input-title">
				<label for="cafePhone">카페 전화번호</label>
			</div>
			<div>
				<input type="text" id="cafePhone" name="cafePhone" placeholder="010-0000-0000형식" maxlength="13" value="${cafeInfo.cafePhone}">
			</div>
			<p id="phoneMsg"></p>
		</div>
		
		<div class="input-wrap">
			<div class="input-title">
				<label for="cafeBiznum">사업자 등록번호</label>
			</div>
			<div>
				<input type="text" id="cafeBiznum" name="cafeBiznum" placeholder="000-00-00000형식" maxlength="12" value="${cafeInfo.cafeBiznum}">
			</div>
			<p id="biznumMsg"></p>
		</div>
		
		<div class="input-wrap">
			<div class="input-title">
				<label for="cafeIntroduce">소개글</label>
			</div>
			<div>
				<input type="text" id="cafeIntroduce" name="cafeIntroduce" placeholder="최대 한글160자" value="${cafeInfo.cafeIntroduce}">
			</div>
		</div>
		
		<div class="input-wrap">
			<div class="input-title">
				<label for="cafeIntroDetail">공간소개</label>
			</div>
			<div>
				<input type="text" id="cafeIntroDetail" name="cafeIntroDetail" placeholder="최대 한글160자" value="${cafeInfo.cafeIntroDetail}">
			</div>
		</div>

		<div class="input-wrap">
			<div class="input-title">
				<label for="cafeStartHour">영업 시작시간</label>
			</div>
			<div>
				<input type="text" id="cafeStartHour" name="cafeStartHour" placeholder="00:00 형식" maxlength="5" value="${cafeInfo.cafeStartHour}">
			</div>
			<p id="startHourMsg"></p>
		</div>
		
		<div class="input-wrap">
			<div class="input-title">
				<label for="cafeEndHour">영업 종료시간</label>
			</div>
			<div>
				<input type="text" id="cafeEndHour" name="cafeEndHour" placeholder="00:00 형식" maxlength="5" value="${cafeInfo.cafeEndHour}">
			</div>
			<p id="endHourMsg"></p>
		</div>
		
		<div class="input-wrap">
			<div class="input-title">
				<label for="status">심사 현황</label>
				<input type="text" id="cafeRejectReason" name="cafeRejectReason" value="${cafeInfo.cafeRejectReason}">
				<input type="text" id="cafeApplyStatus" name="cafeApplyStatus" value="${cafeInfo.cafeApplyStatus}">
			</div>
			<div>
				<p id="status"></p>
			</div>
		</div>
		
		<div class="submit-wrap">
			<button type="submit" class="submit-btn1">수정</button> 
		</div>
</form>	
</main>	
		<script>
		const chkObj = {
				
				"cafeAddr" : false,
				"cafePhone" : false,
				"cafeBiznum" : false,
				"cafeStartHour" : false,
				"cafeEndHour" : false
		};
		
		const cafeAddr = $('#cafeAddr');
		cafeAddr.on('input', function(){
			const regExp = /^(?=.*[0-9])(?=.*[가-힣])[가-힣0-9\s\-]{10,}$/;
			if(regExp.test($(this).val())){
				$('#addrMsg').text('');
				chkObj.cafeAddr = true;
			}else{
				$('#addrMsg').text('10글자 이상 입력해주세요.');
			}
			
		});
		
		const cafePhone = $('#cafePhone');
		cafePhone.on('input', function(){
			const regExp = /^010-\d{3,4}-\d{4}/;
			
			if(regExp.test($(this).val())){
				$('#phoneMsg').text('');
				chkObj.cafePhone = true;
			}else{
				$('#phoneMsg').text('전화번호 형식이 올바르지 않습니다.');
			}
		});
		
		const cafeBiznum = $('#cafeBiznum');
		cafeBiznum.on('input', function(){
			const regExp = /^\d{3}-\d{2}-\d{5}$/;
			if(regExp.test($(this).val())){
				$('#biznumMsg').text('');
				chkObj.cafeBiznum = true;
			}else{
				$('#biznumMsg').text('사업자 번호 형식이 올바르지 않습니다.');
			}
		});
		
		
		const cafeStartHour = $('#cafeStartHour');
		cafeStartHour.on('input', function(){
			const regExp = /^([01]\d|2[0-3]):[0-5]\d$/ ;
			if(regExp.test($(this).val())){
				$('#startHourMsg').text('');
			}else{
				$('#startHourMsg').text('영업 시간 형식이 올바르지 않습니다.');
				
			}
		});
		
		const cafeEndHour = $('#cafeEndHour');
		cafeEndHour.on('input', function(){
			const regExp = /^(?:([01]\d|2[0-3]):[0-5]\d|24:00)$/ ;
			if(regExp.test($(this).val())){
				$('#endHourMsg').text('');
			}else{
				$('#endHourMsg').text('영업 시간 형식이 올바르지 않습니다.');
			}
		});

		
		</script>
</body>
</html>