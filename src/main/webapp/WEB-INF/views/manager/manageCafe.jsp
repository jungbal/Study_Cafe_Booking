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
                   <!-- 디버깅 -->
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
                    <!-- 디버깅 --><script>console.log("cafeNo: '${cafe.cafeNo}'");</script>
                        <tr class="hover:bg-gray-50">
                            <td class="px-4 py-2">${cafe.cafeName}</td>
                            <td class="px-4 py-2">${cafe.hostId}</td>
                            <td class="px-4 py-2">${cafe.cafeAddr}</td>
                            <td class="px-4 py-2">${cafe.cafeManageStatus}</td>
                            <td class="px-4 py-2">
                                <c:choose>
                                    <c:when test="${cafe.cafeManageStatus == '수정대기'}">
                                    <!--"handleStatusChange('${cafe.cafeNo}')" 에서 값이 null로 넘어감 -->
                                        <select id="statusSelect_${cafe.cafeNo}" name="${cafe.cafeNo}" onchange="handleStatusChange('${cafe.cafeNo}')"  
                                        	class="border border-gray-300 rounded px-2 py-1 w-full"> 
                                            <option value="0">상태변경</option>
                                            <option value="1">승인</option>
                                            <option value="3">반려</option> <!-- select.value 값 => 3 반려를 눌렀을 때만 반려 코드 selectbox가 생성되므로  -->
                                        </select>
                                    </c:when>
                                    <c:when test="${cafe.cafeManageStatus == '등록대기'}">
                                        <select id="statusSelect_${cafe.cafeNo}" name="${cafe.cafeNo}" onchange="handleStatusChange('${cafe.cafeNo}')"
                                        	class="border border-gray-300 rounded px-2 py-1 w-full">
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
                                
                                <select id="rejectReason_${cafe.cafeNo}" name="rejectReason_${cafe.cafeNo}" class="rejectReason mt-2 border border-gray-300 rounded px-2 py-1 w-full" style="display: none">
								    <option value="">반려 사유 선택</option>
								    <c:forEach var="codeList" items="${codeList}">
								        <option value="3_${codeList.codeId}">${codeList.codeName}</option>
								    </c:forEach>
								</select>
                                
                            </td>
                            <td class="px-4 py-2 text-center">
                                <c:choose>
                                    <c:when test="${cafe.cafeManageStatus == '수정대기' || cafe.cafeManageStatus == '등록대기'}">	<%-- cafeManageStatus => 카페에 대한 상태 => 그 값이 수정대기 && 등록대기일 경우에만 보여줌--%>
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
// 반려 클릭 시, 반려 selectBox 동적으로 추가하는 스크립트
function handleStatusChange(cafeNo) {
    const statusSelect = document.querySelector(`select[name='${cafeNo}']`);
    const reasonSelect = document.getElementById(`rejectReason_${cafeNo}`);

    if (!statusSelect || !reasonSelect) {
        console.warn(`Elements not found for cafeNo=${cafeNo}`);
        return;
    }

    if (statusSelect.value === "3") {
        reasonSelect.style.display = "block"; // 반려 사유 보이기
    } else {
        reasonSelect.style.display = "none";  // 숨기기
        reasonSelect.value = ""; // 선택 초기화
    }
}

function submitCafeForm() {
    const statusMap = {};
    document.querySelectorAll("select").forEach(select => {
    	// rejectReason_로 시작하는 select는 제외
        if (select.name.startsWith("rejectReason_")) {
            return;
        }
    	
        const cafeNo = select.name;
        const selectedValue = select.value; // 3번 (반려)를 클릭했을 경우, selectedValue 값을 
        if (selectedValue && selectedValue !== '0') {
            statusMap[cafeNo] = selectedValue;
        }
    });
    document.getElementById("selectedStatusJson").value = JSON.stringify(statusMap);
    return true;
}
function requestInfo(cafeNo) {		//cafeNo는 파라미터 
    const url = "/admin/cafeRequestDetail?cafeNo=" + cafeNo;
    window.open(url, "신청정보", "width=600,height=500,left=100,top=100");
}
</script>

</body>
</html>