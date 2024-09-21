package com.sparta.outsourcing_team_project.menu.optiongroup.dto;

import com.sparta.outsourcing_team_project.menu.optiongroup.entity.OptionGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OptionGroupResponse {

    private final String optionGroupName;

    public static OptionGroupResponse entityToDto(OptionGroup optionGroup) {
        return new OptionGroupResponse(
                optionGroup.getOptionGroupName()
        );
    }
}
