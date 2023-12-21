package com.project.expensemanage.domain.budget.service;

import static com.project.expensemanage.domain.budget.exception.BudgetExceptionCode.BUDGET_EXIST;
import static com.project.expensemanage.domain.budget.exception.BudgetExceptionCode.BUDGET_NOT_FOUND;

import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.domain.budget.controller.dto.response.BudgetResponse;
import com.project.expensemanage.domain.budget.dto.request.PatchBudgetRequest;
import com.project.expensemanage.domain.budget.dto.request.PostBudgetRequest;
import com.project.expensemanage.domain.budget.dto.response.BudgetIdResponse;
import com.project.expensemanage.domain.budget.entity.Budget;
import com.project.expensemanage.domain.budget.mapper.BudgetMapper;
import com.project.expensemanage.domain.budget.repository.BudgetRepository;
import com.project.expensemanage.domain.budget.service.dto.RecommendBudget;
import com.project.expensemanage.domain.budget.service.dto.RecommendBudgetHelper;
import com.project.expensemanage.domain.category.service.CategoryValidService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetService {

  private final BudgetRepository repository;
  private final CategoryValidService categoryValid;
  private final BudgetMapper mapper;

  public BudgetIdResponse postBudget(Long userId, PostBudgetRequest post) {
    validBudget(userId, post);
    Budget save = repository.save(mapper.toEntity(userId, post));

    return mapper.toDto(save);
  }

  /*
   * 카테고리가 없으면 예외 발생
   * */
  public void validBudget(Long userId, PostBudgetRequest post) {
    categoryValid.validCategory(post.categoryId());
    validBudgetExist(userId, post);
  }

  public BudgetIdResponse patchBudget(Long userId, Long budgetId, PatchBudgetRequest patch) {
    Budget result = patch(findBudget(userId, budgetId), patch);
    return mapper.toDto(result);
  }

  public List<BudgetResponse> getBudget(Long userId) {
    return mapper.toDto(repository.findByUserId(userId));
  }

  private Budget patch(Budget entity, PatchBudgetRequest patch) {
    Optional.ofNullable(patch.amount()).ifPresent(entity::updatePrice);
    return entity;
  }

  private Budget findBudget(Long userId, Long budgetId) {
    return repository
        .findByBudgetIdAndUserId(budgetId, userId)
        .orElseThrow(() -> new BusinessLogicException(BUDGET_NOT_FOUND));
  }

  public List<RecommendBudget> getRecommendedAmountForCategory(Long totalAmount) {
    return new RecommendBudgetHelper(repository.findTotalAmountByCategory(), totalAmount)
        .getRecommendedData();
  }

  // 예산이 이미 존재한다면 예외 발생
  private void validBudgetExist(Long userId, PostBudgetRequest post) {
    repository
        .findByDateAndUserIdAndCategoryId(post.budgetDate(), post.categoryId(), userId)
        .ifPresent(
            d -> {
              throw new BusinessLogicException(BUDGET_EXIST);
            });
  }
}
