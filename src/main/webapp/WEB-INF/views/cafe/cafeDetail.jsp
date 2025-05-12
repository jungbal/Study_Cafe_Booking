<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- cafeDetail.jsp -->
<html>
<head>
  <title>스터디카페 상세정보</title>
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

  <!-- AJAX -->
  <div id="cafeContent">
    <jsp:include page="/WEB-INF/views/cafe/cafeInfo.jsp"/>
  </div>

  <script>
    $(function () {
      $("#btn-info").click(function () {
        console.log("탭 클릭됨: info");
        $(".tab-btn").removeClass("active");
        $(this).addClass("active");

        $("#cafeContent").load("/cafeDetail/info");
      });

      $("#btn-review").click(function () {
        console.log("탭 클릭됨: review");
        $(".tab-btn").removeClass("active");
        $(this).addClass("active");

        $("#cafeContent").load("/cafeDetail/review");
      });

      $("#btn-qna").click(function () {
        console.log("탭 클릭됨: qna");
        $(".tab-btn").removeClass("active");
        $(this).addClass("active");

        $("#cafeContent").load("/cafeDetail/qa");
      });
    });
  </script>
</body>
</html>
