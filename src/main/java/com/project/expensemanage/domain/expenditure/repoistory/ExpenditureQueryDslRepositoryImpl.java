package com.project.expensemanage.domain.expenditure.repoistory;

import static com.project.expensemanage.domain.category.entity.QCategory.category;
import static com.project.expensemanage.domain.expenditure.entity.QExpenditure.expenditure;
import static com.project.expensemanage.domain.expenditure.enums.ExcludeSpendingTotal.*;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import com.project.expensemanage.domain.expenditure.repoistory.dto.GetExpenditureDetailsCondition;
import com.project.expensemanage.domain.expenditure.repoistory.dto.TotalExpenditureByCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ExpenditureQueryDslRepositoryImpl implements ExpenditureQueryDslRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Expenditure> findAllExpenditureByCondition(
            GetExpenditureDetailsCondition condition) {
        return query
                .selectFrom(expenditure)
                .join(
                        category)
                .where(
                        expendDateRange(condition.startDate(), condition.endDate()),
                        userIdEq(condition.userId()),
                        categoryIdEq(condition.categoryId()),
                        amountBetweenRequest(condition.minAmount(), condition.maxAmount())
                ).fetch();
    }

    @Override
    public List<TotalExpenditureByCategory> findTotalExpenditureByCategory(
            GetExpenditureDetailsCondition condition) {
        return query
                .select(Projections.constructor(TotalExpenditureByCategory.class,
                        expenditure.category.id.as("categoryId"),
                        expenditure.category.name.as("categoryName"),
                        expenditure.price.value.sum().as("amount")
                        ))
                .where(
                        expenditureSumIncludeCondition(),
                        userIdEq(condition.userId()),
                        categoryIdEq(condition.categoryId())
                        )
                .groupBy(expenditure.category.id)
                .fetch();
    }

    @Override
    public List<TotalExpenditureByCategory> findDailyTotalExpenditureByUserId(Long userId,LocalDate date){
        return query
            .select(Projections.constructor(TotalExpenditureByCategory.class,
                expenditure.category.id.as("categoryId"),
                expenditure.category.name.as("categoryName"),
                expenditure.price.value.sum().as("amount")))
            .from(expenditure)
            .where(
                dailyExpendEq(date),
                userIdEq(userId)
            )
            .groupBy()
            .fetch();
    }

    private static BooleanExpression expenditureSumIncludeCondition() {
        return expenditure.excludeSpendingTotal.eq(INCLUDE);
    }

    //필수 값
    private BooleanExpression expendDateRange(LocalDate startDate, LocalDate endDate) {
        return expenditure.expendedDate.between(startDate, endDate);
    }

    private BooleanExpression dailyExpendEq(LocalDate date){
        return expenditure.expendedDate.eq(date);
    }

    private BooleanExpression userIdEq(Long userId) {
        return expenditure.user.id.eq(userId);
    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        return isEmpty(categoryId) ? null : expenditure.category.id.eq(categoryId);
    }

    private BooleanExpression amountBetweenRequest(Long min, Long max) {
        if (isEmpty(min) && isEmpty(max)) {
            return null;
        }
        if (isEmpty(min) && !isEmpty(max)) {
            expenditure.price.value.loe(max);
        }
        if (!isEmpty(min) && isEmpty(max)) {
            expenditure.price.value.goe(min);
        }
        return expenditure.price.value.between(min, max);
    }
}

/*
* 1. 특정 기간 조회[시작일 ~ 끝일] **필수**
2. 특정 카테고리 조회 **선택**
3. 최대 최소 금액 조회 **선택**
* */