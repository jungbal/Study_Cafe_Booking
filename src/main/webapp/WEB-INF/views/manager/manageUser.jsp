<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이용자 목록</title>


<style>
	
</style>
</head>
<body class="bg-gray-100">
	<div class="wrap max-w-6xl mx-auto p-6 bg-white shadow-md mt-8 rounded-md">
		<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
		<main class="content">
			<section class="section notice-List-wrap">
				<h1 class="text-2xl font-bold mb-6">이용자 목록</h1>
				<div class="list-content overflow-x-auto">
					<table class="min-w-full border border-gray-300 divide-y divide-gray-200">
						<thead class="bg-gray-50">
							<tr>
								<th class="w-1/4 px-4 py-2 text-left text-sm font-medium text-gray-700">이용자ID</th>
								<th class="w-1/4 px-4 py-2 text-left text-sm font-medium text-gray-700">권한</th>
								<th class="w-1/4 px-4 py-2 text-left text-sm font-medium text-gray-700">휴대폰번호</th>
								<th class="w-1/4 px-4 py-2 text-left text-sm font-medium text-gray-700">회원삭제</th>
							</tr>
						</thead>
						<tbody class="bg-white divide-y divide-gray-200">
							<c:forEach var="user" items="${userList}">
							<tr>
								<td class="px-4 py-2">${user.userId}</td>
								<td class="px-4 py-2">${user.userRole}</td>
								<td class="px-4 py-2">${user.userPhone}</td>
								<td class="px-4 py-2">
									<c:if test="${user.userRole == 3}">
										<form action="/user/deleteUser" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
											<input type="hidden" name="userId" value="${user.userId}" />
											<button type="submit" class="bg-red-600 hover:bg-red-700 text-white text-sm px-3 py-1 rounded">삭제</button>
										</form>
									</c:if>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div id="pageNavi" class="mt-8">
					${pageNavi}
				</div>
			</section>
		</main>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
</body>
</html>
