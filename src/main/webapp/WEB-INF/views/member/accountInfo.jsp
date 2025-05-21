<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.or.iei.member.model.vo.Member" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>계정 관리</title>
  <link type="text/css" rel="stylesheet" href="/resources/css/mypage.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

<div class="mypage-container">
  <h2>회원정보 수정</h2>

  <form action="${pageContext.request.contextPath}/member/update" 
        method="post" enctype="multipart/form-data" class="account-form">
    
    <label>
      아이디:
      <input type="text" name="userId" 
             value="${sessionScope.loginMember.userId}" readonly />
    </label>
    
    <label>
      전화번호:
      <input type="text" name="userPhone" 
             value="${sessionScope.loginMember.userPhone}" required />
    </label>
    
    <div style="margin-bottom: 10px;">
      <img id="previewImage" 
           src="<c:out value='${not empty sessionScope.loginMember.userImage 
                 ? pageContext.request.contextPath.concat(\"/resources/upload/\")
                   .concat(sessionScope.loginMember.userImage) : \"\"}'/>"
           alt="프로필 이미지" 
           width="100" height="100"
           style="${empty sessionScope.loginMember.userImage ? 'display:none;' : ''}">
    </div>

    <label>
      프로필 이미지:
      <input type="file" name="userImage" accept="image/*" onchange="previewFile(this)" />
    </label>

   <!-- 수정 버튼만 포함된 form-actions -->
    <div class="form-actions">
      <button type="submit" class="primary">수정</button>
    </div>
  </form>

  <!-- form 밖에 위치한 비밀번호 변경 및 회원 탈퇴 -->
  <div class="form-actions" style="margin-top: 10px;">
    <div class="left-buttons">
      <button type="button" class="primary" onclick="openPwChangePopup()">비밀번호 변경</button>
      <form action="${pageContext.request.contextPath}/member/delete" method="post" style="display:inline;">
        <button type="submit" class="danger">회원 탈퇴</button>
      </form>
    </div>
  </div>
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
    }
    reader.readAsDataURL(file);
  }
}

function openPwChangePopup() {
  window.open(
    "/member/pwChgFrm",
    "pwChgPopup",
    "width=500,height=300,left=200,top=150"
  );
}
</script>
</body>
</html>
