package com.project.expensemanage.domain.category.service;

import static com.project.expensemanage.domain.category.exception.CategoryExceptionCode.CATEGORY_NOT_FOUND;

import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryValidService {

  private final CategoryRepository repository;

  public void validCategory(Long categoryId) {
    repository
        .findById(categoryId)
        .orElseThrow(() -> new BusinessLogicException(CATEGORY_NOT_FOUND));
  }

  public Category validCategoryReturnEntity(Long categoryId) {
    return repository
        .findById(categoryId)
        .orElseThrow(() -> new BusinessLogicException(CATEGORY_NOT_FOUND));
  }
}
