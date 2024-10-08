package com.sparta.outsourcing_team_project.domain.store.controller;

import com.sparta.outsourcing_team_project.domain.common.annotation.Auth;
import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.store.dto.*;
import com.sparta.outsourcing_team_project.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<StoreSaveResponseDto> addStores(
            @RequestBody StoreRequestDto requestDto,
            @Auth AuthUser authUser) throws Exception {
        return ResponseEntity.ok(storeService.addStores(requestDto, authUser));
    }

    @GetMapping
    public ResponseEntity<List<StoresResponseDto>> getStores(
            @RequestParam String store_name) {
        return ResponseEntity.ok(storeService.getStores(store_name));
    }

    @GetMapping("/{store_id}")
    public ResponseEntity<StoreResponseDto> getStore(
            @PathVariable long store_id) throws Exception {
        return ResponseEntity.ok(storeService.getStore(store_id));
    }

    @PutMapping("/{store_id}")
    public ResponseEntity<StoreResponseDto> updateStore(
            @PathVariable long store_id,
            @RequestBody StoreRequestDto requestDto,
            @Auth AuthUser authUser) throws Exception {
        return ResponseEntity.ok(storeService.updateStore(store_id, requestDto, authUser));
    }

    @DeleteMapping("/{store_id}/closed")
    public ResponseEntity<String> closedStore(
            @PathVariable long store_id,
            @Auth AuthUser authUser) throws Exception {
        return ResponseEntity.ok(storeService.closedStore(store_id, authUser));
    }

    @PatchMapping("/{store_id}/ads")
    public ResponseEntity<AdsResponseDto> addAdPrice(
            @PathVariable long store_id,
            @RequestParam long ad_price) {
        return ResponseEntity.ok(storeService.addAdPrice(store_id, ad_price));
    }
}
