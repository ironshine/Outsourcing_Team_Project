package com.sparta.outsourcing_team_project.domain.user.entity;

import com.sparta.outsourcing_team_project.domain.order.entity.TimeStamp;
import com.sparta.outsourcing_team_project.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @Column(name = "user_delete_at")
    private LocalDateTime userDeleteAt;

    // token 용도
    public User(Long userId, String userEmail, UserRole userRole) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userRole = userRole;
    }

    public User(String userEmail, String password, String userName, UserRole userRole) {
        this.userEmail = userEmail;
        this.password = password;
        this.userName = userName;
        this.userRole = userRole;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void changePassword(String password) {
        this.password = password;
    }

}
