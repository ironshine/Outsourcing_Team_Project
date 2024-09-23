package com.sparta.outsourcing_team_project.domain.home.controller;

import com.sparta.outsourcing_team_project.domain.store.dto.StoresResponseDto;
import com.sparta.outsourcing_team_project.domain.home.service.HomeService;
import com.sparta.outsourcing_team_project.domain.menu.enums.MenuCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public ResponseEntity<List<StoresResponseDto>> getStoresByCategory(@RequestParam MenuCategory category) {
        return ResponseEntity.ok(homeService.getStoresByCategory(category));
    }

    @GetMapping("/random")
    public ResponseEntity<MenuCategory> getCategoryRandom() {
        return ResponseEntity.ok(homeService.getCategoryRandom());
    }
}
