package com.sparta.outsourcing_team_project.domain.common.dto;

import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;
import lombok.Getter;

@Getter
public class AuthUser {

    private final Long userId;
    private final String userEmail;
    private final UserRole userRole;

    public AuthUser( Long userId, String userEmail, UserRole userRole) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userRole = userRole;
    }
}
