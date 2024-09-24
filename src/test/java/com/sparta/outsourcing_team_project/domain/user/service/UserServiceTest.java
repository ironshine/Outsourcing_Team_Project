package com.sparta.outsourcing_team_project.domain.user.service;

import com.sparta.outsourcing_team_project.config.PasswordEncoder;
import com.sparta.outsourcing_team_project.domain.common.exception.InvalidRequestException;
import com.sparta.outsourcing_team_project.domain.user.dto.request.UserChangePasswordRequest;
import com.sparta.outsourcing_team_project.domain.user.dto.response.UserResponse;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;
import com.sparta.outsourcing_team_project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void 유저_조회_정상동작() {
        //given
        long userId = 1;
        User user = new User("test@example.com", "password", "username", UserRole.USER);
        ReflectionTestUtils.setField(user, "userId", userId);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));//anyLong()말고 userId 사용 고려

        //when
        UserResponse userResponse = userService.getUser(userId);

        //then
        assertNotNull(userResponse);
        assertEquals(userId, userResponse.getUserId()); // 유저 ID 확인
        assertEquals("test@example.com", userResponse.getUserEmail());
    }

    @Test
    public void 유저_조회_유저가_존재하지_않음() {
        //given
        long userId = 1;
//        User user = new User("test@example.com", "password", "username", UserRole.USER);
//        ReflectionTestUtils.setField(user, "userId", userId);

        given(userRepository.findById(userId)).willReturn(Optional.empty());//anyLong()말고 userId 사용 고려

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.getUser(userId));

        //then
        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    public void 비밀번호_변경_정상동작() {
        //given
        long userId = 1;
        String oldPassword = "OldPassword1!";
        String newPassword = "NewPassword1!";
        String encodedOldPassword = passwordEncoder.encode(oldPassword);
        User user = new User("test@example.com", encodedOldPassword, "username", UserRole.USER);
        ReflectionTestUtils.setField(user, "userId", userId);

        UserChangePasswordRequest request = new UserChangePasswordRequest(oldPassword, newPassword);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(oldPassword, user.getPassword())).willReturn(true);
        given(passwordEncoder.matches(newPassword, user.getPassword())).willReturn(false);

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        given(passwordEncoder.encode(newPassword)).willReturn(encodedNewPassword);

        //when
        userService.changePassword(userId, request);

        //then
        assertEquals(passwordEncoder.encode(newPassword), user.getPassword());
    }

    @Test
    public void 비밀번호를_변경할_유저가_존재하지_않음() {
        long userId = 1;
        UserChangePasswordRequest request = new UserChangePasswordRequest("OldPassword1!", "NewPassword1!");

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> userService.changePassword(userId, request));
        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    public void 새로운_비밀번호가_기존비밀번호와_같을경우() {
        //given
        long userId = 1;
        String oldPassword = "OldPassword1!";
        User user = new User("test@example.com", passwordEncoder.encode(oldPassword), "username", UserRole.USER);
        ReflectionTestUtils.setField(user, "userId", userId);

        UserChangePasswordRequest request = new UserChangePasswordRequest(oldPassword, oldPassword);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(oldPassword, user.getPassword())).willReturn(true);

        //when

        //then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> userService.changePassword(userId, request));
        assertEquals("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.", exception.getMessage());
    }

    @Test
    public void 잘못된_비밀번호() {
        long userId = 1;
        String oldPassword = "OldPassword1!";
        String newPassword = "NewPassword1!";
        String encodedOldPassword = passwordEncoder.encode(oldPassword);
        User user = new User("test@example.com", encodedOldPassword, "username", UserRole.USER);
        ReflectionTestUtils.setField(user, "userId", userId);

        UserChangePasswordRequest request = new UserChangePasswordRequest("wrongOldPassword", newPassword);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches("wrongOldPassword", user.getPassword())).willReturn(false);
        given(passwordEncoder.matches(newPassword, null)).willReturn(false);

        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> userService.changePassword(userId, request));

        assertEquals("잘못된 비밀번호입니다.", exception.getMessage());
    }
}
