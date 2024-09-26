package com.sparta.outsourcing_team_project.domain.review.service;

import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.order.entity.CustomerOrder;
import com.sparta.outsourcing_team_project.domain.order.enums.OrderStatusEnum;
import com.sparta.outsourcing_team_project.domain.order.repository.OrderRepository;
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
        CustomerOrder order = orderRepository.findById(orderId).orElseThrow(()-> new NullPointerException("존재하지 않는 주문입니다."));

        if(requestDto.getContent() == null || requestDto.getContent().isEmpty()) {
            throw new NullPointerException("내용이 비어있습니다.");
        }

        if(requestDto.getRating() < 0 || requestDto.getRating() > 5) {
            throw new IllegalArgumentException("허용되지 않는 값입니다.");
        }

        if (order.getOrderStatus() != OrderStatusEnum.DELIVERED){
            throw new IllegalArgumentException("배달이 완료되지 않은 주문입니다");
        }

        //주문한 사람과 리뷰를 작성하려는(현재 로그인 된 사람) 사람이 일치하지 않으면 에러발생
        if (!order.getUser().getUserId().equals(authUser.getUserId())){
            throw new IllegalArgumentException("주문자와 리뷰 작성자가 일치하지않습니다.");
        }

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
        if (minRating < 0 || minRating > 5 || maxRating < 0 || maxRating > 5) {
            throw new IllegalArgumentException("잘못된 입력값입니다.");
        }

        List<Review> reviewList = reviewRepository.findAllByStoreIdAndRatingBetweenOrderByModifiedAtDesc(storeId, minRating, maxRating);
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        for (Review review : reviewList) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
            reviewResponseDtoList.add(reviewResponseDto);
        }

        return reviewResponseDtoList;
    }
}
