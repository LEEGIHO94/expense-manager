package com.porejct.expensemanage.domain.category.mock;

import com.porejct.expensemanage.domain.category.entity.Category;
import com.porejct.expensemanage.domain.category.enums.CategoryType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CategoryMock {
    private final String name = "교통비";
    private final Long id = 100L;

    public Category customEntityMock(String name){
        return Category.builder()
                .categoryType(CategoryType.CUSTOM)
                .name(name)
                .id(id)
                .build();
    }

    public Category customEntityPostMock(String name){
        return Category.builder()
                .categoryType(CategoryType.CUSTOM)
                .name(name)
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


}
