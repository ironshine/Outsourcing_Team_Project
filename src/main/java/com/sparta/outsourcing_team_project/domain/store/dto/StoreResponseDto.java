package com.sparta.outsourcing_team_project.domain.store.dto;

import com.sparta.outsourcing_team_project.domain.menu.dto.MenuResponse;
import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
public class StoreResponseDto {
    private final Long id;
    private final String storeName;
    private final LocalTime storeOpenTime;
    private final LocalTime storeCloseTime;
    private final int minOrderPrice;
    private final String storeStatus;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Long userId;
    private final List<MenuResponse> menus;

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeOpenTime = store.getStoreOpenTime();
        this.storeCloseTime = store.getStoreCloseTime();
        this.minOrderPrice = store.getMinOrderPrice();
        this.storeStatus = storeStatus(store.getStoreStatus());
        this.createdAt = store.getCreatedAt();
        this.modifiedAt = store.getModifiedAt();
        this.userId = store.getUser().getUserId();
        this.menus = menuResponseList(store.getMenus());
    }

    private List<MenuResponse> menuResponseList(List<Menu> menuList) {
        return menuList.stream().map(MenuResponse::entityToDto).toList();
    }
    protected static String storeStatus(boolean storeStatus) {
        return storeStatus ? "정상영업" : "폐업";
    }
}
