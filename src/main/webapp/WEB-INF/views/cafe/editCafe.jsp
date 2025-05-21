<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>업체(신청서식) 수정</title>
  <link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
  <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-50 min-h-screen text-gray-800">

  <jsp:include page="/WEB-INF/views/common/header.jsp" />

  <main class="max-w-2xl mx-auto p-6 bg-white mt-10 rounded-lg shadow-md">
    <form action="/editCafe" method="post" class="space-y-6">
      
      <!-- 업체명 -->
      <div>
        <label for="cafeName" class="block text-sm font-medium">업체명</label>
        <input type="text" id="cafeName" name="cafeName" value="${cafeInfo.cafeName}" 
               class="mt-1 w-full border rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
      </div>

      <!-- 주소 -->
      <div>
        <label for="cafeAddr" class="block text-sm font-medium">업체 주소</label>
        <input type="text" id="cafeAddr" name="cafeAddr" value="${cafeInfo.cafeAddr}" 
               class="mt-1 w-full border rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
        <p id="addrMsg" class="text-sm text-red-500 mt-1"></p>
      </div>

      <!-- 전화번호 -->
      <div>
        <label for="cafePhone" class="block text-sm font-medium">전화번호</label>
        <input type="text" id="cafePhone" name="cafePhone" maxlength="13" value="${cafeInfo.cafePhone}" placeholder="010-0000-0000형식"
               class="mt-1 w-full border rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
        <p id="phoneMsg" class="text-sm text-red-500 mt-1"></p>
      </div>

      <!-- 사업자 등록번호 -->
      <div>
        <label for="cafeBiznum" class="block text-sm font-medium">사업자 등록번호</label>
        <input type="text" id="cafeBiznum" name="cafeBiznum" maxlength="12" value="${cafeInfo.cafeBiznum}" placeholder="000-00-00000형식"
               class="mt-1 w-full border rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
        <p id="biznumMsg" class="text-sm text-red-500 mt-1"></p>
      </div>

      <!-- 소개글 -->
      <div>
        <label for="cafeIntroduce" class="block text-sm font-medium">소개글</label>
        <input type="text" id="cafeIntroduce" name="cafeIntroduce" placeholder="최대 한글160자" value="${cafeInfo.cafeIntroduce}"
               class="mt-1 w-full border rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
      </div>

      <!-- 공간소개 -->
      <div>
        <label for="cafeIntroDetail" class="block text-sm font-medium">공간소개</label>
        <input type="text" id="cafeIntroDetail" name="cafeIntroDetail" placeholder="최대 한글160자" value="${cafeInfo.cafeIntroDetail}"
               class="mt-1 w-full border rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
      </div>

      <!-- 시작시간 -->
      <div>
        <label for="cafeStartHour" class="block text-sm font-medium">영업 시작시간</label>
        <input type="text" id="cafeStartHour" name="cafeStartHour" placeholder="00:00 형식" maxlength="5" value="${cafeInfo.cafeStartHour}"
               class="mt-1 w-full border rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
        <p id="startHourMsg" class="text-sm text-red-500 mt-1"></p>
      </div>

      <!-- 종료시간 -->
      <div>
        <label for="cafeEndHour" class="block text-sm font-medium">영업 종료시간</label>
        <input type="text" id="cafeEndHour" name="cafeEndHour" placeholder="00:00 형식" maxlength="5" value="${cafeInfo.cafeEndHour}"
               class="mt-1 w-full border rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
        <p id="endHourMsg" class="text-sm text-red-500 mt-1"></p>
      </div>

      <!-- 심사 현황 -->
      <div class="p-4 border border-red-300 bg-red-50 rounded-md">
        <h2 class="text-red-600 font-semibold mb-3">심사 현황</h2>
        <div class="mb-3">
          <label class="block text-sm font-medium">신청 상태</label>
          <input type="text" id="cafeApplyStatus" name="cafeApplyStatus" value="${cafeInfo.cafeApplyStatus}" readonly
                 class="mt-1 w-full bg-gray-100 border rounded-md p-2 text-sm text-gray-600">
        </div>
        <div>
          <label class="block text-sm font-medium">반려 사유</label>
          <input type="text" id="cafeRejectReason" name="cafeRejectReason" value="${cafeInfo.cafeRejectReason}" readonly
                 class="mt-1 w-full bg-gray-100 border rounded-md p-2 text-sm text-gray-600">
        </div>
      </div>

      <!-- 수정 버튼 -->
      <c:if test="${cafeInfo.cafeApplyStatus eq '반려 상태'}">
        <div class="text-center">
          <button type="submit"
                  class="mt-6 bg-blue-600 text-white px-6 py-2 rounded-md hover:bg-blue-700 transition">수정</button>
        </div>
      </c:if>
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