package hhplus.ticketing.watingqueue.aop;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class AopTest {


    AspectJProxyFactory aspectJProxyFactory;

    TokenValidationAop interceptor;

    TestAopService testAopService;

    @BeforeEach
    void setUp(){
        testAopService = Mockito.mock(TestAopService.class);
        interceptor = Mockito.mock(TokenValidationAop.class);
        aspectJProxyFactory = new AspectJProxyFactory(testAopService);
        aspectJProxyFactory.addAspect(interceptor);
    }


    @Test
    public void testAspectOutput() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        when(testAopService.sampleRun()).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                return "sample run";
            }
        });

        TestAopService proxy = aspectJProxyFactory.getProxy();
        // AOP가 적용된 메소드 호출
        proxy.sampleRun();

        String capturedOutput = outContent.toString();
        assertTrue(capturedOutput.contains("token validation 진행"));
    }
}
