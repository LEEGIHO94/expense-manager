package com.porejct.expensemanage.commone.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonExceptionCode implements ExceptionCode {
    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류"),
    ACCEPT_TYPE_ERROR(HttpStatus.NOT_ACCEPTABLE, "Accept 타입 오류");

    private final HttpStatus httpStatus;
    private final String message;
}
