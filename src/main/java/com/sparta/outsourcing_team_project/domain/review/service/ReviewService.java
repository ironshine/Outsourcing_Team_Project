package com.sparta.outsourcing_team_project.domain.review.service;

import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.orders.entity.Orders;
import com.sparta.outsourcing_team_project.domain.orders.enums.OrderStatusEnum;
import com.sparta.outsourcing_team_project.domain.orders.repository.OrderRepository;
import com.sparta.outsourcing_team_project.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing_team_project.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing_team_project.domain.review.entity.Review;
import com.sparta.outsourcing_team_project.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public ReviewResponseDto addReview(long storeId, long orderId, ReviewRequestDto requestDto, AuthUser authUser) {
        Orders order = orderRepository.findById(orderId).orElseThrow(()-> new NullPointerException("존재하지 않는 주문입니다."));

        if (order.getOrderStatus() != OrderStatusEnum.DELIVERED){
            throw new IllegalArgumentException("배달이 완료되지 않은 주문입니다");
        }
        
        //주문한 사람과 리뷰를 작성하려는(현재 로그인 된 사람) 사람이 일치하지 않으면 에러발생 

        Review newReview = Review.builder()
                .content(requestDto.getContent())
                .rating(requestDto.getRating())
                .order(order)
                .storeId(storeId)
                .build();

        reviewRepository.save(newReview);

        return new ReviewResponseDto(newReview);
    }

    public List<ReviewResponseDto> getReviewByStar(long storeId, int minRating, int maxRating) {
        List<Review> reviewList = reviewRepository.findAllByStore_IdAndRatingBetweenOrderByModifiedAtDesc(storeId, minRating, maxRating);
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        for (Review review : reviewList) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
            reviewResponseDtoList.add(reviewResponseDto);
        }

        return reviewResponseDtoList;
    }
}
