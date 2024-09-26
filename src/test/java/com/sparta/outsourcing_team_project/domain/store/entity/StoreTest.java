package com.sparta.outsourcing_team_project.domain.store.entity;

import com.sparta.outsourcing_team_project.domain.store.dto.StoreRequestDto;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StoreTest {

    @Test
    @DisplayName("가게 엔티티 생성 테스트")
    void StoreEntityTest() {
        // given
        String storeName = "치킨집";
        LocalTime storeOpenTime = LocalTime.of(10, 00, 00);
        LocalTime storeCloseTime = LocalTime.of(21, 00, 00);
        int minOrderPrice = 20000;
        Boolean storeStatus = true;
        User user = new User();
        Long adPrice = 0L;

        // when
        Store store = Store.builder()
                .storeName(storeName)
                .storeOpenTime(storeOpenTime)
                .storeCloseTime(storeCloseTime)
                .minOrderPrice(minOrderPrice)
                .storeStatus(storeStatus)
                .user(user)
                .adPrice(adPrice)
                .build();

        // then
        assertEquals(store.getStoreName(), storeName);           // 생성된 store 의 저장된 값이 입력된 storeName 값과 일치하는지 확인
        assertEquals(store.getStoreOpenTime(), storeOpenTime);   // 생성된 store 의 저장된 값이 입력된 storeOpenTime 값과 일치하는지 확인
        assertEquals(store.getStoreCloseTime(), storeCloseTime); // 생성된 store 의 저장된 값이 입력된 storeCloseTime 값과 일치하는지 확인
        assertEquals(store.getMinOrderPrice(), minOrderPrice);   // 생성된 store 의 저장된 값이 입력된 minOrderPrice 값과 일치하는지 확인
        assertEquals(store.getStoreStatus(), storeStatus);       // 생성된 store 의 저장된 값이 입력된 storeStatus 값과 일치하는지 확인
        assertEquals(store.getUser(), user);                     // 생성된 store 의 저장된 값이 입력된 user 값과 일치하는지 확인
        assertEquals(store.getAdPrice(), adPrice);               // 생성된 store 의 저장된 값이 입력된 adPrice 값과 일치하는지 확인
    }

    @Test
    @DisplayName("가게 수정 메서드 테스트")
    void updateStore_메서드_테스트() {
        // given
        Store store = Store.builder()
                .storeName("피자집")
                .storeOpenTime(LocalTime.of(9, 00, 00))
                .storeCloseTime(LocalTime.of(21, 00, 00))
                .minOrderPrice(20000)
                .storeStatus(true)
                .user(new User())
                .adPrice(0L)
                .build();
        String storeName = "치킨집";
        LocalTime storeOpenTime = LocalTime.of(10, 00, 00);
        LocalTime storeCloseTime = LocalTime.of(21, 00, 00);
        int minOrderPrice = 20000;

        // when
        StoreRequestDto storeRequestDto = StoreRequestDto.builder()
                .storeName(storeName)
                .storeOpenTime(storeOpenTime)
                .storeCloseTime(storeCloseTime)
                .minOrderPrice(minOrderPrice)
                .build();
        store.updateStore(storeRequestDto);

        // then
        assertEquals(store.getStoreName(), storeName);            // 초기값 피자집 -> 치킨집 으로 수정 되었는지 확인
        assertEquals(store.getStoreOpenTime(), storeOpenTime);    // 초기값 11:00 -> 10:00 으로 수정 되었는지 확인
        assertEquals(store.getStoreCloseTime(), storeCloseTime);  // 초기값 21:00 -> 20:00 으로 수정 되었는지 확인
        assertEquals(store.getMinOrderPrice(), minOrderPrice);    // 초기값 25000원 -> 20000원 으로 수정 되었는지 확인
    }

    @Test
    @DisplayName("가게 상태 변경 메서드 테스트")
    void closedStore_메서드_테스트() {
        // given
        Store store = Store.builder()
                .storeName("피자집")
                .storeOpenTime(LocalTime.of(11, 00, 00))
                .storeCloseTime(LocalTime.of(21, 00, 00))
                .minOrderPrice(25000)
                .storeStatus(true)
                .user(new User())
                .adPrice(0L)
                .build();

        // when
        store.closedStore();

        // then
        assertEquals(store.getStoreStatus(), false);   // 초기값 true 에서 closedStore() 메서드 실행시 false 로 변경되는지 확인.
    }

    @Test
    @DisplayName("광고비 추가 메서드 테스트")
    void addAdPrice_메서드_테스트() {
        // given
        Store store = Store.builder()
                .storeName("피자집")
                .storeOpenTime(LocalTime.of(11, 00, 00))
                .storeCloseTime(LocalTime.of(21, 00, 00))
                .minOrderPrice(25000)
                .storeStatus(true)
                .user(new User())
                .adPrice(0L)
                .build();
        // when
        store.addAdPrice(100000L);

        // then
        assertEquals(store.getAdPrice(), 100000L);
    }
}