<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>검색 결과</title>
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@3.3.2/dist/tailwind.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body class="bg-gray-100">
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

<div class="max-w-screen-xl mx-auto px-4 py-10">
  <div class="bg-white/70 rounded-xl shadow p-6 mb-8">
  	<h1 class="text-5xl font-semibold text-gray-500">검색 결과</h1>
  </div>
  <hr class="mb-8">

  <div class="flex flex-wrap gap-6 justify-center">
    <c:choose>
      <c:when test="${empty list}">
        <div class="w-full text-center text-gray-500 text-lg py-20">검색 결과가 없습니다.</div>
      </c:when>
      <c:otherwise>
        <c:forEach var="list" items="${list}">
          <a href="/cafeDetail?cafeNo=${list.cafeNo}" class="block w-full sm:w-[47%] lg:w-[30%] hover:no-underline">
            <div class="bg-white rounded-xl shadow-md overflow-hidden hover:shadow-lg transition duration-300 h-full">
              <img src="${list.cafeImagePath}" alt="${list.cafeName}" class="w-full h-48 object-cover rounded-t-xl">
              <div class="p-4 text-center">
                <h2 class="text-lg font-bold text-gray-800 mb-1">${list.cafeName}</h2>
                <p class="text-sm text-gray-500">${list.cafeAddr}</p>
              </div>
            </div>
          </a>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </div>
</div>

</body>
</html>
