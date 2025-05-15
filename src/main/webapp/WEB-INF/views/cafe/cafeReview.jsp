<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="cafeReview">
  <h2>리뷰</h2>

  <!-- 댓글 작성창: 로그인했고 카페 사장이 아닌 경우 -->
  <c:if test="${not empty loginCafe and loginCafe.loginId ne cafe.hostId}">
    <div class="comment-form">
      <form action="/cafeDetail/review/updateComment" method="post">
        <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
        <input type="hidden" name="writerId" value="${loginCafe.loginId}" />
        <textarea name="content" placeholder="댓글을 입력하세요" rows="3" cols="50"></textarea>
        <br />
        <button type="submit">댓글 작성</button>
      </form>
    </div>
  </c:if>

  <div class="review-list">
    <c:forEach var="review" items="${reviewList}">
      <c:if test="${review.userRole == '3'}"> <%-- 댓글 --%>
        <div class="comment" style="margin-bottom: 20px;">
          <input type="hidden" value="${review.commentId}" />
          <p><strong>${review.commentUserId}</strong> (${review.commentTime})</p>
          <p>${review.content}</p>

          <!-- 카페 사장인 경우: 답글 달기 버튼 -->
          <c:if test="${not empty loginCafe and loginCafe.loginId eq cafe.hostId}">
            <button type="button" onclick="toggleReplyForm('${review.commentId}')">답글 달기</button>

            <div class="reply-form" id="reply-form-${review.commentId}" style="display: none; margin-top: 10px;">
              <form action="/cafeDetail/review/updateComment" method="post">
                <input type="hidden" name="parentId" value="${review.commentId}" />
                <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
                <input type="hidden" name="writerId" value="${loginCafe.loginId}" />
                <textarea name="content" placeholder="답글을 입력하세요" rows="2" cols="45"></textarea>
                <br />
                <button type="submit">답글 작성</button>
              </form>
            </div>
          </c:if>

          <%-- 답글 목록 --%>
          <c:forEach var="reply" items="${reviewList}">
            <c:if test="${reply.userRole == '2' && reply.commentParent == review.commentId}">
              <div class="reply" style="margin-left: 30px; margin-top: 10px;">
                <input type="hidden" value="${reply.commentId}" />
                <p><strong>${reply.commentUserId}</strong> (${reply.commentTime})</p>
                <p>${reply.content}</p>
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
    if (form.style.display === 'none') {
      form.style.display = 'block';
    } else {
      form.style.display = 'none';
    }
  }
</script>
