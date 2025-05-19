<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.or.iei.member.model.vo.Member" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>계정 관리</title>
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
    <h2>회원정보 수정</h2>
    <form action="${pageContext.request.contextPath}/member/update" 
          method="post" enctype="multipart/form-data">
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
             ? pageContext.request.contextPath.concat("/resources/upload/")
               .concat(sessionScope.loginMember.userImage) : ""}'/>"
       alt="프로필 이미지" 
       width="100" height="100"
       style="${empty sessionScope.loginMember.userImage ? 'display:none;' : ''}">
</div>
      <label>
        프로필 이미지:
        <input type="file" name="userImage" accept="image/*" onchange="previewFile(this)"/>
      </label>
      <button type="submit">수정</button>
    </form>
    <!-- 2) 비밀번호 변경 버튼 -->
    <form action="${pageContext.request.contextPath}/member/pwChgFrm"
          method="get">
      <button type="button" class="primary" onclick="openPwChangePopup()">
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
        }
        reader.readAsDataURL(file);
    }
}

function openPwChangePopup() {
    window.open(
        "/member/pwChgFrm",     // 열릴 JSP 경로
        "pwChgPopup",            // 새 창 이름
        "width=500,height=300,left=200,top=150"  // 창 속성
    );
}
</script>
</body>
</html>