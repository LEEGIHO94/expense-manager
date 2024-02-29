package com.project.expensemanage.domain.expenditure.repoistory;

import com.project.expensemanage.commone.config.QueryDslConfig;
import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import com.project.expensemanage.domain.expenditure.repoistory.dto.GetExpenditureDetailsCondition;
import com.project.expensemanage.domain.expenditure.repoistory.dto.TotalExpenditureByCategory;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({ExpenditureQueryDslRepositoryImpl.class, QueryDslConfig.class})
class ExpenditureRepositoryTest {
  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  ExpenditureRepository repository;

  GetExpenditureDetailsCondition condition;

  @BeforeEach
  void init() {
    condition =
        GetExpenditureDetailsCondition.builder()
            .userId(1L)
            .startDate(LocalDate.of(2010, 1, 1))
            .endDate(LocalDate.of(2011, 1, 1))
            .minAmount(1000L)
            .maxAmount(100000L)
            .build();
  }

  @Test
  @DisplayName("조건에 따른 조회 Expenditure 데이터 조회")
  void get_expenditure_by_condition_test() {
    // given

    // when
    List<Expenditure> result = repository.findAllExpenditureByCondition(condition);
    // then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isInstanceOf(List.class);
    for (Expenditure expenditure : result) {
      Assertions.assertThat(expenditure.getPrice().getValue()).isBetween(1000L,100000L);
    }
  }
  @Test
  @DisplayName("조건에 따른 조회 Expenditure 데이터 조회 : min 값 미 등록")
  void get_expenditure_by_condition_min_null_test() {
    // given
    condition =
        GetExpenditureDetailsCondition.builder()
            .userId(1L)
            .startDate(LocalDate.of(2020, 1, 1))
            .endDate(LocalDate.of(2031, 1, 1))
            .maxAmount(100000L)
            .build();
    // when
    List<Expenditure> result = repository.findAllExpenditureByCondition(condition);
    // then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isInstanceOf(List.class);
    for (Expenditure expenditure : result) {
      Assertions.assertThat(expenditure.getPrice().getValue()).isLessThanOrEqualTo(100000L);
    }
  }
  @Test
  @DisplayName("조건에 따른 조회 Expenditure 데이터 조회 : max 값 미 등록")
  void get_expenditure_by_condition_max_null_test() {
    // given
    condition =
        GetExpenditureDetailsCondition.builder()
            .userId(1L)
            .startDate(LocalDate.of(2020, 1, 1))
            .endDate(LocalDate.of(2021, 1, 1))
            .minAmount(1000L)
            .build();
    // when
    List<Expenditure> result = repository.findAllExpenditureByCondition(condition);
    // then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isInstanceOf(List.class);

    for (Expenditure expenditure : result) {
      Assertions.assertThat(expenditure.getPrice().getValue()).isGreaterThanOrEqualTo(1000L);
    }
  }
  @Test
  @DisplayName("조건에 따른 조회 Expenditure 데이터 조회 : max, min null")
  void get_expenditure_by_condition_max_min_null_test() {
    // given
    condition =
        GetExpenditureDetailsCondition.builder()
            .userId(1L)
            .startDate(LocalDate.of(2010, 1, 1))
            .endDate(LocalDate.of(2011, 1, 1))
            .build();
    // when
    List<Expenditure> result = repository.findAllExpenditureByCondition(condition);
    // then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isInstanceOf(List.class);
  }

  @Test
  @DisplayName("카테고리에 따른 조회 total Expenditure 데이터 조회")
  void get_total_expenditure_by_category_test() {
    // when
    List<TotalExpenditureByCategory> result = repository.findTotalExpenditureByCategory(condition);
    // then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isInstanceOf(List.class);
  }

  @Test
  @DisplayName("사용자 별 금일 total Expenditure 데이터 조회")
  void get_total_expenditure_by_user_id_test() {
    // when
    List<TotalExpenditureByCategory> result =
        repository.findDailyTotalExpenditureByUserId(1L, LocalDate.of(2010, 1, 1));
    // then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isInstanceOf(List.class);
  }

}
