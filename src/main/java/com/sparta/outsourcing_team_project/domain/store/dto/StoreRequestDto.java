package com.sparta.outsourcing_team_project.domain.store.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class StoreRequestDto {
    private String storeName;
    private LocalTime storeOpenTime;
    private LocalTime storeCloseTime;
    private int minOrderPrice;
}
