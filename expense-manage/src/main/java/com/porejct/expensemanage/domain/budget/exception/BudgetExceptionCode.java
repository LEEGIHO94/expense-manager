package com.porejct.expensemanage.domain.budget.exception;

import com.porejct.expensemanage.commone.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BudgetExceptionCode implements ExceptionCode {
    BUDGET_EXIST(HttpStatus.CONFLICT,"예산이 이미 존재");


    private final HttpStatus httpStatus;
    private final String message;
}
