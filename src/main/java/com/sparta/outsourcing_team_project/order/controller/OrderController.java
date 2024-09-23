package com.sparta.outsourcing_team_project.order.controller;

import com.sparta.outsourcing_team_project.order.dto.*;
import com.sparta.outsourcing_team_project.order.enums.OrderStatusEnum;
import com.sparta.outsourcing_team_project.order.repository.OrderRepository;
import com.sparta.outsourcing_team_project.order.service.OrderService;
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
            @PathVariable Long storeId,
            @PathVariable Long menuId) {
        return ResponseEntity.ok(orderService.getMenuOptions(storeId, menuId));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderOptionsRequestDto requestDto) {

        return ResponseEntity.ok(orderService.createOrder(requestDto));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderStatusResponseDto> approveOrder(@PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.acceptOrder(orderId));
    }

    @PatchMapping
    public ResponseEntity<OrderStatusResponseDto> changeOrderStatus(
            @RequestParam Long storeId,
            @RequestParam Long orderId,
            @RequestParam OrderStatusEnum orderStatus) {

        return ResponseEntity.ok(orderService.changeOrderStatus(storeId, orderId, orderStatus));
    }
}
