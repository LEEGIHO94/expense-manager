package com.project.expensemanage.domain.budget.service;

import static com.project.expensemanage.domain.budget.exception.BudgetExceptionCode.BUDGET_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.domain.budget.config.BudgetTestConfig;
import com.project.expensemanage.domain.budget.controller.dto.response.BudgetResponse;
import com.project.expensemanage.domain.budget.dto.response.BudgetIdResponse;
import com.project.expensemanage.domain.budget.entity.Budget;
import com.project.expensemanage.domain.budget.exception.BudgetExceptionCode;
import com.project.expensemanage.domain.budget.mock.BudgetMock;
import com.project.expensemanage.domain.budget.repository.BudgetRepository;
import com.project.expensemanage.domain.budget.service.dto.RecommendBudget;
import com.project.expensemanage.domain.category.exception.CategoryExceptionCode;
import com.project.expensemanage.domain.category.mock.CategoryMock;
import com.project.expensemanage.domain.category.repository.CategoryRepository;
import com.project.expensemanage.domain.user.exception.UserExceptionCode;
import com.project.expensemanage.domain.user.mock.UserMock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
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

  @MockBean BudgetRepository budgetRepository;
  @MockBean CategoryRepository categoryRepository;

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
  void post_budget_success_test() {
    // given

    given(categoryRepository.findById(anyLong()))
        .willReturn(Optional.of(categoryMock.standardEntityMock()));
    given(
            budgetRepository.findByDateAndUserIdAndCategoryId(
                any(LocalDate.class), anyLong(), anyLong()))
        .willReturn(Optional.empty());
    given(budgetRepository.save(any(Budget.class))).willReturn(mock.entityMock());

    // when
    BudgetIdResponse result = service.postBudget(userMock.getUserId(), mock.postDtoMock());
    // then
    assertThat(result.budgetId()).isNotNull();
  }

  @Test
  @DisplayName("예산 설정 테스트: 실패[예산 이미 존재]")
  void post_budget_fail_budget_already_exist_test() {
    // given

    given(categoryRepository.findById(anyLong()))
        .willReturn(Optional.of(categoryMock.standardEntityMock()));
    given(
            budgetRepository.findByDateAndUserIdAndCategoryId(
                any(LocalDate.class), anyLong(), anyLong()))
        .willReturn(Optional.of(mock.entityMock()));
    given(budgetRepository.save(any(Budget.class))).willReturn(mock.entityMock());

    // when
    // then
    assertThatThrownBy(() -> service.postBudget(userMock.getUserId(), mock.postDtoMock()))
        .isInstanceOf(BusinessLogicException.class)
        .hasMessage(BudgetExceptionCode.BUDGET_EXIST.getMessage());
  }

  @Test
  @DisplayName("예산 설정 테스트: 실패[카테고리 존재하지 않음]")
  void post_budget_fail_budget_no_have_category_test() {
    // given

    given(categoryRepository.findById(anyLong())).willReturn(Optional.empty());

    // when
    // then
    assertThatThrownBy(() -> service.postBudget(userMock.getUserId(), mock.postDtoMock()))
        .isInstanceOf(BusinessLogicException.class)
        .hasMessage(CategoryExceptionCode.CATEGORY_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("카테고리 별 추천 금액 서비스 테스트")
  void get_recommended_amount_for_category_test() {
    // given
    Long totalAmount = 1000000L;
    given(budgetRepository.findTotalAmountByCategory()).willReturn(mock.dbDtoMock());
    // when
    List<RecommendBudget> result = service.getRecommendedAmountForCategory(totalAmount);
    // then
    for (int i = 0; i < result.size(); i++) {
      assertThat(result.get(i).getCategoryId()).isNotNull();
      assertThat(result.get(i).getAmount()).isNotZero();
      assertThat(result.get(i).getCategoryName()).isInstanceOf(String.class);
    }
    long resultSum = result.stream().mapToLong(RecommendBudget::getAmount).sum();
    assertThat(resultSum).isEqualTo(totalAmount);
  }

  @Test
  @DisplayName("예산 수정 테스트 : 성공")
  void update_budget_success_test() {
    // given
    given(budgetRepository.findByBudgetIdAndUserId(anyLong(), anyLong()))
        .willReturn(Optional.of(mock.entityMock()));
    // when
    BudgetIdResponse result =
        service.patchBudget(userMock.getUserId(), mock.getBudgetId(), mock.patchDtoMock());
    // then
    assertThat(result.budgetId()).isNotNull().isEqualTo(mock.getBudgetId());
    verify(budgetRepository, times(1)).findByBudgetIdAndUserId(anyLong(), anyLong());
  }

  @Test
  @DisplayName("예산 수정 테스트 : 실패[예산 조회 실패]")
  void update_budget_failure_test() {
    // given
    given(budgetRepository.findByBudgetIdAndUserId(anyLong(), anyLong()))
        .willReturn(Optional.empty());
    // when
    // then
    assertThatThrownBy(
            () ->
                service.patchBudget(userMock.getUserId(), mock.getBudgetId(), mock.patchDtoMock()))
        .isInstanceOf(BusinessLogicException.class)
        .hasMessage(BudgetExceptionCode.BUDGET_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("예산 다건 조회 테스트 : 성공")
  void get_budget_list_success_test() {
    // given
    given(budgetRepository.findByUserId(anyLong())).willReturn(mock.entityMockList());
    // when
    List<BudgetResponse> result = service.getBudgetList(userMock.getUserId());
    // then
    assertThat(result.stream().map(BudgetResponse::budgetId)).isNotNull().containsExactly(1L, 2L);
  }

  @Test
  @DisplayName("예산 단건 조회 테스트 : 성공")
  void get_budget_success_test() {
    // given
    given(budgetRepository.findById(anyLong())).willReturn(Optional.of(mock.entityMock()));
    // when
    BudgetResponse result = service.getBudget(userMock.getUserId(), mock.getBudgetId());
    // then
    assertThat(result.budgetId()).isEqualTo(mock.getBudgetId());
    assertThat(result.amount()).isEqualTo(100000L);
    assertThat(result.category().categoryId()).isEqualTo(1L);
    assertThat(result.category().categoryName()).isEqualTo("카테고리");
  }

  @Test
  @DisplayName("예산 단건 조회 테스트 : 조회 실패")
  void get_budget_search_fail_test() {
    // given
    given(budgetRepository.findById(anyLong())).willReturn(Optional.empty());
    // when
    // then
    assertThatThrownBy(() -> service.getBudget(userMock.getUserId(), mock.getBudgetId()))
        .isInstanceOf(BusinessLogicException.class)
        .hasMessage(BUDGET_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("예산 단건 조회 테스트 : 사용자 일치 실패, userId 미일치")
  void get_budget_user_access_fail_test() {
    // given
    given(budgetRepository.findById(anyLong())).willReturn(Optional.of(mock.entityMock()));
    // when
    // then
    assertThatThrownBy(() -> service.getBudget(userMock.getUserId() + 1L, mock.getBudgetId()))
        .isInstanceOf(BusinessLogicException.class)
        .hasMessage(UserExceptionCode.USER_NOT_SAME.getMessage());
  }

  @Test
  @DisplayName("예산 삭제 테스트 : 성공")
  void delete_budget_success_test() {
    // given
    BDDMockito.given(budgetRepository.findById(anyLong()))
        .willReturn(Optional.of(mock.entityMock()));
    BDDMockito.willDoNothing().given(budgetRepository).deleteById(anyLong());
    // when
    service.deleteBudget(userMock.getUserId(), mock.getBudgetId());
    // then
    Mockito.verify(budgetRepository, times(1)).findById(anyLong());
    Mockito.verify(budgetRepository, times(1)).deleteById(anyLong());
  }

  @Test
  @DisplayName("예산 삭제 테스트 : 실패[사용자 불일치]")
  void delete_budget_user_access_fail_test() {
    // given
    BDDMockito.given(budgetRepository.findById(anyLong()))
        .willReturn(Optional.of(mock.entityMock()));
    BDDMockito.willDoNothing().given(budgetRepository).deleteById(anyLong());
    // when

    // then
    Assertions.assertThatThrownBy(
            () -> service.deleteBudget(userMock.getUserId() + 1, mock.getBudgetId()))
        .hasMessage(UserExceptionCode.USER_NOT_SAME.getMessage())
        .isInstanceOf(BusinessLogicException.class);
  }

  @Test
  @DisplayName("예산 삭제 테스트 : 성공[조회된 데이터 없을 경우 넘어감]")
  void delete_budget_not_research_budget_test() {
    // given
    BDDMockito.given(budgetRepository.findById(anyLong())).willReturn(Optional.empty());
    // when
    service.deleteBudget(userMock.getUserId(), mock.getBudgetId());
    // then
    Mockito.verify(budgetRepository, times(1)).findById(anyLong());
  }
}
