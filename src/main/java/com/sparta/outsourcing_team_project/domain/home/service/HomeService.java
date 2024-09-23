package com.sparta.outsourcing_team_project.domain.home.service;

import com.sparta.outsourcing_team_project.domain.store.dto.StoresResponseDto;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import com.sparta.outsourcing_team_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_team_project.domain.menu.enums.MenuCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final StoreRepository storeRepository;

    /**
     * 카테고리로 가게 목록 가져오기
     *
     * @param category : 메뉴 카테고리 (Enum)
     * @return List<StoresResponseDto> : 가게(가게 ID, 이름, 오픈시간, 마감시간, 최소주문금액, 폐업유무(true:정상영업, false:폐업),
     * 가게생성일, 가게수정일, 유저(가게주인) ID가 담긴 DTO) 목록
     */
    public List<StoresResponseDto> getStoresByCategory(MenuCategory category) {
        List<Store> storeList = storeRepository.findAllByMenuCategory(category);
        return storeList.stream().map(StoresResponseDto::new).toList();
    }

    /**
     * 카테고리 랜덤 추천
     *
     * @return MenuCategory : 무작위 메뉴 카테고리
     */
    public MenuCategory getCategoryRandom() {
        Random rd = new Random();
        MenuCategory[] categoryList = MenuCategory.values();
        return categoryList[rd.nextInt(MenuCategory.values().length)];
    }
}
