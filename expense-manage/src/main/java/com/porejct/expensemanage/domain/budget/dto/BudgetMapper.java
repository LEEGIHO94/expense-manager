package com.porejct.expensemanage.domain.budget.dto;

import com.porejct.expensemanage.domain.budget.dto.response.BudgetIdResponse;
import com.porejct.expensemanage.domain.budget.entity.Budget;
import com.porejct.expensemanage.domain.budget.dto.request.PostBudgetRequest;
import com.porejct.expensemanage.domain.category.mapper.CategoryMapper;
import com.porejct.expensemanage.domain.user.mapper.UserMapper;
import com.porejct.expensemanage.domain.vo.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BudgetMapper {

    public Budget toEntity(Long userId, PostBudgetRequest post) {
        return Budget.builder()
                .date(post.budgetDate())
                .price(new Price(post.amount()))
                .user(UserMapper.toIdEntity(userId))
                .category(CategoryMapper.toIdEntity(post.categoryId()))
                .build();
    }


    public BudgetIdResponse toDto(Budget budget) {
        return BudgetIdResponse.builder()
                .budgetId(budget.getId())
                .build();
    }
}
