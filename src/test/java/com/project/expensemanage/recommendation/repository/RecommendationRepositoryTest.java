package com.project.expensemanage.recommendation.repository;

import com.project.expensemanage.commone.config.QueryDslConfig;
import com.project.expensemanage.domain.budget.repository.BudgetRepository;
import com.project.expensemanage.domain.expenditure.repoistory.ExpenditureRepository;
import com.project.expensemanage.notification.recommendation.dto.RecommendationExpenditure;
import com.project.expensemanage.notification.recommendation.dto.RecommendationExpenditureAllUser;
import com.project.expensemanage.notification.recommendation.repository.RecommendationRepository;
import com.project.expensemanage.recommendation.config.TestSQLUtils;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({QueryDslConfig.class, TestSQLUtils.class})
class RecommendationRepositoryTest {
  @Autowired RecommendationRepository repository;
  @Autowired BudgetRepository budgetRepository;
  @Autowired ExpenditureRepository expenditureRepository;
  @Autowired TestSQLUtils sql;

  @Test
  @Disabled("삽입한 데이터가 많아 테스트 중지 중")
  @DisplayName("조회가 성공 하는지 부터 테스트 해보자")
  void recommend_date_test() {
    LocalDate startDate = LocalDate.of(2024, 01, 01);
    LocalDate endDate = LocalDate.of(2024, 01, 01);
    long userId = 1L;
    List<RecommendationExpenditure> result =
        repository.findTotalExpenditureByCategoryAndDateAndId(startDate, endDate, userId);

    result.forEach(
        data ->
            Assertions.assertThat(data.totalExpenditure())
                .isEqualTo(
                    sql.getTotalExpenditureByCategoryAndUser(
                        userId, data.categoryId(), startDate, endDate)));
  }

  @Test
  @Disabled("삽입한 데이터가 많아 테스트 중지 중")
  @DisplayName("조회가 성공 하는지 부터 테스트 해보자")
  void recommend_date_all_user_test() {
    LocalDate startDate = LocalDate.of(2020, 01, 01);
    LocalDate endDate = LocalDate.of(2020, 01, 01);
    long userId = 1L;
    List<RecommendationExpenditureAllUser> result =
        repository.findTotalExpenditureByCategoryAndDate(startDate, endDate);

    result.forEach(
        data ->
            Assertions.assertThat(data.totalExpenditure())
                .isEqualTo(
                    sql.getTotalExpenditureByCategoryAndUser(
                        data.userId(), data.categoryId(), startDate, endDate)));
  }
}
