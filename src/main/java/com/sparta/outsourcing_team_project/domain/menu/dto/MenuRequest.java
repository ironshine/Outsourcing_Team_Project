package com.sparta.outsourcing_team_project.domain.menu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuRequest {

    @NotBlank
    private String menuName;

    @NotBlank
    private int price;

    @NotBlank
    private String menuCategory;
}
