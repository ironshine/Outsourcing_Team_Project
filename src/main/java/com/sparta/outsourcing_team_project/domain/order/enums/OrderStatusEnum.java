package com.sparta.outsourcing_team_project.domain.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    PENDING(Status.PENDING),
    ORDER_CANCELLED(Status.ORDER_CANCELLED),
    PREPARING(Status.PREPARING),
    IN_DELIVERY(Status.IN_DELIVERY),
    DELIVERED(Status.DELIVERED);

    private final String status;

    OrderStatusEnum(String status) {
        // 입력데이터 대문자변환 및 입력값 검증로직
        this.status = status.toUpperCase();
        if(!this.status.equals("ORDER_CANCELLED")
                && !this.status.equals("PREPARING")
                && !this.status.equals("IN_DELIVERY")
                && !this.status.equals("DELIVERED")
        ){
            new IllegalArgumentException("주문 상태 변경값이 올바르지 않습니다.");
        }
    }

    public static class Status{
        public static final String PENDING = "주문 대기";
        public static final String ORDER_CANCELLED = "주문 취소";
        public static final String PREPARING = "가게 조리중";
        public static final String IN_DELIVERY = "배송중";
        public static final String DELIVERED = "배송완료";
    }
}
