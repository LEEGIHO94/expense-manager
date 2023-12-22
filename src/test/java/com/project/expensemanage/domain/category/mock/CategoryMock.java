package com.project.expensemanage.domain.category.mock;

import com.project.expensemanage.domain.category.dto.request.PostCustomCategoryRequest;
import com.project.expensemanage.domain.category.dto.request.PostStandardCategoryRequest;
import com.project.expensemanage.domain.category.dto.response.CategoryIdResponse;
import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.domain.category.enums.CategoryType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CategoryMock {
  private final String name = "교통비";
  private final Long id = 100L;
  private final String customName = "사용자 설정 카테고리";
  private final List<String> nameList = List.of("교통비", "식비", "관광비", "경조사비", "취미생활비");

  public Category customEntityMock() {
    return Category.builder().categoryType(CategoryType.CUSTOM).name(customName).id(id).build();
  }

  public List<Category> EntityListMock() {
    List<Category> result = new ArrayList<>();
    for (int i = 1; i <= nameList.size(); i++) {
      Category category =
          Category.builder()
              .categoryType(CategoryType.CUSTOM)
              .name(nameList.get(i - 1))
              .id((long) i)
              .build();
      result.add(category);
    }
    return result;
  }

  public Category standardEntityMock() {
    return Category.builder().categoryType(CategoryType.STANDARD).name(name).id(id).build();
  }

  public Category standardEntityPostMock() {
    return Category.builder().categoryType(CategoryType.STANDARD).name(name).build();
  }

  public PostCustomCategoryRequest customCategoryPostDto() {
    return new PostCustomCategoryRequest(customName, null);
  }

  public PostStandardCategoryRequest standardCategoryPostDto() {
    return new PostStandardCategoryRequest(name, null);
  }

  public List<String> getNameList() {
    return nameList;
  }

  public CategoryIdResponse getIdDto() {
    return new CategoryIdResponse(2L);
  }
}
