package com.sparta.outsourcing_team_project.domain.menu.service;

import com.sparta.outsourcing_team_project.config.exception.InvalidRequestException;
import com.sparta.outsourcing_team_project.domain.menu.dto.MenuResponse;
import com.sparta.outsourcing_team_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_team_project.domain.store.repository.StoreRepository;
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

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private MenuService menuService;

    @Nested
    class create {

        @Test
        public void 메뉴_생성_중_가게가_존재하지_않아_에러발생() {
            //given

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> menuService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_MENU_REQUEST_1));

            //then
            assertEquals("존재하지 않는 가게 입니다.", exception.getMessage());
        }

        @Test
        public void 메뉴_생성_중_가게가_폐업한_경우_에러발생() {
            //given
            given(storeRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_STORE_2));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> menuService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_MENU_REQUEST_1));

            //then
            assertEquals("폐업한 가게 입니다.", exception.getMessage());
        }

        @Test
        public void 메뉴_생성_중_가게의_사장님이_아닌_경우_에러발생() {
            //given
            given(storeRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_STORE_3));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> menuService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_MENU_REQUEST_1));

            //then
            assertEquals("해당 가게의 사장님이 아닙니다.", exception.getMessage());
        }

        @Test
        public void 메뉴_생성_정상동작() {
            //given
            given(storeRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_STORE_1));
            given(menuRepository.save(any())).willReturn(TEST_MENU_1);

            //when
            MenuResponse result = menuService.create(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_MENU_REQUEST_1);

            //then
            assertThat(result).isNotNull();
        }
    }

    @Nested
    class update {

        @Test
        public void 메뉴_수정_중_가게가_존재하지_않아_에러발생() {
            //given

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> menuService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_MENU_UPDATE_REQUEST_1));

            //then
            assertEquals("존재하지 않는 가게 입니다.", exception.getMessage());
        }

        @Test
        public void 메뉴_수정_중_가게가_폐업한_경우_에러발생() {
            //given
            given(storeRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_STORE_2));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> menuService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_MENU_UPDATE_REQUEST_1));

            //then
            assertEquals("폐업한 가게 입니다.", exception.getMessage());
        }

        @Test
        public void 메뉴_수정_중_가게의_사장님이_아닌_경우_에러발생() {
            //given
            given(storeRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_STORE_3));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> menuService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_MENU_UPDATE_REQUEST_1));

            //then
            assertEquals("해당 가게의 사장님이 아닙니다.", exception.getMessage());
        }

        @Test
        public void 메뉴_수정_중_존재하지_않는_메뉴인_경우_에러발생() {
            //given
            given(storeRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_STORE_1));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> menuService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_MENU_UPDATE_REQUEST_1));
            //then
            assertEquals("존재하지 않는 메뉴입니다.", exception.getMessage());
        }

        @Test
        public void 메뉴_수정_중_가게에_존재하지_않는_메뉴인_경우_에러발생() {
            //given
            given(storeRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_STORE_1));
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_2));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> menuService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_MENU_UPDATE_REQUEST_1));
            //then
            assertEquals("가게에 존재하지 않는 메뉴입니다.", exception.getMessage());
        }

        @Test
        public void 메뉴_수정_정상작동() {
            //given
            given(storeRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_STORE_1));
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_1));

            //when
            MenuResponse result = menuService.update(TEST_AUTH_USER_OWNER, TEST_ID_1, TEST_ID_1, TEST_MENU_UPDATE_REQUEST_1);

            //then
            assertThat(result).isNotNull();
        }
    }

    @Nested
    class delete {

        @Test
        public void 메뉴_삭제_중_존재하지_않는_메뉴인_경우_에러발생() {
            //given

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> menuService.delete(TEST_AUTH_USER_OWNER, TEST_ID_1));
            //then
            assertEquals("존재하지 않는 메뉴입니다.", exception.getMessage());
        }

        @Test
        public void 메뉴_삭제_중_가게의_사장님이_아닌_경우_에러발생() {
            //given
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_2));

            //when
            InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                    () -> menuService.delete(TEST_AUTH_USER_OWNER, TEST_ID_1));

            //then
            assertEquals("해당 가게의 사장님이 아닙니다.", exception.getMessage());
        }

        @Test
        public void 메뉴_삭제_정상작동() {
            //given
            given(menuRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_MENU_1));

            //when
            menuService.delete(TEST_AUTH_USER_OWNER, TEST_ID_1);

            //then
            assertThat(TEST_MENU_1.getMenuStatus()).isEqualTo(false);
        }
    }

}