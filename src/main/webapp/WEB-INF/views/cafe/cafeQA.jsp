<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="cafeReview">
  <h2>Q&A</h2>

  <!-- 댓글 작성창: 로그인한 사용자만 가능 -->
  <c:if test="${not empty loginCafe}">
    <div class="comment-form">
      <form action="/cafeDetail/qa/updateComment" method="post">
        <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
        <input type="hidden" name="writerId" value="${loginCafe.loginId}" />
        <textarea name="content" placeholder="댓글을 입력하세요" rows="3" cols="50"></textarea>
        <br />
        <button type="submit">댓글 작성</button>
      </form>
    </div>
  </c:if>

  <div class="review-list">
    <c:forEach var="qa" items="${qaList}">
      <c:if test="${empty qa.commentParent}">
        <div class="comment" style="margin-bottom: 20px;">
          <input type="hidden" value="${qa.commentId}" />
          <p><strong>${qa.commentUserId}</strong> (${qa.commentTime})</p>
          <p>${qa.content}</p>

          <!-- 삭제 버튼: 관리자만 가능 -->
          <c:if test="${not empty loginCafe and loginCafe.loginRole == 1}">
            <form action="/cafeDetail/qa/deleteComment" method="post" style="display: inline;">
              <input type="hidden" name="commentId" value="${qa.commentId}" />
              <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
              <button type="submit">삭제</button>
            </form>
          </c:if>

          <!-- 답글 달기: 로그인한 카페 사장만 가능 -->
          <c:if test="${not empty loginCafe and loginCafe.loginId eq cafe.hostId}">
            <button type="button" onclick="toggleReplyForm('${qa.commentId}')">답글 달기</button>

            <div class="reply-form" id="reply-form-${qa.commentId}" style="display: none; margin-top: 10px;">
              <form action="/cafeDetail/qa/updateComment" method="post">
                <input type="hidden" name="parentId" value="${qa.commentId}" />
                <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
                <input type="hidden" name="writerId" value="${loginCafe.loginId}" />
                <textarea name="content" placeholder="답글을 입력하세요" rows="2" cols="45"></textarea>
                <br />
                <button type="submit">답글 작성</button>
              </form>
            </div>
          </c:if>

          <!-- 답글 목록 -->
          <c:forEach var="reply" items="${qaList}">
            <c:if test="${reply.userRole == '2' and reply.commentParent == qa.commentId}">
              <div class="reply" style="margin-left: 30px; margin-top: 10px;">
                <input type="hidden" value="${reply.commentId}" />
                <p><strong>${reply.commentUserId}</strong> (${reply.commentTime})</p>
                <p>${reply.content}</p>

                <!-- 답글 삭제 버튼: 관리자만 가능 -->
                <c:if test="${not empty loginCafe and loginCafe.loginRole == 1}">
                  <form action="/cafeDetail/qa/deleteComment" method="post" style="display: inline;">
                    <input type="hidden" name="commentId" value="${reply.commentId}" />
                    <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
                    <button type="submit">삭제</button>
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

<!-- JavaScript: 답글 입력창 토글 -->
<script>
  function toggleReplyForm(commentId) {
    const form = document.getElementById('reply-form-' + commentId);
    form.style.display = (form.style.display === 'none') ? 'block' : 'none';
  }
</script>
