package com.project.expensemanage.domain.budget.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record PatchBudgetRequest( @NotNull LocalDate budgetDate,
                                  @Positive Long amount,
                                  @NotNull Long categoryId) {

}
