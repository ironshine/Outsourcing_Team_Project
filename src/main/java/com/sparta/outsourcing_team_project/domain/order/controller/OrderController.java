package com.sparta.outsourcing_team_project.domain.order.controller;

import com.sparta.outsourcing_team_project.domain.common.annotation.Auth;
import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.order.aop.annotation.RequestTrack;
import com.sparta.outsourcing_team_project.domain.order.dto.OrderOptionsRequestDto;
import com.sparta.outsourcing_team_project.domain.order.dto.OrderOptionsResponseDto;
import com.sparta.outsourcing_team_project.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing_team_project.domain.order.dto.OrderStatusResponseDto;
import com.sparta.outsourcing_team_project.domain.order.enums.OrderStatusEnum;
import com.sparta.outsourcing_team_project.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    // 주문 메뉴 옵션 조회
    @GetMapping("/store_id/{storeId}/menu_id/{menuId}")
    public ResponseEntity<List<OrderOptionsResponseDto>> getMenuOptions(
            @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @PathVariable Long menuId) {
        return ResponseEntity.ok(orderService.getMenuOptions(storeId, menuId, authUser));
    }
    // 주문 요청
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody @Valid OrderOptionsRequestDto requestDto, @Auth AuthUser authUser) throws AccessDeniedException {

        return ResponseEntity.ok(orderService.createOrder(requestDto, authUser));
    }

    // 주문 수락
    @RequestTrack
    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderStatusResponseDto> approveOrder(@PathVariable Long orderId, @Auth AuthUser authUser) throws AccessDeniedException {

        return ResponseEntity.ok(orderService.acceptOrder(orderId, authUser));
    }

    // 주문 상태 변경
    @PatchMapping
    public ResponseEntity<OrderStatusResponseDto> changeOrderStatus(
            @RequestParam Long storeId,
            @RequestParam Long orderId,
            @RequestParam OrderStatusEnum orderStatus,
            @Auth AuthUser authUser) throws AccessDeniedException {

        return ResponseEntity.ok(orderService.changeOrderStatus(storeId, orderId, orderStatus, authUser));
    }
}
