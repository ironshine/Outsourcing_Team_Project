package com.sparta.outsourcing_team_project.menu.optiongroup.option.controller;

import com.sparta.outsourcing_team_project.menu.optiongroup.option.dto.OptionRequest;
import com.sparta.outsourcing_team_project.menu.optiongroup.option.dto.OptionResponse;
import com.sparta.outsourcing_team_project.menu.optiongroup.option.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/menus/option-groups")
public class OptionController {

    private final OptionService optionService;

    @PostMapping("/{optionGroupId}")
    public ResponseEntity<OptionResponse> create(@PathVariable long optionGroupId, @RequestBody OptionRequest optionRequest) {
        return ResponseEntity.ok().body(optionService.create(optionGroupId, optionRequest));
    }

    @PutMapping("/{optionGroupId}/options/{optionId}")
    public ResponseEntity<OptionResponse> update(@PathVariable long optionGroupId, @PathVariable long optionId, @RequestBody OptionRequest optionRequest) {
        return ResponseEntity.ok().body(optionService.update(optionGroupId, optionId, optionRequest));
    }

    @DeleteMapping("/options/{optionId}")
    public void create(@PathVariable long optionId) {
        optionService.delete(optionId);
    }
}
