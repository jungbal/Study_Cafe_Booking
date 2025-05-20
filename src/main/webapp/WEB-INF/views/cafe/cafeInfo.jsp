<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=40357a867ee4bcc2fc977d204c502462&libraries=services"></script>

<div class="max-w-5xl mx-auto px-4 py-6 bg-white rounded shadow">
  <main class="space-y-8">
    
    <!-- 공간소개 -->
    <section>
      <h2 class="text-2xl font-semibold mb-2 text-gray-800">공간소개</h2>
      <ul class="list-disc list-inside text-gray-700">
        <li>${cafe.cafeIntroDetail}</li>
      </ul>
    </section>

    <!-- 영업시간 -->
    <section>
      <h2 class="text-2xl font-semibold mb-2 text-gray-800">영업시간</h2>
      <p class="text-gray-700">영업 시작 시간: <span class="font-medium">${cafe.cafeStartHour}</span></p>
      <p class="text-gray-700">영업 종료 시간: <span class="font-medium">${cafe.cafeEndHour}</span></p>
    </section>

    <!-- 연락처 -->
    <section>
      <h2 class="text-2xl font-semibold mb-2 text-gray-800">스터디 카페 전화번호</h2>
      <p class="text-gray-700">${cafe.cafePhone}</p>
    </section>

    <!-- 위치 -->
    <section>
      <h2 class="text-2xl font-semibold mb-4 text-gray-800">위치</h2>
      <div id="map" class="w-full h-[350px] rounded-lg border shadow"></div>
    </section>

  </main>
</div>

<!-- Kakao 지도 스크립트 -->
<script>
  var cafeAddr = '${cafe.cafeAddr}';
  var cafeName = '${cafe.cafeName}';

  var mapContainer = document.getElementById('map'),
      mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3
      };

  var map = new kakao.maps.Map(mapContainer, mapOption); 
  var geocoder = new kakao.maps.services.Geocoder();

  geocoder.addressSearch(cafeAddr, function(result, status) {
    if (status === kakao.maps.services.Status.OK) {
      var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

      var marker = new kakao.maps.Marker({
        map: map,
        position: coords
      });

      var infowindow = new kakao.maps.InfoWindow({
        content: '<div style="width:150px;text-align:center;padding:6px 0;">' + cafeName + '</div>'
      });
      infowindow.open(map, marker);

      map.setCenter(coords);
    }
  });
</script>
