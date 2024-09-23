package com.sparta.outsourcing_team_project.domain.order.exception;

import com.sparta.outsourcing_team_project.domain.order.controller.OrderController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

    @RestControllerAdvice(basePackageClasses = OrderController.class)
    public class OrderExceptionHandler {

        @ExceptionHandler({Exception.class})
        public ResponseEntity<OrderApiException> handleException(Exception ex){
            OrderApiException orderApiException = new OrderApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(orderApiException, HttpStatus.BAD_REQUEST);
        }
    }

