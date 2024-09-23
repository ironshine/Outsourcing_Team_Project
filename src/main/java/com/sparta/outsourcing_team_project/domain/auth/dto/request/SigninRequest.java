package com.sparta.outsourcing_team_project.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {

    @Email
    @NotNull(message = "email 은 필수 항목입니다.")
    @NotBlank(message = "email 은 필수 항목입니다.")
    private String userEmail;

    @NotNull(message = "비밀번호는 필수 항목입니다.")
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

}
