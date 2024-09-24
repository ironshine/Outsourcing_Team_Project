package com.sparta.outsourcing_team_project.domain.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.outsourcing_team_project.config.ArgumentResolver;
import com.sparta.outsourcing_team_project.domain.common.dto.AuthUser;
import com.sparta.outsourcing_team_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_team_project.domain.store.dto.*;
import com.sparta.outsourcing_team_project.domain.store.entity.Store;
import com.sparta.outsourcing_team_project.domain.store.service.StoreService;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(StoreController.class)
class StoreControllerTest {
    @MockBean
    private StoreService storeService;
    @Autowired
    private StoreController storeController;
    private MockMvc mvc;

    @MockBean
    private ArgumentResolver argumentResolver;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(storeController)
                .setCustomArgumentResolvers(argumentResolver)
                .build();
    }

    private User user = new User(1L, "치킨집@email.com", UserRole.OWNER);
    private List<Menu> menuList = List.of(new Menu());
    private Store store = Store.builder()
            .id(1L)
            .storeName("치킨집")
            .storeOpenTime(LocalTime.now())
            .storeCloseTime(LocalTime.now())
            .minOrderPrice(20000)
            .storeStatus(true)
            .adPrice(0L)
            .user(user)
            .menus(menuList)
            .build();
    private StoreRequestDto storeRequestDto = StoreRequestDto.builder()
            .storeName("백반집")
            .minOrderPrice(20)
            .build();
    private ObjectMapper objectMapper = new ObjectMapper();
    private StoreSaveResponseDto storeSaveResponseDto = new StoreSaveResponseDto(store);
    AuthUser authUser = new AuthUser(1L, "치킨집@email.com", UserRole.OWNER);

    private void authSetUp() {
        given(argumentResolver.supportsParameter(any())).willReturn(true);
        given(argumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(authUser);
    }

    @Test
    @DisplayName("가게 추가")
    void addStores_성공() throws Exception {
        // given
        authSetUp();
        objectMapper.registerModule(new JavaTimeModule());
        given(storeService.addStores(any(), any())).willReturn(storeSaveResponseDto);
        // when-then
        mvc.perform(post("/api/stores")
                        .content(objectMapper.writeValueAsString(storeRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("가게 이름으로 다건 조회")
    void getStores_성공() throws Exception {
        // given
        given(storeService.getStores(any())).willReturn(List.of(new StoresResponseDto(store)));

        // when-then
        mvc.perform(get("/api/stores?store_name={store_name}", store.getStoreName()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("가게 ID로 단건 조회")
    void getStore_성공() throws Exception {
        // given
        given(storeService.getStore(anyLong())).willReturn(new StoreResponseDto(store));
        // when-then
        mvc.perform(get("/api/stores/{store_id}", store.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("가게 수정")
    void updateStore_성공() throws Exception {
        // given
        authSetUp();
        given(storeService.updateStore(anyLong(), any(), any())).willReturn(new StoreResponseDto(store));

        // when-then
        mvc.perform(put("/api/stores/{store_id}", store.getId())
                        .content(objectMapper.writeValueAsString(storeRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("가게 폐업")
    void closedStore_성공() throws Exception {
        // given
        authSetUp();
        given(storeService.closedStore(anyLong(), any())).willReturn("폐업 신고 완료");

        // when-then
        mvc.perform(delete("/api/stores/{store_id}/closed", store.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("가게 광고비용 추가")
    void addAdPrice_성공() throws Exception {
        // given
        AdsResponseDto adsResponseDto = new AdsResponseDto(1000L);
        given(storeService.addAdPrice(anyLong(), anyLong())).willReturn(adsResponseDto);

        // when-then
        mvc.perform(patch("/api/stores/{store_id}/ads?ad_price={ad_price}", store.getId(), 1000L)
                        .content(objectMapper.writeValueAsString(adsResponseDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}