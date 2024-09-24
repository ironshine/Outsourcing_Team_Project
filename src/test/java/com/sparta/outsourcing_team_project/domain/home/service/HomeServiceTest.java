package com.sparta.outsourcing_team_project.domain.home.service;

import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_team_project.domain.menu.enums.MenuCategory;
import com.sparta.outsourcing_team_project.domain.store.dto.StoresResponseDto;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import com.sparta.outsourcing_team_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class HomeServiceTest {
    @Mock
    private StoreRepository storeRepository;
    @InjectMocks
    private HomeService homeService;
    private User user = new User(1L, "치킨집@email.com", UserRole.OWNER);
    private List<Menu> menuList = List.of(new Menu());
    private Store store = Store.builder()
            .id(1L)
            .storeName("치킨집")
            .storeOpenTime(LocalTime.of(10, 00, 00))
            .storeCloseTime(LocalTime.of(10, 00, 00))
            .minOrderPrice(20000)
            .storeStatus(true)
            .adPrice(0L)
            .user(user)
            .menus(menuList)
            .build();
    private List<Store> storeList = List.of(store);

    @Test
    @DisplayName("카테고리로 가게 목록 가져오기 성공")
    void getStoresByCategory_성공() {
        // given
        given(storeRepository.findAllByMenuCategory(any())).willReturn(storeList);
        // when
        List<StoresResponseDto> storesResponseDtoList = homeService.getStoresByCategory(any());
        // then
        assertNotNull(storesResponseDtoList);
    }

    @Test
    @DisplayName("카테고리 랜덤 추천 성공")
    void getCategoryRandom_성공() {
        // given
        // when
        MenuCategory menuCategory = homeService.getCategoryRandom();
        // then
        assertNotNull(menuCategory);
    }
}