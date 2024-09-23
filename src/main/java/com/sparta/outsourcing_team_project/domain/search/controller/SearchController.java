package com.sparta.outsourcing_team_project.domain.search.controller;

import com.sparta.outsourcing_team_project.domain.store.dto.StoresResponseDto;
import com.sparta.outsourcing_team_project.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<StoresResponseDto>> searchStoreList(@RequestParam String searchTerm) {
        return ResponseEntity.ok().body(searchService.searchStoreList(searchTerm));
    }

    @GetMapping("/hot")
    public ResponseEntity<List<String>> searchHotSearchTerm() {
        return ResponseEntity.ok().body(searchService.searchHotSearchTerm());
    }
}
