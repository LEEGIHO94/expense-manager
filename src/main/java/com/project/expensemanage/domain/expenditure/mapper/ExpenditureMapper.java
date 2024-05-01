package com.project.expensemanage.domain.expenditure.mapper;

import static com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureListResponse.ExpenditureCategory;

import com.project.expensemanage.domain.category.dto.CategoryByExpenditure;
import com.project.expensemanage.domain.category.dto.GetCategoryResponse;
import com.project.expensemanage.domain.category.mapper.CategoryMapper;
import com.project.expensemanage.domain.expenditure.controller.dto.request.GetExpenditureList;
import com.project.expensemanage.domain.expenditure.controller.dto.request.PostExpenditureRequest;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureIdResponse;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureListResponse;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureResponse;
import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import com.project.expensemanage.domain.expenditure.enums.ExcludeSpendingTotal;
import com.project.expensemanage.domain.expenditure.repoistory.dto.GetExpenditureDetailsCondition;
import com.project.expensemanage.domain.expenditure.repoistory.dto.TotalExpenditureByCategory;
import com.project.expensemanage.domain.user.mapper.UserMapper;
import com.project.expensemanage.domain.vo.Price;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ExpenditureMapper {

  public Expenditure toEntity(PostExpenditureRequest dto, Long userId) {
    return Expenditure.builder()
        .expendedDate(dto.expendedDate())
        .price(new Price(dto.expendedAmount()))
        .user(UserMapper.toIdEntity(userId))
        .memo(dto.memo())
        .category(CategoryMapper.toIdEntity(dto.categoryId()))
        .excludeSpendingTotal(ExcludeSpendingTotal.INCLUDE)
        .build();
  }

  public ExpenditureIdResponse toIdDto(Expenditure entity) {
    return ExpenditureIdResponse.builder().expenditureId(entity.getId()).build();
  }

  public ExpenditureResponse toDto(Expenditure entity) {
    return ExpenditureResponse.builder()
        .expenditureId(entity.getId())
        .amount(entity.getPrice().getValue())
        .expendedDate(entity.getExpendedDate())
        .memo(entity.getMemo())
        .category(
            new GetCategoryResponse(entity.getCategory().getId(), entity.getCategory().getName()))
        .build();
  }

  public GetExpenditureDetailsCondition toRepositoryDto(GetExpenditureList dto, Long userId) {
    return GetExpenditureDetailsCondition.builder()
        .endDate(dto.endDate())
        .maxAmount(dto.maxAmount())
        .minAmount(dto.minAmount())
        .startDate(dto.startDate())
        .userId(userId)
        .categoryId(dto.categoryId())
        .build();
  }

  public ExpenditureListResponse toDto(
      List<Expenditure> expenditureList, List<TotalExpenditureByCategory> categoryList) {
    List<ExpenditureCategory> result = new ArrayList<>();
    HashMap<Long, TotalExpenditureByCategory> categoryMap = new HashMap<>();

    for (TotalExpenditureByCategory category : categoryList) {
      categoryMap.put(category.categoryId(), category);
    }

    for (int i = 0; i < expenditureList.size(); i++) {
      result.add(
          createExpenditureCategory(
              expenditureList.get(i), categoryMap.get(expenditureList.get(i).getCategory().getId())));
    }
    return new ExpenditureListResponse(result);
  }

  private ExpenditureCategory createExpenditureCategory(
      Expenditure expenditure, TotalExpenditureByCategory category) {
    return ExpenditureCategory.builder()
        .expenditureId(expenditure.getId())
        .expendedDate(expenditure.getExpendedDate())
        .amount(expenditure.getPrice().getValue())
        .category(
            new CategoryByExpenditure(
                category.categoryId(), category.categoryName(), category.amount()))
        .build();
  }
}
