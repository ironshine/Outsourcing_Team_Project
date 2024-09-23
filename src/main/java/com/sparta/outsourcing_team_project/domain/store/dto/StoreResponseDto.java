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
    private final Boolean storeStatus;
    private final LocalDateTime createAt;
    private final LocalDateTime updatedAt;
    private final Long userId;
    private final List<MenuResponse> menus;

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeOpenTime = store.getStoreOpenTime();
        this.storeCloseTime = store.getStoreCloseTime();
        this.minOrderPrice = store.getMinOrderPrice();
        this.storeStatus = store.getStoreStatus();
        this.createAt = store.getCreateAt();
        this.updatedAt = store.getUpdatedAt();
        this.userId = store.getUser().getUserId();
        this.menus = menuResponseList(store.getMenus());
    }

    private List<MenuResponse> menuResponseList(List<Menu> menuList) {
        return menuList.stream().map(MenuResponse::entityToDto).toList();
    }
}
