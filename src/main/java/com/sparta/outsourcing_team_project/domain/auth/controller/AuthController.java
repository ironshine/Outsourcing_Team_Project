package com.sparta.outsourcing_team_project.domain.auth.controller;

import com.sparta.outsourcing_team_project.domain.auth.dto.request.SigninRequest;
import com.sparta.outsourcing_team_project.domain.auth.dto.request.SignupRequest;
import com.sparta.outsourcing_team_project.domain.auth.dto.response.SigninResponse;
import com.sparta.outsourcing_team_project.domain.auth.dto.response.SignupResponse;
import com.sparta.outsourcing_team_project.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }

    @PostMapping("/signin")
    public SigninResponse signin(@Valid @RequestBody SigninRequest signinRequest) {
        return authService.signin(signinRequest);
    }
}
