package com.project.expensemanage.domain.analysis.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ExpenditureTotalUser {
  private Long totalExpenditure;
  private Long userExpenditure;
}
