package com.sparta.outsourcing_team_project.orders.controller;

import com.sparta.outsourcing_team_project.orders.dto.RequestDto;
import com.sparta.outsourcing_team_project.orders.dto.ResponseDto;
import com.sparta.outsourcing_team_project.orders.enums.OrderStatusEnum;
import com.sparta.outsourcing_team_project.orders.repository.OrderRepository;
import com.sparta.outsourcing_team_project.orders.service.OrderService;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @GetMapping("/orderId/{orderId}/storeId/{storeId}/userId/{userId}/menuId/{menuId}")
    public ResponseEntity<?> getMenuOptions(
            @PathVariable Long orderId,
            @PathVariable Long storeId,
            @PathVariable Long userId,
            @PathVariable Long menuId) {
        return ResponseEntity.ok(orderService.getMenuOptions(orderId, storeId, userId, menuId));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody RequestDto requestDto) {

        return ResponseEntity.ok(orderService.createOrder(requestDto));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<?> approveOrder(@PathVariable Long orderId) {


        return ResponseEntity.ok(orderService.acceptOrder(orderId));
    }

    @PatchMapping
    public ResponseEntity<?> changeOrderStatus(
            @RequestParam Long storeId,
            @RequestParam Long orderId,
            @RequestParam OrderStatusEnum orderStatus) {

        return ResponseEntity.ok(orderService.changeOrderStatus(storeId, orderId, orderStatus));
    }
}
