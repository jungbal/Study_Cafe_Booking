<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>마이페이지</title>
  <style>
    .container {
      display: flex;
      max-width: 1000px;
      margin: 0 auto;
      min-height: 600px;
    }
    .sidebar {
      width: 200px;
      border-right: 1px solid #ccc;
      padding: 20px;
    }
    .sidebar button {
      display: block;
      width: 100%;
      margin-bottom: 10px;
      padding: 10px;
      font-size: 1rem;
      background-color: #f5f5f5;
      border: none;
      cursor: pointer;
      border-radius: 8px; /* 둥글게 하고 싶으면 추가 */
    }
    .sidebar button.active {
      background-color: #3498db;
      color: white;
    }
    .content {
      flex-grow: 1;
      padding: 20px;
    }
  </style>
 <link type="text/css" rel="stylesheet" href="/resources/css/mypage.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
  <div class="container">
    <!-- 사이드바 탭 버튼: 클릭 시 /myPage/myInfo?tab=review 등으로 요청 -->
    <div class="sidebar">
      <button class="tab-btn ${tab eq 'account' ? 'active' : ''}" onclick="switchTab('account')">계정 관리</button>
      <button class="tab-btn ${tab eq 'review' ? 'active' : ''}" onclick="switchTab('review')">리뷰 / Q&A</button>
      <button class="tab-btn ${tab eq 'payment' ? 'active' : ''}" onclick="switchTab('payment')">결제 내역</button>
    </div>

    <!-- 우측 콘텐츠 -->
    <div class="content">
    <!-- 조건 분기로 JSP 파일 탭별로 불러오기 -->
      <c:if test="${tab eq 'account'}">
        <jsp:include page="accountInfo.jsp"/>
      </c:if>
      <c:if test="${tab eq 'review'}">
        <jsp:include page="reviewQa.jsp"/>
      </c:if>
      <c:if test="${tab eq 'payment'}">
        <jsp:include page="myPay.jsp"/>
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