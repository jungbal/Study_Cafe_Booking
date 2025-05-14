<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="cafeReview">
  <h2>리뷰</h2>
  <div class="review-list">

    <c:forEach var="review" items="${reviewList}">
    
      <c:if test="${review.userRole == '3'}"> <%-- 댓글일 때 => userRole이 3이어야 함 --%>
        <div class="comment" style="margin-bottom: 20px;">
          <input type="hidden" value="${review.commentId}" />
          <p><strong>${review.commentUserId}</strong> (${review.commentTime})</p>
          <p>${review.content}</p>

          <%-- 답글 리스트 표시 --%>
          <c:forEach var="reply" items="${reviewList}">

            <c:if test="${reply.userRole == '2' && reply.commentParent == review.commentId}"> <%-- 답글일 때 => userRole이 2이어야 함 --%>
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
