package com.project.expensemanage.domain.budget.controller.dto.response;

import com.project.expensemanage.domain.category.dto.CategoryByBudget;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record BudgetResponse(
    Long budgetId, LocalDate date, Long amount, CategoryByBudget category) {}
