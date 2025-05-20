<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href="https://fonts.googleapis.com/css2?family=Nanum+Brush+Script&display=swap" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@3.3.2/dist/tailwind.min.css" rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<script>
    function toggleMenu() {
        const menu = document.getElementById("userMenu");
        menu.classList.toggle("hidden");
    }
</script>

<header class="w-full bg-white shadow-md fixed top-0 left-0 z-50">
  <div class="max-w-screen-xl mx-auto px-4 py-3 flex items-center justify-between relative">

    <!-- 좌측: 로고 -->
    <div class="flex items-center space-x-2 whitespace-nowrap cursor-pointer select-none">
      <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 text-blue-600" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
        <path stroke-linecap="round" stroke-linejoin="round" d="M12 6v6h4" />
        <path stroke-linecap="round" stroke-linejoin="round" d="M3 6h18M3 18h18M3 6v12a2 2 0 002 2h14a2 2 0 002-2V6" />
      </svg>
      <a href="/" class="text-3xl font-extrabold text-blue-600 hover:text-blue-800 transition-colors duration-300">공부해라</a>
    </div>

    <!-- 가운데: 검색 폼 -->
    <form action="/search" method="get" class="flex items-center gap-2 max-w-md w-full mx-auto px-4">
      <input type="text" name="srchStr" placeholder="검색어를 입력해주세요."
             class="px-4 border border-gray-300 rounded-l-md focus:outline-none focus:ring-2 focus:ring-blue-500 h-10 w-full" />
      <button type="submit"
              class="px-4 bg-blue-600 text-white rounded-r-md hover:bg-blue-700 h-10 flex items-center justify-center">
        <span class="whitespace-nowrap">검색</span>
      </button>
    </form>

    <!-- 우측: 메뉴 버튼 + 토글 메뉴 (같은 부모 내 위치) -->
    <div class="relative ml-4">
      <button onclick="toggleMenu()" class="text-gray-700 hover:text-blue-600 focus:outline-none">
        <svg class="w-6 h-6" fill="none" stroke="currentColor" stroke-width="2"
             viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path stroke-linecap="round" stroke-linejoin="round"
                d="M4 6h16M4 12h16M4 18h16"></path>
        </svg>
      </button>

      <!-- 토글 메뉴 -->
      <div id="userMenu" class="absolute right-0 mt-2 bg-white border border-gray-200 shadow-lg rounded-md w-48 hidden z-50">
        <div class="flex flex-col items-center p-3 space-y-2 text-sm text-gray-700">
          <c:if test="${empty sessionScope.loginCafe}">
            <a href="/loginFrm" class="hover:text-blue-600 text-center w-full">로그인</a>
            <a href="/joinFrm" class="hover:text-blue-600 text-center w-full">회원가입</a>
          </c:if>

          <c:if test="${not empty sessionScope.loginCafe}">
            <a href="/LogoutServlet" class="hover:text-blue-600 text-center w-full">로그아웃</a>
            <a href="/myPage/chkPwFrm" class="hover:text-blue-600 text-center w-full">마이페이지</a>
            <a href="#" class="hover:text-blue-600 text-center w-full">내 리뷰/Q&A</a>


      <!-- 일반 이용자 -->
      <c:if test="${sessionScope.role == 3}">

      	<c:if test="${sessionScope.hostId ne sessionScope.loginCafe.loginId}">
        	<a href="/applyCafe" class="hover:text-blue-600 text-center w-full">업체(호스트) 신청</a>
        </c:if>
        <c:if test="${sessionScope.hostId eq sessionScope.loginCafe.loginId}">
        	<a href="/editCafeFrm" class="hover:text-blue-600 text-center w-full">업체(호스트) 수정</a>
        </c:if>
        
      </c:if>


            <c:if test="${sessionScope.role == 2}">
              <a href="#" class="hover:text-blue-600 text-center w-full">카페 관리</a>
              <a href="#" class="hover:text-blue-600 text-center w-full">카페 리뷰/Q&A</a>
            </c:if>

            <c:if test="${sessionScope.role == 1}">
              <a href="/manager/userManage?reqPage=1" class="hover:text-blue-600 text-center w-full">이용자 관리</a>
              <a href="/manager/cafeManage?reqPage=1" class="hover:text-blue-600 text-center w-full">업체 관리</a>
             
              <a href="/manager/chkReport?reqPage=1" class="hover:text-blue-600 text-center w-full">신고접수열람</a>
            </c:if>
          </c:if>
        </div>
      </div>
    </div>

  </div>
</header>

<!-- 헤더 아래 공간 확보 -->
<div class="h-[80px]"></div>
