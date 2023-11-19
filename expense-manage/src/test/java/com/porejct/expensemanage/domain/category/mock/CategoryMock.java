package com.porejct.expensemanage.domain.category.mock;

import com.porejct.expensemanage.domain.category.dto.request.PostCustomCategoryRequest;
import com.porejct.expensemanage.domain.category.entity.Category;
import com.porejct.expensemanage.domain.category.enums.CategoryType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CategoryMock {
    private final String name = "교통비";
    private final Long id = 100L;
    private final String customName = "사용자 설정 카테고리";

    public Category customEntityMock(){
        return Category.builder()
                .categoryType(CategoryType.CUSTOM)
                .name(customName)
                .id(id)
                .build();
    }

    public Category customEntityPostMock(){
        return Category.builder()
                .categoryType(CategoryType.CUSTOM)
                .name(customName)
                .build();
    }

    public Category standardEntityMock(){
        return Category.builder()
                .categoryType(CategoryType.STANDARD)
                .name(name)
                .id(id)
                .build();
    }

    public Category standardEntityPostMock(){
        return Category.builder()
                .categoryType(CategoryType.STANDARD)
                .name(name)
                .build();
    }

    public PostCustomCategoryRequest customCategoryPostDto() {
        return new PostCustomCategoryRequest(customName,null);
    }

}
