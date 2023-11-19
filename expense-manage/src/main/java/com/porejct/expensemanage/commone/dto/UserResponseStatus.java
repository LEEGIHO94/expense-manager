package com.porejct.expensemanage.commone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserResponseStatus {
    USER_CREATE(HttpStatus.CREATED,"회원 가입 완료");

    private final HttpStatus httpStatus;
    private final String message;

    public int getCode() {
        return httpStatus.value();
    }
}
