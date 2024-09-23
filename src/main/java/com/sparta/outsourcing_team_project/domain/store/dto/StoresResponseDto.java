package com.sparta.outsourcing_team_project.domain.store.dto;

import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.sparta.outsourcing_team_project.domain.store.dto.StoreResponseDto.storeStatus;

@Getter
public class StoresResponseDto {
    private final Long id;
    private final String storeName;
    private final LocalTime storeOpenTime;
    private final LocalTime storeCloseTime;
    private final int minOrderPrice;
    private final String storeStatus;
    private final LocalDateTime createAt;
    private final LocalDateTime updatedAt;
    private final Long userId;

    public StoresResponseDto(Store store) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeOpenTime = store.getStoreOpenTime();
        this.storeCloseTime = store.getStoreCloseTime();
        this.minOrderPrice = store.getMinOrderPrice();
        this.storeStatus = storeStatus(store.getStoreStatus());
        this.createAt = store.getCreateAt();
        this.updatedAt = store.getUpdatedAt();
        this.userId = store.getUser().getUserId();
    }
}
