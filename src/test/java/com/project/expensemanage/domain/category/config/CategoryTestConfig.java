package com.project.expensemanage.domain.category.config;

import com.project.expensemanage.domain.category.mapper.CategoryMapper;
import com.project.expensemanage.domain.category.mock.CategoryMock;
import com.project.expensemanage.domain.category.repository.CategoryRepository;
import com.project.expensemanage.domain.category.service.CategoryService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CategoryTestConfig {
  @Bean
  public CategoryMock categoryMock() {
    return new CategoryMock();
  }

  @Bean
  public CategoryMapper categoryMapper() {
    return new CategoryMapper();
  }

  @Bean
  public CategoryRepository categoryRepository() {
    return Mockito.mock(CategoryRepository.class);
  }

  @Bean
  public CategoryService categoryService() {
    return new CategoryService(categoryRepository(), categoryMapper());
  }
}
