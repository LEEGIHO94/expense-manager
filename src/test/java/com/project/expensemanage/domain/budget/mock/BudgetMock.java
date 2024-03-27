package com.project.expensemanage.domain.budget.mock;

import com.project.expensemanage.domain.budget.controller.dto.response.BudgetResponse;
import com.project.expensemanage.domain.budget.dto.request.PatchBudgetRequest;
import com.project.expensemanage.domain.budget.dto.request.PostBudgetRequest;
import com.project.expensemanage.domain.budget.dto.response.BudgetIdResponse;
import com.project.expensemanage.domain.budget.entity.Budget;
import com.project.expensemanage.domain.budget.entity.TotalBudget;
import com.project.expensemanage.domain.budget.repository.dto.RecommendedBudgetData;
import com.project.expensemanage.domain.budget.service.dto.RecommendBudget;
import com.project.expensemanage.domain.category.dto.CategoryByBudget;
import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.domain.category.enums.CategoryType;
import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.vo.Phone;
import com.project.expensemanage.domain.vo.Price;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BudgetMock {

  private final Long id = 1L;
  private final Long categoryId = 1L;
  private final Long amount = 100000L;
  private final Long patchedAmount = 200000L;
  private final Price price = new Price(amount);
  private final LocalDate date = LocalDate.of(2030, 1, 1);
  private final User serviceUser = entity();
  private final User requestUser = postEntity();
  private final Category category = categoryEntity();

  public Budget entityMock() {
    return Budget.builder()
        .id(id)
        .price(price)
        .category(category)
        .date(date)
        .user(serviceUser)
        .build();
  }

  public Budget entityPostMock() {
    return Budget.builder().price(price).date(date).user(requestUser).build();
  }

  public Category categoryEntity() {
    return new Category(categoryId, "카테고리", CategoryType.STANDARD, new TotalBudget());
  }

  public PostBudgetRequest postDtoMock() {
    return PostBudgetRequest.builder()
        .budgetDate(date)
        .amount(amount)
        .categoryId(categoryId)
        .build();
  }

  public BudgetIdResponse idDtoMock() {
    return BudgetIdResponse.builder().budgetId(id).build();
  }

  public LocalDate getDate() {
    return date;
  }

  private User entity() {
    return User.builder()
        .phone(new Phone("010-1234-1234"))
        .email("email001@gmail.com")
        .id(1L)
        .password("encryptedPassword001")
        .build();
  }

  private User postEntity() {
    return User.builder().id(1L).build();
  }

  public List<RecommendedBudgetData> dbDtoMock() {
    List<RecommendedBudgetData> result = new ArrayList<>();
    for (Long i = 1L; i < 5; i++) {
      result.add(new RecommendedBudgetData(i, "카테고리 이름" + i, 10000 * i));
    }
    result.add(new RecommendedBudgetData(5L, "기타", 10000L));
    return result;
  }

  public PatchBudgetRequest patchDtoMock() {
    return PatchBudgetRequest.builder().amount(patchedAmount).categoryId(categoryId).build();
  }

  public Long getBudgetId() {
    return id;
  }

  public List<BudgetResponse> getDtoMock() {
    return List.of(
        BudgetResponse.builder()
            .budgetId(1L)
            .amount(100000L)
            .date(LocalDate.now())
            .category(new CategoryByBudget(4L, "카테고리4"))
            .build(),
        BudgetResponse.builder()
            .budgetId(2L)
            .amount(200000L)
            .date(LocalDate.now())
            .category(new CategoryByBudget(5L, "카테고리5"))
            .build());
  }

  public List<RecommendBudget> getDto() {
    return List.of(
        RecommendBudget.builder().categoryName("카테고리1").categoryId(1L).amount(10000L).build(),
        RecommendBudget.builder().categoryName("카테고리2").categoryId(2L).amount(20000L).build(),
        RecommendBudget.builder().categoryName("카테고리3").categoryId(3L).amount(30000L).build());
  }

  public List<Budget> entityMockList() {
    return List.of(
        Budget.builder()
            .id(id)
            .category(new Category(categoryId, "카테고리1", CategoryType.STANDARD, new TotalBudget()))
            .price(price)
            .date(date)
            .user(serviceUser)
            .build(),
        Budget.builder()
            .id(id + 1)
            .price(price)
            .category(
                new Category(categoryId + 1, "카테고리2", CategoryType.STANDARD, new TotalBudget()))
            .date(date)
            .user(serviceUser)
            .build());
  }
}
