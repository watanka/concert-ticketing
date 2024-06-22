** API 인풋 아웃풋에 대한 값은 개발을 진행하면서 변동이 생길 수 있으니 대략적으로 적음.


## WaitingQueue 도메인
### 유저 대기열 토큰 기능
- 서비스를 이용할 토큰을 발급받는 API를 작성합니다.
- 토큰은 유저의 UUID 와 해당 유저의 대기열을 관리할 수 있는 정보 ( 대기 순서 or 잔여 시간 등 ) 를 포함합니다.
- 이후 모든 API 는 위 토큰을 이용해 대기열 검증을 통과해야 이용 가능합니다.

| uri      | method | request header | request body | response | description |
| :------- | :----- | :------------- | ------------ | :------- | ----------- |
| `/token` | `GET`  | jwt 토큰         |              | 대기열 순번   | 대기열 정보를 조회함 |
| `/token` | `POST` |                | 유저 정보        | jwt 토큰   | 대기열에 등록함    |

## Concert 도메인

### 예약 가능 날짜/ 좌석 API
- 예약가능한 날짜와 해당 날짜의 좌석을 조회하는 API 를 각각 작성합니다.
- 예약 가능한 날짜 목록을 조회할 수 있습니다.
- 날짜 정보를 입력받아 예약가능한 좌석정보를 조회할 수 있습니다.

| uri                                    | method | request header | request body | response       | description               |
| :------------------------------------- | :----- | :------------- | ------------ | :------------- | ------------------------- |
| `/concerts`                            | `GET`  | jwt 토큰         |              | List[Concert]  | 콘서트 목록 조회                 |
| `/concerts/{concert_id}`               | `GET`  | jwt 토큰         |              | List[ShowTime] | `concert_id`의 showtime 조회 |
| `/concerts/{concert_id}:{showtime_id}` | `GET`  | jwt 토큰         |              | List[Seat]     | `showtime_id`의 좌석 조회      |

## Ticket 도메인

### 좌석 예약 요청 API
- 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API 를 작성합니다.
- 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정됩니다. ( 시간은 정책에 따라 자율적으로 정의합니다. )
- 배정 시간 내에 결제가 완료되지 않았다면 좌석에 대한 임시 배정은 해제되어야 합니다.
- 배정 시간 내에는 다른 사용자는 예약할 수 없어야 합니다.

| uri        | method | request header | request body                 | response     | description               |
| :--------- | :----- | :------------- | ---------------------------- | :----------- | ------------------------- |
| `/tickets` | `POST` | jwt 토큰         | user_id,showtime_id, seat_id | Ticket       | 선택한 좌석 예약<br>예약한 티켓 정보 반환 |
| `/tickets` | `GET`  | jwt 토큰         | user_id                      | List[Ticket] | 예약한 티켓(들) 정보 반환           |


## Point 도메인

### 잔액 충전/ 조회 API (대기열 토큰 검증X)
- 결제에 사용될 금액을 API 를 통해 충전하는 API 를 작성합니다.
- 사용자 식별자 및 충전할 금액을 받아 잔액을 충전합니다.
- 사용자 식별자를 통해 해당 사용자의 잔액을 조회합니다.

### 결제 API
- 결제 처리하고 결제 내역을 생성하는 API 를 작성합니다.
- 결제가 완료되면 해당 좌석의 소유권을 유저에게 배정하고 대기열 토큰을 만료시킵니다.

| uri                | method | request header | request body       | response                 | description |
| :----------------- | :----- | :------------- | ------------------ | :----------------------- | ----------- |
| `/payments`        | `POST` |                | user_id, ticket_id | PaymentTransaction       | 티켓 결제       |
| `/points`          | `POST` |                | user_id            | `null`                   | 포인트 충전      |
| `/point_history`   | `GET`  |                | user_id            | List[PointTransaction]   | 포인트 내역 반환   |
| `/payment_history` | `GET`  |                | user_id            | List[PaymentTransaction] | 결제 내역 반환    |

