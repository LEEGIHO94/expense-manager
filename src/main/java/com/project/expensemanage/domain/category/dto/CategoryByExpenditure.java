package com.project.expensemanage.domain.category.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CategoryByExpenditure {
    private final Long categoryId;
    private final String categoryName;
    private final Long amount;
}
