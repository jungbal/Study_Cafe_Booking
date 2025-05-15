<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="kr.or.iei.comment.model.vo.Comment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>리뷰 관리</title>
    <script src="${pageContext.request.contextPath}/resources/js/sweetalert.min.js"></script>
    <style>
        .tab { display: flex; justify-content: center; margin-bottom: 20px; }
        .tab button {
            padding: 10px 20px;
            margin: 0 5px;
            cursor: pointer;
        }
        .comment-box {
            width: 70%;
            margin: 0 auto 20px auto;
            padding: 15px;
            border: 1px solid #ccc;
            border-radius: 8px;
        }
        textarea {
            width: 100%;
            height: 80px;
            margin: 10px 0;
            resize: vertical;
        }
        .btn-group {
            text-align: right;
        }
        .btn-group button {
            margin-left: 10px;
        }
    </style>
</head>
<body>

    <div class="tab">
        <button onclick="location.href='/myPage/reply?type=RV'">리뷰 관리</button>
        <button onclick="location.href='/myPage/reply?type=QNA'">Q&A 관리</button>
    </div>

    <h2 style="text-align:center;">내가 작성한 리뷰 목록</h2>

    <c:forEach var="c" items="${commentList}" varStatus="vs">
        <div class="comment-box">
            <form id="commentForm${vs.index}" action="/comment/handle" method="post">
                <input type="hidden" name="commentId" value="${c.commentId}">
                <label>카페명: ${c.cafeName}</label>
                <textarea name="content">${c.content}</textarea>
                <div class="btn-group">
                    <button type="submit" name="action" value="update">수정</button>
                    <button type="button" onclick="confirmDelete(${vs.index})">삭제</button>
                </div>
            </form>
        </div>
    </c:forEach>
    <script>
        function confirmDelete(index) {
            swal({
                title: "정말 삭제하시겠습니까?",
                text: "삭제된 내용은 복구할 수 없습니다.",
                icon: "warning",
                buttons: ["취소", "삭제"],
                dangerMode: true,
            }).then(function(willDelete) {
                if (willDelete) {
                    var form = document.getElementById("commentForm" + index);
                    var input = document.createElement("input");
                    input.type = "hidden";
                    input.name = "action";
                    input.value = "delete";
                    form.appendChild(input);
                    form.submit();
                }
            });
        }
    </script>
    

</body>
</html>
