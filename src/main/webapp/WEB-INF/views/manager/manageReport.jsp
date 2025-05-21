<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신고 목록</title>
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body class="bg-gray-100 text-gray-800">

<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

<main class="max-w-7xl mx-auto p-6">
  <section class="bg-white shadow rounded-lg p-6">
    <h1 class="text-2xl font-bold mb-6 border-b pb-2">신고 목록</h1>
    
    <div class="overflow-x-auto">
      <table class="min-w-full table-auto border-collapse border border-gray-200">
        <thead>
          <tr class="bg-gray-100 text-left">
            <th class="border border-gray-300 px-4 py-2">신고 번호</th>
            <th class="border border-gray-300 px-4 py-2">신고한 사용자ID</th>
            <th class="border border-gray-300 px-4 py-2">신고된 사용자ID</th>
            <th class="border border-gray-300 px-4 py-2">신고 사유</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="report" items="${reportList}">
            <tr class="hover:bg-gray-50">
              <td class="border border-gray-300 px-4 py-2">${report.reportId}</td>
              <td class="border border-gray-300 px-4 py-2">${report.reporterId}</td>
              <td class="border border-gray-300 px-4 py-2">${report.targetCommentId}</td>
              <td class="border border-gray-300 px-4 py-2">${report.reportCodeId}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </section>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

</body>
</html>
