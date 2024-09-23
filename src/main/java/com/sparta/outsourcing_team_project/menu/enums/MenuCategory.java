package com.sparta.outsourcing_team_project.menu.enums;

import com.sparta.outsourcing_team_project.common.exception.InvalidRequestException;

import java.util.Arrays;

public enum MenuCategory {

    KOREAN, // 한식
    JAPANESE, // 일식
    CHINESE, // 중식
    FORM, // 양식
    ASIAN, // 동남아음식?
    SNACK, // 분식
    DESSERT; // 간식

    public static MenuCategory of(String menuCategory) {
        return Arrays.stream(MenuCategory.values())
                .filter(r -> r.name().equalsIgnoreCase(menuCategory))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 카테고리"));
    }
}
