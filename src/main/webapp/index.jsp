<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <% // 메인 페이지 접속 시, 카페리스트 정보 가져오기 위해 설정
    RequestDispatcher rd = request.getRequestDispatcher("/main");
    rd.forward(request, response);
%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
<style>
    .cafe-list {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 20px;
        justify-items: center;
        padding: 20px;
    }

    .cafe-item {
        width: 200px;
        border: 1px solid #ddd;
        border-radius: 10px;
        padding: 15px;
        box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1);
        text-align: center;
        background-color: #fff;
        transition: transform 0.2s, box-shadow 0.2s;
    }

    .cafe-item:hover {
        transform: translateY(-5px);
        box-shadow: 4px 4px 12px rgba(0, 0, 0, 0.2);
    }

    .cafe-item img {
        max-width: 100%;
        height: auto;
        border-radius: 8px;
    }

    .cafe-item p {
        margin: 10px 0 0;
        font-size: 14px;
    }
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<!-- 검색 폼 -->
<form action="/search" method="get">
    <input type="text" name="srchStr" placeholder="검색어를 입력해주세요.">
    <button type="submit">검색</button>
</form>

<br><br><br>
<hr>

		

	<%-- 인기많은 카페 top6 (리뷰/Q&A 달린 기준) --%>
<div class="cafe-list">
    <c:forEach var="cafe" items="${cafeList}">
        <div class="cafe-item">
            <img src="${cafe.cafeImagePath}" alt="${cafe.cafeName}">
            <p><strong>${cafe.cafeName}</strong></p>
            <p>${cafe.cafeAddr}</p>
        </div>
    </c:forEach>
    
    
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
</body>
</html>
