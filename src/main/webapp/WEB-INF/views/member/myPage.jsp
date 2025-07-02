<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="hideHeaderSpacer" value="true" scope="request"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>마이페이지</title>
  <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
  <link type="text/css" rel="stylesheet" href="/resources/css/mypage.css" />
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

<div class="flex max-w-screen-lg mx-auto min-h-[600px]">
<!-- 사이드바 -->
<div class="w-48 border-r border-gray-300 p-5">
  <!-- MENU 텍스트 -->
  <h2 class="text-lg font-semibold mb-4 text-gray-700">MENU</h2>
<br>
  <button class="w-full mb-2 px-4 py-2 text-base rounded-lg 
                 ${tab eq 'account' ? 'bg-blue-600 text-white' : 'bg-gray-100'}"
          onclick="switchTab('account')">
    계정 관리
  </button>
  <button class="w-full mb-2 px-4 py-2 text-base rounded-lg 
                 ${tab eq 'review' ? 'bg-blue-600 text-white' : 'bg-gray-100'}"
          onclick="switchTab('review')">
    리뷰 / Q&A
  </button>
  <button class="w-full mb-2 px-4 py-2 text-base rounded-lg
                 ${tab eq 'payment' ? 'bg-blue-600 text-white' : 'bg-gray-100'}"
          onclick="switchTab('payment')">
    결제 내역
  </button>
  <button class="w-full mb-2 px-4 py-2 text-base rounded-lg
                 ${tab eq 'reservation' ? 'bg-blue-600 text-white' : 'bg-gray-100'}"
          onclick="switchTab('reservation')">
    예약 내역
  </button>
</div>


  <!-- 콘텐츠 -->
  <div class="flex-grow p-5">
    <c:if test="${tab eq 'account'}">
      <jsp:include page="accountInfo.jsp"/>
    </c:if>
    <c:if test="${tab eq 'review'}">
      <jsp:include page="reviewQa.jsp"/>
    </c:if>
    <c:if test="${tab eq 'payment'}">
      <jsp:include page="myPay.jsp"/>
    </c:if>
    <c:if test="${tab eq 'reservation'}">
      <jsp:include page="myReservation.jsp"/>
    </c:if>
  </div>
</div>

<script>
  function switchTab(tabName) {
    location.href = '/myPage/myInfo?tab=' + tabName;
  }
</script>
</body>
</html>
