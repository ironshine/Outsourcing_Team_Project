package com.sparta.outsourcing_team_project.store.entity;

import com.sparta.outsourcing_team_project.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Boolean storeStatus; // true : 가게정상운영(기본값), false : 가게폐업

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
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    public Store(String storeName, String storeOpenTime, String storeCloseTime, String minOrderPrice, Boolean storeStatus) { //, User user, List<Menu> menus) {
        this.storeName = storeName;
        this.storeOpenTime = storeOpenTime;
        this.storeCloseTime = storeCloseTime;
        this.minOrderPrice = minOrderPrice;
        this.storeStatus = storeStatus;
//        this.user = user;
//        this.menus = menus;
    }

    // 가게 수정 메서드
    // 파라미터 StoreRequestDto -> 풀어서 받게 변경 (테스트 코드 작성 쉽게 하기 위해서)
    public void updateStore(String storeName, String storeOpenTime, String storeCloseTime, String minOrderPrice) {
        this.storeName = storeName;
        this.storeOpenTime = storeOpenTime;
        this.storeCloseTime = storeCloseTime;
        this.minOrderPrice = minOrderPrice;
    }

    // 가게 상태 변경 메서드 (폐업)
    public void closedStore() {
        this.storeStatus = false;
    }
}
