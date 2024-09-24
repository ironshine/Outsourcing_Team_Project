package com.sparta.outsourcing_team_project.domain.home.controller;

import com.sparta.outsourcing_team_project.config.ArgumentResolver;
import com.sparta.outsourcing_team_project.domain.home.service.HomeService;
import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_team_project.domain.menu.enums.MenuCategory;
import com.sparta.outsourcing_team_project.domain.store.dto.StoresResponseDto;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(HomeController.class)
class HomeControllerTest {
    @MockBean
    private HomeService homeService;
    @Autowired
    private HomeController homeController;
    private MockMvc mvc;

    @MockBean
    private ArgumentResolver argumentResolver;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(homeController)
                .setCustomArgumentResolvers(argumentResolver)
                .build();
    }

    private User user = new User(1L, "치킨집@email.com", UserRole.OWNER);
    private List<Menu> menuList = List.of(new Menu());
    private Store store = Store.builder()
            .id(1L)
            .storeName("치킨집")
            .storeOpenTime(LocalTime.of(10, 00, 00))
            .storeCloseTime(LocalTime.of(10, 00, 00))
            .minOrderPrice(20000)
            .storeStatus(true)
            .adPrice(0L)
            .user(user)
            .menus(menuList)
            .build();

    @Test
    @DisplayName("카테고리로 가게 목록 조회")
    void getStoresByCategory_성공() throws Exception {
        // given
        MenuCategory category = MenuCategory.KOREAN;
        StoresResponseDto storesResponseDto = new StoresResponseDto(store);
        given(homeService.getStoresByCategory(category)).willReturn(List.of(storesResponseDto));

        // when-then
        mvc.perform(get("/api/home?category={category}", category))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("카테고리 랜덤 추천")
    void getCategoryRandom_성공() throws Exception {
        // given
        given(homeService.getCategoryRandom()).willReturn(MenuCategory.ASIAN);
        // when-then
        mvc.perform(get("/api/home/random"))
                .andExpect(status().isOk());
    }
}