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
</head>
<body>
   <div class = 'wrap'>
   <jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
   <main class = 'content'>
      <section class = 'section notice-list-wrap'>
         <div class = 'page-title'>업체 목록</div>
         <div class = 'list-content'>
         <form action="/updateCafe" method = 'post'>
            <table class = 'tbl tbl-hover'>
               <tr>
                  <th style = "width:10%;">업체명</th>
                  <th style = "width:10%;">호스트ID</th>
                  <th style = "width:10%;">주소</th>
                  <th style = "width:10%;">상태</th>
                  <th style = "width:10%;">상태변경</th>
                  <th style = "width:10%;">신청정보</th>
               </tr>
               <c:forEach var = "cafe" items = "${cafeList}">
                  <tr>
                     <th>${cafe.cafeName}</th>
                     <th>${cafe.hostId}</th>
                     <th>${cafe.cafeAddr}</th>
                     <th>${cafe.cafeManageStatus}</th>
                     <%-- <th>
                        <c:choose>
                           <c:when test="${cafe.userStatus == 'N' && cafe.cafeStatus == 'N' && hostRequest.status == 'N'}">등록대기</c:when>
                           <c:when test="${user.userStatus == 'N' && cafe.cafeStatus == 'N' && hostRequest.status == 'Y'}">수정대기</c:when>
                           <c:when test="${user.userStatus == 'Y' && cafe.cafeStatus == 'Y' && hostRequest.status == 'Y'}">승인</c:when>
                        </c:choose>
                     </th>--%>
                     <th>
                        <c:choose>
                           <c:when test="${cafe.cafeManageStatus == '수정대기'}">
                              <select name="${cafe.cafeNo}">
                                 <option value="0">상태변경</option>
                                 <option value="1">승인</option>
                                 <option value="3">반려</option>
                              </select>
                           </c:when>
                           <c:when test="${cafe.cafeManageStatus == '등록대기'}">
                              <select name="${cafe.cafeNo}">
                                 <option value="0">상태변경</option>
                                 <option value="2">승인</option>
                                 <option value="3">반려</option>
                              </select>
                           </c:when>
                           <c:when test="${cafe.cafeManageStatus == '승인'}">
                              <select name="${cafe.cafeNo}">
                                 <option value="0">상태변경</option>
                                 <option value="4">삭제</option>
                              </select>
                           </c:when>
                           <%--<c:when test="${cafe.cafeManageStatus == '승인'}">
                              <form action="/deleteHost" method="post">
                                 <input type="hidden" name="cafeNo" value="${cafe.cafeNo}">
                                 <button type="button" onclick ="deleteBtn()">삭제</button>
                              </form>
                           </c:when>--%>
                        </c:choose> 
                     </th>
                  </tr>
               </c:forEach>
            </table>
            <!-- 숨겨진 input에 JS로 값 삽입 -->
             <input type="hidden" name="selectedStatusJson" id="selectedStatusJson">
            <button type="submit" onclick="return submitCafeForm();">저장</button>
            </form>
         </div>
         <div id ='pageNavi'>${pageNavi}</div>
         
      </section>
   </main>
   <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
   </div>
<script>
function submitCafeForm() {
    const statusMap = {};
    document.querySelectorAll("select[name='statusMap']").forEach(select => {
        const cafeNo = select.getAttribute("data-cafe-no");
        const selectedValue = select.value;
        if (selectedValue) {
            statusMap[cafeNo] = selectedValue;
        }
    });

    const jsonStr = JSON.stringify(statusMap);
    document.getElementById("selectedStatusJson").value = jsonStr;
    return true; // form 제출 계속
}
</script>

</body>
</html>