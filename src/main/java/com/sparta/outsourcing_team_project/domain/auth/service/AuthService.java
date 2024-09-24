package com.sparta.outsourcing_team_project.domain.auth.service;

import com.sparta.outsourcing_team_project.config.JwtUtil;
import com.sparta.outsourcing_team_project.config.PasswordEncoder;
import com.sparta.outsourcing_team_project.config.exception.AuthException;
import com.sparta.outsourcing_team_project.config.exception.InvalidRequestException;
import com.sparta.outsourcing_team_project.domain.auth.dto.request.SigninRequest;
import com.sparta.outsourcing_team_project.domain.auth.dto.request.SignupRequest;
import com.sparta.outsourcing_team_project.domain.auth.dto.response.SigninResponse;
import com.sparta.outsourcing_team_project.domain.auth.dto.response.SignupResponse;
import com.sparta.outsourcing_team_project.domain.user.entity.User;
import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;
import com.sparta.outsourcing_team_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {

        if (userRepository.existsByUserEmail(signupRequest.getUserEmail())) {
            throw new InvalidRequestException("이미 존재하는 이메일 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        UserRole userRole = UserRole.of(signupRequest.getUserRole());

        User newUser = new User(
                signupRequest.getUserEmail(),
                encodedPassword,
                signupRequest.getUserName(),
                userRole
        );
        User savedUser = userRepository.save(newUser);

        String bearerToken = jwtUtil.createToken(savedUser.getUserId(), savedUser.getUserEmail(), savedUser.getUserRole());

        return new SignupResponse(bearerToken);
    }

    public SigninResponse signin(SigninRequest signinRequest) {
        User user = userRepository.findByUserEmail(signinRequest.getUserEmail()).orElseThrow(
                () -> new InvalidRequestException("가입되지 않은 유저입니다."));

        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }

        String bearerToken = jwtUtil.createToken(user.getUserId(), user.getUserEmail(), user.getUserRole());

        return new SigninResponse(bearerToken);
    }
}
