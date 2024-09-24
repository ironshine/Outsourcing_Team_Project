package com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing_team_project.config.ArgumentResolver;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.service.OptionService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(OptionController.class)
class OptionControllerTest {

    @MockBean
    private OptionService optionService;

    @Autowired
    private OptionController optionController;

    private MockMvc mockMvc;

    @MockBean
    private ArgumentResolver argumentResolver;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(optionController)
                .setCustomArgumentResolvers(argumentResolver)
                .build();
    }

    @Nested
    class create {

        @Test
        public void 옵션_그룹_생성_정상동작() throws Exception {
            //given
            ObjectMapper objectMapper = new ObjectMapper();

            given(argumentResolver.supportsParameter(any())).willReturn(true);
            given(argumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(TEST_AUTH_USER_OWNER);
            given(optionService.create(any(), anyLong(), any())).willReturn(TEST_OPTION_RESPONSE_1);

            //when
            ResultActions result = mockMvc.perform(post("/api/stores/menus/option-groups/{optionGroupId}", TEST_ID_1)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(TEST_OPTION_REQUEST_1)));

            //then
            result.andExpect(status().isOk());
        }
    }

    @Nested
    class update {

        @Test
        public void 옵션_그룹_수정_정상동작() throws Exception {
            //given
            ObjectMapper objectMapper = new ObjectMapper();

            given(argumentResolver.supportsParameter(any())).willReturn(true);
            given(argumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(TEST_AUTH_USER_OWNER);
            given(optionService.update(any(), anyLong(), anyLong(), any())).willReturn(TEST_OPTION_RESPONSE_1);

            //when
            ResultActions result = mockMvc.perform(put("/api/stores/menus/option-groups/{optionGroupId}/options/{optionId}", TEST_ID_1, TEST_ID_1)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(TEST_OPTION_REQUEST_1)));

            //then
            result.andExpect(status().isOk());
        }
    }

    @Nested
    class delete {

        @Test
        public void 옵션_그룹_삭제_정상동작() throws Exception {
            //given

            //when
            ResultActions result = mockMvc.perform(delete("/api/stores/menus/option-groups/options/{optionId}", TEST_ID_1));

            //then
            result.andExpect(status().isOk());
        }
    }
}