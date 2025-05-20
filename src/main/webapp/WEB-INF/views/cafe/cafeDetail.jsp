<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>스터디카페 상세정보 페이지</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <style>
    .seats-container {
  width: 100%; /* 부모 너비에 맞춤 */
  white-space: nowrap; /* 줄바꿈 방지 */
  overflow-x: auto; /* 넘칠 경우 스크롤 생성 */
}

.seat-box {
  display: inline-block;
  width: 30px;   /* 너비 줄임 */
  height: 30px;  /* 높이도 같이 줄임 */
  line-height: 30px;
  border: 1px solid #333;
  text-align: center;
  margin: 2px;   /* 마진도 줄임 */
  cursor: pointer;
  user-select: none;
  vertical-align: middle;
}

.seat-box.blocked {
  background-color: #f44336; /* 빨간색 */
  color: white;
  cursor: not-allowed;
}


.seat-box.selected {
  background-color: #4caf50;
  color: white;
  }
  
.cafeImage {
  width: 200px;       /* 적절한 너비 지정 */
  height: auto;
  margin: 0 auto 20px;
  overflow: hidden;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.1);
}

.cafeImage img {
  width: 100%;        /* 부모 너비에 맞춰 줄임 */
  height: auto;       /* 비율 유지 */
  display: block;
  object-fit: cover;
  border-radius: 10px;
}

  </style>
</head>
<body>
  <section class="cafe-header">
    <h1 class="cafeName">${cafe.cafeName}</h1>
    <p class="cafeAddr">${cafe.cafeAddr}</p>

    <div class="cafe-media">
      <div class="cafeImage">
        <img src="${cafe.cafeImagePath}" alt="${cafe.cafeName}">
      </div>

      <br><hr><br>

      <div class="seat-selection">
        <p><strong>좌석선택 화면</strong></p>

        <c:choose>
  <c:when test="${empty sessionScope.loginCafe}">
    <p style="color: red; font-weight: bold;">로그인 후 좌석을 선택할 수 있습니다.</p>
  </c:when>
  <c:otherwise>
    <c:if test="${not empty currentUsage}">
      <p style="color: red; font-weight: bold;">이미 좌석 이용중인 회원입니다.</p>
    </c:if>
    <c:if test="${empty currentUsage}">
      <div class="seats-container">
        <c:forEach var="i" begin="1" end="30">
          <c:set var="isUsed" value="false" />
          <c:forEach var="s" items="${seatList}">
            <c:if test="${s.historySeatNo eq i}">
              <c:set var="isUsed" value="true" />
            </c:if>
          </c:forEach>
          <div class="seat-box ${isUsed ? 'blocked' : ''}" data-seat="${i}">${i}</div>
        </c:forEach>
      </div>

      <div class="ticket-section" style="display: none;">
        <form id="ticketForm" method="post" action="/payInfo">
          <input type="hidden" name="seatNo" id="seatNo" value="">
          <input type="hidden" name="cafeNo" value="${cafe.cafeNo}">

          <p><strong>이용권 선택</strong></p>

          <c:choose>
            <c:when test="${not empty ticketList}">
              <c:forEach var="ticket" items="${ticketList}">
                <div>
                  <label>
                    <input type="radio" name="ticketId" value="${ticket.ticketId}" required>
                    ${ticket.ticketType} - ${ticket.ticketPrice}원
                  </label>
                </div>
              </c:forEach>
              <br>
              <button type="submit">이용권 선택하기</button>
            </c:when>
            <c:otherwise>
              <p style="color: red; font-weight: bold;">이용권 정보가 등록되지 않은 업체입니다. 호스트에게 문의해주세요.</p>
            </c:otherwise>
          </c:choose>
        </form>
      </div>
    </c:if>
  </c:otherwise>
</c:choose>

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
    <div id="review-tab" class="tab-content" style="display: none;"></div>
    <div id="qna-tab" class="tab-content" style="display: none;"></div>
  </div>

<script>
$(document).ready(function () {
  $(".seat-box").click(function () {
    if ($(this).hasClass("blocked")) {
      alert("이미 사용 중인 좌석입니다.");
      return;
    }

    $(".seat-box").removeClass("selected");
    $(this).addClass("selected");

    let seatNo = $(this).data("seat");
    $("#seatNo").val(seatNo);
    $(".ticket-section").slideDown();
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
          data: { cafeNo: cafeNo, RVQA: "RV", userId: "${loginCafe.loginId}" },
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

  // 페이지 진입 시 URL 파라미터로 탭 열기
  const urlParams = new URLSearchParams(window.location.search);
  const tab = urlParams.get("tab");

  if (tab === "review") {
    document.getElementById("btn-review").click();
  } else if (tab === "qna") {
    document.getElementById("btn-qna").click();
  } else {
    document.getElementById("btn-info").click();
  }
});
</script>

</body>
</html>
