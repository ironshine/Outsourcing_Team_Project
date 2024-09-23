package com.sparta.outsourcing_team_project.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.List;

@Getter
@AllArgsConstructor
public class OrderOptionsRequestDto {
    private Long storeId;
    private Long menuId;
    private Long optionGroupId;
    private List<Long> optionIds;
    private String address;
}
