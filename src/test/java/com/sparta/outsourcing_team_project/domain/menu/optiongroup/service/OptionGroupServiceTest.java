package com.sparta.outsourcing_team_project.domain.menu.optiongroup.service;

import com.sparta.outsourcing_team_project.domain.common.exception.InvalidRequestException;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.dto.OptionGroupResponse;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.repository.OptionGroupRepository;
import com.sparta.outsourcing_team_project.domain.menu.repository.MenuRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.sparta.outsourcing_team_project.domain.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OptionGroupServiceTest {
    @Mock
    private OptionGroupRepository optionGroupRepository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private OptionGroupService optionGroupService;

    @Nested
    class create {

        @Test
        public void 옵션_그룹_생성_중_가게가_존재하지_않아_에러발생() {
            //given

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionGroupService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_OPTION_GROUP_REQUEST_1));

            //then
            assertEquals("존재하지 않는 메뉴입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_그룹_생성_중_메뉴가_삭제된_상태인_에러발생() {
            //given
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_4));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionGroupService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_OPTION_GROUP_REQUEST_1));

            //then
            assertEquals("삭제된 메뉴입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_그룹_생성_중_가게가_폐업한_경우_에러발생() {
            //given
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_3));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionGroupService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_OPTION_GROUP_REQUEST_1));

            //then
            assertEquals("폐업한 가게입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_그룹_생성_중_가게의_사장님이_아닌_경우_에러발생() {
            //given
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_2));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionGroupService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_OPTION_GROUP_REQUEST_1));

            //then
            assertEquals("해당 가게의 사장님이 아닙니다.", exception.getMessage());
        }

        @Test
        public void 옵션_그룹_생성_정상동작() {
            //given
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_1));
            given(optionGroupRepository.save(any())).willReturn(TEST_OPTION_GROUP_1);

            //when
            OptionGroupResponse result = optionGroupService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_OPTION_GROUP_REQUEST_1);

            //then
            assertThat(result).isNotNull();
        }
    }

    @Nested
    class update {

        @Test
        public void 옵션_그룹_수정_중_메뉴가_존재하지_않아_에러발생() {
            //given

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionGroupService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_OPTION_GROUP_REQUEST_1));

            //then
            assertEquals("존재하지 않는 메뉴입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_그룹_수정_중_메뉴가_삭제된_상태인_에러발생() {
            //given
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_4));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionGroupService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_OPTION_GROUP_REQUEST_1));

            //then
            assertEquals("삭제된 메뉴입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_그룹_수정_중_가게가_폐업한_경우_에러발생() {
            //given
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_3));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionGroupService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_OPTION_GROUP_REQUEST_1));

            //then
            assertEquals("폐업한 가게입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_그룹_수정_중_가게의_사장님이_아닌_경우_에러발생() {
            //given
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_2));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionGroupService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_OPTION_GROUP_REQUEST_1));

            //then
            assertEquals("해당 가게의 사장님이 아닙니다.", exception.getMessage());
        }

        @Test
        public void 옵션_그룹_수정_중_메뉴에_존재하지_않는_옵션_그룹인_경우_에러발생() {
            //given
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_1));
            given(optionGroupRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_2));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionGroupService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_OPTION_GROUP_REQUEST_1));
            //then
            assertEquals("메뉴에 존재하지 않는 옵션 그룹입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_그룹_수정_정상동작() {
            //given
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_1));
            given(optionGroupRepository.findById(any())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_1));

            //when
            OptionGroupResponse result = optionGroupService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_OPTION_GROUP_REQUEST_1);

            //then
            assertThat(result).isNotNull();
        }
    }

    @Nested
    class delete {

        @Test
        public void 옵션_그룹_삭제_중_메뉴가_존재하지_않아_에러발생() {
            //given

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionGroupService.delete(TEST_AUTH_USER_OWNER, TEST_ID_1));

            //then
            assertEquals("존재하지 않는 옵션 그룹입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_그룹_삭제_중_가게의_사장님이_아닌_경우_에러발생() {
            //given
            given(optionGroupRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_3));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionGroupService.delete(TEST_AUTH_USER_OWNER, TEST_ID_1));

            //then
            assertEquals("해당 가게의 사장님이 아닙니다.", exception.getMessage());
        }

        @Test
        public void 옵션_그룹_삭제_정상동작() {
            //given
            given(optionGroupRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_1));

            //when
            optionGroupService.delete(TEST_AUTH_USER_OWNER, TEST_ID_1);

            //then
            verify(optionGroupRepository, times(1)).delete(TEST_OPTION_GROUP_1);
        }
    }
}