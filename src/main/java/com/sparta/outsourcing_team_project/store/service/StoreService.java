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
    public StoreResponseDto addStores(StoreRequestDto requestDto) { //, AuthUser authUser) {
        // requestDto 값의 Store 생성 (store 값은 생성시 항상 true)
        Store store = new Store(
                requestDto.getStoreName(),
                requestDto.getStoreOpenTime(),
                requestDto.getStoreCloseTime(),
                requestDto.getMinOrderPrice(),
                true);
//                , authUser.getUserId());

        // store 저장
        storeRepository.save(store);

        // store ResponseDto 로 변경해서 반환
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
        // DTO List 생성
        List<StoresResponseDto> dtoList = new ArrayList<>();

        // 입력받은 가게 이름과 동일한 storeStatus 가 true 인 Store List 찾아와서 storeList 에 담기
        List<Store> storeList = storeRepository.findAllByStoreNameAndStoreStatus(store_name, true);

        for (Store store : storeList) {
            // Store -> ResponseDto 변경
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
            // 변경된 ResponseDto DTO List 에 추가
            dtoList.add(dto);
        }
        // 전부 추가 받은 DTO List 반환
        return dtoList;
    }

    public StoreResponseDto getStore(Long storeId) {
        // 입력받은 가게 id로 가게 찾아오기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
        // 찾아온 가게 DTO 로 변경해서 반환
        return new StoreResponseDto(
                store.getId(),
                store.getStoreName(),
                store.getStoreCloseTime(),
                store.getStoreOpenTime(),
                store.getMinOrderPrice(),
                store.getStoreStatus(),
                store.getCreateAt(),
                store.getUpdatedAt());
    }

    @Transactional
    public StoreResponseDto updateStore(Long storeId, StoreRequestDto requestDto) {
        // 입력받은 가게 id로 가게 찾아오기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
        // 가게 정보 수정
        store.updateStore(
                requestDto.getStoreName(),
                requestDto.getStoreOpenTime(),
                requestDto.getStoreCloseTime(),
                store.getMinOrderPrice());
        // 수정된 가게 정보 반환
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

    @Transactional
    public String closedStore(Long storeId) {
        // 입력받은 가게 id로 가게 찾아오기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NullPointerException("해당 id의 가게가 없습니다."));
        // storeStatus false 변경 메서드
        store.closedStore();

        return "폐업신고 완료";
    }
}
