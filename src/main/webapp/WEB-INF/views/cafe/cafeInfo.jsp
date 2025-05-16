<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=40357a867ee4bcc2fc977d204c502462&libraries=services"></script>
<div class="cafeIntroDetail">
  <main>
    <section class="details">
      
      <!-- 공간소개 -->
      <div class="intro">
        <h2>공간소개</h2>
        <ul class="cafeIntroList">
          <li>${cafe.cafeIntroDetail}</li>
        </ul>
      </div>

      <!-- 영업시간 -->
      <div class="business-hours">
        <h2>영업시간</h2>
        <p>영업 시작 시간 : <span class="cafeStartHour">${cafe.cafeStartHour}</span></p>
        <p>영업 종료 시간 : <span class="cafeEndHour">${cafe.cafeEndHour}</span></p>
      </div>

      <!-- 연락처 -->
      <div class="contact">
        <h2>스터디 카페 전화번호</h2>
        <p class="cafePhone">${cafe.cafePhone}</p>
      </div>

<br>
<h2>위치</h2>
      <!-- 지도 영역 -->
	<div id="map" style="width:50%;height:350px;"></div>
    </section>
  </main>
</div>

<!-- Kakao 지도 스크립트 -->
<script>
var cafeAddr = '${cafe.cafeAddr}';  // JSP 값을 JS 변수로
var cafeName = '${cafe.cafeName}';  // JSP 값을 JS 변수로

console.log("cafeAddr : " + cafeAddr);
console.log("cafeName : " + cafeName);

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
mapOption = {
    center: new kakao.maps.LatLng(33.450701, 126.570667), // 초기 중심좌표
    level: 3 
};  

var map = new kakao.maps.Map(mapContainer, mapOption); 

var geocoder = new kakao.maps.services.Geocoder();

// 주소로 좌표를 검색
geocoder.addressSearch(cafeAddr, function(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 마커 표시
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });

        // 인포윈도우 내용에 cafeName 사용
        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">' + cafeName + '</div>'
        });
        infowindow.open(map, marker);

        // 지도 중심 이동
        map.setCenter(coords);
    }
});
</script>

