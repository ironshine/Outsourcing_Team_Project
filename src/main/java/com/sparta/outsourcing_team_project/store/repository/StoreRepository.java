package com.sparta.outsourcing_team_project.store.repository;

import com.sparta.outsourcing_team_project.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    // 가게 이름으로 가게 리스트 찾아오기(폐업가게 제외)
    List<Store> findAllByStoreNameAndStoreStatus(String storeName, Boolean storeStatus);
}
