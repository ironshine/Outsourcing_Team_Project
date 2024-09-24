package com.sparta.outsourcing_team_project.domain.store.service;

import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_team_project.domain.store.dto.*;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import com.sparta.outsourcing_team_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;
import com.sparta.outsourcing_team_project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private StoreService storeService;
    private AuthUser authUser = new AuthUser(1L, "치킨집@email.com", UserRole.OWNER);
    private User user = new User(1L, "치킨집@email.com", UserRole.OWNER);
    private List<Menu> menuList = List.of(new Menu());
    private StoreRequestDto storeRequestDto = StoreRequestDto.builder()
            .storeName("백반집")
            .storeOpenTime(LocalTime.of(1, 00, 00))
            .storeCloseTime(LocalTime.of(21, 00, 00))
            .minOrderPrice(20)
            .build();
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

    @Test
    @DisplayName("가게 생성 DB에 저장 성공")
    void addStores_DB에_저장_성공() throws Exception {
        // given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(storeRepository.save(any())).willReturn(store);
        // when
        StoreSaveResponseDto responseDto = storeService.addStores(storeRequestDto, authUser);
        // then
        assertNotNull(responseDto);
    }

    @Test
    @DisplayName("가게 생성 없는 유저값 에러 발생")
    void addStores_없는_유저값_에러_발생() throws Exception {
        // given
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> storeService.addStores(storeRequestDto, authUser));
        // then
        assertEquals("없는 유저 ID 입니다", exception.getMessage());
    }

    @Test
    @DisplayName("가게 생성 권한 에러 발생")
    void addStores_권한_에러_발생() {
        // given
        AuthUser authUser1 = new AuthUser(1L, "치킨집@mail.com", UserRole.USER);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        // when
        IllegalAccessException exception = assertThrows(IllegalAccessException.class,
                () -> storeService.addStores(storeRequestDto, authUser1));
        // then
        assertEquals("사장님 권한이 아닙니다", exception.getMessage());
    }

    @Test
    @DisplayName("가게 생성 3개 초과 생성시 에러 발생")
    void addStores_3개_초과_생성시_에러_발생() {
        // given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(storeRepository.countByUserAndStoreStatus(any(), any())).willReturn(4L);
        // when
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> storeService.addStores(storeRequestDto, authUser));
        // then
        assertEquals("가게는 3개 제한입니다", exception.getMessage());
    }

    @Test
    @DisplayName("가게 이름 다건 조회 성공")
    void getStores_조회_성공() {
        // given
        User user1 = new User(1L, "치킨@mail.com", UserRole.OWNER);
        Store store1 = Store.builder()
                .id(1L)
                .storeName("치킨집")
                .storeStatus(true)
                .minOrderPrice(20000)
                .user(user1)
                .build();
        List<Store> storeList = List.of(store1);
        given(storeRepository.findAllByStoreNameContainingAndStoreStatusOrderByAdPriceDesc(any(), any())).willReturn(storeList);
        // when
        List<StoresResponseDto> responseDtoList = storeService.getStores(store.getStoreName());
        // then
        assertEquals(1, responseDtoList.size());
        assertEquals(store1.getId(), responseDtoList.get(0).getId());
        assertEquals(store1.getStoreName(), responseDtoList.get(0).getStoreName());
    }

    @Test
    @DisplayName("가게 단건 조회 성공")
    void getStore_조회_성공() throws Exception {
        // given
        Long store_id = 1L;

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        // when
        StoreResponseDto storeResponseDto = storeService.getStore(store_id);
        // then
        assertEquals(storeResponseDto.getId(), store_id);
    }

    @Test
    @DisplayName("폐업 가게 조회 에러 발생")
    void getStore_폐업_가게_조회_에러_발생() throws Exception {
        // given
        ReflectionTestUtils.setField(store, "storeStatus", false);

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        // when
        IllegalAccessException exception = assertThrows(IllegalAccessException.class,
                () -> storeService.getStore(anyLong()));
        // then
        assertEquals("폐업한 가게입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("가게 수정 성공")
    void updateStore_성공() throws Exception {
        // given
        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));

        // when
        StoreResponseDto storeResponseDto = storeService.updateStore(anyLong(), storeRequestDto, authUser);

        // then
        assertEquals(storeResponseDto.getStoreName(), storeRequestDto.getStoreName());
        assertEquals(storeResponseDto.getMinOrderPrice(), storeRequestDto.getMinOrderPrice());
    }

    @Test
    @DisplayName("가게 수정 본인 가게 아닐시 에러")
    void updateStore_본인_가게_아닐시_에러() throws Exception {
        // given
        AuthUser authUser1 = new AuthUser(3L, "a@mail.com", UserRole.OWNER);
        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> storeService.updateStore(anyLong(), storeRequestDto, authUser1));

        // then
        assertEquals("본인 가게가 아닙니다", exception.getMessage());
    }

    @Test
    @DisplayName("가게 수정 폐업 가게 에러")
    void updateStore_폐업_가게_에러() throws Exception {
        // given
        ReflectionTestUtils.setField(store, "storeStatus", false);

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));

        // when
        IllegalAccessException exception = assertThrows(IllegalAccessException.class,
                () -> storeService.updateStore(anyLong(), storeRequestDto, authUser));

        // then
        assertEquals("폐업한 가게입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("가게 폐업 성공")
    void closedStore_성공() throws Exception {
        // given
        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        // when
        String closedStore = storeService.closedStore(anyLong(), authUser);
        // then
        assertEquals("폐업신고 완료", closedStore);
        assertEquals(false, store.getStoreStatus());
    }

    @Test
    @DisplayName("광고비용 추가 성공")
    void addAdPrice_성공() {
        // given
        Long adPrice = 200000L;
        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        // when
        AdsResponseDto adsResponseDto = storeService.addAdPrice(anyLong(), adPrice);
        // then
        assertEquals(store.getAdPrice(), adsResponseDto.getAdPrice());
        assertEquals(store.getAdPrice(), adPrice);
    }

    @Test
    @DisplayName("광고비용 추가 음수 에러")
    void addAdPrice_음수_에러() {
        // given
        Long adPrice = -200L;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> storeService.addAdPrice(1L, adPrice));

        // then
        assertEquals("음수는 입력 못해요", exception.getMessage());
    }
}