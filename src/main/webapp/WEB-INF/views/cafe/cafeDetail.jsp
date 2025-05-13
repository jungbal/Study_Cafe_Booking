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
const tabButtons = document.querySelectorAll(".tab-btn");
const tabContents = document.querySelectorAll(".tab-content");

tabButtons.forEach((btn, index) => {
  btn.addEventListener("click", function () {
    // 버튼 활성화 처리
    tabButtons.forEach(b => b.classList.remove("active"));
    btn.classList.add("active");

    // 탭 컨텐츠 보이기/숨기기
    tabContents.forEach(c => c.style.display = "none");
    tabContents[index].style.display = "block";

    const cafeNo = "${cafe.cafeNo}";
    // 리뷰 탭 클릭 시
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
    // Q&A 탭 클릭 시
    if (btn.id === "btn-qna") {
      $.ajax({
        url: "/cafeDetail/qna", // Q&A를 처리하는 서블릿
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

</html>
