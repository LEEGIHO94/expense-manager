package com.project.expensemanage.domain.analysis.repository;

import com.project.expensemanage.commone.config.QueryDslConfig;
import com.project.expensemanage.domain.analysis.repository.dto.ExpenditureDiff;
import com.project.expensemanage.domain.analysis.repository.dto.ExpenditureTotalUser;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({AnalysisQueryDslRepositoryImpl.class, QueryDslConfig.class})
class AnalysisRepositoryTest {

  @Container static final MySQLContainer container = new MySQLContainer("mysql:8");

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  AnalysisRepository repository;

  @Test
  @DisplayName("전체 사용자 및 사용자의 금일 전체 예산 조회 테스트")
  void getTotalExpenditureAllUserAndUser_success_test() {
    // given
    // when
    ExpenditureTotalUser result =
        repository.getTotalExpenditureAllUserAndUser(1L, LocalDate.of(2020, 1, 1));
    // then

    Assertions.assertThat(result.totalExpenditure()).isPositive();
    Assertions.assertThat(result.userExpenditure()).isPositive();
    Assertions.assertThat(result.userExpenditure()).isLessThan(result.totalExpenditure());
  }

  @Test
  @DisplayName("사용자 예산 지출 비용 차 조회")
  void getExpenditureSumDuringDateRange_success_test() {
    // given
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 2, 1);
    // when
    List<ExpenditureDiff> result =
        repository.getExpenditureSumDuringDateRange(1L, startDate, endDate);
    // then
    Assertions.assertThat(result).isNotEmpty();
    for (ExpenditureDiff data : result) {
      Assertions.assertThat(data.categoryId()).isBetween(1L, 6L);
      Assertions.assertThat(data.expenditure()).isPositive();
    }
  }
}
