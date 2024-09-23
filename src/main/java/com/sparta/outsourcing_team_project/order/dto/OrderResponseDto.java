package com.sparta.outsourcing_team_project.order.dto;

import com.sparta.outsourcing_team_project.order.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long orderId;
    private String userName;
    private String storeName;
    private String menuName;
    private Integer price;
    private List<OrderOptionInfoDto> menuOptions = new ArrayList<>();
    private Integer totalPrice;
    private String address;
    private OrderStatusEnum orderStatus;
    private LocalDateTime orderedAt;
}
