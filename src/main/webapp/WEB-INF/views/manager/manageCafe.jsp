<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업체 리스트</title>
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body class="bg-gray-50 text-gray-800">

<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

<div class="max-w-7xl mx-auto px-4 py-8">
    <div class="text-3xl font-bold mb-6">업체 목록</div>

    <form action="/updateCafe" method="post">
        <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-300 border border-gray-300 rounded-lg overflow-hidden shadow-sm">
                <thead class="bg-gray-100">
                    <tr>
                        <th class="px-4 py-2 text-left">업체명</th>
                        <th class="px-4 py-2 text-left">호스트ID</th>
                        <th class="px-4 py-2 text-left">주소</th>
                        <th class="px-4 py-2 text-left">상태</th>
                        <th class="px-4 py-2 text-left">상태변경</th>
                        <th class="px-4 py-2 text-center">신청정보</th>
                    </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                    <c:forEach var="cafe" items="${cafeList}">
                        <tr class="hover:bg-gray-50">
                            <td class="px-4 py-2">${cafe.cafeName}</td>
                            <td class="px-4 py-2">${cafe.hostId}</td>
                            <td class="px-4 py-2">${cafe.cafeAddr}</td>
                            <td class="px-4 py-2">${cafe.cafeManageStatus}</td>
                            <td class="px-4 py-2">
                                <c:choose>
                                    <c:when test="${cafe.cafeManageStatus == '수정대기'}">
                                        <select name="${cafe.cafeNo}" class="border border-gray-300 rounded px-2 py-1 w-full">
                                            <option value="0">상태변경</option>
                                            <option value="1">승인</option>
                                            <option value="3">반려</option>
                                        </select>
                                    </c:when>
                                    <c:when test="${cafe.cafeManageStatus == '등록대기'}">
                                        <select name="${cafe.cafeNo}" class="border border-gray-300 rounded px-2 py-1 w-full">
                                            <option value="0">상태변경</option>
                                            <option value="2">승인</option>
                                            <option value="3">반려</option>
                                        </select>
                                    </c:when>
                                    <c:when test="${cafe.cafeManageStatus == '승인'}">
                                        <select name="${cafe.cafeNo}" class="border border-gray-300 rounded px-2 py-1 w-full">
                                            <option value="0">상태변경</option>
                                            <option value="4">삭제</option>
                                        </select>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td class="px-4 py-2 text-center">
                                <c:choose>
                                    <c:when test="${cafe.cafeManageStatus == '수정대기' || cafe.cafeManageStatus == '등록대기'}">
                                        <button type="button" onclick="requestInfo('${cafe.cafeNo}')" class="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700 transition">
                                            신청정보 열람
                                        </button>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <input type="hidden" name="selectedStatusJson" id="selectedStatusJson">

        <div class="mt-6 text-right">
            <button type="submit" onclick="return submitCafeForm();" class="bg-green-600 text-white px-5 py-2 rounded hover:bg-green-700 transition">
                저장
            </button>
        </div>
    </form>

    <!-- 페이지네이션 -->
    <div id="pageNavi" class="mt-8 text-center">
        ${pageNavi}
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

<script>
function submitCafeForm() {
    const statusMap = {};
    document.querySelectorAll("select").forEach(select => {
        const cafeNo = select.name;
        const selectedValue = select.value;
        if (selectedValue && selectedValue !== '0') {
            statusMap[cafeNo] = selectedValue;
        }
    });
    document.getElementById("selectedStatusJson").value = JSON.stringify(statusMap);
    return true;
}
function requestInfo(cafeNo) {
    const url = "/admin/cafeRequestDetail?cafeNo=" + cafeNo;
    window.open(url, "신청정보", "width=600,height=500,left=100,top=100");
}
</script>

</body>
</html>
