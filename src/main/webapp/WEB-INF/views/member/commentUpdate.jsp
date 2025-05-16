<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>댓글 수정</title>
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
</body>
</html>
