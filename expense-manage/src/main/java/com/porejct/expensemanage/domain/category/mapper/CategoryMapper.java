package com.porejct.expensemanage.domain.category.mapper;


import com.porejct.expensemanage.commone.dto.ResponseDto;
import com.porejct.expensemanage.commone.dto.ResponseStatus;
import com.porejct.expensemanage.domain.category.dto.request.PostCustomCategoryRequest;
import com.porejct.expensemanage.domain.category.dto.response.CategoryIdResponse;
import com.porejct.expensemanage.domain.category.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(PostCustomCategoryRequest post) {
        return Category.builder()
                .categoryType(post.categoryType())
                .name(post.name())
                .build();
    }

    public ResponseDto<CategoryIdResponse> toDto(Category category) {
        return ResponseDto.<CategoryIdResponse>builder()
                .status(ResponseStatus.CREATE)
                .data(toIdDto(category))
                .build();
    }

    private CategoryIdResponse toIdDto(Category category) {
        return CategoryIdResponse.builder()
                .categoryId(category.getId())
                .build();
    }
}
