package com.porejct.expensemanage.domain.category.dto.request;

import com.porejct.expensemanage.domain.category.enums.CategoryType;
import jakarta.validation.constraints.NotNull;

public record PostStandardCategoryRequest (@NotNull String name, CategoryType categoryType ){

    public PostStandardCategoryRequest {
        categoryType = CategoryType.STANDARD;
    }
}
