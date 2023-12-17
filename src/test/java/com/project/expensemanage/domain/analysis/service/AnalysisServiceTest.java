package com.project.expensemanage.domain.analysis.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.project.expensemanage.domain.analysis.config.AnalysisConfig;
import com.project.expensemanage.domain.analysis.controller.dto.ExpenditureDiffResponse;
import com.project.expensemanage.domain.analysis.repository.AnalysisRepository;
import com.project.expensemanage.domain.analysis.repository.dto.ExpenditureDiff;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({AnalysisConfig.class})
class AnalysisServiceTest {
  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  AnalysisService service;

  @MockBean AnalysisRepository repository;

  @Test
  @DisplayName("저번 주 대비 이번 주 지출 내역 조회")
  void weekly_expenditure_comparison_test() throws Exception {
    // given
    Long userId = 1L;
    BDDMockito.given(
            repository.getRateOfExpenditureDiff(
                1L, LocalDate.now(), LocalDate.now().minusWeeks(1)))
        .willReturn(expenditureDiffList());
BDDMockito.given(
            repository.getRateOfExpenditureDiff(
                1L, LocalDate.now().minusWeeks(1), LocalDate.now().minusWeeks(2)))
        .willReturn(lastExpenditureList());

    // when
    List<ExpenditureDiffResponse> result = service.getWeeklyExpenditureComparison(userId);
    // then
    System.out.println("result = " + result);
    assertThat(result.stream().mapToLong(ExpenditureDiffResponse::categoryId)).contains(1L,2L,3L,4L);
    assertThat(result.stream().mapToLong(ExpenditureDiffResponse::rate)).containsExactly(10L,1200L,2200000L,0L);
  }

  @Test
  @DisplayName("저번 달 대비 이번 달 지출 내역 조회")
  void monthly_expenditure_comparison_test() throws Exception {
    // given
    Long userId = 1L;
    BDDMockito.given(
            repository.getRateOfExpenditureDiff(
                1L, LocalDate.now(), LocalDate.now().minusMonths(1)))
        .willReturn(expenditureDiffList());
BDDMockito.given(
            repository.getRateOfExpenditureDiff(
                1L, LocalDate.now().minusMonths(1), LocalDate.now().minusMonths(2)))
        .willReturn(lastExpenditureList());

    // when
    List<ExpenditureDiffResponse> result = service.getMonthlyExpenditureComparison(userId);
    // then
    System.out.println("result = " + result);
    assertThat(result.stream().mapToLong(ExpenditureDiffResponse::categoryId)).contains(1L,2L,3L,4L);
    assertThat(result.stream().mapToLong(ExpenditureDiffResponse::rate)).containsExactly(10L,1200L,2200000L,0L);
  }

  List<ExpenditureDiff> expenditureDiffList() {
    return List.of(
        ExpenditureDiff.builder().categoryId(1L).expenditure(1000000L).build(),
        ExpenditureDiff.builder().categoryId(2L).expenditure(12000L).build(),
        ExpenditureDiff.builder().categoryId(3L).expenditure(22000L).build(),
        ExpenditureDiff.builder().categoryId(4L).expenditure(0L).build()
    );
  }
  List<ExpenditureDiff> lastExpenditureList() {
    return List.of(
        ExpenditureDiff.builder().categoryId(1L).expenditure(10000000L).build(),
        ExpenditureDiff.builder().categoryId(2L).expenditure(1000L).build(),
        ExpenditureDiff.builder().categoryId(3L).expenditure(0L).build(),
        ExpenditureDiff.builder().categoryId(4L).expenditure(100000L).build()
    );
  }
}
