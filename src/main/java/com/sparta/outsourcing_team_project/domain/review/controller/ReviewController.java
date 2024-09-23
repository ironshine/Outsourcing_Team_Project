package com.sparta.outsourcing_team_project.domain.review.controller;

import com.sparta.outsourcing_team_project.domain.common.annotation.Auth;
import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing_team_project.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing_team_project.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/api/stores/{storeId}/{orderId}/reviews")
    public ResponseEntity<ReviewResponseDto> addReview(@PathVariable long storeId,
                                                       @PathVariable long orderId,
                                                       @RequestBody ReviewRequestDto requestDto,
                                                       @Auth AuthUser authUser) {
        return ResponseEntity.ok(reviewService.addReview(storeId, orderId, requestDto, authUser));
    }


    @GetMapping("/api/stores/{storeId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviewsByStar(
            @PathVariable long storeId,
            @RequestParam int minRating,
            @RequestParam int maxRating) {
        return ResponseEntity.ok(reviewService.getReviewByStar(storeId, minRating, maxRating));
    }


}
