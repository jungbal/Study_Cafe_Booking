<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
<style>
body {
    margin: 0;
    padding: 0;
}

.cafe-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 24px;
    padding: 40px;
    max-width: 1200px;
    margin: 0 auto;
}

.cafe-item {
    background-color: #ffffff;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.06);
    transition: transform 0.25s ease, box-shadow 0.25s ease;
    cursor: pointer;
    width: 100%;
    max-width: 300px;
}

.cafe-item:hover {
    transform: translateY(-6px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
}

.cafe-item img {
    width: 100%;
    height: 180px;
    object-fit: cover;
}

.cafe-item p {
    margin: 10px 16px;
    font-size: 15px;
    color: #333;
}

.cafe-item p strong {
    font-size: 16px;
    color: #222;
}

</style>
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<img src="/resources/cssImage/banner.jpg" alt="배너이미지" class="main-banner">
	 
<br><br><br>
<hr>

<%-- 인기많은 카페 top6 (리뷰/Q&A 달린 기준) --%> <!-- 클릭하면 카페 detail.jsp 로 이동 -->

<div class="cafe-list">
    <c:forEach var="cafe" items="${cafeList}">
        <a class="cafe-link" href="/cafeDetail?cafeNo=${cafe.cafeNo}">
            <div class="cafe-item">
                <img src="${cafe.cafeImagePath}" alt="${cafe.cafeName}">
                <p><strong>${cafe.cafeName}</strong></p>
                <p class="cafe-addr">${cafe.cafeAddr}</p>
            </div>
        </a>
    </c:forEach>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
</body>
</html>
