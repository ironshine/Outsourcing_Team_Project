package com.sparta.outsourcing_team_project.domain.review.dto;

import com.sparta.outsourcing_team_project.domain.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
    private final Long id;
    private final String userNmae;
    private final String content;
    private final int rating;
    private final LocalDateTime updatedAt;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.userNmae = review.getOrder().getUser().getUserName();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.updatedAt = review.getModifiedAt();
    }
}
