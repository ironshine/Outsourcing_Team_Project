package com.sparta.outsourcing_team_project.domain.order.exception;

import com.sparta.outsourcing_team_project.domain.common.exception.InvalidRequestException;
import com.sparta.outsourcing_team_project.domain.order.controller.OrderController;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice(basePackageClasses = OrderController.class)
    public class OrderExceptionHandler {

        @ExceptionHandler({InvalidRequestException.class})
        public ResponseEntity<OrderApiException> InvalidRequestException(Exception ex){
            OrderApiException orderApiException = new OrderApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(orderApiException, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler({EntityNotFoundException.class})
        public ResponseEntity<OrderApiException> EntityNotFoundException(Exception ex){
            OrderApiException orderApiException = new OrderApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(orderApiException, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler({IllegalArgumentException.class})
        public ResponseEntity<OrderApiException> IllegalArgumentException(Exception ex){
            OrderApiException orderApiException = new OrderApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(orderApiException, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler({AccessDeniedException.class})
        public ResponseEntity<OrderApiException> AccessDeniedException(Exception ex){
            OrderApiException orderApiException = new OrderApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(orderApiException, HttpStatus.BAD_REQUEST);
        }

    }

