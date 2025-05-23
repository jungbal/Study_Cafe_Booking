<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.or.iei.member.model.vo.Member" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="max-w-xl mx-auto p-5 bg-white shadow rounded-md">
  <h2 class="text-xl font-semibold mb-5 text-gray-800">회원정보 수정</h2>

  <form action="${pageContext.request.contextPath}/member/update" method="post" enctype="multipart/form-data" class="space-y-4">
    <!-- 아이디 -->
    <div>
      <label class="block text-gray-700 text-sm font-medium mb-1">아이디</label>
      <input type="text" name="userId"
             value="${sessionScope.loginMember.userId}"
             readonly
             class="w-full border border-gray-300 rounded px-3 py-2 bg-gray-100 text-gray-600 text-sm" />
    </div>

    <!-- 전화번호 -->
    <div>
      <label class="block text-gray-700 text-sm font-medium mb-1">전화번호</label>
      <input type="text" name="userPhone"
             value="${sessionScope.loginMember.userPhone}"
             required
             class="w-full border border-gray-300 rounded px-3 py-2 text-sm" />
    </div>

    <!-- 기존 이미지 -->
    <div class="mb-3">
      <img id="previewImage"
           src="<c:out value='${not empty sessionScope.loginMember.userImage 
             ? pageContext.request.contextPath.concat("/resources/upload/")
               .concat(sessionScope.loginMember.userImage) : ""}'/>"
           alt="프로필 이미지"
           width="100" height="100"
           class="${empty sessionScope.loginMember.userImage ? 'hidden' : 'inline-block'} rounded" />
    </div>

    <!-- 프로필 이미지 업로드 -->
    <div>
      <label class="block text-gray-700 text-sm font-medium mb-1">프로필 이미지</label>
      <input type="file" name="userImage" accept="image/*"
             onchange="previewFile(this)"
             class="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:border-0
                    file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100" />
    </div>

<!-- 수정 버튼 -->
  <!-- 버튼들 컨테이너 -->
  <div class="pt-4 flex justify-end gap-3">
    

    <!-- 비밀번호 변경 버튼 -->
    <button type="button"
            onclick="openPwChangePopup()"
            class="bg-gray-100 text-gray-700 px-4 py-2 rounded hover:bg-gray-200 text-sm">
      비밀번호 변경
    </button>
    <!-- 수정 버튼 -->
    <button type="submit"
            class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 text-sm">
      수정
    </button>
  </div>
</form>

<!-- 하단 버튼 -->
<div class="pt-5 border-t mt-6 flex gap-3 justify-end">

  <form action="${pageContext.request.contextPath}/member/delete" method="post">
    <button type="submit"
            class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 text-sm">
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
    reader.onload = function(e) {
      preview.src = e.target.result;
      preview.classList.remove("hidden");
      preview.classList.add("inline-block");
    }
    reader.readAsDataURL(file);
  }
}

function openPwChangePopup() {
	  window.open(
	    "/member/pwChgFrm",
	    "pwChgPopup",
	    "width=460,height=655,left=200,top=150"
	  );
	}

</script>
