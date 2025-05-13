<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- cafeDetail.jsp -->
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
  <nav class="tab-menu">
    <button id="btn-info" class="tab-btn active">상세정보</button>
    <button id="btn-review" class="tab-btn">리뷰</button>
    <button id="btn-qna" class="tab-btn">Q&A</button>
  </nav>

  <div id="cafeContent">
    <!-- 탭 컨텐츠: 모두 미리 포함 -->
    <div id="info-tab" class="tab-content active">
      <jsp:include page="/WEB-INF/views/cafe/cafeInfo.jsp"/>
    </div>

    <div id="review-tab" class="tab-content" style="display: none;">
      <jsp:include page="/WEB-INF/views/cafe/cafeReview.jsp"/>
    </div>

    <div id="qna-tab" class="tab-content" style="display: none;">
      <jsp:include page="/WEB-INF/views/cafe/cafeQA.jsp"/>
    </div>
  </div>
</body>
<script>
  const tabButtons = document.querySelectorAll(".tab-btn");
  const tabContents = document.querySelectorAll(".tab-content");

  tabButtons.forEach((btn, index) => {
    btn.addEventListener("click", function () {
      // 버튼 활성화 상태 갱신
      tabButtons.forEach(b => b.classList.remove("active"));
      btn.classList.add("active");

      // 콘텐츠 보이기/숨기기
      tabContents.forEach(c => c.style.display = "none");
      tabContents[index].style.display = "block";
    });
  });
</script>


</html>
