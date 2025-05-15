<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="cafeIntroDetail">
    <main>
    <section class="details">
      <div class="cafeIntroDetail">
        <h2>공간소개</h2> <%--공간소개 --%>
        <ul class="cafeIntroList">
          <li>${cafe.cafeIntroDetail}</li>
        </ul>
      </div>


      <div class="business-hours">
        <h2>영업시간</h2>
        <p>영업 시작 시간 : <span class="cafeStartHour">${cafe.cafeStartHour}</span></p>
        <p>영업 종료 시간 : <span class="cafeEndHour">${cafe.cafeEndHour}</span></p>
      </div>

      <div class="contact">
        <h2>스터디 카페 전화번호</h2>
        <p class="cafePhone">${cafe.cafePhone}</p>
      </div>

      <div class="location-info">
        <h2>위치정보</h2>
        <div class="map-placeholder">지도api사용 <br> 지도에 위치 표시</div>
        <img src="/map?cafeNo=${cafe.cafeNo}" alt="지도">
        
        <!--<img src="https://maps.apigw.ntruss.com/map-static/v2/raster-cors?w=600&h=400&center=126.9307891,37.5189066&markers=type:d|size:mid|pos:126.9307891 37.5189066" alt="Map">-->
        <!--<img src="https://maps.apigw.ntruss.com/map-static/v2/raster-cors?w=300&h=300&center=127.1054221,37.3591614&level=16&X-NCP-APIGW-API-KEY-ID={cwtyjal38c}">-->
        


      </div>
    </section>
  </main>

</div>
