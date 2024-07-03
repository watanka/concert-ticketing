## 티켓팅 프로젝트

### 목표
- 도메인을 어그리게이트 기준으로 분리합니다.
- 개발자가 아니더라도 알아들을 수 있도록 문서를 작성하고 업데이트합니다.
- 테스트 기반 코드를 작성함으로써 안정성 있는 코드를 구현합니다.
- 대용량 트래픽을 제어할 수 있는 대기열을 구현합니다.

### TODO
- [ ] 2024-07-02: 포인트 동시성처리 시에 DistributedLock을 구현했음에도 user balance의 결과가 누락됨.
- [ ] 2024-07-03: 티켓예약(TicketService.register) 시, 예약가능 좌석인지 검증하는 로직을 현재는 Seat.status가 AVAILABLE인지 확인함. 이걸 티켓들 중 해당 (concertId, showTime, seatNo)가 있는지 조회하는 것을 변경해야함. 도메인 책임 분리 관점에서, 좌석의 예약 상태는 콘서트 도메인의 책임이 아님. 문제는 `isAvailable(Seat)` 로직이 동시성 처리에서 계속 잘못된 결과를 냄. 


### Getting Started
```bash
```

### API 문서
- 어플리케이션 실행후,[Swagger-ui](http://localhost:8080/swagger-ui/index.html#/)에서 확인할 수 있습니다.

### 기술스택
- **개발환경**: Intellij CE
- **WebFramework**: Spring Boot
- **DB**: MySQL
- **ORM**: Spring Data JPA
- **Test Framework**: JUnit, Mockito
- **DistributedLock**: Jedis
- **MessageBroker**: Redis(대기열), Kafka(메세지큐)

### 요구사항 정의
[요구사항분석](assets/docs/요구사항분석.md)  
- 토큰 발급/조회: 예약서비스에 접근하기 위해 토큰을 부여받고 입장순서를 기다립니다. 
- 콘서트 조회: 콘서트 날짜와 좌석을 조회합니다.
- 좌석 예약: 좌석 예약을 요청합니다.
- 포인트 충전/조회: 결제를 위한 포인트를 충전 및 조회합니다. 
- 결제: 예약에 대한 결제를 요청합니다.

### 도메인 구성
[ERD](assets/docs/ERD.md)  
- Concert
- Ticket
- User
- Point
- WaitingQueue




### 아키텍쳐
- layered + clean architecture 채택
- 채택 이유는?
  - 아키텍쳐는 제 기능을 수행하면서도 변경에 유연한 코드를 작성하기 위한 틀. 변경에 유연한 코드를 위해서는 '관심사 분리'가 중요합니다. 
  - layered 아키텍쳐는 presentation, business logic, infrastructure로 이루어진 레이어드 아키텍쳐는 흐름이 단순명료하지만, 도메인이 인프라에 의존하게 된다는 단점이 있습니다.
  - clean 아키텍쳐는 의존성 역전을 통해 양파껍질 같은 구조의 정가운데에 business logic을 배치하고, 그 밖의 서비스를 위한 요소들을 위치시킴으로써 안쪽 레이어는 바깥쪽 레이어에 대해 알지 못하도록 구성합니다. 이는 분명 유연하긴 하지만, 비교적 복잡한 구조로 초기에 셋팅해야할 시간비용이 크다는 단점이 있습니다.  
  
  - 이 두 아키텍쳐의 장점을 섞어서 layered + clean architecture를 채택했습니다.
  - 도메인 영역을 순수하게 보존하기 위해 의존성 역전을 위한 인터페이스를 추가하였습니다.
```

```

### 시나리오 분석
- 우선 대략적인 흐름을 시퀀스 다이어그램으로 작성합니다.
- TDD를 기반으로 코드를 작성하면서, 예외케이스들을 추가해나갑니다. 추가된 내용은 문서에 반영합니다.


### API 명세
[api디자인](assets/docs/api_design.md)  
- 도메인별 API 디자인  
   

### Mock API
- API Spec이 정해지고 난 후에는 다른 동료들이 사용할 수 있도록 Mock API를 배포합니다.
  - 아직 구체적이지 않을 수 있는 API Spec에 대해서 구체적인 예시들을 MockAPI를 통해 제공함으로써 같은 이해선상에 있을 수 있도록 합니다.
  - 다른 동료들의 산출물과 통합할 때 생길 수 있는 문제들을 최소화할 수 있도록 합니다.


### 동시성 처리
[동시성 처리](assets/docs/동시성처리.md)
- 동시성 처리가 필요한 상황
  - `좌석예약`: 하나의 좌석에 대해 동시에 여러 명이 예약을 요청할 수 있다.
  - `포인트 충전/사용`: 한 유저가 포인트 충전/사용 요청을 동시에 여러 개 보낼 수 있다. (e.g: 버튼 클릭 따닥, 브라우져 두 개) 

### 예외 처리

### CI/CD 구성
- Lint



### 성능개선 사항
- 캐쉬 적용

