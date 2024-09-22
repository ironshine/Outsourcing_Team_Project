package com.sparta.outsourcing_team_project.order.entity;

import com.sparta.outsourcing_team_project.menu.entity.Menu;
import com.sparta.outsourcing_team_project.order.enums.OrderStatusEnum;
import com.sparta.outsourcing_team_project.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "customer_order")
public class CustomerOrder extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, name = "order_status", length = 6)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    // 연관관계 임시설정 합병후 확인필요
//    @OneToOne(mappedBy = "user")
//    private User user;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
