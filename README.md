# CEOS 결제 서버

https://developers.portone.io/api/rest-v2/payment?v=v2#post%20%2Fpayments%2F%7BpaymentId%7D%2Finstant <br>
포트원을 참고해 구현했습니당

이상 있으면 슬랙 질문방으로 바로 알려주세요!!

## 개요

API hostname : https://payment.loopz.co.kr <br>

### 상황

PG사 승인을 모두 받고 개발을 시작하는 상태라고 가정!<br>
결제 시스템이 노후되어 10% 확률로 결제가 실패합니다.

### 요청 및 응답 형식
요청과 응답의 본문은 JSON 형식입니다.

API 매개 변수 중 URL 경로 또는 query에 들어가는 문자열 값이 있는 경우, 그 자리에 들어갈 수 없는 문자는 이스케이프하여야 합니다. 자바스크립트의 encodeURIComponent 함수 등을 사용할 수 있습니다.

### 인증 방식
V2 API를 사용하기 위해서는 API Secret이 필요하며, 포트원 관리자콘솔 내 결제연동 탭에서 발급받으실 수 있습니다.

인증 관련 API를 제외한 모든 API는 HTTP Authorization 헤더로 아래 형식의 인증 정보를 전달해주셔야 합니다.

Authorization: Bearer MY_API_SECRET

## 인증 API

API Secret 발급 (여러번 발급 가능, 바뀌지 않음) <br>
GET<br>
auth/{본인 github id}



## 결제 API

결제 <br>
POST<br>
payments/{paymentId}/instant <br>

취소 <br>
POST<br>
payments/{paymentId}/cancel <br>

단건 조회 <br>
GET<br>
payments/{paymentId} <br>

