package hhplus.ticketing.watingqueue.aop;

import hhplus.ticketing.domain.token.components.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
public class TokenValidationAop {

    @Autowired
    private final WaitingQueueService waitingQueueService;

    @Before("@annotation(TokenValidation)")
    public void validate(final ProceedingJoinPoint joinPoint) throws Throwable{
//        waitingQueueService.validate();
        System.out.println("token validation 진행"+(joinPoint.getArgs()));

    }
}
