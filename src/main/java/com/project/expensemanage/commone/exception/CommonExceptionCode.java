package com.project.expensemanage.commone.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonExceptionCode implements ExceptionCode {
  INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류"),
  ACCEPT_TYPE_ERROR(HttpStatus.NOT_ACCEPTABLE, "Accept 타입 오류"),
  TRANS_ENTITY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Entity 변환 에러"),
  TRANS_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "JSON 변환 에러");

  private final HttpStatus httpStatus;
  private final String message;
}
