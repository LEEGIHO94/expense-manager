package com.porejct.expensemanage.domain.user.exception;

import com.porejct.expensemanage.commone.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionCode implements ExceptionCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"회원 조회 실패");

    private final HttpStatus httpStatus;
    private final String message;
}
