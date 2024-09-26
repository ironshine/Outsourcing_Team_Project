package com.sparta.outsourcing_team_project.domain.order.dto;

import com.sparta.outsourcing_team_project.domain.order.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusResponseDto {
    private Long orderId;
    private OrderStatusEnum orderStatus;
    private String address;
    private LocalDateTime changedAt;
}
