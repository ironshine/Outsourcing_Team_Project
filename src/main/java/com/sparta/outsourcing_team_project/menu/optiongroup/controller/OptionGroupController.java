package com.sparta.outsourcing_team_project.menu.optiongroup.controller;

import com.sparta.outsourcing_team_project.menu.optiongroup.dto.OptionGroupRequest;
import com.sparta.outsourcing_team_project.menu.optiongroup.dto.OptionGroupResponse;
import com.sparta.outsourcing_team_project.menu.optiongroup.service.OptionGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class OptionGroupController {

    private final OptionGroupService optionGroupService;

    @PostMapping("/menus/{menuId}")
    public ResponseEntity<OptionGroupResponse> create(
            @PathVariable long menuId,
            @RequestBody OptionGroupRequest optionGroupRequest) {
        return ResponseEntity.ok().body(optionGroupService.create(menuId, optionGroupRequest));
    }

    @PutMapping("/menus/{menuId}/option-groups/{optionGroupId}")
    public ResponseEntity<OptionGroupResponse> update(
            @PathVariable long menuId,
            @PathVariable long optionGroupId,
            @RequestBody OptionGroupRequest optionGroupRequest) {
        return ResponseEntity.ok().body(optionGroupService.update(menuId, optionGroupId, optionGroupRequest));
    }

    @DeleteMapping("/option-groups/{optionGroupId}")
    public void delete(@PathVariable long optionGroupId) {
        optionGroupService.delete(optionGroupId);
    }
}
