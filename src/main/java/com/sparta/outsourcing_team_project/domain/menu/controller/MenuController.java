package com.sparta.outsourcing_team_project.domain.menu.controller;

import com.sparta.outsourcing_team_project.domain.common.annotation.Auth;
import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.menu.dto.MenuRequest;
import com.sparta.outsourcing_team_project.domain.menu.dto.MenuResponse;
import com.sparta.outsourcing_team_project.domain.menu.dto.MenuUpdateRequest;
import com.sparta.outsourcing_team_project.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/{storeId}/menus")
    public ResponseEntity<MenuResponse> create(
            @Auth AuthUser authUser,
            @PathVariable long storeId,
            @RequestBody MenuRequest menuRequest) {
        return ResponseEntity.ok().body(menuService.create(authUser, storeId, menuRequest));
    }

    @PutMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<MenuResponse> update(
            @Auth AuthUser authUser,
            @PathVariable long storeId,
            @PathVariable long menuId,
            @RequestBody MenuUpdateRequest menuUpdateRequest) {
        return ResponseEntity.ok().body(menuService.update(authUser, storeId, menuId, menuUpdateRequest));
    }

    @DeleteMapping("/menus/{menuId}")
    public void delete(
            @Auth AuthUser authUser,
            @PathVariable long menuId) {
        menuService.delete(authUser, menuId);
    }
}
