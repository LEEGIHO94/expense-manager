package com.project.expensemanage.domain.expenditure.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record PostExpenditureRequest(@NotNull
                                     @PastOrPresent
                                     LocalDate expendedDate,
                                     @Positive
                                     @NotNull
                                     Long expendedAmount,
                                     @Positive
                                     @NotNull
                                     Long categoryId,
                                     String memo) {

}
