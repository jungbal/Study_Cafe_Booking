<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
<script src="https://cdn.tailwindcss.com"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body class="bg-gray-100 text-gray-900 min-h-screen flex flex-col">
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

 <main class="flex-grow max-w-xl mx-auto mt-10 bg-white shadow-lg p-8 rounded-xl">
  <h2 class="text-2xl font-bold mb-6 text-center">회원가입</h2>
  <form action="/joinServlet" id="formSubmit" method="post" autocomplete="off">
    
    <!-- 아이디 -->
    <div class="mb-5">
      <label for="memberId" class="block font-semibold mb-1">아이디</label>
      <div class="flex gap-2">
        <input type="text" id="memberId" name="loginId" placeholder="영문, 숫자 혼합 4~10글자" maxlength="20"
          class="flex-1 border rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
        <button type="button" id="idDuplChkBtn" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-400">중복체크</button>
      </div>
      <p id="idMessage" class="text-sm text-red-500 mt-1"></p>
    </div>

    <!-- 비밀번호 -->
    <div class="mb-5">
      <label for="memberPw" class="block font-semibold mb-1">비밀번호</label>
      <input type="password" id="memberPw" name="loginPw" placeholder="영문, 숫자, 특수문자 4~10글자" maxlength="30"
        class="w-full border rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
    </div>

    <!-- 비밀번호 확인 -->
    <div class="mb-5">
      <label for="memberPwRe" class="block font-semibold mb-1">비밀번호 확인</label>
      <input type="password" id="memberPwRe" maxlength="30"
        class="w-full border rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
      <p id="pwMessage" class="text-sm text-red-500 mt-1"></p>
    </div>

    <!-- 전화번호 -->
    <div class="mb-5">
      <label for="memberPhone" class="block font-semibold mb-1">전화번호</label>
      <input type="text" id="memberPhone" name="memberPhone" placeholder="전화번호 (010-0000-0000)" maxlength="13"
        class="w-full border rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
      <p id="phoneMessage" class="text-sm text-red-500 mt-1"></p>
    </div>

    <!-- 회원가입 버튼 -->
    <div class="text-center">
      <button type="submit" id="submit" class="w-full py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-400 text-lg font-semibold">
        회원가입
      </button>
      <p id="submitMsg" class="text-sm text-red-500 mt-2"></p>
    </div>
  </form>
</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
<script>
const checkObj = {
  "memberId": false,
  "memberPw": false,
  "memberPwRe": false,
  "memberPhone": false,
  "idDuplChk": false
};

const memberId = $('#memberId');
const idMessage = $('#idMessage');

memberId.on('input', function () {
  checkObj.idDuplChk = false;
  const regExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,10}$/;
  if (regExp.test($(this).val())) {
    idMessage.text('');
    checkObj.memberId = true;
  } else {
    idMessage.text('유효한 값을 입력하세요(숫자포함 10자이내)');
    checkObj.memberId = false;
  }
});

$('#idDuplChkBtn').on('click', function () {
  if (!checkObj.memberId) {
    Swal.fire("알림", "유효한 ID를 입력하고, 중복체크를 진행하세요", "warning");
    return;
  }

  $.ajax({
    url: "/idDuplChk",
    data: { 'memberId': memberId.val() },
    type: "get",
    success: function (res) {
      if (res == 0) {
        Swal.fire("알림", "사용 가능한 ID 입니다", "success");
        checkObj.idDuplChk = true;
        $('#submitMsg').text('');
      } else {
        Swal.fire("알림", "중복된 ID 입니다", "warning");
        checkObj.idDuplChk = false;
      }
    }
  });
});

const memberPw = $('#memberPw');
const pwMessage = $('#pwMessage');
const memberPwRe = $('#memberPwRe');

memberPw.on('input', function () {
  const regExp = /^[a-zA-Z0-9!@#$]{4,10}$/;
  if (regExp.test($(this).val())) {
    checkObj.memberPw = true;
    if (memberPwRe.val().length > 0) {
      checkPw();
    } else {
      pwMessage.text('');
    }
  } else {
    checkObj.memberPw = false;
    pwMessage.text('비밀번호 형식이 유효하지 않습니다');
  }
});

memberPwRe.on('input', checkPw);

function checkPw() {
  if (memberPw.val() == memberPwRe.val()) {
    pwMessage.text('');
    checkObj.memberPwRe = true;
  } else {
    pwMessage.text('비밀번호가 일치하지 않습니다');
    checkObj.memberPwRe = false;
  }
}

const memberPhone = $('#memberPhone');
const phoneMessage = $('#phoneMessage');

memberPhone.on('input', function () {
  const regExp = /^010-\d{3,4}-\d{4}$/;
  if (regExp.test($(this).val())) {
    phoneMessage.text('');
    checkObj.memberPhone = true;
  } else {
    phoneMessage.text('전화번호 형식이 올바르지 않습니다');
    checkObj.memberPhone = false;
  }
});

$('#formSubmit').on('submit', function (e) {
  const allValid = Object.values(checkObj).every(Boolean);
  if (!allValid) {
    e.preventDefault();
    $('#submitMsg').text(
      !checkObj.idDuplChk
        ? '아이디 중복 체크를 진행하세요'
        : '모든 값을 정확히 입력하세요.'
    );
  }
});
</script>
</body>
</html>
