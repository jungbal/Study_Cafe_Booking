<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <style>
    .mypage-container { max-width: 600px; margin: 0 auto; }
    .mypage-container form { display: flex; flex-direction: column; }
    .mypage-container label { margin: 10px 0; }
    .mypage-container input[type="text"],
    .mypage-container input[type="file"] { padding: 8px; font-size: 1rem; }
    .mypage-container button { width: 100px; padding: 8px; margin-top: 20px; }
    .danger { background: #e74c3c; color: #fff; }
    .primary { background: #3498db; color: #fff; }
  </style>
</head>
<body>
<div class="mypage-container">
    <h2>업체 정보 수정</h2>
    <form id="updateForm">
  <input type="hidden" name="cafeNo" value="${cafe.cafeNo}">
  <label>업체명: <input type="text" name="cafeName" value="${cafe.cafeName}"></label>
  <label>카페 전화번호: <input type="text" name="cafePhone" value="${cafe.cafePhone}"></label>
  <label>카페 주소: <input type="text" name="cafeAddr" value="${cafe.cafeAddr}"></label>
  <input type="hidden" name="cafeBiznum" value="${cafe.cafeBiznum}">
  <label>카페 소개글: <input type="text" name="cafeIntroduce" value="${cafe.cafeIntroduce}"></label>
  <label>영업 시작 시간: <input type="text" name="cafeStartHour" value="${cafe.cafeStartHour}"></label>
  <label>영업 종료 시간: <input type="text" name="cafeEndHour" value="${cafe.cafeEndHour}"></label>
  <input type="hidden" name="cafeStatus" value="${cafe.cafeStatus}">
  <label>공간 소개: <input type="text" name="cafeIntroDetail" value="${cafe.cafeIntroDetail}"></label>
  <input type="hidden" name="hostId" value="${cafe.hostId}">

  <div style="margin-bottom: 10px;">
    <img id="previewImage"
         src="<c:out value='${not empty sessionScope.loginMember.userImage ? pageContext.request.contextPath.concat("/resources/upload/").concat(sessionScope.loginMember.userImage) : ""}'/>"
         alt="프로필 이미지"
         width="100" height="100"
         style="${empty sessionScope.loginMember.userImage ? 'display:none;' : ''}">
  </div>

  <label>프로필 이미지:
    <input type="file" name="userImage" accept="image/*" onchange="previewFile(this)">
  </label>

  <button type="button" onclick="submitForm()">수정</button>
</form>
    
    
    <!-- 2) 비밀번호 변경 버튼 -->
    <form action="${pageContext.request.contextPath}/member/pwChgFrm"
          method="get">
      <button type="submit" class="primary" onclick="window.open(
          '${pageContext.request.contextPath}/member/pwChgFrm',
          'pwChg',
          'width=450,height=300,scrollbars=yes');">
        비밀번호 변경
      </button>
    </form>
    <form action="${pageContext.request.contextPath}/member/delete" method="post" 
          style="margin-top:10px;">
      <button type="submit" style="background:#e74c3c;color:white;">
        회원 탈퇴
      </button>
    </form>
  </div>
<script>
function previewFile(input) {
  const file = input.files[0];
  const preview = document.getElementById("previewImage");

  if (file) {
    const reader = new FileReader();
    reader.onload = function(e) {
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
      // location.reload(); // 또는 이동할 페이지로 리다이렉트
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