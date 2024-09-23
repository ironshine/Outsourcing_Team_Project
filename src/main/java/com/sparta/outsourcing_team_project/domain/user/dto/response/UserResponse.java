package com.sparta.outsourcing_team_project.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserResponse {

    private final Long userId;
    private final String userEmail;

    public UserResponse(Long userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }
}
