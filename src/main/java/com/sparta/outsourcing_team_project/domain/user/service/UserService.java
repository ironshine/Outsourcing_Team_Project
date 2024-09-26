package com.sparta.outsourcing_team_project.domain.user.service;

import com.sparta.outsourcing_team_project.config.PasswordEncoder;
import com.sparta.outsourcing_team_project.config.exception.InvalidRequestException;
import com.sparta.outsourcing_team_project.domain.user.dto.request.UserChangePasswordRequest;
import com.sparta.outsourcing_team_project.domain.user.dto.response.UserResponse;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return new UserResponse(user.getUserId(), user.getUserEmail());
    }

    @Transactional
    public void changePassword(long userId, UserChangePasswordRequest userChangePasswordRequest) {

        passwordConditions(userChangePasswordRequest.getNewPassword());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidRequestException("사용자를 찾을 수 없습니다."));

        if (passwordEncoder.matches(userChangePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new InvalidRequestException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }

        if (!passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidRequestException("잘못된 비밀번호입니다.");
        }

        user.changePassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
    }

    private void passwordConditions(String newPassword) {
        if (!newPassword.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,15}$")) {
            throw new InvalidRequestException("비밀번호는 8자 이상 15자 이하로, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개씩 포함해야 합니다.");
        }
    }
}
