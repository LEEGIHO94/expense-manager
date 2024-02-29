package com.project.expensemanage.domain.expenditure.mock;

import com.project.expensemanage.domain.category.dto.CategoryByExpenditure;
import com.project.expensemanage.domain.category.dto.GetCategoryResponse;
import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.domain.expenditure.controller.dto.request.GetExpenditureList;
import com.project.expensemanage.domain.expenditure.controller.dto.request.PostExpenditureRequest;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureIdResponse;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureListResponse;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureListResponse.ExpenditureCategory;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureResponse;
import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import com.project.expensemanage.domain.expenditure.enums.ExcludeSpendingTotal;
import com.project.expensemanage.domain.expenditure.repoistory.dto.TotalExpenditureByCategory;
import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.vo.Price;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ExpenditureMock {
  private Long expenditureId = 1L;
  private LocalDate expenditureDate = LocalDate.now();
  private Long amount = 100000L;
  private String memo = "메모";
  private ExcludeSpendingTotal include = ExcludeSpendingTotal.INCLUDE;
  private ExcludeSpendingTotal exclude = ExcludeSpendingTotal.EXCLUDE;
  private User user = User.builder().id(1L).build();
  private Category category = Category.builder().id(1L).build();

  public ExpenditureIdResponse getIdMock() {
    return ExpenditureIdResponse.builder().expenditureId(expenditureId).build();
  }

  public PostExpenditureRequest getPostDtoMock() {
    return PostExpenditureRequest.builder()
        .expendedAmount(10000L)
        .memo(memo)
        .categoryId(1L)
        .expendedDate(LocalDate.now())
        .build();
  }

  public PostExpenditureRequest getPostDtoMockDateBadValidation() {
    return PostExpenditureRequest.builder()
        .expendedAmount(10000L)
        .memo(memo)
        .categoryId(1L)
        .expendedDate(LocalDate.now().minusMonths(1))
        .build();
  }

  public PostExpenditureRequest getPostDtoMockAmountBadValidation() {
    return PostExpenditureRequest.builder()
        .expendedAmount(-10000L)
        .memo(memo)
        .categoryId(1L)
        .expendedDate(LocalDate.now())
        .build();
  }

  public Long getId() {
    return expenditureId;
  }

  public ExpenditureResponse getDtoMock() {
    return ExpenditureResponse.builder()
        .expenditureId(expenditureId)
        .amount(amount)
        .memo(memo)
        .expendedDate(expenditureDate)
        .category(new GetCategoryResponse(1L, "카테고리 이름1"))
        .build();
  }

  public ExpenditureListResponse getExpenditureListResponse() {
    return new ExpenditureListResponse(getExpendtirueCategoryList());
  }

  public List<ExpenditureCategory> getExpendtirueCategoryList() {
    return List.of(
        new ExpenditureCategory(
            1L, LocalDate.now(), 10000L, new CategoryByExpenditure(1L, "카테고리 이름1", 1000000L)),
        new ExpenditureCategory(
            2L, LocalDate.now(), 20000L, new CategoryByExpenditure(2L, "카테고리 이름2", 2000000L)));
  }

  public Expenditure getEntity() {
    return Expenditure.builder()
        .id(expenditureId)
        .expendedDate(expenditureDate)
        .category(category)
        .memo(memo)
        .excludeSpendingTotal(include)
        .price(new Price(amount))
        .user(user)
        .build();
  }

  public List<Expenditure> getEntityList() {
    return List.of(getEntity());
  }

  public List<TotalExpenditureByCategory> getTotalExpenditureByCategory() {
    return List.of(new TotalExpenditureByCategory(1L, "카테고리이름1", 10000L));
  }

  public GetExpenditureList getGetExpenditureListDto() {
    return new GetExpenditureList(
        1L, LocalDate.of(2020, 2, 1), LocalDate.of(2020, 3, 1), 1000L, 10000000L);
  }
}
