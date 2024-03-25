package com.project.expensemanage.domain.expenditure.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public record GetExpenditureList(
    Long categoryId,
    @NotNull @PastOrPresent LocalDate startDate,
    @NotNull @PastOrPresent LocalDate endDate,
    Long minAmount,
    Long maxAmount) {}
