<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- myPay.jsp -->

<!-- 전체 결제내역 + 테이블 감싸는 흰 박스 -->
<div class="bg-white shadow rounded-md p-4 max-w-3xl mx-auto">
  <!-- 제목 -->
  <div class="flex justify-center mb-4">
    <h1 class="text-xl font-semibold text-black">전체 결제내역</h1>
  </div>

  <!-- 결제 테이블 -->
  <div class="overflow-x-auto">
    <table class="w-full border border-gray-300 text-xs text-left bg-white rounded-md">
      <thead class="bg-gray-100">
        <tr>
          <th class="px-3 py-2 border-b border-gray-300 whitespace-nowrap">결제일자</th>
          <th class="px-3 py-2 border-b border-gray-300 whitespace-nowrap">결제수단</th>
          <th class="px-3 py-2 border-b border-gray-300 whitespace-nowrap">결제금액</th>
          <th class="px-3 py-2 border-b border-gray-300 whitespace-nowrap">상태</th>
          <th class="px-3 py-2 border-b border-gray-300 whitespace-nowrap">카페명</th>
          <th class="px-3 py-2 border-b border-gray-300 whitespace-nowrap">좌석번호</th>
          <th class="px-3 py-2 border-b border-gray-300">예약시작시간</th>
          <th class="px-3 py-2 border-b border-gray-300">예약종료시간</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="p" items="${sessionScope.payList}">
          <tr class="hover:bg-gray-50">
            <td class="px-3 py-2 border-b border-gray-300 whitespace-nowrap text-black">${p.payTime}</td>
            <td class="px-3 py-2 border-b border-gray-300 whitespace-nowrap text-black">${p.payMethod}</td>
            <td class="px-3 py-2 border-b border-gray-300 whitespace-nowrap text-black">${p.payAmount}원</td>
            <td class="px-3 py-2 border-b border-gray-300 whitespace-nowrap text-black">${p.payStatus}</td>
            <td class="px-3 py-2 border-b border-gray-300 whitespace-nowrap text-black">${p.payCafeName}</td>
            <td class="px-3 py-2 border-b border-gray-300 whitespace-nowrap text-black">${p.payHistorySeatNo}</td>
            <td class="px-3 py-2 border-b border-gray-300 text-black">${p.payHistoryStart}</td>
            <td class="px-3 py-2 border-b border-gray-300 text-black">${p.payHistoryEnd}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>