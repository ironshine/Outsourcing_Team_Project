package com.sparta.outsourcing_team_project.domain.order.entity;

import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_team_project.domain.order.enums.OrderStatusEnum;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import com.sparta.outsourcing_team_project.domain.user.entity.User;

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

    private Integer totalPrice;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, name = "order_status", length = 6)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus = OrderStatusEnum.STATELESS;

    //연관관계 임시설정 합병후 확인필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public CustomerOrder(Integer totalPrice, String address, OrderStatusEnum orderStatus, User user, Store store, Menu menu) {
        this.totalPrice = totalPrice;
        this.address = address;
        this.orderStatus = orderStatus;
        this.user = user;
        this.store = store;
        this.menu = menu;
    }

    public CustomerOrder changeStatus(OrderStatusEnum status){
        this.orderStatus = status;
        return this;
    }
}
