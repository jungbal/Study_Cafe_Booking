<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업체 리스트</title>
<style>
   #pageNavi{
      margin-top : 30px;
   }
</style>
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
   <div class='wrap'>
   <jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
   <main class='content'>
      <section class='section notice-list-wrap'>
         <div class='page-title text-2xl font-bold mb-4'>업체 목록</div>
         <div class='list-content'>
         <form action="/updateCafe" method='post'>
            <table class='tbl tbl-hover w-full text-left border-collapse border border-gray-300'>
               <thead>
               <tr class="bg-gray-100">
                  <th class="border border-gray-300 px-4 py-2 w-1/6">업체명</th>
                  <th class="border border-gray-300 px-4 py-2 w-1/6">호스트ID</th>
                  <th class="border border-gray-300 px-4 py-2 w-1/6">주소</th>
                  <th class="border border-gray-300 px-4 py-2 w-1/6">상태</th>
                  <th class="border border-gray-300 px-4 py-2 w-1/6">상태변경</th>
                  <th class="border border-gray-300 px-4 py-2 w-1/6">신청정보</th>
               </tr>
               </thead>
               <tbody>
               <c:forEach var="cafe" items="${cafeList}">
                  <tr class="hover:bg-gray-50">
                     <td class="border border-gray-300 px-4 py-2">${cafe.cafeName}</td>
                     <td class="border border-gray-300 px-4 py-2">${cafe.hostId}</td>
                     <td class="border border-gray-300 px-4 py-2">${cafe.cafeAddr}</td>
                     <td class="border border-gray-300 px-4 py-2">${cafe.cafeManageStatus}</td>
                     <td class="border border-gray-300 px-4 py-2">
                        <c:choose>
                           <c:when test="${cafe.cafeManageStatus == '수정대기' }">
                              <select name="${cafe.cafeNo}" class="border border-gray-300 rounded px-2 py-1">
                                 <option value="0">상태변경</option>
                                 <option value="1">승인</option>
                                 <option value="3">반려</option>
                              </select>
                           </c:when>
                           <c:when test="${cafe.cafeManageStatus == '등록대기'}">
                              <select name="${cafe.cafeNo}" class="border border-gray-300 rounded px-2 py-1">
                                 <option value="0">상태변경</option>
                                 <option value="2">승인</option>
                                 <option value="3">반려</option>
                              </select>
                           </c:when>
                           <c:when test="${cafe.cafeManageStatus == '승인'}">
                              <select name="${cafe.cafeNo}" class="border border-gray-300 rounded px-2 py-1">
                                 <option value="0">상태변경</option>
                                 <option value="4">삭제</option>
                              </select>
                           </c:when>
                        </c:choose>
                     </td>
                     <td class="border border-gray-300 px-4 py-2 text-center">
                     	<c:choose>
                     		<c:when test="${cafe.cafeManageStatus == '수정대기' || cafe.cafeManageStatus == '등록대기'}">
                     			<button type="button" onclick="requestInfo('${cafe.cafeNo}')" class="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700">신청정보 열람</button>
                     		</c:when>
                     	</c:choose>      
                     </td>
                  </tr>
               </c:forEach>
               </tbody>
            </table>
            <!-- 숨겨진 input에 JS로 값 삽입 -->
             <input type="hidden" name="selectedStatusJson" id="selectedStatusJson">
            <button type="submit" onclick="return submitCafeForm();" class="mt-4 bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">저장</button>
         </form>
         </div>

         <!-- Tailwind 스타일 페이지네이션 영역 -->
         <div id='pageNavi' class="mt-8">
            ${pageNavi}
         </div>
         
      </section>
   </main>
   <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
   </div>
<script>
function submitCafeForm() {
    const statusMap = {};
    // 선택된 select 요소들에 맞게 이름 변경 필요할 수 있음
    document.querySelectorAll("select").forEach(select => {
        const cafeNo = select.name;
        const selectedValue = select.value;
        if (selectedValue && selectedValue !== '0') {
            statusMap[cafeNo] = selectedValue;
        }
    });

    const jsonStr = JSON.stringify(statusMap);
    document.getElementById("selectedStatusJson").value = jsonStr;
    return true; // form 제출 계속
}
function requestInfo(cafeNo){
	const url = "/admin/cafeRequestDetail?cafeNo=" + cafeNo;
	window.open(url, "신청정보", "width=600,height=500,left=100,top=100");
}
</script>

</body>
</html>
