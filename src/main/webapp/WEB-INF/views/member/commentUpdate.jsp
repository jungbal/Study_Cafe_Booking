<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>댓글 수정</title>
  <link type="text/css" rel="stylesheet" href="/resources/css/mypage.css" />
</head>
<body>
  <h2>댓글 수정</h2>
  <form action="${pageContext.request.contextPath}/comment/handle" method="post">
    <input type="hidden" name="action"    value="update"/>
    <input type="hidden" name="commentId" value="${comment.commentId}"/>
    <input type="hidden" name="type"      value="${param.type}"/>
    <textarea name="content" rows="5" cols="60">${comment.content}</textarea><br/>
    <button type="submit">저장</button>
    <button type="button" onclick="history.back()">취소</button>
  </form>
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
