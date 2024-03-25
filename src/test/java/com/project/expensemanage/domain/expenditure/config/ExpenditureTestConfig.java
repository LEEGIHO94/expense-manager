package com.project.expensemanage.domain.expenditure.config;

import com.project.expensemanage.ExpenseManageApplication;
import com.project.expensemanage.domain.category.mock.CategoryMock;
import com.project.expensemanage.domain.category.repository.CategoryRepository;
import com.project.expensemanage.domain.category.service.CategoryValidService;
import com.project.expensemanage.domain.expenditure.mapper.ExpenditureMapper;
import com.project.expensemanage.domain.expenditure.mock.ExpenditureMock;
import com.project.expensemanage.domain.expenditure.repoistory.ExpenditureRepository;
import com.project.expensemanage.domain.expenditure.service.ExpenditureService;
import com.project.expensemanage.domain.user.mock.UserMock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ExpenditureTestConfig {
  @Bean
  public ExpenditureMapper expenditureMapper() {
    return new ExpenditureMapper();
  }

  @Bean
  public ExpenditureMock expenditureMock() {
    return new ExpenditureMock();
  }

  @Bean
  public ExpenditureRepository expenditureRepository() {
    return Mockito.mock(ExpenditureRepository.class);
  }

  @Bean
  public ExpenditureService expenditureService() {
    return new ExpenditureService(
        expenditureRepository(), expenditureMapper(), categoryValidService());
  }

  @Bean
  public CategoryMock categoryMock() {
    return new CategoryMock();
  }

  @Bean
  public CategoryValidService categoryValidService() {
    return new CategoryValidService(categoryRepository());
  }

  @Bean
  public CategoryRepository categoryRepository() {
    return Mockito.mock(CategoryRepository.class);
  }
}
