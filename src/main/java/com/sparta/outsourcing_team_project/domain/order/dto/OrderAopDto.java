package com.sparta.outsourcing_team_project.domain.order.dto;

import com.sparta.outsourcing_team_project.domain.order.aop.annotation.RequestTrack;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class OrderAopDto {
    private Long storeId;
    private Long orderId;

    @RequestTrack
    public OrderAopDto inputArgs(Long storeId, Long orderId) {
        this.storeId = storeId;
        this.orderId = orderId;
        return this;
    }
}
