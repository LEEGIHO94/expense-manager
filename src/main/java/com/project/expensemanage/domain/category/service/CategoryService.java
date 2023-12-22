package com.project.expensemanage.domain.category.service;

import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.domain.category.dto.GetCategoryResponse;
import com.project.expensemanage.domain.category.dto.request.PostStandardCategoryRequest;
import com.project.expensemanage.domain.category.dto.response.CategoryIdResponse;
import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.domain.category.exception.CategoryExceptionCode;
import com.project.expensemanage.domain.category.mapper.CategoryMapper;
import com.project.expensemanage.domain.category.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository repository;
  private final CategoryMapper mapper;

  public CategoryIdResponse postCategory(PostStandardCategoryRequest post) {
    validCategoryExist(post.name());
    Category savedEntity = repository.save(mapper.toEntity(post));
    return mapper.toDto(savedEntity);
  }

  private void validCategoryExist(String name) {
    repository
        .findByName(name)
        .ifPresent(
            d -> {
              throw new BusinessLogicException(CategoryExceptionCode.CATEGORY_EXIST);
            });
  }

  public List<Category> getCategoryList() {
    return repository.findAllByType();
  }

  public GetCategoryResponse getCategory(Long categoryId) {
    return null;
  }
}
