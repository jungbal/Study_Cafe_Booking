<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>댓글 수정</title>
  <link type="text/css" rel="stylesheet" href="/resources/css/mypage.css" />
  <style>
    .comment-edit-container {
      max-width: 500px;
      margin: 80px auto;
      padding: 30px;
      background-color: white;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    }

    .comment-edit-container h2 {
      text-align: center;
      margin-bottom: 20px;
      color: var(--main1);
    }

    .comment-edit-form textarea {
      width: 100%;
      height: 120px;
      padding: 12px;
      border: 1px solid var(--gray3);
      border-radius: 6px;
      resize: vertical;
      font-size: 15px;
      color: #333;
      margin-bottom: 20px;
    }

    .comment-edit-form .btn-group {
      display: flex;
      justify-content: space-between;
    }

    .comment-edit-form button {
      flex: 1;
      padding: 10px;
      border-radius: 6px;
      border: none;
      font-weight: bold;
      cursor: pointer;
      transition: background-color 0.2s;
    }

    .comment-edit-form .btn-submit {
      background-color: var(--main1);
      color: white;
      margin-right: 10px;
    }

    .comment-edit-form .btn-submit:hover {
      background-color: #2980b9;
    }

    .comment-edit-form .btn-cancel {
      background-color: #ddd;
      color: #333;
    }

    .comment-edit-form .btn-cancel:hover {
      background-color: #bbb;
    }
  </style>
</head>
<body>
<div class="comment-edit-container">
  <h2><c:choose>
  		<c:when test="${type eq 'review'}">리뷰 수정 </c:when>
  		<c:when test="${type eq 'qna'}">Q&A 수정 </c:when>
  		</c:choose>
  </h2>
  <form action="${pageContext.request.contextPath}/comment/handle" method="post">
    <input type="hidden" name="action"    value="update"/>
    <input type="hidden" name="commentId" value="${comment.commentId}"/>
    <input type="hidden" name="type"      value="${param.type}"/>
    <textarea name="content" rows="5" cols="60">${comment.content}</textarea><br/>
    <div class="btn-group">
    <button type="submit" class="primary">저장</button>
    <button type="button" class="danger" onclick="window.close();">취소</button>
    </div>
  </form>
 </div>
  <c:if test="${updateSuccess}">
  <script>
  alert("리뷰가 수정되었습니다.");
  if (window.opener && !window.opener.closed) {
    // 부모창에서 review 탭 강제 활성화 함수 호출
    window.opener.switchReviewQa(null, '${type}');
    // 혹시 탭은 유지하고 리스트만 새로고침하고 싶다면 아래처럼
    // window.opener.location.reload();
  }
  window.close(); // 팝업창 닫기
</script>
</c:if>
</body>
</html>
