package com.sparta.outsourcing_team_project.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderOptionsResponseDto {
    private Long optionGroupId;
    private String optionGroupName;
    private Long orderId;
    private String optionName;
    private Integer optionPrice;
}
