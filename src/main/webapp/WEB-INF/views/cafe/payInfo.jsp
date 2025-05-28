<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>결제 정보</title>
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@3.3.2/dist/tailwind.min.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
<!-- 포트원 결제 -->
    <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
    <!-- jQuery -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
    <!-- iamport.payment.js -->
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<!-- 포트원 결제 -->
</head>
<body class="font-sans m-0 p-0">
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<main class="max-w-7xl mx-auto px-4">
  <h1 class="text-center text-3xl font-semibold my-8">결제하기</h1>
  <div class="flex justify-between px-5">
    
    <!-- 공간 정보 + 결제 예정 금액 -->
    <div class="border border-gray-300 rounded-xl p-5 w-[48%] bg-white">
      <h2 class="text-2xl font-semibold mb-4">공간 정보</h2>
      <div class="mb-3">
        <strong>카페명:</strong> ${cafe.cafeName} <br />
        <em>${cafe.cafeIntroduce}</em>
      </div>
      <div class="mb-3">
        <strong>주소:</strong> ${cafe.cafeAddr}
      </div>
      <div class="mb-3">
        <strong>전화번호:</strong> ${cafe.cafePhone}
      </div>
      <div class="mb-3">
        <strong>운영시간:</strong> ${cafe.cafeStartHour} ~ ${cafe.cafeEndHour}
      </div>

      <hr class="my-5 border-gray-300" />

      <h2 class="text-2xl font-semibold mb-4">결제 예정 금액</h2>
      <div class="mb-3">
        <strong>예약 날짜:</strong>
        <span id="displayDateLeft"></span>
        <input type="hidden" id="hiddenDateLeft" name="reservationDate" />
      </div>
      <div class="mb-3">
        <strong>시간권:</strong> ${ticket.ticketType}
      </div>
      <div class="mb-3">
        <strong>좌석 번호:</strong> ${seatNo}
      </div>
      <div class="mb-3 text-xl font-bold">
        <strong>금액:</strong> <span class="text-2xl font-extrabold text-blue-600">₩ ${ticket.ticketPrice}</span>
      </div>
      <form action="/payInfo/payProcess" method="post">
        <input type="hidden" name="ticketPrice" value="${ticket.ticketPrice}" />
        <input type="hidden" name="ticketHour" value="${ticket.ticketHour}" />
        <input type="hidden" name="ticketId" value="${ticket.ticketId}" />
        <input type="hidden" name="cafeNo" value="${cafe.cafeNo}" />
        <input type="hidden" name="seatNo" value="${seatNo}" />
        <input type="hidden" name="userId" value="${loginCafe.loginId}" />
        
        <button type="submit" id="payment" class="bg-blue-600 hover:bg-blue-700 text-white py-2 px-5 rounded-md text-lg cursor-pointer transition-colors duration-300">
		  결제하기
		</button>
      </form>
    </div>

    <!-- 결제 상세 정보 -->
    <div class="border border-gray-300 rounded-xl p-5 w-[48%] bg-white">
      <h2 class="text-2xl font-semibold mb-4">결제 상세 정보</h2>
      <div class="mb-3">
        <strong>예약 날짜:</strong>
        <span id="displayDateRight"></span>
        <input type="hidden" id="hiddenDateRight" />
      </div>
      <div class="mb-3">
        <strong>시간권:</strong> ${ticket.ticketType}
      </div>
      <div class="mb-3">
        <strong>좌석 번호:</strong> ${seatNo}
      </div>
      <h2 class="text-xl font-semibold mt-6">선택한 좌석</h2>
      <div class="grid grid-cols-6 gap-2 mt-2 w-[320px]">
		  <c:forEach var="i" begin="1" end="30">
		    <div class="w-[50px] h-[50px] border border-gray-400 flex items-center justify-center font-bold
		      ${i == seatNo ? 'bg-blue-600 text-white' : 'bg-gray-200'} rounded">
		      ${i}
		    </div>
		  </c:forEach>
		</div>
    </div>

  </div>
</main>

<script>
const today = new Date();

const yyyy = today.getFullYear();
const mm = String(today.getMonth() + 1).padStart(2, '0');
const dd = String(today.getDate()).padStart(2, '0');

const formattedDate = yyyy+'-'+mm+'-'+dd;

document.getElementById('displayDateLeft').textContent = formattedDate;
document.getElementById('displayDateRight').textContent = formattedDate;
document.getElementById('hiddenDateLeft').value = formattedDate;
document.getElementById('hiddenDateRight').value = formattedDate;



//카카오페이
// 구매자 정보
const useremail = response.req_user_email // 이메일??
const username = response.req_username // 세션에서 유저명 가져오기
//const userPhone = // 세션에서 전화번호 가져오기

// 결제창 함수 넣어주기
const buyButton = document.getElementById('payment') // 결제하기 버튼의 id = payment
buyButton.setAttribute('onclick', `kakaoPay('${user_email}', '${username}')`)

var IMP = window.IMP;

var today = new Date();
var hours = today.getHours(); // 시
var minutes = today.getMinutes();  // 분
var seconds = today.getSeconds();  // 초
var milliseconds = today.getMilliseconds();
var makeMerchantUid = hours + minutes + seconds + milliseconds;

function kakaoPay(useremail, username) {
    if (confirm("구매 하시겠습니까?")) { // 구매 클릭시 한번 더 확인하기
        if (localStorage.getItem("access")) { // 회원만 결제 가능
            const emoticonName = document.getElementById('title').innerText

            IMP.init("가맹점식별코드"); // 가맹점 식별코드
            IMP.request_pay({
                pg: 'kakaopay.TC0ONETIME', // PG사 코드표에서 선택
                pay_method: 'card', // 결제 방식
                merchant_uid: "IMP" + makeMerchantUid, // 결제 고유 번호
                name: '상품명', // 제품명
                amount: 100, // 가격
                //구매자 정보 ↓
                buyer_email: `${useremail}`,
                buyer_name: `${username}`,
                //buyer_tel : '010-1234-5678',
                //buyer_addr : '서울특별시 강남구 삼성동',
                // buyer_postcode : '123-456'
            }, async function (rsp) { // callback
                if (rsp.success) { //결제 성공시
                    console.log(rsp);
					...
                    //결제 성공시 프로젝트 DB저장 요청
                    ...

                    if (response.status == 200) { // DB저장 성공시
                        alert('결제 완료!')
                        window.location.reload();
                    } else { // 결제완료 후 DB저장 실패시
                        alert(`error:[${response.status}]\n결제요청이 승인된 경우 관리자에게 문의바랍니다.`);
                        // DB저장 실패시 status에 따라 추가적인 작업 가능성
                    }
                } else if (rsp.success == false) { // 결제 실패시
                    alert(rsp.error_msg)
                }
            });
        }
        else { // 비회원 결제 불가
            alert('로그인이 필요합니다!')
        }
    } else { // 구매 확인 알림창 취소 클릭시 돌아가기
        return false;
    }
}
</script>

</body>
</html>
