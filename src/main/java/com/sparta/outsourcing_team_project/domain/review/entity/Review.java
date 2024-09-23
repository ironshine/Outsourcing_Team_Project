package com.sparta.outsourcing_team_project.domain.review.entity;

import com.sparta.outsourcing_team_project.domain.order.entity.CustomerOrder;
import com.sparta.outsourcing_team_project.domain.order.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reivew_id")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "rating", nullable = false)
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @Column(name ="store_id")
    private Long storeId;
}
