package com.project.expensemanage.domain.budget.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.domain.budget.config.BudgetTestConfig;
import com.project.expensemanage.domain.budget.dto.response.BudgetIdResponse;
import com.project.expensemanage.domain.budget.entity.Budget;
import com.project.expensemanage.domain.budget.exception.BudgetExceptionCode;
import com.project.expensemanage.domain.budget.mock.BudgetMock;
import com.project.expensemanage.domain.budget.repository.BudgetRepository;
import com.project.expensemanage.domain.category.exception.CategoryExceptionCode;
import com.project.expensemanage.domain.category.mock.CategoryMock;
import com.project.expensemanage.domain.category.repository.CategoryRepository;
import com.project.expensemanage.domain.user.mock.UserMock;
import java.time.LocalDate;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(BudgetTestConfig.class)
class BudgetServiceTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    BudgetService service;
    @MockBean
    BudgetRepository budgetRepository;
    @MockBean
    CategoryRepository categoryRepository;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    UserMock userMock;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    BudgetMock mock;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    CategoryMock categoryMock;

    @Test
    @DisplayName("예산 설정 테스트")
    void post_budget_success_test() throws Exception {
        // given

        given(categoryRepository.findById(anyLong()))
                .willReturn(Optional.of(categoryMock.standardEntityMock()));
        given(budgetRepository.findByDateAndUserIdAndCategoryId(any(LocalDate.class), anyLong(),
                anyLong())).willReturn(Optional.empty());
        given(budgetRepository.save(any(Budget.class)))
                .willReturn(mock.entityMock());

        // when
        BudgetIdResponse result = service.postBudget(userMock.getUserId(),
                mock.postDtoMock());
        // then
        Assertions.assertThat(result.budgetId()).isNotNull();
    }

    @Test
    @DisplayName("예산 설정 테스트: 실패[예산 이미 존재]")
    void post_budget_fail_budget_already_exist_test() throws Exception {
        // given

        given(categoryRepository.findById(anyLong()))
                .willReturn(Optional.of(categoryMock.standardEntityMock()));
        given(budgetRepository.findByDateAndUserIdAndCategoryId(any(LocalDate.class), anyLong(),
                anyLong())).willReturn(Optional.of(mock.entityMock()));
        given(budgetRepository.save(any(Budget.class)))
                .willReturn(mock.entityMock());

        // when
        // then
        Assertions.assertThatThrownBy(
                        () -> service.postBudget(userMock.getUserId(), mock.postDtoMock()))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage(BudgetExceptionCode.BUDGET_EXIST.getMessage());
    }

    @Test
    @DisplayName("예산 설정 테스트: 실패[카테고리 존재하지 않음]")
    void post_budget_fail_budget_no_have_category_test() throws Exception {
        // given

        given(categoryRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                        () -> service.postBudget(userMock.getUserId(), mock.postDtoMock()))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage(CategoryExceptionCode.CATEGORY_NOT_FOUND.getMessage());
    }
}