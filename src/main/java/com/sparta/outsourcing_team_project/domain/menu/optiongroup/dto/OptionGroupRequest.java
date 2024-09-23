package com.sparta.outsourcing_team_project.domain.menu.optiongroup.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OptionGroupRequest {

    @NotBlank
    private String optionGroupName;
}
