package com.sparta.outsourcing_team_project.store.repository;

import com.sparta.outsourcing_team_project.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAllByStoreNameAndStoreStatus(String storeName, Boolean storeStatus);
}
