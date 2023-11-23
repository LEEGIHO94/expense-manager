package com.project.expensemanage.domain.expenditure.mapper;

import com.project.expensemanage.domain.category.dto.GetCategoryResponse;
import com.project.expensemanage.domain.category.mapper.CategoryMapper;
import com.project.expensemanage.domain.expenditure.controller.dto.request.PostExpenditureRequest;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureIdResponse;
import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import com.project.expensemanage.domain.expenditure.enums.ExcludeSpendingTotal;
import com.project.expensemanage.domain.expenditure.service.ExpenditureResponse;
import com.project.expensemanage.domain.user.mapper.UserMapper;
import com.project.expensemanage.domain.vo.Price;
import org.springframework.stereotype.Component;

@Component
public class ExpenditureMapper {

    public Expenditure toEntity(PostExpenditureRequest dto,Long userId) {
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
        return ExpenditureIdResponse.builder()
                .expenditureId(entity.getId())
                .build();
    }

    public ExpenditureResponse toDto(Expenditure entity) {
        return ExpenditureResponse.builder()
                .expenditureId(entity.getId())
                .amount(entity.getPrice().getValue())
                .expendedDate(entity.getExpendedDate())
                .memo(entity.getMemo())
                .category(new GetCategoryResponse(entity.getCategory().getId(),entity.getCategory().getName()))
                .build();
    }
}
