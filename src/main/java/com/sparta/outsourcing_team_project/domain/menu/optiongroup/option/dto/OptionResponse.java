package com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.dto;

import com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.entity.Option;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OptionResponse {

    private final String optionName;
    private final Integer optionPrice;

    public static OptionResponse entityToDto(Option option) {
        return new OptionResponse(
                option.getOptionName(),
                option.getOptionPrice()
        );
    }
}
