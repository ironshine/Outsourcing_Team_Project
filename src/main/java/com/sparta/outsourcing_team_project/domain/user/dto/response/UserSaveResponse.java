package com.sparta.outsourcing_team_project.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserSaveResponse {

    private final String bearerToken;

    public UserSaveResponse(String bearerToken) {this.bearerToken = bearerToken;}
}
