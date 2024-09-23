package com.sparta.outsourcing_team_project.domain.menu.dto;

import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuResponse {

    private final String menuName;
    private final Integer price;

    public static MenuResponse entityToDto(Menu menu) {
        return new MenuResponse(
                menu.getMenuName(),
                menu.getPrice()
        );
    }
}
