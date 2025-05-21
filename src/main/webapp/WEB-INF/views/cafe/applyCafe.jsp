<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업체(호스트) 신청</title>



<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body class="bg-gray-100 font-sans">

<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

<main class="flex justify-center items-center min-h-[calc(100vh-140px)] py-[70px]">
  <form action="/ApplyCafeServlet" id="formSubmit" method="post" class="bg-white p-10 rounded-xl shadow-lg w-full max-w-lg">
    
    <!-- 업체명 -->
    <div class="mb-5">
      <label for="cafeName" class="block text-sm font-semibold text-gray-700 mb-2">업체명</label>
      <input type="text" id="cafeName" name="cafeName" class="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
    </div>

    <!-- 업체 주소 -->
    <div class="mb-5">
      <label for="cafeAddr" class="block text-sm font-semibold text-gray-700 mb-2">업체 주소</label>
      <input type="text" id="cafeAddr" name="cafeAddr" class="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
      <p id="addrMsg" class="text-sm text-red-500 mt-1"></p>
    </div>

    <!-- 전화번호 -->
    <div class="mb-5">
      <label for="cafePhone" class="block text-sm font-semibold text-gray-700 mb-2">카페 전화번호</label>
      <input type="text" id="cafePhone" name="cafePhone" maxlength="13" placeholder="010-0000-0000형식" class="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
      <p id="phoneMsg" class="text-sm text-red-500 mt-1"></p>
    </div>

    <!-- 사업자 등록번호 -->
    <div class="mb-5">
      <label for="cafeBiznum" class="block text-sm font-semibold text-gray-700 mb-2">사업자 등록번호</label>
      <input type="text" id="cafeBiznum" name="cafeBiznum" maxlength="12" placeholder="000-00-00000형식" class="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
      <p id="biznumMsg" class="text-sm text-red-500 mt-1"></p>
    </div>

    <!-- 소개글 -->
    <div class="mb-5">
      <label for="cafeIntroduce" class="block text-sm font-semibold text-gray-700 mb-2">소개글</label>
      <input type="text" id="cafeIntroduce" name="cafeIntroduce" placeholder="최대 한글160자" class="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
    </div>

    <!-- 공간소개 -->
    <div class="mb-5">
      <label for="cafeIntroDetail" class="block text-sm font-semibold text-gray-700 mb-2">공간소개</label>
      <input type="text" id="cafeIntroDetail" name="cafeIntroDetail" placeholder="최대 한글160자" class="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
    </div>

    <!-- 영업 시작시간 -->
    <div class="mb-5">
      <label for="cafeStartHour" class="block text-sm font-semibold text-gray-700 mb-2">영업 시작시간</label>
      <input type="text" id="cafeStartHour" name="cafeStartHour" placeholder="00:00 형식" maxlength="5" class="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
      <p id="startHourMsg" class="text-sm text-red-500 mt-1"></p>
    </div>

    <!-- 영업 종료시간 -->
    <div class="mb-8">
      <label for="cafeEndHour" class="block text-sm font-semibold text-gray-700 mb-2">영업 종료시간</label>
      <input type="text" id="cafeEndHour" name="cafeEndHour" placeholder="00:00 형식" maxlength="5" class="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
      <p id="endHourMsg" class="text-sm text-red-500 mt-1"></p>
    </div>

    <!-- 버튼 -->
    <div class="text-center">
      <button type="submit" id="submit" class="bg-blue-600 text-white px-6 py-3 rounded-md font-bold hover:bg-blue-700 transition duration-300">신청</button>
      <p id="submitMsg"></p>
    </div>

  </form>
</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
<script>
  const chkObj = {
    "cafeAddr": false,
    "cafePhone": false,
    "cafeBiznum": false,
    "cafeStartHour": false,
    "cafeEndHour": false
  };

  const cafeAddr = $('#cafeAddr');
  cafeAddr.on('input', function () {
    const regExp = /^(?=.*[0-9])(?=.*[가-힣])[가-힣0-9\s\-]{10,}$/;
    if (regExp.test($(this).val())) {
      $('#addrMsg').text('');
      chkObj.cafeAddr = true;
    } else {
      $('#addrMsg').text('숫자 포함 10글자 이상 입력해주세요.');
      chkObj.cafeAddr = false;
    }
  });

  const cafePhone = $('#cafePhone');
  cafePhone.on('input', function () {
    const regExp = /^010-\d{3,4}-\d{4}$/;
    if (regExp.test($(this).val())) {
      $('#phoneMsg').text('');
      chkObj.cafePhone = true;
    } else {
      $('#phoneMsg').text('전화번호 형식이 올바르지 않습니다.');
      chkObj.cafePhone = false;
    }
  });

  const cafeBiznum = $('#cafeBiznum');
  cafeBiznum.on('input', function () {
    const regExp = /^\d{3}-\d{2}-\d{5}$/;
    if (regExp.test($(this).val())) {
      $('#biznumMsg').text('');
      chkObj.cafeBiznum = true;
    } else {
      $('#biznumMsg').text('사업자 번호 형식이 올바르지 않습니다.');
      chkObj.cafeBiznum = false;
    }
  });

  const cafeStartHour = $('#cafeStartHour');
  cafeStartHour.on('input', function () {
    const regExp = /^([01]\d|2[0-3]):[0-5]\d$/;
    if (regExp.test($(this).val())) {
      $('#startHourMsg').text('');
      chkObj.cafeStartHour = true;
    } else {
      $('#startHourMsg').text('영업 시간 형식이 올바르지 않습니다.');
      chkObj.cafeStartHour = false;
    }
  });

  const cafeEndHour = $('#cafeEndHour');
  cafeEndHour.on('input', function () {
    const regExp = /^([01]\d|2[0-3]):[0-5]\d$/;
    if (regExp.test($(this).val())) {
      $('#endHourMsg').text('');
      chkObj.cafeEndHour = true;
    } else {
      $('#endHourMsg').text('영업 시간 형식이 올바르지 않습니다.');
      chkObj.cafeEndHour = false;
    }
  });
  
	const submit = $('#submit');

	$('#formSubmit').on('submit', function(e) {
	const allVal = Object.values(chkObj).every(value => Boolean(value));
	
		  if(!allVal) {
		    e.preventDefault(); 
		    $('#submitMsg').text('모든 값을 정확히 입력하세요.');
		  }else{
			swal.fire({
				title : "알림",
				text : "신청이 완료됐습니다.",
				icon : "success"
			});
		  }
		});
	
  
</script>

</body>
</html>
