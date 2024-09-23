package com.sparta.outsourcing_team_project.domain.order.controller;

import com.sparta.outsourcing_team_project.domain.common.annotation.Auth;
import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.order.dto.OrderOptionsRequestDto;
import com.sparta.outsourcing_team_project.domain.order.dto.OrderOptionsResponseDto;
import com.sparta.outsourcing_team_project.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing_team_project.domain.order.dto.OrderStatusResponseDto;
import com.sparta.outsourcing_team_project.domain.order.enums.OrderStatusEnum;
import com.sparta.outsourcing_team_project.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    //주문 메뉴 옵션 조회
    @GetMapping("/storeId/{storeId}/menuId/{menuId}")
    public ResponseEntity<List<OrderOptionsResponseDto>> getMenuOptions(
            @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @PathVariable Long menuId) {
        return ResponseEntity.ok(orderService.getMenuOptions(storeId, menuId, authUser));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody OrderOptionsRequestDto requestDto, @Auth AuthUser authUser) {

        return ResponseEntity.ok(orderService.createOrder(requestDto, authUser));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderStatusResponseDto> approveOrder(@PathVariable Long orderId, @Auth AuthUser authUser) {

        return ResponseEntity.ok(orderService.acceptOrder(orderId, authUser));
    }

    @PatchMapping
    public ResponseEntity<OrderStatusResponseDto> changeOrderStatus(
            @RequestParam Long storeId,
            @RequestParam Long orderId,
            @RequestParam OrderStatusEnum orderStatus,
            @Auth AuthUser authUser) {

        return ResponseEntity.ok(orderService.changeOrderStatus(storeId, orderId, orderStatus, authUser));
    }
}
