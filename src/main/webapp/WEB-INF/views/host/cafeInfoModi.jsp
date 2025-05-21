<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>업체 정보 수정</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body class="bg-gray-100 min-h-screen">
  <jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

  <div class="max-w-2xl mx-auto bg-white shadow-md rounded-xl p-8 mt-10">
    <h2 class="text-2xl font-bold mb-6 text-center">업체 정보 수정</h2>

    <form id="updateForm" class="space-y-4">
      <input type="hidden" name="cafeNo" value="${cafe.cafeNo}">
      
      <label class="block">
        <span class="text-gray-700">업체명</span>
        <input type="text" name="cafeName" value="${cafe.cafeName}" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
      </label>

      <label class="block">
        <span class="text-gray-700">카페 전화번호</span>
        <input type="text" name="cafePhone" value="${cafe.cafePhone}" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
      </label>

      <label class="block">
        <span class="text-gray-700">카페 주소</span>
        <input type="text" name="cafeAddr" value="${cafe.cafeAddr}" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
      </label>

      <input type="hidden" name="cafeBiznum" value="${cafe.cafeBiznum}">

      <label class="block">
        <span class="text-gray-700">카페 소개글</span>
        <input type="text" name="cafeIntroduce" value="${cafe.cafeIntroduce}" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
      </label>

      <label class="block">
        <span class="text-gray-700">영업 시작 시간</span>
        <input type="text" name="cafeStartHour" value="${cafe.cafeStartHour}" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
      </label>

      <label class="block">
        <span class="text-gray-700">영업 종료 시간</span>
        <input type="text" name="cafeEndHour" value="${cafe.cafeEndHour}" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
      </label>

      <input type="hidden" name="cafeStatus" value="${cafe.cafeStatus}">

      <label class="block">
        <span class="text-gray-700">공간 소개</span>
        <input type="text" name="cafeIntroDetail" value="${cafe.cafeIntroDetail}" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
      </label>

      <input type="hidden" name="hostId" value="${cafe.hostId}">

      <div>
        <span class="text-gray-700 block mb-2">프로필 이미지</span>
        <img id="previewImage"
             src="<c:out value='${not empty sessionScope.loginMember.userImage ? pageContext.request.contextPath.concat("/resources/upload/").concat(sessionScope.loginMember.userImage) : ""}'/>"
             alt="프로필 이미지"
             class="w-24 h-24 object-cover rounded-full mb-2"
             style="${empty sessionScope.loginMember.userImage ? 'display:none;' : ''}">
        <input type="file" name="userImage" accept="image/*" onchange="previewFile(this)" class="block w-full text-sm text-gray-700 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:bg-blue-500 file:text-white hover:file:bg-blue-600">
      </div>

      <div class="flex justify-end space-x-4">
        <button type="button" onclick="submitForm()" class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700">
          수정
        </button>
      </div>
    </form>

    <hr class="my-6">

    <div class="flex flex-col space-y-3">
      <form action="${pageContext.request.contextPath}/member/pwChgFrm" method="get">
        <button type="submit" class="bg-yellow-500 text-white px-4 py-2 rounded hover:bg-yellow-600 w-full"
          onclick="window.open('${pageContext.request.contextPath}/member/pwChgFrm','pwChg','width=450,height=300,scrollbars=yes'); return false;">
          비밀번호 변경
        </button>
      </form>

      <form action="${pageContext.request.contextPath}/member/delete" method="post">
        <button type="submit" class="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700 w-full">
          회원 탈퇴
        </button>
      </form>
    </div>
  </div>

  <script>
    function previewFile(input) {
      const file = input.files[0];
      const preview = document.getElementById("previewImage");

      if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
          preview.src = e.target.result;
          preview.style.display = "inline-block";
        };
        reader.readAsDataURL(file);
      }
    }

    function submitForm() {
      const form = document.getElementById("updateForm");
      const formData = new FormData(form);

      fetch('/host/update', {
        method: 'POST',
        body: formData
      })
      .then(response => {
        if (response.ok) {
          alert('업체 정보가 성공적으로 수정되었습니다.');
          location.href = '/main'; // 이동
        } else {
          throw new Error("서버 오류");
        }
      })
      .catch(error => {
        console.error(error);
        alert('수정 중 오류가 발생했습니다.');
      });
    }
  </script>
</body>
</html>
