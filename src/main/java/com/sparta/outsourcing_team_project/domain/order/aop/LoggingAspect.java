package com.sparta.outsourcing_team_project.domain.order.aop;

import com.sparta.outsourcing_team_project.domain.order.dto.OrderAopDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Date;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final OrderAopDto aopDto;

    @Pointcut("@annotation(com.sparta.outsourcing_team_project.domain.order.aop.annotation.RequestTrack)")
    private void requestLog() {
    }

    @Around("requestLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Long storeId = 0L;
        Long orderId = 0L;
        Object result = null;
        try{
            result = joinPoint.proceed();
            if (result instanceof OrderAopDto) {
                OrderAopDto aopDto = (OrderAopDto) result;
                storeId = aopDto.getStoreId();
                orderId = aopDto.getOrderId();
            }
        }finally {
            Date requestTimestamp = new Date();
            log.info("::: requestTimestamp: {}", requestTimestamp);
            log.info("::: requestStoreId: {}", storeId);
            log.info("::: requestOrderId: {}", orderId);

            return result;
        }
    }
}
