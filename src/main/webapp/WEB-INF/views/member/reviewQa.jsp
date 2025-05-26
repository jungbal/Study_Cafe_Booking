<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.or.iei.member.model.vo.Member" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 탭 버튼 -->
<div class="flex space-x-4 my-4 px-4">
  <div class="sub-tab-btn active cursor-pointer px-4 py-2 text-black" onclick="switchReviewQa(event, 'review')">내 리뷰</div>
  <div class="sub-tab-btn cursor-pointer px-4 py-2 text-gray-500" onclick="switchReviewQa(event, 'qna')">내 Q&A</div>
</div>

<!-- 리뷰 테이블 -->
<div class="overflow-x-auto px-4">
  <table id="reviewTable" class="w-full table-auto border border-gray-300 tab-visible">
    <thead class="bg-gray-100">
      <tr>
        <th class="p-3 text-left">카페명</th>
        <th class="p-3 text-left">내용</th>
        <th class="p-3 text-left">작성일</th>
        <th class="p-3 text-left whitespace-nowrap w-32">관리</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="c" items="${reviewList}">
        <c:if test="${empty c.commentParent}">
          <tr class="border-b bg-white">
            <td class="p-3 text-black">${c.cafeName}</td>
            <td class="p-3 text-black">${c.content}</td>
            <td class="p-3 text-black">${c.commentTime}</td>
            <td class="p-3 whitespace-nowrap space-x-2">
              <button class="update-btn text-blue-600 hover:underline" data-id="${c.commentId}" data-type="review">수정</button>
              <span class="text-gray-400">|</span>
              <button type="button" class="delete-btn text-red-600 hover:underline" data-id="${c.commentId}">삭제</button>
            </td>
          </tr>
          <c:forEach var="reply" items="${reviewList}">
            <c:if test="${reply.commentParent == c.commentId}">
              <tr class="bg-gray-50">
                <td colspan="4" class="p-3">
                  ↳ <strong>답글:</strong> ${reply.content}
                  <span class="float-right text-black text-sm">${reply.commentTime}</span>
                </td>
              </tr>
            </c:if>
          </c:forEach>
        </c:if>
      </c:forEach>
      
      <!-- 부모 없이 단독 존재하는 답글 (추가) -->
      <c:forEach var="c" items="${reviewList}">
        <c:if test="${not empty c.commentParent}">
          <c:set var="hasParent" value="false" />
          <c:forEach var="parent" items="${reviewList}">
            <c:if test="${parent.commentId == c.commentParent}">
              <c:set var="hasParent" value="true" />
            </c:if>
          </c:forEach>
          <c:if test="${!hasParent}">
            <tr class="bg-gray-50 border-b">
              <td class="p-3 text-black">${c.cafeName}</td>
              <td class="p-3 text-black">↳ ${c.content}</td>
              <td class="p-3 text-black">${c.commentTime}</td>
              <td class="p-3 whitespace-nowrap space-x-2">
                <button class="update-btn text-blue-600 hover:underline" data-id="${c.commentId}" data-type="review">수정</button>
                <span class="text-gray-400">|</span>
                <button type="button" class="delete-btn text-red-600 hover:underline" data-id="${c.commentId}">삭제</button>
              </td>
            </tr>
          </c:if>
        </c:if>
      </c:forEach>
    </tbody>
  </table>
</div>

<!-- Q&A 테이블 -->
<div class="overflow-x-auto px-4">
  <table id="qnaTable" class="w-full table-auto border border-gray-300 hidden">
    <thead class="bg-gray-100">
      <tr>
        <th class="p-3 text-left">카페명</th>
        <th class="p-3 text-left">내용</th>
        <th class="p-3 text-left">작성일</th>
        <th class="p-3 text-left whitespace-nowrap w-32">관리</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="c" items="${qnaList}">
        <c:if test="${empty c.commentParent}">
          <tr class="border-b bg-white">
            <td class="p-3 text-black">${c.cafeName}</td>
            <td class="p-3 text-black">${c.content}</td>
            <td class="p-3 text-black">${c.commentTime}</td>
            <td class="p-3 whitespace-nowrap space-x-2">
              <button class="update-btn text-blue-600 hover:underline" data-id="${c.commentId}" data-type="qna">수정</button>
              <span class="text-gray-400">|</span>
              <button type="button" class="delete-btn text-red-600 hover:underline" data-id="${c.commentId}">삭제</button>
            </td>
          </tr>
          <c:forEach var="reply" items="${qnaList}">
            <c:if test="${reply.commentParent == c.commentId}">
              <tr class="bg-gray-50">
                <td colspan="4" class="p-3">
                  ↳ <strong>답글:</strong> ${reply.content}
                  <span class="float-right text-black text-sm">${reply.commentTime}</span>
                </td>
              </tr>
            </c:if>
          </c:forEach>
        </c:if>
      </c:forEach>
      
      <!-- 부모 없이 단독 존재하는 답글 (추가) -->
      <c:forEach var="c" items="${qnaList}">
        <c:if test="${not empty c.commentParent}">
          <c:set var="hasParent" value="false" />
          <c:forEach var="parent" items="${qnaList}">
            <c:if test="${parent.commentId == c.commentParent}">
              <c:set var="hasParent" value="true" />
            </c:if>
          </c:forEach>
          <c:if test="${!hasParent}">
            <tr class="bg-gray-50 border-b">
              <td class="p-3 text-black">${c.cafeName}</td>
              <td class="p-3 text-black">↳ ${c.content}</td>
              <td class="p-3 text-black">${c.commentTime}</td>
              <td class="p-3 whitespace-nowrap space-x-2">
                <button class="update-btn text-blue-600 hover:underline" data-id="${c.commentId}" data-type="qna">수정</button>
                <span class="text-gray-400">|</span>
                <button type="button" class="delete-btn text-red-600 hover:underline" data-id="${c.commentId}">삭제</button>
              </td>
            </tr>
          </c:if>
        </c:if>
      </c:forEach>
    </tbody>
  </table>
</div>

<script>
  function switchReviewQa(event, type) {
    document.querySelectorAll(".sub-tab-btn").forEach(btn => {
      btn.classList.remove("active", "text-black");
      btn.classList.add("text-gray-500");
    });
    event.target.classList.add("active", "text-black");
    event.target.classList.remove("text-gray-500");

    document.getElementById("reviewTable").classList.add("hidden");
    document.getElementById("qnaTable").classList.add("hidden");

    if (type === 'review') {
      document.getElementById("reviewTable").classList.remove("hidden");
    } else {
      document.getElementById("qnaTable").classList.remove("hidden");
    }
  }

  $(document).ready(function () {
    $(".update-btn").on("click", function (e) {
      e.preventDefault();
      const commentId = $(this).data("id");
      const type = $(this).data("type");
      const url = "/comment/handle?action=update&commentId=" + encodeURIComponent(commentId) + "&type=" + encodeURIComponent(type);
      window.open(url, "commentUpdatePopup", "width=600,height=400,left=300,top=200");
    });

    $(".delete-btn").on("click", function () {
      const commentId = $(this).data("id");

      Swal.fire({
        title: "정말 삭제하시겠습니까?",
        text: "삭제 후에는 복구할 수 없습니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "삭제",
        cancelButtonText: "취소",
        confirmButtonColor: "#e74c3c",
        cancelButtonColor: "#95a5a6"
      }).then((result) => {
        if (result.isConfirmed) {
          location.href = "/comment/handle?action=delete&commentId=" + encodeURIComponent(commentId);
        }
      });
    });
  });
</script>
