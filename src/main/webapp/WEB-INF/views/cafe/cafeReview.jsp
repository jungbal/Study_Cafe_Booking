<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="cafeReview max-w-4xl mx-auto p-4 bg-white rounded shadow">

  <h2 class="text-2xl font-semibold mb-6 text-gray-800">리뷰</h2>

  <!-- 댓글 작성창: 이용 이력이 있는 사용자만 가능 -->
  <c:if test="${hasHistory}">
    <div class="comment-form mb-6">
      <form action="/cafeDetail/review/updateComment" method="post" class="space-y-3">
        <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
        <input type="hidden" name="writerId" value="${loginCafe.loginId}" />
        <textarea name="content" placeholder="댓글을 입력하세요" rows="3" class="w-full p-2 border rounded resize-none focus:outline-blue-500"></textarea>
        <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition">댓글 작성</button>
      </form>
    </div>
  </c:if>

  <c:if test="${not hasHistory}">
    <div class="comment-form mb-6">
      <p class="text-gray-500 italic">※ 이용내역이 있는 이용자만 리뷰 작성이 가능합니다.</p>
    </div>
  </c:if>

  <div class="review-list space-y-8">
    <c:forEach var="review" items="${reviewList}">
      <c:if test="${empty review.commentParent}">
        <div class="comment border-b border-gray-200 pb-4">
          <input type="hidden" value="${review.commentId}" />

          <p class="text-gray-800 font-semibold">
            ${review.commentUserId} <span class="text-sm text-gray-500">(${review.commentTime})</span>
          </p>

          <p class="mt-1 text-gray-700 whitespace-pre-wrap">${review.content}</p>

          <!-- 삭제 버튼: 관리자만 가능 -->
          <c:if test="${not empty loginCafe and loginCafe.loginRole == 1}">
            <form action="/cafeDetail/review/deleteComment" method="post" class="inline-block mt-2">
              <input type="hidden" name="commentId" value="${review.commentId}" />
              <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
              <button type="submit" class="text-red-600 hover:underline">삭제</button>
            </form>
          </c:if>

          <!-- 답글 달기: 로그인한 카페 사장만 가능 -->
          <c:if test="${not empty loginCafe and loginCafe.loginId eq cafe.hostId}">
            <button type="button" onclick="toggleReplyForm('${review.commentId}')"
              class="ml-4 text-blue-600 hover:underline focus:outline-none">답글 달기</button>

            <div id="reply-form-${review.commentId}" class="reply-form mt-3 hidden">
              <form action="/cafeDetail/review/updateComment" method="post" class="space-y-2">
                <input type="hidden" name="parentId" value="${review.commentId}" />
                <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
                <input type="hidden" name="writerId" value="${loginCafe.loginId}" />
                <textarea name="content" placeholder="답글을 입력하세요" rows="2" class="w-full p-2 border rounded resize-none focus:outline-blue-500"></textarea>
                <button type="submit" class="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700 transition">답글 작성</button>
              </form>
            </div>
          </c:if>

          <!-- 답글 목록 -->
          <c:forEach var="reply" items="${reviewList}">
            <c:if test="${reply.userRole == '2' and reply.commentParent == review.commentId}">
              <div class="reply ml-8 mt-4 border-l-2 border-blue-400 pl-4">
                <input type="hidden" value="${reply.commentId}" />

                <p class="text-gray-800 font-semibold">
                  ${reply.commentUserId} <span class="text-sm text-gray-500">(${reply.commentTime})</span>
                </p>
                <p class="mt-1 text-gray-700 whitespace-pre-wrap">${reply.content}</p>

                <!-- 답글 삭제 버튼: 관리자만 가능 -->
                <c:if test="${not empty loginCafe and loginCafe.loginRole == 1}">
                  <form action="/cafeDetail/review/deleteComment" method="post" class="inline-block mt-2">
                    <input type="hidden" name="commentId" value="${reply.commentId}" />
                    <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
                    <button type="submit" class="text-red-600 hover:underline">삭제</button>
                  </form>
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
</script>