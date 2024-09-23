package com.sparta.outsourcing_team_project.domain.orders.service;

import com.sparta.outsourcing_team_project.domain.orders.dto.RequestDto;
import com.sparta.outsourcing_team_project.domain.orders.dto.ResponseDto;
import com.sparta.outsourcing_team_project.domain.orders.enums.OrderStatusEnum;
import com.sparta.outsourcing_team_project.domain.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public ResponseDto getMenuOptions(Long orderId, Long storeId, Long userId, Long menuId) {
        return new ResponseDto();
    }

    public ResponseDto createOrder(RequestDto requestDto) {
        return new ResponseDto();
    }

    public ResponseDto acceptOrder(Long orderId) {
        return new ResponseDto();
    }

    public ResponseDto changeOrderStatus(Long storeId, Long orderId, OrderStatusEnum orderStatus) {
        return new ResponseDto();
    }
}
