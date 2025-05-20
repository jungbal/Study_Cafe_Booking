<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>결제 정보</title>
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@3.3.2/dist/tailwind.min.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body class="font-sans m-0 p-0">
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<main>
  <h1 class="text-center text-3xl font-semibold my-8">결제하기</h1>
  <div class="flex justify-between px-5">
    
    <!-- 공간 정보 + 결제 예정 금액 -->
    <div class="border border-gray-300 rounded-xl p-5 w-[48%]">
      <h2 class="text-2xl font-semibold mb-4">공간 정보</h2>
      <div class="mb-3">
        <strong>카페명:</strong> ${cafe.cafeName} <br />
        <em>${cafe.cafeIntroduce}</em>
      </div>
      <div class="mb-3">
        <strong>주소:</strong> ${cafe.cafeAddr}
      </div>
      <div class="mb-3">
        <strong>전화번호:</strong> ${cafe.cafePhone}
      </div>
      <div class="mb-3">
        <strong>운영시간:</strong> ${cafe.cafeStartHour} ~ ${cafe.cafeEndHour}
      </div>

      <hr class="my-5 border-gray-300" />

      <h2 class="text-2xl font-semibold mb-4">결제 예정 금액</h2>
      <div class="mb-3">
        <strong>예약 날짜:</strong>
        <span id="displayDateLeft"></span>
        <input type="hidden" id="hiddenDateLeft" name="reservationDate" />
      </div>
      <div class="mb-3">
        <strong>시간권:</strong> ${ticket.ticketType}
      </div>
      <div class="mb-3">
        <strong>좌석 번호:</strong> ${seatNo}
      </div>
      <div class="mb-3 text-xl font-bold">
        <strong>금액:</strong> <span class="text-2xl font-extrabold text-blue-600">₩ ${ticket.ticketPrice}</span>
      </div>
      <form action="/payInfo/payProcess" method="post">
        <input type="hidden" name="ticketPrice" value="${ticket.ticketPrice}" />
        <input type="hidden" name="ticketHour" value="${ticket.ticketHour}" />
        <input type="hidden" name="ticketId" value="${ticket.ticketId}" />
        <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
        <input type="hidden" name="seatNo" value="${seatNo}" />
        <input type="hidden" name="userId" value="${loginCafe.loginId}" />
        
        <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white py-2 px-5 rounded-md text-lg cursor-pointer transition-colors duration-300">
		  결제하기
		</button>
      </form>
    </div>

    <!-- 결제 상세 정보 -->
    <div class="border border-gray-300 rounded-xl p-5 w-[48%]">
      <h2 class="text-2xl font-semibold mb-4">결제 상세 정보</h2>
      <div class="mb-3">
        <strong>예약 날짜:</strong>
        <span id="displayDateRight"></span>
        <input type="hidden" id="hiddenDateRight" />
      </div>
      <div class="mb-3">
        <strong>시간권:</strong> ${ticket.ticketType}
      </div>
      <div class="mb-3">
        <strong>좌석 번호:</strong> ${seatNo}
      </div>
      <h2 class="text-xl font-semibold mt-6">선택한 좌석</h2>
      <div class="grid grid-cols-6 gap-2 mt-2 w-[320px]">
		  <c:forEach var="i" begin="1" end="30">
		    <div class="w-[50px] h-[50px] border border-gray-400 flex items-center justify-center font-bold
		      ${i == seatNo ? 'bg-blue-600 text-white' : 'bg-gray-200'} rounded">
		      ${i}
		    </div>
		  </c:forEach>
		</div>
    </div>

  </div>
</main>

<script>
const today = new Date();

const yyyy = today.getFullYear();
const mm = String(today.getMonth() + 1).padStart(2, '0');
const dd = String(today.getDate()).padStart(2, '0');

const formattedDate = yyyy+'-'+mm+'-'+dd;

document.getElementById('displayDateLeft').textContent = formattedDate;
document.getElementById('displayDateRight').textContent = formattedDate;
document.getElementById('hiddenDateLeft').value = formattedDate;
document.getElementById('hiddenDateRight').value = formattedDate;
</script>

</body>
</html>
