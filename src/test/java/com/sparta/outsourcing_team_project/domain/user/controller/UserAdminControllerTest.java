package com.sparta.outsourcing_team_project.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing_team_project.config.ArgumentResolver;
import com.sparta.outsourcing_team_project.domain.user.service.UserAdminService;
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

import static com.sparta.outsourcing_team_project.domain.TestData.TEST_ID_1;
import static com.sparta.outsourcing_team_project.domain.TestData.TEST_USER_ROLE_CHANGE_REQUEST_1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(UserAdminController.class)
class UserAdminControllerTest {

    @MockBean
    private UserAdminService userAdminService;

    @Autowired
    UserAdminController userAdminController;

    private MockMvc mockMvc;

    @MockBean
    private ArgumentResolver argumentResolver;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userAdminController)
                .setCustomArgumentResolvers(argumentResolver)
                .build();
    }

    @Nested
    class changeUserRole {

        @Test
        public void 유저_권한_변경_정상동작() throws Exception {
            //given
            ObjectMapper objectMapper = new ObjectMapper();

            //when
            ResultActions result = mockMvc.perform(patch("/api/users/admin/{userId}", TEST_ID_1)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(TEST_USER_ROLE_CHANGE_REQUEST_1)));

            //then
            result.andExpect(status().isOk());
        }
    }
}