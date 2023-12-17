package com.project.expensemanage.domain.budget.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendedBudgetData {
  private Long categoryId;
  private String name;
  private Long amount;
}