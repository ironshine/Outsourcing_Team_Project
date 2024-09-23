package com.sparta.outsourcing_team_project.domain.order.service;

import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.entity.OptionGroup;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.entity.Option;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.repository.OptionRepository;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.repository.OptionGroupRepository;
import com.sparta.outsourcing_team_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_team_project.domain.order.dto.*;
import com.sparta.outsourcing_team_project.domain.order.entity.CustomerOrder;
import com.sparta.outsourcing_team_project.domain.order.enums.OrderStatusEnum;
import com.sparta.outsourcing_team_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import com.sparta.outsourcing_team_project.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OptionRepository optionRepository;
    private final OptionGroupRepository optionGroupRepository;

    public List<OrderOptionsResponseDto> getMenuOptions(Long storeId, Long menuId) {

        // 유저영역 개발시 검증 로직 추가구현필요
        Store store = storeRepository.findById(storeId).orElseThrow();
        Menu menu = menuRepository.findById(menuId).orElseThrow();

        // 메뉴 옵션 그룹 추출
        List<OptionGroup> optionGroups = menu.getOptionGroups();

        // 옵션 그룹의 옵션 데이터 추출
        List<Option> options = optionGroups.stream()
                .flatMap(OptionGroup -> OptionGroup.getOptions().stream())
                .collect(Collectors.toList());

        // 추출한 데이터 dto객체에 주입
        List<OrderOptionsResponseDto> optionDtos = options.stream()
                .map(Option -> new OrderOptionsResponseDto(
                        Option.getOptionGroup().getId(),
                        Option.getOptionGroup().getOptionGroupName(),
                        Option.getId(),
                        Option.getOptionName(),
                        Option.getOptionPrice()))
                .collect(Collectors.toList());

        return optionDtos;
    }

    public OrderResponseDto createOrder(OrderOptionsRequestDto requestDto) {
        // 유저영역 개발시 검증 로직 추가구현필요
        Store store = storeRepository.findById(requestDto.getStoreId()).orElseThrow();
        Menu menu = menuRepository.findById(requestDto.getMenuId()).orElseThrow();
        List<Option> options = optionRepository.findByIdIn(requestDto.getOptionIds());
        if(options.isEmpty()){
            // 예외문 커스텀필요
        }

        // 주문 총 가격
        Integer totalPrice = 0;
        for(Option option : options){
            totalPrice += option.getOptionPrice();
        }
        totalPrice += menu.getPrice();


        // 주문 요청 저장
        CustomerOrder order = orderRepository.save(new CustomerOrder(
                totalPrice,
                requestDto.getAddress(),
                OrderStatusEnum.PENDING));

        // 옵션 객체에서 필요한 정보추출후 DTO에 주입
        List<OrderOptionInfoDto> optionInfoDtos = options.stream()
                .map(Options -> new OrderOptionInfoDto(
                        Options.getOptionGroup().getOptionGroupName(),
                        Options.getOptionName(),
                        Options.getOptionPrice()))
                .collect(Collectors.toList());

        OrderResponseDto responseDto = OrderResponseDto
                .builder()
                .orderId(order.getId())
                .userName("임시")
                .storeName(store.getStoreName())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .menuOptions(optionInfoDtos)
                .totalPrice(totalPrice)
                .address(requestDto.getAddress())
                .orderStatus(OrderStatusEnum.PENDING)
                .orderedAt(order.getCreatedAt())
                .build();

        return responseDto;
    }

    @Transactional
    public OrderStatusResponseDto acceptOrder(Long orderId) {
        // 주문데이터 조회
        CustomerOrder order = orderRepository.findById(orderId).orElseThrow();

        // 주문상태 업데이트
        CustomerOrder updateStatus = order.changeStatus(OrderStatusEnum.PREPARING);

        // 필요데이터 추출후 DTO 주입
        OrderStatusResponseDto response = OrderStatusResponseDto
                .builder()
                .orderId(updateStatus.getId())
                .orderStatus(OrderStatusEnum.PREPARING)
                .address(updateStatus.getAddress())
                .changedAt(updateStatus.getModifiedAt())
                .build();

        return response;
    }

    public OrderStatusResponseDto changeOrderStatus(Long storeId, Long orderId, OrderStatusEnum orderStatus) {
        // 가게 데이터 조회
        Store store = storeRepository.findById(storeId).orElseThrow();

        // 주문데이터 조회
        CustomerOrder order = orderRepository.findById(orderId).orElseThrow();

        // 주문상태 업데이트
        CustomerOrder updateStatus = order.changeStatus(orderStatus);

        // 필요데이터 추출후 DTO 주입
        OrderStatusResponseDto response = OrderStatusResponseDto
                .builder()
                .orderId(updateStatus.getId())
                .orderStatus(orderStatus)
                .address(updateStatus.getAddress())
                .changedAt(updateStatus.getModifiedAt())
                .build();

        return response;
    }
}
