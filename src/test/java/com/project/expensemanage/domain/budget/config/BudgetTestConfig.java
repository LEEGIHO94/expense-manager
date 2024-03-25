package com.project.expensemanage.domain.budget.config;

import com.project.expensemanage.domain.budget.mapper.BudgetMapper;
import com.project.expensemanage.domain.budget.mock.BudgetMock;
import com.project.expensemanage.domain.budget.repository.BudgetRepository;
import com.project.expensemanage.domain.budget.service.BudgetService;
import com.project.expensemanage.domain.category.mock.CategoryMock;
import com.project.expensemanage.domain.category.repository.CategoryRepository;
import com.project.expensemanage.domain.category.service.CategoryValidService;
import com.project.expensemanage.domain.user.mock.UserMock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class BudgetTestConfig {

  @Bean
  public BudgetMock budgetMock() {
    return new BudgetMock();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public UserMock userMock() {
    return new UserMock(passwordEncoder());
  }

  @Bean
  public BudgetRepository budgetRepository() {
    return Mockito.mock(BudgetRepository.class);
  }

  @Bean
  public BudgetService budgetService() {
    return new BudgetService(budgetRepository(), categoryValidService(), budgetMapper());
  }

  @Bean
  public CategoryValidService categoryValidService() {
    return new CategoryValidService(categoryRepository());
  }

  @Bean
  public CategoryRepository categoryRepository() {
    return Mockito.mock(CategoryRepository.class);
  }

  @Bean
  public BudgetMapper budgetMapper() {
    return new BudgetMapper();
  }

  @Bean
  public CategoryMock categoryMock() {
    return new CategoryMock();
  }
}
