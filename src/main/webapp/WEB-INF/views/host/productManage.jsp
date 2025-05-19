<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이용권 관리</title>
<style>
    .delete-btn {
        cursor: pointer;
        color: red;
        font-weight: bold;
        margin-left: 10px;
    }
</style>
</head>
<body>

<h2>이용권 관리</h2>

<c:choose>
    <c:when test="${empty ticketList}">
        <p>아직 등록된 이용권이 없습니다.</p>
    </c:when>
    <c:otherwise>
        <form action="/productManage/modiProduct" method="post">
            <table>
                <tr>
                    <th>이용권 종류</th>
                    <th>이용권 가격</th>
                    <th>삭제</th>
                </tr>
                <c:forEach var="ticket" items="${ticketList}">
                    <tr>
                        <td>
                            <input type="hidden" name="ticketId" value="${ticket.ticketId}" />
                            <input type="text" name="ticketHour" value="${ticket.ticketHour}" /> 시간권
                        </td>
                        <td>
                            <input type="hidden" name="ticketType" value="${ticket.ticketType}" />
                            <input type="number" name="ticketPrice" value="${ticket.ticketPrice}" />
                        </td>
                        <td>
                            <span class="delete-btn" onclick="submitModified()">-</span>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <button type="button" onclick="submitModified()">수정</button>
        </form>

        <!-- 삭제용 hidden form -->
		<form id="deleteForm" action="/productManage/deleteProduct" method="post" style="display: none;">
		    <input type="hidden" name="ticketId" id="deleteTicketId" />
		    <input type="hidden" name="ticketHour" id="deleteTicketHour" />
		    <input type="hidden" name="ticketPrice" id="deleteTicketPrice" />
		    <input type="hidden" name="ticketType" id="deleteTicketType" />
		</form>

    </c:otherwise>
</c:choose>

<hr/>

<h3>이용권 추가</h3>
<form action="/productManage/insertProduct" method="post" id="insertForm">
    <div id="addTicketContainer">
        <input type="text" name="ticketHour" placeholder="이용권 시간"/> 시간권
        <input type="number" name="ticketPrice" placeholder="이용권 가격"/>
    </div>
    <button type="submit">추가</button>
</form>

<script>
function removeRow(span, ticketId) {
    const row = span.closest("tr");

    // 값 추출
    const ticketHour = row.querySelector("input[name='ticketHour']").value;
    const ticketPrice = row.querySelector("input[name='ticketPrice']").value;
    const ticketType = row.querySelector("input[name='ticketType']").value;

    // 삭제 확인
    if (confirm("정말 삭제하시겠습니까?")) {
        // hidden form에 값 설정
        document.getElementById("deleteTicketId").value = ticketId;
        document.getElementById("deleteTicketHour").value = ticketHour;
        document.getElementById("deleteTicketPrice").value = ticketPrice;
        document.getElementById("deleteTicketType").value = ticketType;

        // submit
        document.getElementById("deleteForm").submit();

        // 화면에서 행 제거 (원하면 confirm 이후 제거)
        row.remove();
    }
}

function submitModified() {
    const rows = document.querySelectorAll("table tr");
    const tickets = [];

    rows.forEach((row, index) => {
        if (index === 0) return; // skip header row

        const ticketId = row.querySelector("input[name='ticketId']").value;
        const ticketType = row.querySelector("input[name='ticketType']").value;
        const ticketHour = row.querySelector("input[name='ticketHour']").value;
        const ticketPrice = row.querySelector("input[name='ticketPrice']").value;

        tickets.push({
            ticketId,
            ticketType,
            ticketHour,
            ticketPrice
        });
    });

    fetch('/productManage/modiProduct', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ tickets })
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
        if(data.success) {
            location.reload();
        }
    })
    .catch(error => {
        alert("오류가 발생했습니다.");
        console.error(error);
    });
}
</script>


</body>
</html>
