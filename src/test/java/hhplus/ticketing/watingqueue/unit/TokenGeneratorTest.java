package hhplus.ticketing.watingqueue.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TokenGeneratorTest {

    /**
     * 토큰 제너레이터 사용 시나리오
     * - 사용자가 대기열에 등록한다. 서버는 토큰을 제공한다.
     * - 사용자는 토큰을 조회한다. 만약 차례가 되어 토큰이 활성화되면, 서비스에 접근한다.
     * - 예약 api들에 요청시, 토큰을 http request의 Authorization header에 넣어서 보낸다.
     * - 서버는 토큰의 유효성을 검증한다.
     * - JWT 토큰을 사용함으로써, 해커가 대기열의 토큰을 무작위로 찍어도, header+payload+서버의 secret key로 인코딩한 값이 signature와 일치하는지를 검증하기 때문에, signature가 없는 이상 대기열에 접근할 수 없다.
     * 토큰 제너레이터가 가져야하는 기능
     *
     * 1. 토큰 생성
     * 2. 토큰 검증
     */

    @Test
    @DisplayName("signature와 ")


}
