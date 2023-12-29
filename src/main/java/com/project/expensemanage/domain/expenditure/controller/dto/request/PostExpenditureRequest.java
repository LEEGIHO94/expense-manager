package com.project.expensemanage.domain.expenditure.controller.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record PostExpenditureRequest(
    @NotNull @FutureOrPresent LocalDate expendedDate,
    @Positive @NotNull Long expendedAmount,
    @Positive @NotNull Long categoryId,
    String memo) {}
