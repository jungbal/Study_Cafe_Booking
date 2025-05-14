<%@page import="kr.or.iei.member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/sweetalert.min.js"></script>

<style>
	.mypage-container {
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.mypage-wrap {
		width: 800px;
	}
	.my-info-wrap {
		width: 80%;
		margin: 0 auto;
	}
	.mypage-btn {
		margin : 20px 0px;
		text-align: center;
	}
	.mypage-btn>button {
		width: 25%;
		margin : 10px 10px;
	}
</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
		<main class="content mypage-container">
			<section class="section mypage-wrap">
				<div class="page-title">마이페이지</div>
				<div class="my-info-wrap">
					<form id="updateForm" action="/myInfo/update" method="post">
						<table class="tbl">
							<tr>
								<th>아이디</th>
								<td>${loginMember.userId}
									<input type="hidden" name="userId" value="${loginMember.userId}">
								</td>
							</tr>
							<tr>
								<th>비밀번호</th>
								<td>
									<button type="button" class="btn-primary sm" onclick="chgPassword()">비밀번호 변경</button>
								</td>
							</tr>
							<tr>
								<th>전화번호</th>
								<td>
									<input type="text" id="userPhone" name="userPhone" value="${loginMember.userPhone}" maxlength="13" placeholder="010-0000-0000">
								</td>
							</tr>
							<tr>
								<th>프로필 이미지</th>
								<td colspan="2">
									<c:choose>
										<c:when test="${not empty loginMember.userImage}">
											<img src="${pageContext.request.contextPath}${loginMember.userImage}" 
												width="100" height="100" 
												alt="회원 프로필">
										</c:when>
										<c:otherwise>
											<img src="data:image/svg+xml;utf8,
												<svg xmlns='http://www.w3.org/2000/svg' width='100' height='100'>
													<rect width='100' height='100' fill='%23eee'/>
													<text x='50%' y='50%' dominant-baseline='middle' text-anchor='middle' font-size='12'>No Image</text>
												</svg>" 
												width="100" height="100" alt="기본 프로필">
										</c:otherwise>
									</c:choose>
									<br>
									<button type="button" class="btn-primary sm" onclick="document.getElementById('profileImgInput').click()">
										이미지 변경
									</button>
								</td>
							</tr>
							<tr>
								<th>회원 상태</th>
								<td>${loginMember.userStatus}</td>
							</tr>
							<tr>
								<th>회원 등급</th>
								<td>
									<c:choose>
										<c:when test="${loginMember.userRole == 1}">관리자</c:when>
										<c:when test="${loginMember.userRole == 2}">호스트</c:when>
										<c:otherwise>일반회원</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</table>
						<div class="mypage-btn">
							<button type="button" onclick="updateValidate()" class="btn-primary lg">정보수정</button>
							<c:if test="${loginMember.userRole ne 1}">
								<button type="button" onclick="deleteMember()" class="btn-secondary lg">회원탈퇴</button>
							</c:if>
							<c:if test="${loginMember.userRole eq 1}">
								<button type="button" class="btn-point lg" onclick="moveAdminPage()">관리자 페이지</button>
							</c:if>
						</div>
					</form>

					<!-- 🔥 프로필 이미지 업로드용 숨겨진 form -->
					<form id="profileImgForm" action="/member/updateImg" method="post" enctype="multipart/form-data" style="display:none;">
						<input type="file" name="profileImg" id="profileImgInput" accept="image/*">
					</form>
				</div>
			</section>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	</div>

<script>
	function updateValidate(){
		const phoneRegExp = /^010-\d{3,4}-\d{4}$/;
		const userPhone = $('#userPhone');

		if (!phoneRegExp.test($(userPhone).val())) {
			swal({
				title: "알림",
				text: "전화번호는 - 포함 13자리로 입력해주세요.",
				icon: "warning"
			});
			return;
		}

		swal({
			title: "수정",
			text: "회원 정보를 수정하시겠습니까?",
			icon: "warning",
			buttons: { cancel: "취소", confirm: "수정" }
		}).then(function(val){
			if(val){
				$('#updateForm').submit();
			}
		});
	}

	function deleteMember(){
		swal({
			title: "탈퇴",
			text: "정말 회원탈퇴를 하시겠습니까?",
			icon: "warning",
			buttons: { cancel: "취소", confirm: "탈퇴" }
		}).then(function(val){
			if(val){
				location.href = '/member/delete?userId=' + $('input[name=userId]').val();
			}
		});
	}

	function chgPassword(){
		let width = 500, height = 450;
		let top = screen.availHeight / 2 - height / 2;
		let left = screen.availWidth / 2 - width / 2;
		let attr = `width=${width}, height=${height}, top=${top}, left=${left}`;
		window.open("/member/pwChgFrm", "chgPw", attr);
	}

	window.addEventListener('DOMContentLoaded', function () {
	    const input = document.getElementById('profileImgInput');
	    if (input) {
	        input.addEventListener('change', function () {
	            if (this.files.length > 0) {
	                swal({
	                    title: "프로필 이미지 변경",
	                    text: "선택한 이미지로 프로필을 변경하시겠습니까?",
	                    icon: "info",
	                    buttons: ["취소", "변경"]
	                }).then((confirm) => {
	                    if (confirm) {
	                        console.log("🔥 submit 실행");
	                        document.getElementById('profileImgForm').submit();
	                    }
	                });
	            }
	        });
	    } else {
	        console.log("❌ profileImgInput 요소를 찾을 수 없습니다.");
	    }
	});

	<% if (((Member)session.getAttribute("loginMember")).getUserRole() == 1) { %>
	function moveAdminPage(){
		location.href = '/member/admin';
	}
	<% } %>
</script>
</body>
</html>
