package com.project.expensemanage.domain.expenditure.controller.dto.response;

import com.project.expensemanage.domain.category.dto.CategoryByExpenditure;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExpenditureListResponse {

    private final List<ExpenditureCategory> expenditureList;

    @Builder
    @AllArgsConstructor
    public static class ExpenditureCategory {

        private final Long budgetId;
        private final LocalDate expendedDate;
        private final Long amount;
        private final CategoryByExpenditure category;
    }
}
