<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- myReservation.jsp -->
<div class="bg-white shadow rounded-md p-4 max-w-3xl mx-auto">
  <div class="flex justify-center mb-4">
    <h1 class="text-xl font-semibold text-black">예약 내역</h1>
  </div>
  <div class="overflow-x-auto">
    <table class="w-full border border-gray-300 text-xs text-left bg-white rounded-md">
      <thead class="bg-gray-100">
        <tr>
          <th class="px-3 py-2 border-b border-gray-300 whitespace-nowrap">카페명</th>
          <th class="px-3 py-2 border-b border-gray-300 whitespace-nowrap">좌석번호</th>
          <th class="px-3 py-2 border-b border-gray-300">이용시작시간</th>
          <th class="px-3 py-2 border-b border-gray-300">이용종료시간</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="p" items="${sessionScope.payList}">
          <tr class="hover:bg-gray-50">
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
