package com.sparta.outsourcing_team_project.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderApiException {
    private String errorMessage;
    private int statusCode;
}
