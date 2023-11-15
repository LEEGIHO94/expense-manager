package com.porejct.expensemanage.domain.user.mock;

import com.porejct.expensemanage.commone.security.dto.LoginDto;
import com.porejct.expensemanage.commone.security.dto.UserInfo;
import com.porejct.expensemanage.domain.user.entity.User;
import com.porejct.expensemanage.domain.user.enums.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMock {

    private final PasswordEncoder encoder;
    private final Long userId = 1L;
    private final String email = "test@gmail.com";
    private final String rawPassword = "test131!$@2";
    private final String wrongPassword = "test131!$@2!32!23";
    private final UserRole userRole = UserRole.USER;

    public UserMock(PasswordEncoder passwordEncoder) {
        this.encoder = passwordEncoder;
    }


    public User entityMock() {
        return User.builder()
                .id(userId)
                .password(encoder.encode(rawPassword))
                .userRole(userRole)
                .email(email)
                .build();
    }

    public LoginDto loginMock() {
        return new LoginDto(email, rawPassword);
    }

    public LoginDto wrongLoginMock() {
        return new LoginDto(email, wrongPassword);
    }

    public UserInfo userInfoMock() {
        return new UserInfo(email, "ROLE_USER");
    }

    public String getEmail() {
        return email;
    }
}

