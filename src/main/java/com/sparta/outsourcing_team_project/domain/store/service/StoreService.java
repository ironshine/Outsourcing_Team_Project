package com.sparta.outsourcing_team_project.domain.store.service;

import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.store.dto.*;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import com.sparta.outsourcing_team_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;
import com.sparta.outsourcing_team_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    /**
     * 가게 생성
     *
     * @param storeRequestDto : 가게 이름, 오픈시간, 마감시간, 최소주문금액이 담긴 DTO
     * @param authUser        : JWT 토큰에 담겨있는 user 값
     * @return storeResponseDto : 가게 ID, 이름, 오픈시간, 마감시간, 최소주문금액, 폐업유무(true:정상영업, false:폐업),
     *                              가게생성일, 가게수정일, 유저(가게주인) ID, 가게메뉴(메뉴이름, 가격 담긴 DTO)목록이 담긴 DTO
     * @throws Exception
     */
    @Transactional
    public StoreSaveResponseDto addStores(StoreRequestDto storeRequestDto, AuthUser authUser) throws Exception {
        User user = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new NullPointerException("없는 유저 ID 입니다"));
        if (authUser.getUserRole() != UserRole.OWNER) {
            throw new IllegalAccessException("사장님 권한이 아닙니다");
        }
        if (storeRepository.countByUserAndStoreStatus(user, true) >= 3) {
            throw new IllegalAccessException("가게는 3개 제한입니다");
        }
        Store store = Store.builder()
                .storeName(storeRequestDto.getStoreName())
                .storeOpenTime(storeRequestDto.getStoreOpenTime())
                .storeCloseTime(storeRequestDto.getStoreCloseTime())
                .minOrderPrice(storeRequestDto.getMinOrderPrice())
                .storeStatus(true)
                .user(user)
                .adPrice(0L)
                .build();

        storeRepository.save(store);

        return new StoreSaveResponseDto(store);
    }

    /**
     * 가게 다건 조회 (가게 이름으로 검색)
     *
     * @param store_name : 가게 이름
     * @return dtoList   : 가게(가게 ID, 이름, 오픈시간, 마감시간, 최소주문금액, 폐업유무(true:정상영업, false:폐업),
     *                      가게생성일, 가게수정일, 유저(가게주인) ID가 담긴 DTO) 목록
     */
    public List<StoresResponseDto> getStores(String store_name) {
        List<Store> storeList = storeRepository.findAllByStoreNameAndStoreStatusOrderByAdPriceDesc(store_name, true);

        return storeList.stream().map(StoresResponseDto::new).toList();
    }

    /**
     * 가게 단건 조회
     *
     * @param storeId : 가게 ID
     * @return StoreResponseDto : 가게 ID, 이름, 오픈시간, 마감시간, 최소주문금액, 폐업유무(true:정상영업, false:폐업),
     *                              가게생성일, 가게수정일, 유저(가게주인) ID, 가게메뉴(메뉴이름, 가격 담긴 DTO)목록이 담긴 DTO
     * @throws Exception
     */
    public StoreResponseDto getStore(Long storeId) throws Exception {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
        if (!store.getStoreStatus()) {
            throw new IllegalAccessException("폐업한 가게입니다.");
        }
        return new StoreResponseDto(store);
    }

    /**
     * 가게 수정
     *
     * @param storeId    : 가게 ID
     * @param requestDto : 가게 이름, 오픈시간, 마감시간, 최소주문금액이 담긴 DTO
     * @param authUser   : JWT 토큰에 담겨있는 user 값
     * @return StoreResponseDto : 가게 ID, 이름, 오픈시간, 마감시간, 최소주문금액, 폐업유무(true:정상영업, false:폐업),
     *                              가게생성일, 가게수정일, 유저(가게주인) ID, 가게메뉴(메뉴이름, 가격 담긴 DTO)목록이 담긴 DTO
     * @throws Exception
     */
    @Transactional
    public StoreResponseDto updateStore(Long storeId, StoreRequestDto requestDto, AuthUser authUser) throws Exception {
        if (authUser.getUserRole() != UserRole.OWNER) {
            throw new IllegalAccessException("사장님 권한이 아닙니다");
        }

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
        if (!store.getUser().getUserId().equals(authUser.getUserId())) {
            throw new IllegalArgumentException("본인 가게만 수정 할 수 있습니다.");
        }
        if (!store.getStoreStatus()) {
            throw new IllegalAccessException("폐업한 가게입니다.");
        }
        store.updateStore(requestDto);

        return new StoreResponseDto(store);
    }

    /**
     * 가게 폐업
     *
     * @param storeId  : 가게 ID
     * @param authUser : JWT 토큰에 담겨있는 user 값
     * @return "폐업신고 완료"
     * @throws Exception
     */
    @Transactional
    public String closedStore(Long storeId, AuthUser authUser) throws Exception {
        if (authUser.getUserRole() != UserRole.OWNER) {
            throw new IllegalAccessException("사장님 권한이 아닙니다");
        }
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
        if (!store.getUser().getUserId().equals(authUser.getUserId())) {
            throw new IllegalArgumentException("본인 가게만 삭제 할 수 있습니다.");
        }
        if (!store.getStoreStatus()) {
            throw new IllegalAccessException("폐업한 가게입니다.");
        }
        store.closedStore();

        return "폐업신고 완료";
    }

    /**
     * 가게 광고비 추가
     *
     * @param storeId : 가게 ID
     * @param adPrice : 추가할 광고 비용
     * @return AdsResponseDto : 누적 광고비용이 담긴 DTO
     */
    @Transactional
    public AdsResponseDto addAdPrice(Long storeId, Long adPrice) {
        if (adPrice < 0) {
            throw new IllegalArgumentException("음수는 입력 못해요");
        }
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
        store.addAdPrice(adPrice);
        return new AdsResponseDto(store.getAdPrice());
    }
}
