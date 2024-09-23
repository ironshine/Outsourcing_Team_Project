package com.sparta.outsourcing_team_project.domain.orders.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    ORDER_CANCELLED(Status.ORDER_CANCELLED),
    PREPARING(Status.PREPARING),
    IN_DELIVERY(Status.IN_DELIVERY),
    DELIVERED(Status.DELIVERED);

    private final String status;

    OrderStatusEnum(String status) {this.status = status;}

    public static class Status{
        public static final String ORDER_CANCELLED = "주문 취소";
        public static final String PREPARING = "가게 조리중";
        public static final String IN_DELIVERY = "배송중";
        public static final String DELIVERED = "배송완료";
    }
}
