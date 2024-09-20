package com.sparta.outsourcing_team_project.store.entity;

import com.sparta.outsourcing_team_project.store.dto.StoreRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Getter
@Table(name = "stores")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "store_opentime", nullable = false)
    private String storeOpenTime;

    @Column(name = "store_closetime", nullable = false)
    private String storeCloseTime;

    @Column(name = "min_orderprice", nullable = false)
    private String minOrderPrice;

    @Column(name = "store_status", nullable = false)
    private Boolean storeStatus;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id") // 사장님 id
//    private User user;
//
//    @OneToMany(mappedBy = "stores")
//    private List<Menu> menus = new ArrayList<>();

    public Store(String storeName, String storeOpenTime, String storeCloseTime, String minOrderPrice, Boolean storeStatus){ //, User user, List<Menu> menus) {
        this.storeName = storeName;
        this.storeOpenTime = storeOpenTime;
        this.storeCloseTime = storeCloseTime;
        this.minOrderPrice = minOrderPrice;
        this.storeStatus = storeStatus;
//        this.user = user;
//        this.menus = menus;
    }

    public void updateStore(StoreRequestDto requestDto) {
        this.storeName = requestDto.getStoreName();
        this.storeOpenTime = requestDto.getStoreOpenTime();
        this.storeCloseTime = requestDto.getStoreCloseTime();
        this.minOrderPrice = requestDto.getMinOrderPrice();
        this.storeStatus = requestDto.getStoreStatus();
    }

    public void closedStore() {
        this.storeStatus = false;
    }
}
