<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link type="text/css" rel="stylesheet" href="/resources/css/mypage.css" />
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<div class="sub-tab-wrap">
		<div class="sub-tab-btn active"
			onclick="switchReviewQa(event, 'review')">내 리뷰</div>
		<div class="sub-tab-btn" onclick="switchReviewQa(event, 'qna')">내
			Q&A</div>
	</div>

	<!-- 리뷰 테이블 -->
	<table class="review-table tab-visible" id="reviewTable">
		<tr>
			<th>카페명</th>
			<th>내용</th>
			<th>작성일</th>
			<th>관리</th>
		</tr>
		<c:forEach var="c" items="${reviewList}">
			<c:if test="${empty c.commentParent}">
				<tr>
					<td>${c.cafeName}</td>
					<td>${c.content}</td>
					<td>${c.commentTime}</td>
					<td>
						<button class="update-btn" data-id="${c.commentId}"
							data-type="review">수정</button> |
						<button type="button" class="delete-btn" data-id="${c.commentId}">삭제</button>
					</td>
				</tr>
				<!-- 이 부모 댓글에 달린 답글 출력 -->
				<c:forEach var="reply" items="${reviewList}">
					<c:if test="${reply.commentParent == c.commentId}">
						<tr class="reply">
							<td colspan="4">↳ <strong>답글:</strong> ${reply.content} <span
								style="float: right;">${reply.commentTime}</span>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:if>
		</c:forEach>
	</table>

	<!-- Q&A 테이블 -->
	<table class="qna-table" id="qnaTable">
		<tr>
			<th>카페명</th>
			<th>내용</th>
			<th>작성일</th>
			<th>관리</th>
		</tr>
		<c:forEach var="c" items="${qnaList}">
			<c:if test="${empty c.commentParent}">
				<tr>
					<td>${c.cafeName}</td>
					<td>${c.content}</td>
					<td>${c.commentTime}</td>
					<td>
						<button class="update-btn" data-id="${c.commentId}"
							data-type="qna">수정</button> |
						<button type="button" class="delete-btn" data-id="${c.commentId}">삭제</button>
					</td>
				</tr>
				<!-- 이 부모 댓글에 달린 답글 출력 -->
				<c:forEach var="reply" items="${qnaList}">
					<c:if test="${reply.commentParent == c.commentId}">
						<tr class="reply">
							<td colspan="4">↳ <strong>답글:</strong> ${reply.content} <span
								style="float: right;">${reply.commentTime}</span>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:if>
		</c:forEach>
	</table>

</body>
<script>
function switchReviewQa(event, type) {
  document.querySelectorAll(".sub-tab-btn").forEach(btn => btn.classList.remove("active"));
  event.target.classList.add("active");

  document.getElementById("reviewTable").classList.remove("tab-visible");
  document.getElementById("qnaTable").classList.remove("tab-visible");

  if (type === 'review') {
    document.getElementById("reviewTable").classList.add("tab-visible");
  } else {
    document.getElementById("qnaTable").classList.add("tab-visible");
  }
}

$(document).ready(function () {
	  // 수정 버튼 클릭 시 팝업창 열기
	  $(".update-btn").on("click", function (e) {
	    e.preventDefault();
	    const commentId = $(this).data("id");
	    const type = $(this).data("type");
	    const url = "/comment/handle?action=update&commentId=" + encodeURIComponent(commentId)
	              + "&type=" + encodeURIComponent(type);
	    window.open(url, "commentUpdatePopup", "width=600,height=400,left=300,top=200");
	  });

	  $(".delete-btn").on("click", function () {
	      const commentId = $(this).data("id");

	      Swal.fire({
	    	  title: "정말 삭제하시겠습니까?",
	    	  text: "삭제 후에는 복구할 수 없습니다.",
	    	  icon: "warning",
	    	  showCancelButton: true,
	    	  confirmButtonText: "삭제",
	    	  cancelButtonText: "취소",
	    	  confirmButtonColor: "#e74c3c",
	    	  cancelButtonColor: "#95a5a6"
	    	}).then((result) => {
	    	  if (result.isConfirmed) {
	    	    location.href = "/comment/handle?action=delete&commentId=" + encodeURIComponent(commentId);
	    	  }
	    	});
	    });
	});
</script>
</html>
