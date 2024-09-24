package com.sparta.outsourcing_team_project.domain.order.service;

import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.common.exception.InvalidRequestException;
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
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;
import com.sparta.outsourcing_team_project.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService{

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OptionRepository optionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final UserRepository userRepository;

    public List<OrderOptionsResponseDto> getMenuOptions(Long storeId, Long menuId, AuthUser authUser) {

        // 대이터 조회
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new InvalidRequestException("유효하지 않는 가게입니다.")
        );
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new InvalidRequestException("유효하지 않는 메뉴입니다.")
        );

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

    public OrderResponseDto createOrder(OrderOptionsRequestDto requestDto, AuthUser authUser) throws AccessDeniedException {
        if(authUser.getUserRole() == UserRole.OWNER){
            throw new AccessDeniedException("오너 계정은 주문 요청할 수 없습니다");
        }

        // 대이터 조회
        Store store = storeRepository.findById(requestDto.getStoreId()).orElseThrow(
                () -> new InvalidRequestException("유효하지 않는 가게입니다.")
        );
        Menu menu = menuRepository.findById(requestDto.getMenuId()).orElseThrow(
                () -> new InvalidRequestException("유효하지 않는 메뉴입니다.")
        );
        User user = userRepository.findById(authUser.getUserId()).orElseThrow(
                () -> new InvalidRequestException("유효하지 않는 계정입니다.")
        );
        List<Option> options = optionRepository.findByIdIn(requestDto.getOptionIds());

        // 가게 영업시간 검증로직
        LocalTime currentTime = LocalTime.now();
        LocalTime openTime = store.getStoreOpenTime();
        LocalTime closeTime = store.getStoreCloseTime();
        if(currentTime.isBefore(openTime) || currentTime.isAfter(closeTime)){
            throw new IllegalArgumentException("가게 오픈 시간이 아닙니다.");
        }

        // 주문 총 가격
        Integer totalPrice = 0;

        for(Option option : options){
            totalPrice += option.getOptionPrice();
        }
        totalPrice += menu.getPrice();

        // 최소 주문금액 검증로직
        Integer minPrice = store.getMinOrderPrice();

        if(totalPrice <= minPrice){
            throw new IllegalArgumentException("최소주문 금액은 " + minPrice + "원 입니다.");
        }

        // 주문 요청 저장
        CustomerOrder order = orderRepository.save(new CustomerOrder(
                totalPrice,
                requestDto.getAddress(),
                OrderStatusEnum.PENDING,
                store.getUser(),
                store,
                menu)
        );

        // AOP 데이터 주입
        CustomerOrder savedOrder = orderRepository.findById(order.getId()).orElseThrow(null);


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
                .userName(user.getUserName())
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
    public OrderStatusResponseDto acceptOrder(Long orderId, AuthUser authUser) throws AccessDeniedException {

        // 주문데이터 조회
        CustomerOrder order = orderRepository.findById(orderId).orElseThrow(
                () -> new InvalidRequestException("유효하지 않는 주문입니다.")
        );

        // 유저 인가 로직
        if(order.getStore().getUser().getUserId() != authUser.getUserId()){
            throw new AccessDeniedException("가게 오너 계정만 접근 가능합니다.");
        }

        if(order.getOrderStatus() == OrderStatusEnum.PREPARING){
            throw new DuplicateKeyException("이미 수락된 주문입니다.");
        }

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

    @Transactional
    public OrderStatusResponseDto changeOrderStatus(Long storeId, Long orderId, OrderStatusEnum orderStatus, AuthUser authUser) throws AccessDeniedException {
        // 가게 데이터 조회
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new InvalidRequestException("유효하지 않는 가게입니다.")
        );

        // 유저 인가 로직
        if(store.getUser().getUserId() != authUser.getUserId()){
            new AccessDeniedException("가게 오너 계정만 접근 가능합니다.");
        }

        // 주문 데이터 조회
        CustomerOrder order = orderRepository.findById(orderId).orElseThrow(
                () -> new InvalidRequestException("유효하지 않는 주문입니다.")
        );

        if(order.getOrderStatus() == OrderStatusEnum.ORDER_CANCELLED){
            throw new DuplicateKeyException("이미 취소된 주문은 변경할 수 없습니다.");
        }
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
