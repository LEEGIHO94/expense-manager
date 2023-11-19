package com.porejct.expensemanage.domain.category.exception;

import com.porejct.expensemanage.commone.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CategoryExceptionCode implements ExceptionCode {
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리 조회 실패"),
    CATEGORY_EXIST(HttpStatus.CONFLICT, "카테고리가 이미 존재");

    private final HttpStatus httpStatus;
    private final String message;
}

