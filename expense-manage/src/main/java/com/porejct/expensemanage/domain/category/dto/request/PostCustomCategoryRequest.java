package com.porejct.expensemanage.domain.category.dto.request;

import com.porejct.expensemanage.domain.category.enums.CategoryType;

public record PostCustomCategoryRequest (String name, CategoryType categoryType ){

    public PostCustomCategoryRequest {
        categoryType = CategoryType.CUSTOM;
    }
}
