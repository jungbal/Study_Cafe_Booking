<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신청 정보</title>

</head>
<body class="bg-gray-50 text-gray-800">

<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

<main class="max-w-3xl mx-auto mt-10 p-6 bg-white rounded-lg shadow">
  <h2 class="text-2xl font-semibold mb-6 border-b pb-2">카페 신청 정보</h2>
  <table class="table-auto w-full border border-gray-200">
    <tbody>
      <tr class="bg-gray-100">
        <th class="border border-gray-300 px-4 py-2 text-left w-1/3">카페 이름</th>
        <td class="border border-gray-300 px-4 py-2">${cafe.cafeName}</td>
      </tr>
      <tr>
        <th class="border border-gray-300 px-4 py-2">주소</th>
        <td class="border border-gray-300 px-4 py-2">${cafe.cafeAddr}</td>
      </tr>
      <tr class="bg-gray-100">
        <th class="border border-gray-300 px-4 py-2">전화번호</th>
        <td class="border border-gray-300 px-4 py-2">${cafe.cafePhone}</td>
      </tr>
      <tr>
        <th class="border border-gray-300 px-4 py-2">사업자번호</th>
        <td class="border border-gray-300 px-4 py-2">${cafe.cafeBiznum}</td>
      </tr>
      <tr class="bg-gray-100">
        <th class="border border-gray-300 px-4 py-2">영업시간</th>
        <td class="border border-gray-300 px-4 py-2">${cafe.cafeStartHour} ~ ${cafe.cafeEndHour}</td>
      </tr>
      <tr>
        <th class="border border-gray-300 px-4 py-2">호스트 ID</th>
        <td class="border border-gray-300 px-4 py-2">${cafe.hostId}</td>
      </tr>
      <tr class="bg-gray-100">
        <th class="border border-gray-300 px-4 py-2">카페 소개</th>
        <td class="border border-gray-300 px-4 py-2">${cafe.cafeIntroduce}</td>
      </tr>
      <tr>
        <th class="border border-gray-300 px-4 py-2">상세 소개</th>
        <td class="border border-gray-300 px-4 py-2">${cafe.cafeIntroDetail}</td>
      </tr>
    </tbody>
  </table>

  <div class="mt-6 text-right">
    <button onclick="window.close();" class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded shadow">
      닫기
    </button>
  </div>
</main>

</body>
</html>
