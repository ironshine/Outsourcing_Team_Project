package com.sparta.outsourcing_team_project.domain.user.service;

import com.sparta.outsourcing_team_project.domain.common.exception.InvalidRequestException;
import com.sparta.outsourcing_team_project.domain.user.dto.request.UserRoleChangeRequest;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;
import com.sparta.outsourcing_team_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;

    @Transactional
    public void changeUserRole(long userId, UserRoleChangeRequest userRoleChangeRequest){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new InvalidRequestException("사용자를 찾을 수 없습니다"));
        user.updateRole(UserRole.of(userRoleChangeRequest.getRole()));
    }
}
