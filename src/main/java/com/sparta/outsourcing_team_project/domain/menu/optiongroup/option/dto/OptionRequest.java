package com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OptionRequest {

    @NotBlank
    private String optionName;

    @NotBlank
    private int price;
}
