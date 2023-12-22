package com.project.expensemanage.domain.expenditure.mock;

import com.project.expensemanage.domain.category.dto.GetCategoryResponse;
import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.domain.expenditure.controller.dto.request.PostExpenditureRequest;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureIdResponse;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureResponse;
import com.project.expensemanage.domain.expenditure.enums.ExcludeSpendingTotal;
import com.project.expensemanage.domain.user.entity.User;
import java.time.LocalDate;
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
        .category(new GetCategoryResponse(1L,"카테고리 이름1"))
        .build();
  }
}