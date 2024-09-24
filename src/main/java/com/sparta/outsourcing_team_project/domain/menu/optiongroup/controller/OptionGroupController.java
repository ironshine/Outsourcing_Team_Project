package com.sparta.outsourcing_team_project.domain.menu.optiongroup.controller;

import com.sparta.outsourcing_team_project.domain.common.annotation.Auth;
import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.dto.OptionGroupRequest;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.dto.OptionGroupResponse;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.service.OptionGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/menus")
public class OptionGroupController {

    private final OptionGroupService optionGroupService;

    @PostMapping("/{menuId}")
    public ResponseEntity<OptionGroupResponse> create(
            @Auth AuthUser authUser,
            @PathVariable long menuId,
            @RequestBody OptionGroupRequest optionGroupRequest) {
        return ResponseEntity.ok().body(optionGroupService.create(authUser, menuId, optionGroupRequest));
    }

    @PutMapping("/{menuId}/option-groups/{optionGroupId}")
    public ResponseEntity<OptionGroupResponse> update(
            @Auth AuthUser authUser,
            @PathVariable long menuId,
            @PathVariable long optionGroupId,
            @RequestBody OptionGroupRequest optionGroupRequest) {
        return ResponseEntity.ok().body(optionGroupService.update(authUser, menuId, optionGroupId, optionGroupRequest));
    }

    @DeleteMapping("/option-groups/{optionGroupId}")
    public void delete(@Auth AuthUser authUser, @PathVariable long optionGroupId) {
        optionGroupService.delete(authUser, optionGroupId);
    }
}
