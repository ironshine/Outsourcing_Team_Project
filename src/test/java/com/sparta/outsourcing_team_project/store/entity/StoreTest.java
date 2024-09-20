package com.sparta.outsourcing_team_project.store.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StoreTest {

    @Test
    @DisplayName("가게 엔티티 생성 테스트")
    void StoreEntityTest() {
        // given
        String storeName = "치킨집";
        String storeOpenTime = "10:00";
        String storeCloseTime = "20:00";
        String minOrderPrice = "20000원";
        Boolean storeStatus = true;

        // when
        Store store = new Store(
                storeName,
                storeOpenTime,
                storeCloseTime,
                minOrderPrice,
                storeStatus);

        // then
        assertEquals(store.getStoreName(),storeName);           // 생성된 store 의 입력된 storeName 값과 일치하는지 확인
        assertEquals(store.getStoreOpenTime(),storeOpenTime);   // 생성된 store 의 입력된 storeOpenTime 값과 일치하는지 확인
        assertEquals(store.getStoreCloseTime(),storeCloseTime); // 생성된 store 의 입력된 storeCloseTime 값과 일치하는지 확인
        assertEquals(store.getMinOrderPrice(),minOrderPrice);   // 생성된 store 의 입력된 minOrderPrice 값과 일치하는지 확인
        assertEquals(store.getStoreStatus(),storeStatus);       // 생성된 store 의 입력된 storeStatus 값과 일치하는지 확인
    }

    @Test
    @DisplayName("updateStore 메서드 테스트")
    void updateStore_메서드_테스트(){
        // given
        Store store = new Store(
                "피자집",
                "11:00",
                "21:00",
                "25000원",
                true);
        String storeName = "치킨집";
        String storeOpenTime = "10:00";
        String storeCloseTime = "20:00";
        String minOrderPrice = "20000원";

        // when
        store.updateStore(storeName, storeOpenTime, storeCloseTime, minOrderPrice);

        // then
        assertEquals(store.getStoreName(),storeName);            // 초기값 피자집 -> 치킨집 으로 수정 되었는지 확인
        assertEquals(store.getStoreOpenTime(),storeOpenTime);    // 초기값 11:00 -> 10:00 으로 수정 되었는지 확인
        assertEquals(store.getStoreCloseTime(),storeCloseTime);  // 초기값 21:00 -> 20:00 으로 수정 되었는지 확인
        assertEquals(store.getMinOrderPrice(),minOrderPrice);    // 초기값 25000원 -> 20000원 으로 수정 되었는지 확인
    }

    @Test
    @DisplayName("closedStore 메서드 테스트")
    void closedStore_메서드_테스트(){
        // given
        Store store = new Store(
                "피자집",
                "11:00",
                "21:00",
                "25000원",
                true);

        // when
        store.closedStore();

        // then
        assertEquals(store.getStoreStatus(),false);   // 초기값 true 에서 closedStore() 메서드 실행시 false 로 변경되는지 확인.
    }
}