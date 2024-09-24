package com.sparta.outsourcing_team_project.domain.order.aop;


import com.sparta.outsourcing_team_project.domain.order.entity.CustomerOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    @Pointcut("execution(* com.sparta.outsourcing_team_project.domain.order.repository..*(..))")
    private void requestLog() {
    }

    @Around("requestLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Long storeId = 0L;
        Long orderId = 0L;
        Object result = null;

        result = joinPoint.proceed();
        Optional<?> entity = (Optional<?>) result;

        // 반환객체 검증후 로직실행
        if(entity.get() instanceof CustomerOrder) {
            Optional<CustomerOrder> customerOrder = (Optional<CustomerOrder>) result;
            CustomerOrder order = customerOrder.get();

            storeId = order.getStore().getId();
            orderId = order.getId();

            Date requestTimestamp = new Date();
            log.info("::: requestTimestamp: {}", requestTimestamp);
            log.info("::: requestStoreId: {}", storeId);
            log.info("::: requestOrderId: {}", orderId);
        }
        return result;
    }
}
