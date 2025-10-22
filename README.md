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

### 결제 <br>
POST<br>
payments/{paymentId}/instant <br>

#### Request
pathVariable - paymentId : string <br>
body - InstantPaymentRequest <br>
```
InstantPaymentRequest

storeId : string
orderName : string
totalPayAmount : integer
currency : string (KRW, USD)
customData : string (optional)
```

```
InstantPaymentRequest Example

{
    "storeId": "my_store_001", 
    "orderName": "노트북외 1건",
    "totalPayAmount": 1500000,
    "currency": "KRW",
    "customData": "{"item":"노트북","quantity":1}"
}
```

#### Response
```
{
    "paymentId": "20251022_0001",
    "paidAt": "2025-10-22T20:21:35.529482"
}
```

### 결제 취소 <br>
POST<br>
payments/{paymentId}/cancel <br>

#### Request
pathVariable - paymentId : string <br>

#### Response
```
{
    "paymentId": "20251022_0001",
    "paymentStatus": "CANCELLED",
    "orderName": "주문2",
    "pgProvider": "CEOS_PAY",
    "currency": "KRW",
    "customData": null
    "paidAt": "2025-10-22T20:12:39.295038"

}
```


### 단건 조회 <br>
GET<br>
payments/{paymentId} <br>

#### Request
pathVariable - paymentId : string <br>

#### Response
```
{
    "paymentId": "20251022_0001",
    "paymentStatus": "CANCELLED",
    "orderName": "주문2",
    "pgProvider": "CEOS_PAY",
    "currency": "KRW",
    "customData": null
    "paidAt": "2025-10-22T20:12:39.295038"

}
```

## 참고

- paymentId는 본인 상점마다 고유한 번호를 부여하여 사용합니다. <br>
예를 들어 "20251022_0001", "20251022_0002"<br>

포트원에서도 이러한 방법을 사용하고 있습니다. <br>

- orderName은 주문명으로 고객에게 표시되는 정보입니다. <br>


- 일반적으로 custom data에 상품 정보, 수량등 주문 정보를 담아 사용합니다. 필수X<br>
예를 들어 {"item":"노트북","quantity":1} 와 같은 형식입니다. <br>


- 모든 결제 내역 조회는 존재하지 않습니다. <br>

