package com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.service;

import com.sparta.outsourcing_team_project.domain.common.exception.InvalidRequestException;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.dto.OptionResponse;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.option.repository.OptionRepository;
import com.sparta.outsourcing_team_project.domain.menu.optiongroup.repository.OptionGroupRepository;
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
class OptionServiceTest {

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private OptionGroupRepository optionGroupRepository;

    @InjectMocks
    private OptionService optionService;

    @Nested
    class create {

        @Test
        public void 옵션_생성_중_옵션_그룹이_존재하지_않아_에러발생() {
            //given

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_OPTION_REQUEST_1));

            //then
            assertEquals("존재하지 않는 옵션 그룹입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_생성_중_메뉴가_삭제된_상태인_에러발생() {
            //given
            given(optionGroupRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_4));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_OPTION_REQUEST_1));

            //then
            assertEquals("삭제된 메뉴입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_생성_중_가게가_폐업한_상태인_에러발생() {
            //given
            given(optionGroupRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_2));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_OPTION_REQUEST_1));

            //then
            assertEquals("폐업한 가게입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_생성_중_가게의_사장님이_아닌_경우_에러발생() {
            //given
            given(optionGroupRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_3));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_OPTION_REQUEST_1));

            //then
            assertEquals("해당 가게의 사장님이 아닙니다.", exception.getMessage());
        }

        @Test
        public void 옵션_생성_중_() {
            //given
            given(optionGroupRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_1));
            given(optionRepository.save(any())).willReturn(TEST_OPTION_1);

            //when
            OptionResponse result = optionService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_OPTION_REQUEST_1);


            //then
            assertThat(result).isNotNull();
        }
    }

    @Nested
    class update {

        @Test
        public void 옵션_수정_중_옵션_그룹이_존재하지_않아_에러발생() {
            //given

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_OPTION_REQUEST_1));

            //then
            assertEquals("존재하지 않는 옵션 그룹입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_수정_중_메뉴가_삭제된_상태인_에러발생() {
            //given
            given(optionGroupRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_4));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_OPTION_REQUEST_1));

            //then
            assertEquals("삭제된 메뉴입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_수정_중_가게가_폐업한_상태인_에러발생() {
            //given
            given(optionGroupRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_2));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_OPTION_REQUEST_1));

            //then
            assertEquals("폐업한 가게입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_수정_중_가게의_사장님이_아닌_경우_에러발생() {
            //given
            given(optionGroupRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_3));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_OPTION_REQUEST_1));

            //then
            assertEquals("해당 가게의 사장님이 아닙니다.", exception.getMessage());
        }

        @Test
        public void 옵션_수정_중_존재하지_않는_옵션인_경우_에러발생() {
            //given
            given(optionGroupRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_1));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_OPTION_REQUEST_1));

            //then
            assertEquals("존재하지 않는 옵션입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_수정_정상작동() {
            //given
            given(optionGroupRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_GROUP_1));
            given(optionRepository.findById(any())).willReturn(Optional.ofNullable(TEST_OPTION_1));

            //when
            OptionResponse result = optionService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_OPTION_REQUEST_1);

            //then
            assertThat(result).isNotNull();
        }
    }

    @Nested
    class delete {

        @Test
        public void 옵션_삭제_중_옵션이_존재하지_않아_에러발생() {
            //given

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionService.delete(TEST_AUTH_USER_OWNER, TEST_ID_1));

            //then
            assertEquals("존재하지 않는 옵션입니다.", exception.getMessage());
        }

        @Test
        public void 옵션_삭제_중_가게의_사장이_아닌_경우_에러발생() {
            //given
            given(optionRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_2));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> optionService.delete(TEST_AUTH_USER_OWNER, TEST_ID_1));

            //then
            assertEquals("해당 가게의 사장님이 아닙니다.", exception.getMessage());
        }

        @Test
        public void 옵션_삭제_정상작동() {
            //given
            given(optionRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_OPTION_1));

            //when
            optionService.delete(TEST_AUTH_USER_OWNER, TEST_ID_1);

            //then
            verify(optionRepository, times(1)).delete(TEST_OPTION_1);
        }
    }
}