package com.sparta.outsourcing_team_project.userTest;

import com.sparta.outsourcing_team_project.domain.common.exception.InvalidRequestException;
import com.sparta.outsourcing_team_project.domain.user.dto.request.UserRoleChangeRequest;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;
import com.sparta.outsourcing_team_project.domain.user.repository.UserRepository;
import com.sparta.outsourcing_team_project.domain.user.service.UserAdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserAdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserAdminService userAdminService;

    @Test
    public void 사용자_역할_변경_성공(){
        //given
        long userId = 1;
        UserRoleChangeRequest request = new UserRoleChangeRequest("OWNER");
        User user = new User("test@example.com", "password", "username", UserRole.USER);
        user.updateRole(UserRole.USER);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        //when
        userAdminService.changeUserRole(userId, request);

        //then
        assertEquals(UserRole.OWNER, user.getUserRole());
    }

    @Test
    public void 사용자가_존재하지_않는_경우(){
        long userId = 1;
        UserRoleChangeRequest request = new UserRoleChangeRequest("OWNER");

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> userAdminService.changeUserRole(userId, request));
        assertEquals("사용자를 찾을 수 없습니다", exception.getMessage());
    }
}
