package com.project.expensemanage.domain.expenditure.controller.dto.response;

import com.project.expensemanage.domain.category.dto.CategoryByExpenditure;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExpenditureListResponse {

  private final List<ExpenditureCategory> expenditureList;
  @Builder
  @AllArgsConstructor
  @Getter
  public static class ExpenditureCategory {

    private final Long expenditureId;
    private final LocalDate expendedDate;
    private final Long amount;
    private final CategoryByExpenditure category;
  }
}
