package com.porejct.expensemanage.domain.user.mock;

import com.porejct.expensemanage.commone.security.dto.LoginDto;
import com.porejct.expensemanage.commone.security.dto.UserInfo;
import com.porejct.expensemanage.domain.user.dto.request.UserPostRequest;
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
    private final String wrongPassword = "wefwfe12312";
    private final String notTwoTypePassword = "test";
    private final String lineWrongPassword = "a";
    private final String phone = "010-1234-1234";
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

    public User postMock() {
        return User.builder()
                .password(encoder.encode(rawPassword))
                .userRole(userRole)
                .email(email)
                .build();
    }

    public UserPostRequest postDtoMock() {
        return UserPostRequest
                .builder()
                .phone(phone)
                .email(email)
                .password(rawPassword)
                .build();
    }

    public UserPostRequest postDtoPasswordLineWrongMock(){
        return UserPostRequest
                .builder()
                .phone(phone)
                .email(email)
                .password(lineWrongPassword)
                .build();
    }

    public UserPostRequest postDtoPasswordNoTwoTypeMock(){
        return UserPostRequest
                .builder()
                .phone(phone)
                .email(email)
                .password(notTwoTypePassword)
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

