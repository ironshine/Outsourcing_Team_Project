package com.sparta.outsourcing_team_project.store.dto;

import lombok.Getter;

@Getter
public class StoreRequestDto {
    private String storeName;
    private String storeOpenTime;
    private String storeCloseTime;
    private String minOrderPrice;
}
