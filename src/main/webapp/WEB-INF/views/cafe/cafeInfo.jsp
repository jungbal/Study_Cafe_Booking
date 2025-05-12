<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<!-- WEB-INF/views/cafe/cafeInfo.jsp -->
<div class="cafeIntroDetail">
    <main>
    <section class="cafe-header">
      <h1 class="cafeName">스터디카페이름</h1>
      <p class="cafeAddr">스터디카페세부주소</p>

      <div class="cafe-media">
        <div class="cafeImage">
          <img src="placeholder.jpg" alt="스터디카페 사진">
        </div>
        <div class="seat-selection">
          <p>좌석선택 화면</p>
          <button>이용권 선택하기</button>
        </div>
      </div>
    </section>

    <section class="details">
      <div class="cafeIntroDetail">
        <h2>공간소개</h2>
        <ul class="cafeIntroList">
          <!-- 공간소개 항목이 여기에 동적으로 추가됩니다 -->
        </ul>
      </div>

      <div class="cafeWarn">
        <h2>주의사항</h2>
        <ul class="cafeWarnList">
          <!-- 주의사항 항목이 여기에 동적으로 추가됩니다 -->
        </ul>
      </div>

      <div class="business-hours">
        <h2>영업시간</h2>
        <p>영업 시작 시간 : <span class="cafeStartHour">오전 9시</span></p>
        <p>영업 종료 시간 : <span class="cafeEndHour">오후 11시</span></p>
      </div>

      <div class="contact">
        <h2>스터디 카페 전화번호</h2>
        <p class="cafePhone">02-xxx-xxxx</p>
      </div>

      <div class="location-info">
        <h2>위치정보</h2>
        <div class="map-placeholder">지도api사용 <br> 지도에 위치 표시</div>
      </div>
    </section>
  </main>

</div>
<!-- cafeWarn, cafeStartHour 등 포함 -->
