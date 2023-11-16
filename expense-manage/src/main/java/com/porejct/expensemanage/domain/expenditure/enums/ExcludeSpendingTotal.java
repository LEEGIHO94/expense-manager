package com.porejct.expensemanage.domain.expenditure.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExcludeSpendingTotal {
    EXCLUDE(false),
    INCLUDE(true)
    ;

    private final boolean flag;
}
