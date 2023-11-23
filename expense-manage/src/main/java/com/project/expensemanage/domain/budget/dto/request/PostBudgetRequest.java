package com.project.expensemanage.domain.budget.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record PostBudgetRequest(@NotNull @FutureOrPresent LocalDate budgetDate,
                                @NotNull @Positive Long amount,
                                @NotNull @Positive Long categoryId) {

}
