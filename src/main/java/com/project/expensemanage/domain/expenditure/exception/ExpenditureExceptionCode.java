package com.project.expensemanage.domain.expenditure.exception;

import com.project.expensemanage.commone.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExpenditureExceptionCode implements ExceptionCode {
  NOT_FOUND(HttpStatus.NOT_FOUND, "지출 정보를 칮을 수 없습니다");

  private final HttpStatus httpStatus;
  private final String message;
}
