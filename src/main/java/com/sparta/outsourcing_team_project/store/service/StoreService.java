package com.sparta.outsourcing_team_project.store.service;

import com.sparta.outsourcing_team_project.store.dto.StoreRequestDto;
import com.sparta.outsourcing_team_project.store.dto.StoreResponseDto;
import com.sparta.outsourcing_team_project.store.dto.StoresResponseDto;
import com.sparta.outsourcing_team_project.store.entity.Store;
import com.sparta.outsourcing_team_project.store.repository.StoreRepository;
//import com.sparta.outsourcing_team_project.user.entity.AuthUser;
//import com.sparta.outsourcing_team_project.user.entity.UserEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    /**
     *
     * @param requestDto
//     * @param authUser
     * @return
     * @throws Exception
     */
    @Transactional
    public StoreResponseDto addStores(StoreRequestDto requestDto){ //, AuthUser authUser) throws Exception {
//        if (authUser.getUser().getAuth() != UserEnum.OWNER) {
//            throw new IllegalAccessException("사장님 권한이 아닙니다");
//        }
//        if (storeRepository.countByUserAndStoreStatus(authUser.getUser(), true) >= 3) {
//            throw new IllegalAccessException("가게는 3개 제한입니다");
//        }
        Store store = new Store(
                requestDto.getStoreName(),
                requestDto.getStoreOpenTime(),
                requestDto.getStoreCloseTime(),
                requestDto.getMinOrderPrice(),
                true);
//                ,
//                authUser.getUser());

        storeRepository.save(store);

        return new StoreResponseDto(
                store.getId(),
                store.getStoreName(),
                store.getStoreOpenTime(),
                store.getStoreCloseTime(),
                store.getMinOrderPrice(),
                store.getStoreStatus(),
                store.getCreatedAt(),
                store.getModifiedAt());
    }

    /**
     *
     * @param store_name
     * @return
     */
    public List<StoresResponseDto> getStores(String store_name) {

        List<StoresResponseDto> dtoList = new ArrayList<>();

        List<Store> storeList = storeRepository.findAllByStoreNameAndStoreStatus(store_name, true);

        for (Store store : storeList) {
            StoresResponseDto dto = new StoresResponseDto(
                    store.getId(),
                    store.getStoreName(),
                    store.getStoreOpenTime(),
                    store.getStoreCloseTime(),
                    store.getMinOrderPrice(),
                    store.getStoreStatus(),
                    store.getCreatedAt(),
                    store.getModifiedAt()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     *
     * @param storeId
     * @return
     * @throws Exception
     */
    public StoreResponseDto getStore(Long storeId) throws Exception{
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
        if (!store.getStoreStatus()) {
            throw new IllegalAccessException("폐업한 가게입니다.");
        }
        return new StoreResponseDto(
                store.getId(),
                store.getStoreName(),
                store.getStoreCloseTime(),
                store.getStoreOpenTime(),
                store.getMinOrderPrice(),
                store.getStoreStatus(),
                store.getCreatedAt(),
                store.getModifiedAt());
    }

    /**
     *
     * @param storeId
     * @param requestDto
//     * @param authUser
     * @return
     * @throws Exception
     */
    @Transactional
    public StoreResponseDto updateStore(Long storeId, StoreRequestDto requestDto){ //, AuthUser authUser) throws Exception {
//        if (authUser.getUser().getAuth() != UserEnum.OWNER) {
//            throw new IllegalAccessException("사장님 권한이 아닙니다");
//        }

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
//        if (!store.getUser().getId().equals(authUser.getUser().getId())) {
//            throw new IllegalArgumentException("본인 가게만 수정 할 수 있습니다.");
//        }
//        if (!store.getStoreStatus()) {
//            throw new IllegalAccessException("폐업한 가게입니다.");
//        }
        store.updateStore(
                requestDto.getStoreName(),
                requestDto.getStoreOpenTime(),
                requestDto.getStoreCloseTime(),
                store.getMinOrderPrice());

        return new StoreResponseDto(
                store.getId(),
                store.getStoreName(),
                store.getStoreOpenTime(),
                store.getStoreCloseTime(),
                store.getMinOrderPrice(),
                store.getStoreStatus(),
                store.getCreatedAt(),
                store.getModifiedAt());
    }

    /**
     *
     * @param storeId
//     * @param authUser
     * @return
     * @throws Exception
     */
    @Transactional
    public String closedStore(Long storeId){ //, AuthUser authUser) throws Exception {
//        if (authUser.getUser().getAuth() != UserEnum.OWNER) {
//            throw new IllegalAccessException("사장님 권한이 아닙니다");
//        }
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
//        if (!store.getUser().getId().equals(authUser.getUser().getId())) {
//            throw new IllegalArgumentException("본인 가게만 삭제 할 수 있습니다.");
//        }
//        if (!store.getStoreStatus()) {
//            throw new IllegalAccessException("폐업한 가게입니다.");
//        }
        store.closedStore();

        return "폐업신고 완료";
    }
}
