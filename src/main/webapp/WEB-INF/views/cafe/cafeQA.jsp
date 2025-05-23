<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="cafeReview max-w-4xl mx-auto p-4 bg-white rounded shadow">

  <h2 class="text-2xl font-semibold mb-6 text-gray-800">Q&A</h2>

  <!-- 댓글 작성창: 로그인한 사용자만 가능 -->
  <c:if test="${not empty loginCafe}">
    <div class="comment-form mb-6">
      <form action="/cafeDetail/qa/updateComment" method="post" class="space-y-3">
        <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
        <input type="hidden" name="writerId" value="${loginCafe.loginId}" />
        <textarea name="content" placeholder="댓글을 입력하세요" rows="3"
          class="w-full p-2 border rounded resize-none focus:outline-blue-500"></textarea>
        <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition">댓글 작성</button>
      </form>
    </div>
  </c:if>

  <div class="review-list space-y-8">
    <c:forEach var="qa" items="${qaList}">
      <c:if test="${empty qa.commentParent}">
        <div class="comment border-b border-gray-200 pb-4">
          <input type="hidden" value="${qa.commentId}" />
          <p class="text-gray-800 font-semibold">
            <strong>${qa.commentUserId}</strong> <span class="text-sm text-gray-500">(${qa.commentTime})</span>
          </p>
          <p class="mt-1 text-gray-700 whitespace-pre-wrap">${qa.content}</p>

          <!-- 삭제 버튼: 관리자만 가능 -->
          <c:if test="${not empty loginCafe and loginCafe.loginRole == 1}">
            <form action="/cafeDetail/qa/deleteComment" method="post" class="inline-block mt-2">
              <input type="hidden" name="commentId" value="${qa.commentId}" />
              <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
              <button type="submit" class="text-red-600 hover:underline">삭제</button>
            </form>
          </c:if>
          
          <c:if test="${not empty loginCafe and loginCafe.loginId ne qa.commentUserId}">
			  <button type="button"
			          class="text-yellow-600 hover:underline inline-block ml-2 mt-2"
			          onclick="openReportPopup('${qa.commentId}', '${loginCafe.loginId}')">
			    신고
			  </button>
			</c:if>

          <!-- 답글 달기: 로그인한 카페 사장만 가능 -->
          <c:if test="${not empty loginCafe and loginCafe.loginId eq cafe.hostId}">
            <button type="button" onclick="toggleReplyForm('${qa.commentId}')"
              class="ml-4 text-blue-600 hover:underline focus:outline-none">답글 달기</button>

            <div id="reply-form-${qa.commentId}" class="reply-form mt-3 hidden">
              <form action="/cafeDetail/qa/updateComment" method="post" class="space-y-2">
                <input type="hidden" name="parentId" value="${qa.commentId}" />
                <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
                <input type="hidden" name="writerId" value="${loginCafe.loginId}" />
                <textarea name="content" placeholder="답글을 입력하세요" rows="2"
                  class="w-full p-2 border rounded resize-none focus:outline-blue-500"></textarea>
                <button type="submit" class="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700 transition">답글 작성</button>
              </form>
            </div>
          </c:if>

          <!-- 답글 목록 -->
          <c:forEach var="reply" items="${qaList}">
            <c:if test="${reply.userRole == '2' and reply.commentParent == qa.commentId}">
              <div class="reply ml-8 mt-4 border-l-2 border-blue-400 pl-4">
                <input type="hidden" value="${reply.commentId}" />
                <p class="text-gray-800 font-semibold">
                  <strong>${reply.commentUserId}</strong> <span class="text-sm text-gray-500">(${reply.commentTime})</span>
                </p>
                <p class="mt-1 text-gray-700 whitespace-pre-wrap">${reply.content}</p>

                <!-- 답글 삭제 버튼: 관리자만 가능 -->
                <c:if test="${not empty loginCafe and loginCafe.loginRole == 1}">
                  <form action="/cafeDetail/qa/deleteComment" method="post" class="inline-block mt-2">
                    <input type="hidden" name="commentId" value="${reply.commentId}" />
                    <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
                    <button type="submit" class="text-red-600 hover:underline">삭제</button>
                  </form>
                </c:if>
                <!-- 신고 버튼: 로그인 한 유저만 가능 -->
                <c:if test="${not empty loginCafe and loginCafe.loginId ne reply.commentUserId}">
			  <button type="button"
			          class="text-yellow-600 hover:underline inline-block ml-2 mt-2"
			          onclick="openReportPopup('${reply.commentId}', '${loginCafe.loginId}')">
			    신고
			  </button>
			</c:if>
              </div>
            </c:if>
          </c:forEach>
        </div>
      </c:if>
    </c:forEach>
  </div>
</div>

<script>
function toggleReplyForm(commentId) {
    const form = document.getElementById('reply-form-' + commentId);
    form.classList.toggle('hidden');
  }

function openReportPopup(commentId, reporterId) {
	  const url = "/cafeDetail/comment/report?commentId=" + commentId + "&reporterId=" + reporterId;

	  const popupWidth = 800;
	  const popupHeight = 700;

	  const left = window.screenX + (window.innerWidth - popupWidth) / 2;
	  const top = window.screenY + (window.innerHeight - popupHeight) / 2;

	  window.open(
	    url,
	    '신고하기',
	    "width=" + popupWidth + ",height=" + popupHeight + ",top=" + top + ",left=" + left + ",resizable=no,scrollbars=yes"
	  );
	}
</script>