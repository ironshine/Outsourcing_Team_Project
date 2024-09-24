package com.sparta.outsourcing_team_project.domain.menu.optiongroup.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionGroupRequest {

    @NotBlank
    private String optionGroupName;
}
