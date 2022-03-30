package com.project.auction.lol.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component // 빈 추가
@Aspect // AOP임을 선언
public class TestAspect {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    // 기능 실행 전, 후, 예외시 모든 시점에 적용
    // TestAnnotaion이 붙은 메소드에 대해 실행
    @Around("@annotation(TestAnnotation)")
    public Object logPrint(ProceedingJoinPoint joinPoint) throws  Throwable {
        log.info("Before Execute Method");
        // join.proceed 앞 뒤로 처리해야할 기능을 작성하면
        // 앞으로 Controller로 끝나는 객체의 모든 메서드가 실행될 때 마다 작성된 공통기능들이 실행될것
        Object proceed = joinPoint.proceed();
        log.info("After Execute Method");
        return proceed;
    }
}
