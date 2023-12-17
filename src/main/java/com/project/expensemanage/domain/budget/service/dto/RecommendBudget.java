package com.project.expensemanage.domain.budget.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class RecommendBudget {
  private Long categoryId;
  private String categoryName;
  private Long amount;

  public RecommendBudget(String categoryName) {
    this.categoryName = categoryName;
  }
}
