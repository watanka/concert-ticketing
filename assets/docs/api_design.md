# API 명세

## WaitingQueue 도메인
### 유저 대기열 토큰 기능
- 서비스를 이용할 토큰을 발급받는 API를 작성합니다.
- 토큰은 유저의 UUID 와 해당 유저의 대기열을 관리할 수 있는 정보 ( 대기 순서 or 잔여 시간 등 ) 를 포함합니다.
- 이후 모든 API 는 위 토큰을 이용해 대기열 검증을 통과해야 이용 가능합니다.

| uri       | method | request header | request body | response | description                 |
|:----------| :----- | :------------- |--------------| :------- |-----------------------------|
| `/tokens` | `GET`  | jwt 토큰         |              | 대기열 순번   | 대기열 정보(대기순서,waitingNo)를 조회함 |
| `/tokens` | `POST` |                | userId       | jwt 토큰   | 대기열에 등록함                    |

## Concert 도메인

### 예약 가능 날짜/ 좌석 API
- 예약가능한 날짜와 해당 날짜의 좌석을 조회하는 API 를 각각 작성합니다.
- 예약 가능한 날짜 목록을 조회할 수 있습니다.
- 날짜 정보를 입력받아 예약가능한 좌석정보를 조회할 수 있습니다.

| uri                                    | method | request header | request body | response       | description                                                |
|:---------------------------------------| :----- | :------------- | ------------ | :------------- |------------------------------------------------------------|
| `/concerts`                            | `GET`  | jwt 토큰         |              | List[ConcertResponse]  | 콘서트 목록 조회<br/>ConcertResponse: [콘서트 이름, 공연자 이름]                    |
| `/concerts/{concert_id}`               | `GET`  | jwt 토큰         |              | List[ShowTimeResponse] | `concert_id`의 showtime 조회<br/>ShowTimeResponse: [콘서트ID, 공연시간, 공연장소] |
| `/concerts/{concert_id}/{showtime_id}` | `GET`  | jwt 토큰         |              | List[SeatResponse]     | `showtime_id`의 좌석 조회<br/>SeatResponse: [좌석번호, 금액, 좌석배정상태]          |

## Ticket 도메인

### 좌석 예약 요청 API
- 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API 를 작성합니다.
- 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정됩니다. ( 시간은 정책에 따라 자율적으로 정의합니다. )
- 배정 시간 내에 결제가 완료되지 않았다면 좌석에 대한 임시 배정은 해제되어야 합니다.
- 배정 시간 내에는 다른 사용자는 예약할 수 없어야 합니다.

| uri        | method | request header | request body                 | response     | description                                                                |
| :--------- | :----- | :------------- | ---------------------------- | :----------- |----------------------------------------------------------------------------|
| `/tickets` | `POST` | jwt 토큰         | user_id,showtime_id, seat_id | TicketResponse       | 선택한 좌석 예약<br>예약한 티켓 정보 반환<br/>Ticket: [콘서트 이름, 좌석번호, 금액, 티켓상태, 예약시간, 공연장소] |
| `/tickets` | `GET`  |       | user_id                      | List[TicketResponse] | 예약한 티켓(들) 정보 반환                                                            |


## Point 도메인

### 잔액 충전/ 조회 API (대기열 토큰 검증X)
- 결제에 사용될 금액을 API 를 통해 충전하는 API 를 작성합니다.
- 사용자 식별자 및 충전할 금액을 받아 잔액을 충전합니다.
- 사용자 식별자를 통해 해당 사용자의 잔액을 조회합니다.

| uri                | method | request header | request body              | response                 | description                                                      |
| :----------------- | :----- | :------------- |---------------------------| :----------------------- |------------------------------------------------------------------|
| `/points`          | `POST` |                | user_id, point, pointType | `null`                   | 포인트 사용/충전                                                        |
| `/point_history`   | `GET`  |                | user_id                   | List[PointTransactionResponse]   | 포인트 내역 반환<br/>PointTransactionResponse: [트랜잭션ID, 유저ID, 금액, 포인트타입(사용/충전)] |


## Payment 도메인

### 결제 API
- 결제 처리하고 결제 내역을 생성하는 API 를 작성합니다.
- 결제가 완료되면 해당 좌석의 소유권을 유저에게 배정하고 대기열 토큰을 만료시킵니다.

| uri                | method | request header | request body                       | response                         | description                                          |
| :----------------- | :----- | :------------- |------------------------------------|:---------------------------------|------------------------------------------------------|
| `/payments`        | `POST` |                | user_id, ticket_id, price | PaymentTransactionResponse       | 티켓 결제<br/>PaymentTransactionResponse: [유저ID, 금액, 티켓ID, 결제시간] |
| `/payment_history` | `GET`  |                | user_id                            | List[PaymentTransactionResponse] | 결제 내역 반환                                             |

