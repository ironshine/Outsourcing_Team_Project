package com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.controller;

import com.sparta.outsourcing_team_project.domain.common.annotation.Auth;
import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.dto.OptionRequest;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.dto.OptionResponse;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/menus/option-groups")
public class OptionController {

    private final OptionService optionService;

    @PostMapping("/{optionGroupId}")
    public ResponseEntity<OptionResponse> create(
            @Auth AuthUser authUser,
            @PathVariable long optionGroupId,
            @RequestBody OptionRequest optionRequest) {
        return ResponseEntity.ok().body(optionService.create(authUser, optionGroupId, optionRequest));
    }

    @PutMapping("/{optionGroupId}/options/{optionId}")
    public ResponseEntity<OptionResponse> update(
            @Auth AuthUser authUser,
            @PathVariable long optionGroupId,
            @PathVariable long optionId,
            @RequestBody OptionRequest optionRequest) {
        return ResponseEntity.ok().body(optionService.update(authUser, optionGroupId, optionId, optionRequest));
    }

    @DeleteMapping("/options/{optionId}")
    public void create(@Auth AuthUser authUser, @PathVariable long optionId) {
        optionService.delete(authUser, optionId);
    }
}
