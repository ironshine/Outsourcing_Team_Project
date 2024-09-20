package com.sparta.outsourcing_team_project.store.dto;

import lombok.Getter;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class StoreResponseDto {
    private final Long id;
    private final String storeName;
    private final String storeOpenTime;
    private final String storeCloseTime;
    private final String minOrderPrice;
    private final Boolean storeStatus;
    private final LocalDateTime createAt;
    private final LocalDateTime updatedAt;
//    private final Long userId;
//    private final List<MenuResponseDto> menus;

    public StoreResponseDto(Long id, String storeName, String storeOpenTime, String storeCloseTime, String minOrderPrice, Boolean storeStatus, LocalDateTime createAt, LocalDateTime updatedAt){ //, Long userId, List<MenuResponseDto> menus) {
        this.id = id;
        this.storeName = storeName;
        this.storeOpenTime = storeOpenTime;
        this.storeCloseTime = storeCloseTime;
        this.minOrderPrice = minOrderPrice;
        this.storeStatus = storeStatus;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
//        this.userId = userId;
//        this.menus = menus;
    }
}
