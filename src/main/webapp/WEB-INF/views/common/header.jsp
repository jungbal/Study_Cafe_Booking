<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="https://cdn.tailwindcss.com"></script>
<script>
    function toggleMenu() {
        const menu = document.getElementById("userMenu");
        menu.classList.toggle("hidden");
    }
</script>

<header class="w-full bg-white shadow-md fixed top-0 left-0 z-50">
  <div class="max-w-screen-xl mx-auto px-4 py-3 flex items-center justify-between">
    <!-- 로고 -->
    <div class="text-xl font-bold text-gray-800">
      <a href="/">StudyCafe</a>
    </div>
        
<div class="h-[70px]"></div>

        <!-- 검색 폼 + 메뉴 -->
        <div class="w-full flex justify-center py-4 bg-white shadow-sm">
            <!-- 검색 폼 -->
            <form action="/search" method="get" class="flex items-center gap-2 mx-auto max-w-md w-full">
           <input type="text" name="srchStr" placeholder="검색어를 입력해주세요."
                  class="px-4 border border-gray-300 rounded-l-md focus:outline-none focus:ring-2 focus:ring-blue-500 h-10 w-full" />
           <button type="submit"
        class="px-4 bg-blue-600 text-white rounded-r-md hover:bg-blue-700 h-10 flex items-center justify-center">
  <span class="whitespace-nowrap">검색</span>
</button>
         </form>


            </form>

            <!-- 메뉴 아이콘 -->
          <button onclick="toggleMenu()" class="text-gray-700 hover:text-blue-600 focus:outline-none ml-4">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" stroke-width="2"
                 viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path stroke-linecap="round" stroke-linejoin="round"
                    d="M4 6h16M4 12h16M4 18h16"></path>
            </svg>
          </button>

            <!-- 토글 메뉴 (드롭다운) -->
            <div id="userMenu" class="absolute top-16 right-4 bg-white border border-gray-200 shadow-lg rounded-md w-48 hidden">
                <div class="flex flex-col p-3 space-y-2 text-sm text-gray-700">
                    <!-- 로그인 안 된 상태 -->
                    <c:if test="${empty sessionScope.loginCafe}">
                        <a href="/loginFrm" class="hover:text-blue-600">로그인</a>
                        <a href="/joinFrm" class="hover:text-blue-600">회원가입</a>
                    </c:if>

                    <!-- 로그인 된 상태 -->
                    <c:if test="${not empty sessionScope.loginCafe}">
                        <a href="/LogoutServlet" class="hover:text-blue-600">로그아웃</a>
                        <a href="/myPage/chkPwFrm" class="hover:text-blue-600">마이페이지</a>
                        <a href="#" class="hover:text-blue-600">내 리뷰/Q&A</a>

                        <!-- 일반 이용자 -->
                        <c:if test="${sessionScope.role == 3}">
                            <a href="/applyCafe" class="hover:text-blue-600">업체(호스트) 신청</a>
                            <a href="/editCafeFrm" class="hover:text-blue-600">업체(호스트) 수정</a>
                        </c:if>

                        <!-- 호스트 -->
                        <c:if test="${sessionScope.role == 2}">
                            <a href="#" class="hover:text-blue-600">카페 관리</a>
                            <a href="#" class="hover:text-blue-600">카페 리뷰/Q&A</a>
                        </c:if>

                        <!-- 관리자 -->
                        <c:if test="${sessionScope.role == 1}">
                            <a href="/manager/userManage?reqPage=1" class="hover:text-blue-600">이용자 관리</a>
                            <a href="/manager/cafeManage?reqPage=1" class="hover:text-blue-600">업체 관리</a>
                            <a href="/manager/cafeApplyChk" class="hover:text-blue-600">업체신청정보열람</a>
                            <a href="/manager/chkReport?reqPage=1" class="hover:text-blue-600">신고접수열람</a>
                        </c:if>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</header>

<!-- 헤더 공간 확보 -->
<div class="h-[70px]"></div>
