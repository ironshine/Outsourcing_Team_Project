package com.sparta.outsourcing_team_project.domain.order.aop;

import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    @Autowired
    private final UserRepository userRepository;

    @Pointcut("@annotation(com.sparta.outsourcing_team_project.domain.order.aop.annotation.RequestTrack)")
    private void requestLog(){}

    @Around("requestLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        AuthUser authUser = null;
        for(Object arg : joinPoint.getArgs()){
            if(arg instanceof AuthUser){
                authUser = (AuthUser)arg;
                break;
            }
        }
        User user = userRepository.findById(authUser.getUserId()).orElse(null);
        // 임시
        Long storeId = 1L;
        Long orderId = 1L;

        try {
            return joinPoint.proceed();
        }finally {
            Date requestTimestamp = new Date();
            log.info("::: requestTimestamp: {}", requestTimestamp);
            log.info("::: requestStoreId: {}", storeId);
            log.info("::: requestOrderId: {}", orderId);
        }
    }
}
