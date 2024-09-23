package com.sparta.outsourcing_team_project.domain.orders.entity;

import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_team_project.domain.orders.enums.OrderStatusEnum;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "orders")
public class Orders extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, name = "order_status", length = 6)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    // 연관관계 임시설정 합병후 확인필요
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
