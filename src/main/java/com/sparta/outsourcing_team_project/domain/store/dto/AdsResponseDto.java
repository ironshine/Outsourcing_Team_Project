package com.sparta.outsourcing_team_project.domain.store.dto;

import lombok.Getter;

@Getter
public class AdsResponseDto {
    private final Long adPrice;

    public AdsResponseDto(Long adPrice) {
        this.adPrice = adPrice;
    }
}
