package com.porejct.expensemanage.domain.category.dto.request;

import com.porejct.expensemanage.domain.category.enums.CategoryType;

public record PostStandardCategoryRequest (String name, CategoryType categoryType ){

    public PostStandardCategoryRequest {
        categoryType = CategoryType.STANDARD;
    }
}
