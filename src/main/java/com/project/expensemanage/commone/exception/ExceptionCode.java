package com.project.expensemanage.commone.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
  HttpStatus getHttpStatus();

  String getMessage();
}
