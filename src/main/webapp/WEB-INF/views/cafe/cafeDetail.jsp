<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>스터디카페 상세정보 페이지</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <!-- Tailwind CDN -->
  <script src="https://cdn.tailwindcss.com"></script>
  <link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body class="bg-gray-50 text-gray-800">
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

<section class="max-w-5xl mx-auto p-6">
  <h1 class="text-3xl font-bold mb-2">${cafe.cafeName}</h1>
  <p class="text-lg text-gray-600 mb-6">${cafe.cafeAddr}</p>

  <div class="flex flex-col md:flex-row gap-6">
    <div class="w-full md:w-1/2 rounded-lg overflow-hidden shadow">
      <img src="${cafe.cafeImagePath}" alt="${cafe.cafeName}" class="w-full object-cover h-64">
    </div>

    <div class="w-full md:w-1/2">
      <div class="mb-4">
        <h2 class="text-xl font-semibold mb-2">좌석선택 화면</h2>

        <c:choose>
          <c:when test="${empty sessionScope.loginCafe}">
            <p class="text-red-600 font-bold">로그인 후 좌석을 선택할 수 있습니다.</p>
          </c:when>
          <c:otherwise>
            <c:if test="${not empty currentUsage}">
              <p class="text-red-600 font-bold">현재 좌석 이용중인 회원입니다.</p>
            </c:if>
            <c:if test="${empty currentUsage}">
              <div class="flex flex-wrap gap-2 max-w-lg mb-6">
                <c:forEach var="i" begin="1" end="30">
                  <c:set var="isUsed" value="false" />
                  <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.historySeatNo eq i}">
                      <c:set var="isUsed" value="true" />
                    </c:if>
                  </c:forEach>
                  <div
                    class="w-10 h-10 flex items-center justify-center text-sm font-medium border rounded cursor-pointer
                          ${isUsed ? 'bg-red-500 text-white cursor-not-allowed' : 'bg-white hover:bg-green-100'}"
                    data-seat="${i}">
                    ${i}
                  </div>
                </c:forEach>
              </div>

              <div class="ticket-section hidden bg-white border p-4 rounded shadow">
                <form id="ticketForm" method="post" action="/payInfo">
                  <input type="hidden" name="seatNo" id="seatNo" value="">
                  <input type="hidden" name="cafeNo" value="${cafe.cafeNo}">

                  <h3 class="text-lg font-semibold mb-2">이용권 선택</h3>

                  <c:choose>
                    <c:when test="${not empty ticketList}">
                      <div class="space-y-2">
                        <c:forEach var="ticket" items="${ticketList}">
                          <label class="flex items-center space-x-2">
                            <input type="radio" name="ticketId" value="${ticket.ticketId}" required class="accent-blue-600">
                            <span>${ticket.ticketType} - ${ticket.ticketPrice}원</span>
                          </label>
                        </c:forEach>
                      </div>
                      <button type="submit"
                              class="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">
                        이용권 선택하기
                      </button>
                    </c:when>
                    <c:otherwise>
                      <p class="text-red-600 font-bold mt-2">이용권 정보가 등록되지 않은 업체입니다. 호스트에게 문의해주세요.</p>
                    </c:otherwise>
                  </c:choose>
                </form>
              </div>
            </c:if>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
</section>

<hr class="my-6">

<nav class="flex justify-between max-w-5xl mx-auto mb-4 space-x-4">
  <button id="btn-info" class="tab-btn flex-1 px-4 py-2 rounded bg-blue-600 text-white">상세정보</button>
  <button id="btn-review" class="tab-btn flex-1 px-4 py-2 rounded bg-gray-200 hover:bg-gray-300">리뷰</button>
  <button id="btn-qna" class="tab-btn flex-1 px-4 py-2 rounded bg-gray-200 hover:bg-gray-300">Q&A</button>
</nav>


<div id="cafeContent" class="max-w-5xl mx-auto p-4 bg-white rounded shadow">
  <div id="info-tab" class="tab-content">
    <jsp:include page="/WEB-INF/views/cafe/cafeInfo.jsp"/>
  </div>
  <div id="review-tab" class="tab-content hidden"></div>
  <div id="qna-tab" class="tab-content hidden"></div>
</div>

<script>
$(document).ready(function () {
	  // 좌석 클릭 이벤트
	  $(".w-10.h-10").click(function () {
	    // 이미 사용 중 좌석은 선택 불가
	    if ($(this).hasClass("bg-red-500")) {
	      alert("이미 사용 중인 좌석입니다.");
	      return;
	    }

	    // 기존 선택 해제
	    $(".w-10.h-10").removeClass("bg-green-500 text-white");
	    // 선택된 좌석 표시
	    $(this).addClass("bg-green-500 text-white");

	    // 선택한 좌석번호 hidden input에 세팅
	    let seatNo = $(this).data("seat");
	    $("#seatNo").val(seatNo);

	    // 이용권 선택 폼 보여주기
	    $(".ticket-section").slideDown();
	  });

	  // 탭 메뉴 클릭 이벤트
	  const tabButtons = document.querySelectorAll(".tab-btn");
	  const tabContents = document.querySelectorAll(".tab-content");

	  tabButtons.forEach((btn, index) => {
	    btn.addEventListener("click", function () {
	      tabButtons.forEach(b => {
	        b.classList.remove("bg-blue-600", "text-white");
	        b.classList.add("bg-gray-200", "text-gray-800");
	      });

	      btn.classList.remove("bg-gray-200", "text-gray-800");
	      btn.classList.add("bg-blue-600", "text-white");

	      tabContents.forEach(c => c.classList.add("hidden"));
	      tabContents[index].classList.remove("hidden");

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

	  // 페이지 로드 시 쿼리 파라미터로 탭 선택
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
