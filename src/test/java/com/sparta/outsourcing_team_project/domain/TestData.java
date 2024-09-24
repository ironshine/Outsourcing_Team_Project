package com.sparta.outsourcing_team_project.domain;

import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.menu.dto.MenuRequest;
import com.sparta.outsourcing_team_project.domain.menu.dto.MenuResponse;
import com.sparta.outsourcing_team_project.domain.menu.dto.MenuUpdateRequest;
import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_team_project.domain.menu.enums.MenuCategory;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.dto.OptionGroupRequest;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.dto.OptionGroupResponse;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.entity.OptionGroup;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.dto.OptionRequest;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.dto.OptionResponse;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.entity.Option;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import com.sparta.outsourcing_team_project.domain.user.dto.request.UserChangePasswordRequest;
import com.sparta.outsourcing_team_project.domain.user.dto.request.UserRoleChangeRequest;
import com.sparta.outsourcing_team_project.domain.user.dto.response.UserResponse;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;

import java.time.LocalTime;

public class TestData {

    // Id
    public final static Long TEST_ID_1 = 1L;
    public final static Long TEST_ID_2 = 2L;

    // UserName
    public final static String TEST_USER_NAME_1 = "test";

    // UserEmail
    public final static String TEST_USER_EMAIL_1 = "test1@test.com";

    // UserRole
    public final static UserRole TEST_USER_ROLE_1 = UserRole.USER;
    public final static UserRole TEST_USER_ROLE_2 = UserRole.OWNER;

    // AuthUser
    public final static AuthUser TEST_AUTH_USER_USER = new AuthUser(TEST_ID_1, TEST_USER_EMAIL_1, TEST_USER_ROLE_1);
    public final static AuthUser TEST_AUTH_USER_OWNER = new AuthUser(TEST_ID_1, TEST_USER_EMAIL_1, TEST_USER_ROLE_2);

    // Password
    public final static String TEST_PASSWORD_1 = "Testtest123!";

    // User
    public final static User TEST_USER_1 = new User(TEST_ID_1, TEST_USER_EMAIL_1, TEST_USER_ROLE_1);
    public final static User TEST_USER_2 = new User(TEST_ID_2, TEST_USER_EMAIL_1, TEST_USER_ROLE_1);

    // UserChangePasswordRequest
    public final static UserChangePasswordRequest TEST_USER_CHANGE_PASSWORD_REQUEST_1 = new UserChangePasswordRequest(TEST_PASSWORD_1, TEST_PASSWORD_1);

    // UserResponse
    public final static UserResponse TEST_USER_RESPONSE_1 = new UserResponse(TEST_ID_1, TEST_USER_EMAIL_1);

    // StoreName
    public final static String TEST_STORE_NAME_1 = "가게1";

    // StoreOpenTime
    public final static LocalTime TEST_STORE_OPEN_TIME_1 = LocalTime.NOON;

    // StoreCloseTime
    public final static LocalTime TEST_STORE_CLOSE_TIME_1 = LocalTime.MIDNIGHT;

    // MinOrderPrice
    public final static Integer TEST_MIN_ORDER_PRICE_1 = 12000;

    // StoreStatus
    public final static Boolean TEST_STORE_STATUS_TRUE = true;
    public final static Boolean TEST_STORE_STATUS_FALSE = false;

    // AdPrice
    public final static Long TEST_AD_PRICE_1 = 2000000L;

    // Store
    public final static Store TEST_STORE_1 = Store.builder()
            .id(TEST_ID_1)
            .storeName(TEST_STORE_NAME_1)
            .storeOpenTime(TEST_STORE_OPEN_TIME_1)
            .storeCloseTime(TEST_STORE_CLOSE_TIME_1)
            .minOrderPrice(TEST_MIN_ORDER_PRICE_1)
            .storeStatus(TEST_STORE_STATUS_TRUE)
            .adPrice(TEST_AD_PRICE_1)
            .user(TEST_USER_1)
            .build();

    public final static Store TEST_STORE_2 = Store.builder()
            .id(TEST_ID_1)
            .storeName(TEST_STORE_NAME_1)
            .storeOpenTime(TEST_STORE_OPEN_TIME_1)
            .storeCloseTime(TEST_STORE_CLOSE_TIME_1)
            .minOrderPrice(TEST_MIN_ORDER_PRICE_1)
            .storeStatus(TEST_STORE_STATUS_FALSE)
            .adPrice(TEST_AD_PRICE_1)
            .user(TEST_USER_1)
            .build();

    public final static Store TEST_STORE_3 = Store.builder()
            .id(TEST_ID_2)
            .storeName(TEST_STORE_NAME_1)
            .storeOpenTime(TEST_STORE_OPEN_TIME_1)
            .storeCloseTime(TEST_STORE_CLOSE_TIME_1)
            .minOrderPrice(TEST_MIN_ORDER_PRICE_1)
            .storeStatus(TEST_STORE_STATUS_TRUE)
            .adPrice(TEST_AD_PRICE_1)
            .user(TEST_USER_2)
            .build();


    // MenuName
    public final static String TEST_MENU_NAME_1 = "짜장면";

    // Price
    public final static Integer TEST_PRICE_1 = 7000;

    // MenuStatus
    public final static Boolean TEST_MENU_STATUS_TRUE = true;
    public final static Boolean TEST_MENU_STATUS_FALSE = false;

    // MenuCategory
    public final static MenuCategory TEST_MENU_CATEGORY_1 = MenuCategory.CHINESE;

    // Menu
    public final static Menu TEST_MENU_1 = Menu.builder()
            .id(TEST_ID_1)
            .menuName(TEST_MENU_NAME_1)
            .price(TEST_PRICE_1)
            .menuStatus(TEST_MENU_STATUS_TRUE)
            .menuCategory(TEST_MENU_CATEGORY_1)
            .store(TEST_STORE_1)
            .build();

    public final static Menu TEST_MENU_2 = Menu.builder()
            .id(TEST_ID_1)
            .menuName(TEST_MENU_NAME_1)
            .price(TEST_PRICE_1)
            .menuStatus(TEST_MENU_STATUS_TRUE)
            .menuCategory(TEST_MENU_CATEGORY_1)
            .store(TEST_STORE_3)
            .build();

    public final static Menu TEST_MENU_3 = Menu.builder()
            .id(TEST_ID_2)
            .menuName(TEST_MENU_NAME_1)
            .price(TEST_PRICE_1)
            .menuStatus(TEST_MENU_STATUS_TRUE)
            .menuCategory(TEST_MENU_CATEGORY_1)
            .store(TEST_STORE_2)
            .build();

    public final static Menu TEST_MENU_4 = Menu.builder()
            .id(TEST_ID_1)
            .menuName(TEST_MENU_NAME_1)
            .price(TEST_PRICE_1)
            .menuStatus(TEST_MENU_STATUS_FALSE)
            .menuCategory(TEST_MENU_CATEGORY_1)
            .store(TEST_STORE_1)
            .build();

    // MenuRequest
    public final static MenuRequest TEST_MENU_REQUEST_1 = new MenuRequest(TEST_MENU_NAME_1, TEST_PRICE_1, String.valueOf(TEST_MENU_CATEGORY_1));

    // MenuUpdateRequest
    public final static MenuUpdateRequest TEST_MENU_UPDATE_REQUEST_1 = new MenuUpdateRequest(TEST_MENU_NAME_1, TEST_PRICE_1);

    // MenuResponse
    public final static MenuResponse TEST_MENU_RESPONSE_1 = MenuResponse.entityToDto(TEST_MENU_1);

    // OptionGroupName
    public final static String TEST_OPTION_GROUP_NAME_1 = "추가 사항";

    // OptionGroupRequest
    public final static OptionGroupRequest TEST_OPTION_GROUP_REQUEST_1 = new OptionGroupRequest(TEST_OPTION_GROUP_NAME_1);

    // OptionGroup
    public final static OptionGroup TEST_OPTION_GROUP_1 = OptionGroup.builder()
            .id(TEST_ID_1)
            .optionGroupName(TEST_OPTION_GROUP_NAME_1)
            .menu(TEST_MENU_1)
            .build();

    public final static OptionGroup TEST_OPTION_GROUP_2 = OptionGroup.builder()
            .id(TEST_ID_1)
            .optionGroupName(TEST_OPTION_GROUP_NAME_1)
            .menu(TEST_MENU_3)
            .build();

    public final static OptionGroup TEST_OPTION_GROUP_3 = OptionGroup.builder()
            .id(TEST_ID_1)
            .optionGroupName(TEST_OPTION_GROUP_NAME_1)
            .menu(TEST_MENU_2)
            .build();

    public final static OptionGroup TEST_OPTION_GROUP_4 = OptionGroup.builder()
            .id(TEST_ID_1)
            .optionGroupName(TEST_OPTION_GROUP_NAME_1)
            .menu(TEST_MENU_4)
            .build();

    // OptionGroupResponse
    public final static OptionGroupResponse TEST_OPTION_GROUP_RESPONSE_1 = OptionGroupResponse.entityToDto(TEST_OPTION_GROUP_1);

    // OptionName
    public final static String TEST_OPTION_NAME_1 = "곱빼기";

    // OptionPrice
    public final static Integer TEST_OPTION_PRICE_1 = 2000;

    // Option
    public final static Option TEST_OPTION_1 = Option.builder()
            .id(TEST_ID_1)
            .optionName(TEST_OPTION_NAME_1)
            .optionPrice(TEST_OPTION_PRICE_1)
            .optionGroup(TEST_OPTION_GROUP_1)
            .build();

    public final static Option TEST_OPTION_2 = Option.builder()
            .id(TEST_ID_2)
            .optionName(TEST_OPTION_NAME_1)
            .optionPrice(TEST_OPTION_PRICE_1)
            .optionGroup(TEST_OPTION_GROUP_3)
            .build();

    // OptionRequest
    public final static OptionRequest TEST_OPTION_REQUEST_1 = new OptionRequest(TEST_OPTION_NAME_1, TEST_PRICE_1);

    // OptionResponse
    public final static OptionResponse TEST_OPTION_RESPONSE_1 = OptionResponse.entityToDto(TEST_OPTION_1);

    // UserRoleChangeRequest
    public final static UserRoleChangeRequest TEST_USER_ROLE_CHANGE_REQUEST_1 = new UserRoleChangeRequest("USER");
}
