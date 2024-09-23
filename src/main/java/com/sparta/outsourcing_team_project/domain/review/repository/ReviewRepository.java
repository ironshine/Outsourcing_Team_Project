package com.sparta.outsourcing_team_project.domain.review.repository;

import com.sparta.outsourcing_team_project.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByStore_IdAndStarBetweenOrderByModifiedAtDesc(Long storeId, int minRaing, int maxRating);
}
