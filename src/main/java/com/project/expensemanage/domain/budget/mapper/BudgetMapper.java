package com.project.expensemanage.domain.budget.mapper;

import com.project.expensemanage.domain.budget.dto.response.BudgetIdResponse;
import com.project.expensemanage.domain.budget.entity.Budget;
import com.project.expensemanage.domain.budget.dto.request.PostBudgetRequest;
import com.project.expensemanage.domain.category.mapper.CategoryMapper;
import com.project.expensemanage.domain.user.mapper.UserMapper;
import com.project.expensemanage.domain.vo.Price;
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
