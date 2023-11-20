package com.porejct.expensemanage.domain.category.mapper;


import com.porejct.expensemanage.commone.dto.ResponseDto;
import com.porejct.expensemanage.commone.dto.ResponseStatus;
import com.porejct.expensemanage.domain.category.dto.GetCategoryResponse;
import com.porejct.expensemanage.domain.category.dto.request.PostCustomCategoryRequest;
import com.porejct.expensemanage.domain.category.dto.request.PostStandardCategoryRequest;
import com.porejct.expensemanage.domain.category.dto.response.CategoryIdResponse;
import com.porejct.expensemanage.domain.category.entity.Category;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public static Category toIdEntity(Long categoryId) {
        return Category.builder()
                .id(categoryId)
                .build();
    }

    public Category toEntity(PostCustomCategoryRequest post) {
        return Category.builder()
                .categoryType(post.categoryType())
                .name(post.name())
                .build();
    }

    public Category toEntity(PostStandardCategoryRequest post) {
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

    public ResponseDto<List<GetCategoryResponse>> toDto(List<Category> categoryList) {
        return ResponseDto.<List<GetCategoryResponse>>builder()
                .data(toGetListDto(categoryList))
                .status(ResponseStatus.GET)
                .build();
    }

    private CategoryIdResponse toIdDto(Category category) {
        return CategoryIdResponse.builder()
                .categoryId(category.getId())
                .build();
    }

    private List<GetCategoryResponse> toGetListDto(List<Category> categoryList) {
        return categoryList.stream().map(this::toGetDto).toList();
    }

    private GetCategoryResponse toGetDto(Category category) {
        return GetCategoryResponse.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .build();
    }
}
