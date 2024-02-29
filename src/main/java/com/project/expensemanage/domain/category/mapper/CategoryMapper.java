package com.project.expensemanage.domain.category.mapper;

import com.project.expensemanage.domain.category.dto.GetCategoryResponse;
import com.project.expensemanage.domain.category.dto.request.PostStandardCategoryRequest;
import com.project.expensemanage.domain.category.dto.response.CategoryIdResponse;
import com.project.expensemanage.domain.category.entity.Category;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  public static Category toIdEntity(Long categoryId) {
    return Category.builder().id(categoryId).build();
  }

  public Category toEntity(PostStandardCategoryRequest post) {
    return Category.builder().categoryType(post.categoryType()).name(post.name()).build();
  }

  public CategoryIdResponse toDto(Category category) {
    return CategoryIdResponse.builder().categoryId(category.getId()).build();
  }

  public List<GetCategoryResponse> toGetListDto(List<Category> categoryList) {
    return categoryList.stream().map(this::toGetDto).toList();
  }

  public GetCategoryResponse toGetDto(Category category) {
    return GetCategoryResponse.builder()
        .categoryId(category.getId())
        .name(category.getName())
        .build();
  }
}
