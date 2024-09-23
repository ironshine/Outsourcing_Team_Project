package com.sparta.outsourcing_team_project.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderOptionInfoDto {
    private String optionGroupName;
    private String optionName;
    private Integer optionPrice;
}
