package com.project.expensemanage.domain.category.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {
    CUSTOM("사용자 지정"),
    STANDARD("표준");

    private final String description;
}
