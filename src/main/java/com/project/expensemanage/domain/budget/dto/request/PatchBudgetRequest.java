package com.project.expensemanage.domain.budget.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record PatchBudgetRequest(@Positive Long amount, @NotNull Long categoryId) {}
