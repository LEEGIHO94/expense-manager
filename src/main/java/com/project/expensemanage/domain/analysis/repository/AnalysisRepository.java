package com.project.expensemanage.domain.analysis.repository;

import static com.project.expensemanage.domain.expenditure.entity.QExpenditure.*;

import com.project.expensemanage.domain.analysis.repository.dto.ExpenditureDiff;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnalysisRepository {
  private final JPAQueryFactory query;

  public List<ExpenditureDiff> getRateOfExpenditureDiff(Long userId, LocalDate startDate, LocalDate endDate) {
    return query
        .select(
            Projections.constructor(
                ExpenditureDiff.class, expenditure.category.id, expenditure.category.name,
                expenditure.price.value.sum()
                ))
        .from(expenditure)
        .where(
            userIdEq(userId),
            expendDateRange(startDate,endDate)
            )
        .groupBy(
            expenditure.category.id
        )
        .fetch();
  }

  private BooleanExpression userIdEq(Long userId) {
    return expenditure.user.id.eq(userId);
  }

  //LocalDate를 기준으로 현재 시간과 이전 시간
  private BooleanExpression expendDateRange(LocalDate startDate, LocalDate endDate) {
    return expenditure.expendedDate.between(startDate, endDate);
  }
}
