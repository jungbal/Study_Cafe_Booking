<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>스터디카페 상세정보 페이지</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <style>
    .tab-menu button.active {
      background: #ccc;
      font-weight: bold;
    }
    .seats-container {
      display: grid;
      grid-template-columns: repeat(6, 50px);
      gap: 10px;
      width: 320px;
      margin-bottom: 20px;
    }
    .seat-box {
      width: 50px;
      height: 50px;
      background-color: #eee;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      border: 1px solid #999;
    }
    .seat-box.selected {
      background-color: #4CAF50;
      color: white;
    }
    .ticket-section {
      display: none;
      margin-top: 20px;
    }
  </style>
</head>
<body>
  <section class="cafe-header">
    <h1 class="cafeName">${cafe.cafeName}</h1>
    <p class="cafeAddr">${cafe.cafeAddr}</p>

    <div class="cafe-media">
      <div class="cafeImage">
        <img src="placeholder.jpg" alt="스터디카페 사진">
      </div>
      <br><hr><br>
      <div class="seat-selection">
        <p>좌석선택 화면</p>
        
        <%-- 이용권 선택 화면 --%>
        
        <button>이용권 선택하기</button> <%-- 결제 서블릿 (/payInfo) 로 보내기. --%>
      
      <div class="seat-selection">
        <p><strong>좌석 선택 화면</strong></p>
        <div class="seats-container">
          <c:forEach var="i" begin="1" end="30">
            <div class="seat-box" data-seat="${i}">${i}</div>
          </c:forEach>
        </div>

        <div class="ticket-section">
          <form id="ticketForm" method="post" action="/payInfo">
            <input type="hidden" name="seatNo" id="seatNo" value="">

            <p><strong>이용권 선택</strong></p>
            <c:forEach var="ticket" items="${ticketList}">
              <div>
                <label>
                  <input type="radio" name="ticketId" value="${ticket.ticketId}" required>
                  ${ticket.ticketType} (${ticket.ticketHour}시간) - ${ticket.ticketPrice}원
                </label>
              </div>
            </c:forEach>

            <br>
            <button type="submit">이용권 선택하기</button>
          </form>
        </div>
      </div>
    </div>
  </section>

  <br><hr><br>

  <nav class="tab-menu">
    <button id="btn-info" class="tab-btn active">상세정보</button>
    <button id="btn-review" class="tab-btn">리뷰</button>
    <button id="btn-qna" class="tab-btn">Q&A</button>
  </nav>

  <br><hr><br>

  <div id="cafeContent">
    <div id="info-tab" class="tab-content active">
      <jsp:include page="/WEB-INF/views/cafe/cafeInfo.jsp"/>
    </div>

    <div id="review-tab" class="tab-content" style="display: none;">
      <!-- 리뷰 AJAX 응답 영역 -->
    </div>

    <div id="qna-tab" class="tab-content" style="display: none;">
      <!-- Q&A AJAX 응답 영역 -->
    </div>
  </div>
</body>

<script>
$(document).ready(function() {
  $(".seat-box").click(function() {
    $(".seat-box").removeClass("selected");
    $(this).addClass("selected");

    let seatNo = $(this).data("seat");
    $("#seatNo").val(seatNo);
    $(".ticket-section").slideDown();
  });
});

const tabButtons = document.querySelectorAll(".tab-btn");
const tabContents = document.querySelectorAll(".tab-content");

tabButtons.forEach((btn, index) => {
  btn.addEventListener("click", function () {
    tabButtons.forEach(b => b.classList.remove("active"));
    btn.classList.add("active");

    tabContents.forEach(c => c.style.display = "none");
    tabContents[index].style.display = "block";

    const cafeNo = "${cafe.cafeNo}";
    if (btn.id === "btn-review") {
      $.ajax({
        url: "/cafeDetail/review",
        type: "GET",
        data: { cafeNo: cafeNo, RVQA: "RV" },
        success: function (data) {
          $("#review-tab").html(data);
        },
        error: function () {
          $("#review-tab").html("<p>리뷰 정보를 불러오지 못했습니다.</p>");
        }
      });
    }
    if (btn.id === "btn-qna") {
      $.ajax({
        url: "/cafeDetail/qna",
        type: "GET",
        data: { cafeNo: cafeNo, RVQA: "QA" },
        success: function (data) {
          $("#qna-tab").html(data);
        },
        error: function () {
          $("#qna-tab").html("<p>Q&A 정보를 불러오지 못했습니다.</p>");
        }
      });
    }
  });
});
</script>

</body>
</html>
