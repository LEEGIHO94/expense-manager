package com.project.expensemanage.domain.expenditure.controller.dto.response;

import com.project.expensemanage.domain.category.dto.GetCategoryResponse;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ExpenditureResponse(
    Long expenditureId,
    LocalDate expendedDate,
    String memo,
    Long amount,
    GetCategoryResponse category) {}
