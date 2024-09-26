package com.sparta.outsourcing_team_project.domain.store.entity;

import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_team_project.domain.order.entity.TimeStamp;
import com.sparta.outsourcing_team_project.domain.store.dto.StoreRequestDto;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "store")
@NoArgsConstructor
@AllArgsConstructor
public class Store extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "store_opentime", nullable = false)
    private LocalTime storeOpenTime;

    @Column(name = "store_closetime", nullable = false)
    private LocalTime storeCloseTime;

    @Column(name = "min_orderprice", nullable = false)
    private Integer minOrderPrice;

    @Column(name = "store_status", nullable = false)
    private Boolean storeStatus; // true : 가게정상운영(기본값), false : 가게폐업

    @Column(name = "ad_price", nullable = false)
    private Long adPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 사장님 id
    private User user;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    // 가게 수정 메서드
    // 파라미터 StoreRequestDto -> 풀어서 받게 변경 (테스트 코드 작성 쉽게 하기 위해서)
    public void updateStore(StoreRequestDto storeRequestDto) {
        this.storeName = storeRequestDto.getStoreName();
        this.storeOpenTime = storeRequestDto.getStoreOpenTime();
        this.storeCloseTime = storeRequestDto.getStoreCloseTime();
        this.minOrderPrice = storeRequestDto.getMinOrderPrice();
    }

    // 가게 상태 변경 메서드 (폐업)
    public void closedStore() {
        this.storeStatus = false;
    }

    // 광고비 추가 메서드
    public void addAdPrice(long adPrice) {
        this.adPrice += adPrice;
    }
}
