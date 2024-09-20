package com.sparta.outsourcing_team_project.store.service;

import com.sparta.outsourcing_team_project.store.dto.StoreRequestDto;
import com.sparta.outsourcing_team_project.store.dto.StoreResponseDto;
import com.sparta.outsourcing_team_project.store.dto.StoresResponseDto;
import com.sparta.outsourcing_team_project.store.entity.Store;
import com.sparta.outsourcing_team_project.store.repository.StoreRepository;
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

    @Transactional
    public StoreResponseDto addStores(StoreRequestDto requestDto){ //, AuthUser authUser) {
        Store store = new Store(
                requestDto.getStoreName(),
                requestDto.getStoreOpenTime(),
                requestDto.getStoreCloseTime(),
                requestDto.getMinOrderPrice(),
                true);
//                , authUser.getUserId());

        storeRepository.save(store);

        return new StoreResponseDto(
                store.getId(),
                store.getStoreName(),
                store.getStoreOpenTime(),
                store.getStoreCloseTime(),
                store.getMinOrderPrice(),
                store.getStoreStatus(),
                store.getCreateAt(),
                store.getUpdatedAt());
    }

    public List<StoresResponseDto> getStores(String store_name) {
        List<StoresResponseDto> dtoList = new ArrayList<>();
        List<Store> storeList = storeRepository.findallByStoreName(store_name);
        for (Store store : storeList) {
            StoresResponseDto dto = new StoresResponseDto(
                    store.getId(),
                    store.getStoreName(),
                    store.getStoreOpenTime(),
                    store.getStoreCloseTime(),
                    store.getMinOrderPrice(),
                    store.getStoreStatus(),
                    store.getCreateAt(),
                    store.getUpdatedAt()
                    );
            dtoList.add(dto);
        }
        return dtoList;
    }

    public StoreResponseDto getStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
        return new StoreResponseDto(
                store.getId(),
                store.getStoreName(),
                store.getStoreCloseTime(),
                store.getStoreOpenTime(),
                store.getStoreName(),
                store.getStoreStatus(),
                store.getCreateAt(),
                store.getUpdatedAt());
    }

    @Transactional
    public StoreResponseDto updateStore(Long storeId, StoreRequestDto requestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
        store.updateStore(requestDto);
        return new StoreResponseDto(
                store.getId(),
                store.getStoreName(),
                store.getStoreOpenTime(),
                store.getStoreCloseTime(),
                store.getMinOrderPrice(),
                store.getStoreStatus(),
                store.getCreateAt(),
                store.getUpdatedAt());
    }

    public String closedStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
        store.closedStore();
        return "폐업신고 완료";
    }
}
