package com.sparta.outsourcing_team_project.menu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MenuUpdateRequest {

    @NotBlank
    private String menuName;

    @NotBlank
    private int price;
}
