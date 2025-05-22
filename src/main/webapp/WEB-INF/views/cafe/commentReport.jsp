<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>신고하기</title>
  <!-- Tailwind CSS CDN (개발용) -->
  <script src="https://cdn.tailwindcss.com"></script>
  <link href="https://fonts.googleapis.com/css2?family=Segoe+UI&display=swap" rel="stylesheet" />
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f9f9f9;
    }
  </style>
</head>
<body class="bg-gray-100 font-sans min-h-screen flex items-center justify-center p-4">

  <!-- 흰색 박스 -->
  <div class="bg-white max-w-2xl w-full p-8 rounded-lg shadow-lg border border-gray-300">
    <h2 class="text-2xl font-bold mb-6 text-gray-800">댓글 신고</h2>

    <!-- 신고 대상 댓글 내용 -->
    <div class="mb-6">
      <label class="block text-gray-700 font-semibold mb-2">신고 대상 댓글 내용</label>
      <p class="p-4 bg-gray-100 border rounded text-gray-800 whitespace-pre-wrap">${comment.content}</p>
    </div>

    <!-- 작성자 ID -->
    <div class="mb-6">
      <label class="block text-gray-700 font-semibold mb-2">작성자 ID</label>
      <p class="p-3 bg-gray-50 border rounded text-gray-700">${comment.commentUserId}</p>
    </div>

    <!-- 신고 폼 -->
    <form id="reportForm" class="space-y-6">
      <input type="hidden" name="reporterId" value="${loginCafe.loginId}" />
      <input type="hidden" name="commentId" value="${comment.commentId}" />

      <div>
        <label for="reasonCode" class="block text-gray-700 font-semibold mb-2">신고 사유</label>
        <select name="reasonCode" id="reasonCode" class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
          <c:forEach var="code" items="${codeList}">
            <option value="${code.codeId}">${code.codeName}</option>
          </c:forEach>
        </select>
      </div>

      <div class="flex justify-end">
        <button type="submit" class="bg-red-600 hover:bg-red-700 text-white px-6 py-2 rounded transition">
          신고하기
        </button>
      </div>
    </form>
  </div>

  <script>
    document.getElementById('reportForm').addEventListener('submit', function(e) {
      e.preventDefault(); // 기본 폼 제출 막기

      const form = e.target;
      const data = new URLSearchParams(new FormData(form));

      fetch('/cafeDetail/review/submitReport', {
        method: 'POST',
        body: data
      })
      .then(response => response.json())
      .then(json => {
        alert(json.message);
        if(json.success) {
          window.close();  // 성공 시 팝업 닫기
        }
      })
      .catch(() => {
        alert('신고 처리 중 오류가 발생했습니다.');
      });
    });
  </script>

</body>
</html>
