package com.project.expensemanage.commone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    CREATE(HttpStatus.CREATED,"생성 완료"), GET(HttpStatus.OK, "조회 성공");

    private final HttpStatus httpStatus;
    private final String message;

    public int getCode() {
        return httpStatus.value();
    }
}
