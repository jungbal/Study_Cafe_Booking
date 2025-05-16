<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결제 정보</title>
<style>
  body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
  }
  .container {
    padding: 20px;
    display: flex;
    justify-content: space-between;
  }
  .section {
    border: 1px solid #ccc;
    padding: 20px;
    width: 48%;
    border-radius: 10px;
  }
  .section h2 {
    margin-top: 0;
  }
  .info-box {
    margin-bottom: 10px;
  }
  .pay-button {
    background-color: #4CAF50;
    color: white;
    padding: 10px 20px;
    font-size: 16px;
    border: none;
    cursor: pointer;
    border-radius: 5px;
  }
  .pay-button:hover {
    background-color: #45a049;
  }
  .seat-map {
    width: 100px;
    height: 100px;
    border: 1px solid #aaa;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: bold;
    margin-top: 10px;
  }
  .seats-container {
  display: grid;
  grid-template-columns: repeat(6, 50px);
  gap: 10px;
  width: 320px;
  margin-top: 10px;
}

.seat-box {
  width: 50px;
  height: 50px;
  background-color: #eee;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #999;
  font-weight: bold;
}

.seat-box.selected {
  background-color: #4CAF50;
  color: white;
}
  
</style>
</head>
<body>

<main>
  <h1 style="text-align:center;">결제하기</h1>
  <div class="container">
    
    <!-- 공간 정보 + 결제 예정 금액 -->
    <div class="section">
      <h2>공간 정보</h2>
      <div class="info-box">
        <strong>카페명:</strong> ${cafe.cafeName} <br>
        <em>${cafe.cafeIntroduce}</em>
      </div>
      <div class="info-box">
        <strong>주소:</strong> ${cafe.cafeAddr}
      </div>
      <div class="info-box">
        <strong>전화번호:</strong> ${cafe.cafePhone}
      </div>
      <div class="info-box">
        <strong>운영시간:</strong> ${cafe.cafeStartHour} ~ ${cafe.cafeEndHour}
      </div>

      <hr>

      <h2>결제 예정 금액</h2>
      <div class="info-box">
        <strong>예약 날짜:</strong> 
		<span id="displayDateLeft"></span>
		<input type="hidden" id="hiddenDateLeft" name="reservationDate">
      </div>
      <div class="info-box">
        <strong>시간권:</strong> ${ticket.ticketType}
      </div>
      <div class="info-box">
        <strong>좌석 번호:</strong> ${seatNo}
      </div>
      <div class="info-box">
        <strong>금액:</strong> <span style="font-size:20px; font-weight:bold;">₩ ${ticket.ticketPrice}</span>
      </div>
      <form action="/payment" method="post">
        <input type="hidden" id="hiddenDate" name="reservationDate">
        <input type="hidden" name="ticketId" value="${ticket.ticketId}">
        <input type="hidden" name="seatNo" value="${seatNo}">
        <button type="submit" class="pay-button">결제하기</button>
      </form>
    </div>

    <!-- 결제 상세 정보 -->
    <div class="section">
      <h2>결제 상세 정보</h2>
      <div class="info-box">
        <strong>예약 날짜:</strong> 
		<span id="displayDateRight"></span>
		<input type="hidden" id="hiddenDateRight">
      </div>
      <div class="info-box">
        <strong>시간권:</strong> ${ticket.ticketType}
      </div>
      <div class="info-box">
        <strong>좌석 번호:</strong> ${seatNo}
      </div>
      <h2>선택한 좌석</h2>
		<div class="seats-container">
		  <c:forEach var="i" begin="1" end="30">
		    <div class="seat-box ${i == seatNo ? 'selected' : ''}">${i}</div>
		  </c:forEach>
		</div>

    </div>

  </div>
</main>

<script>
const today = new Date();
console.log("Today:", today);

const yyyy = today.getFullYear();
const mm = String(today.getMonth() + 1).padStart(2, '0'); // 월 계산이 올바르게 되는지 확인
const dd = String(today.getDate()).padStart(2, '0'); // 날짜 계산이 올바르게 되는지 확인

console.log("Year:", yyyy); // 2025
console.log("Month:", mm); // 05
console.log("Day:", dd); // 14

const formattedDate = yyyy+'-'+mm+'-'+dd;
console.log("Formatted Date:", formattedDate); // 2025-05-14

// Update the HTML content for both left and right date fields
document.getElementById('displayDateLeft').textContent = formattedDate;
document.getElementById('displayDateRight').textContent = formattedDate;
document.getElementById('hiddenDateLeft').value = formattedDate;
document.getElementById('hiddenDateRight').value = formattedDate;
</script>

</body>
</html>
