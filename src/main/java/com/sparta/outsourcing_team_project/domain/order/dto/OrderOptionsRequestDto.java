package com.sparta.outsourcing_team_project.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.List;

@Getter
@AllArgsConstructor
public class OrderOptionsRequestDto {
    @NotNull
    private Long storeId;
    @NotNull
    private Long menuId;
    private Long optionGroupId;
    private List<Long> optionIds;
    @NotBlank
    private String address;
}
