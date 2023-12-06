package com.project.expensemanage.commone.utils.date;

import java.time.LocalDate;
import java.time.YearMonth;
import org.springframework.stereotype.Component;

@Component
public class DateUtils {
  public LocalDate startOfMonth() {
    return YearMonth.now().atDay(1);
  }

  public LocalDate endOfMonth() {
    return YearMonth.now().atEndOfMonth();
  }

  public LocalDate getLocalDate() {
    return LocalDate.now();
  }
}
