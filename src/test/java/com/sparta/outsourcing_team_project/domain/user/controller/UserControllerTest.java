package com.sparta.outsourcing_team_project.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing_team_project.config.ArgumentResolver;
import com.sparta.outsourcing_team_project.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.sparta.outsourcing_team_project.domain.TestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @MockBean
    private ArgumentResolver argumentResolver;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(argumentResolver)
                .build();
    }

    @Nested
    class getUser {

        @Test
        public void 사용자_조회_정상동작() throws Exception {
            //given
            given(argumentResolver.supportsParameter(any())).willReturn(true);
            given(argumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(TEST_AUTH_USER_USER);
            given(userService.getUser(anyLong())).willReturn(TEST_USER_RESPONSE_1);

            //when
            ResultActions result = mockMvc.perform(get("/users/{userId}", TEST_ID_1));

            //then
            result.andExpect(status().isOk());
        }
    }

    @Nested
    class changePassword {

        @Test
        public void 비밀번호_변경_정상동작() throws Exception {
            //given
            ObjectMapper objectMapper = new ObjectMapper();

            given(argumentResolver.supportsParameter(any())).willReturn(true);
            given(argumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(TEST_AUTH_USER_USER);

            //when
            ResultActions result = mockMvc.perform(put("/users")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(TEST_USER_CHANGE_PASSWORD_REQUEST_1)));

            //then
            result.andExpect(status().isOk());
        }
    }
}