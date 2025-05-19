<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>검색 결과</title>
<style>
    .list-container {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
        padding: 20px;
    }

    .cafe-link {
        width: calc(33.333% - 20px);
        text-decoration: none;
        color: inherit;
    }

    .cafe-card {
        border: 1px solid #ccc;
        border-radius: 10px;
        padding: 15px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        box-sizing: border-box;
        text-align: center;
        transition: all 0.2s ease-in-out;
        height: 100%;
    }

    .cafe-card:hover {
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        transform: translateY(-3px);
        cursor: pointer;
    }

    .cafe-image {
        margin-bottom: 10px;
        font-weight: bold;
        font-size: 16px;
        color: #333;
    }

    .cafe-name {
        font-size: 18px;
        margin-bottom: 5px;
    }

    .cafe-addr {
        font-size: 14px;
        color: #666;
    }

    .no-result {
        text-align: center;
        padding: 50px 0;
        font-size: 18px;
        color: #888;
        width: 100%;
    }
</style>
</head>
<body>

<div class="list-container">
    <c:choose>
        <c:when test="${empty list}">
            <div class="no-result">검색 결과가 없습니다.</div>
        </c:when>
        <c:otherwise>
            <c:forEach var="list" items="${list}">
                <a class="cafe-link" href='/cafeDetail?cafeNo=${list.cafeNo}'>
                    <div class="cafe-card">
                        <div class="cafe-image">카페이미지(예시)</div>
                        <div class="cafe-name">${list.cafeName}</div>
                        <div class="cafe-addr">${list.cafeAddr}</div>
                    </div>
                </a>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
