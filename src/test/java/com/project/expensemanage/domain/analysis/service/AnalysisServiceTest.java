package com.project.expensemanage.domain.analysis.service;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import com.project.expensemanage.domain.analysis.config.AnalysisConfig;
import com.project.expensemanage.domain.analysis.controller.dto.ExpenditureAnalysisResponse;
import com.project.expensemanage.domain.analysis.controller.dto.ExpenditureDiffResponse;
import com.project.expensemanage.domain.analysis.repository.AnalysisRepository;
import com.project.expensemanage.domain.analysis.repository.dto.ExpenditureDiff;
import com.project.expensemanage.domain.analysis.repository.dto.ExpenditureTotalUser;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
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
            repository.getRateOfExpenditureDiff(1L, LocalDate.now(), LocalDate.now().minusWeeks(1)))
        .willReturn(expenditureDiffList());
    BDDMockito.given(
            repository.getRateOfExpenditureDiff(
                1L, LocalDate.now().minusWeeks(1), LocalDate.now().minusWeeks(2)))
        .willReturn(lastExpenditureList());

    // when
    List<ExpenditureDiffResponse> result = service.getWeeklyExpenditureComparison(userId);
    // then
    System.out.println("result = " + result);
    assertThat(result.stream().mapToLong(ExpenditureDiffResponse::categoryId))
        .contains(1L, 2L, 3L, 4L);
    assertThat(result.stream().mapToLong(ExpenditureDiffResponse::rate))
        .containsExactly(10L, 1200L, 2200000L, 0L);
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
    assertThat(result.stream().mapToLong(ExpenditureDiffResponse::categoryId))
        .contains(1L, 2L, 3L, 4L);
    assertThat(result.stream().mapToLong(ExpenditureDiffResponse::rate))
        .containsExactly(10L, 1200L, 2200000L, 0L);
  }

  @Test
  @DisplayName("당일 전체 사용자 지출 대비 지출 양 [전체 사용자가 더 클때, 개인의 비용 제외 시 더 작을때]")
  void total_user_expenditure_comparison_lager_total_test() throws Exception {
    // given
    Long userId = 1L;

    BDDMockito.given(repository.getTotalExpenditureAllUserAndUser(userId, LocalDate.now()))
        .willReturn(expenditureTotalUserLagerTotal());
    // when
    ExpenditureAnalysisResponse result = service.getExpenditureAnalysisByUser(userId);
    // then
    Assertions.assertThat(result.expenditureRate()).isEqualTo(9L);
    Mockito.verify(repository, times(1)).getTotalExpenditureAllUserAndUser(userId, LocalDate.now());
  }
  @Test
  @DisplayName("당일 전체 사용자 지출 대비 지출 양 [전체 사용자가 더 클때, 개인의 비용 제외 시 더 클 때]")
  void total_user_expenditure_comparison_lager_total_lager_test() throws Exception {
    // given
    Long userId = 1L;

    BDDMockito.given(repository.getTotalExpenditureAllUserAndUser(userId, LocalDate.now()))
        .willReturn(expenditureTotalUserLagerTotalLager());
    // when
    ExpenditureAnalysisResponse result = service.getExpenditureAnalysisByUser(userId);
    // then
    Assertions.assertThat(result.expenditureRate()).isEqualTo(4L);
    Mockito.verify(repository, times(1)).getTotalExpenditureAllUserAndUser(userId, LocalDate.now());
  }

  //당일 사용한 전체 사용자의 지출 보다 한사람이 더 큰 경우 어마무시한 과한 숫자가 나온다.
  @Test
  @DisplayName("당일 전체 사용자 지출 대비 지출 양 [전체 사용자가 더 클때]")
  void total_user_expenditure_comparison_lager_user_test() throws Exception {
    // given
    Long userId = 1L;

    BDDMockito.given(repository.getTotalExpenditureAllUserAndUser(userId, LocalDate.now()))
        .willReturn(expenditureTotalUserLagerUser());
    // when
    ExpenditureAnalysisResponse result = service.getExpenditureAnalysisByUser(userId);
    // then
    Assertions.assertThat(result.expenditureRate()).isEqualTo(12000000L);
    Mockito.verify(repository, times(1)).getTotalExpenditureAllUserAndUser(userId, LocalDate.now());
  }

  @Test
  @DisplayName("당일 전체 사용자 지출 대비 지출 양 [전체 사용자가 지출 0]")
  void total_user_expenditure_comparison_zero_total_test() throws Exception {
    // given
    Long userId = 1L;

    BDDMockito.given(repository.getTotalExpenditureAllUserAndUser(userId, LocalDate.now()))
        .willReturn(expenditureTotalUserZeroTotal());
    // when
    ExpenditureAnalysisResponse result = service.getExpenditureAnalysisByUser(userId);
    // then
    Assertions.assertThat(result.expenditureRate()).isEqualTo(12000000L);
    Mockito.verify(repository, times(1)).getTotalExpenditureAllUserAndUser(userId, LocalDate.now());
  }

  @Test
  @DisplayName("당일 전체 사용자 지출 대비 지출 양 [사용자가 지출 0]")
  void total_user_expenditure_comparison_zero_user_test() throws Exception {
    // given
    Long userId = 1L;

    BDDMockito.given(repository.getTotalExpenditureAllUserAndUser(userId, LocalDate.now()))
        .willReturn(expenditureTotalUserZeroUser());
    // when
    ExpenditureAnalysisResponse result = service.getExpenditureAnalysisByUser(userId);
    // then
    Assertions.assertThat(result.expenditureRate()).isZero();
    Mockito.verify(repository, times(1)).getTotalExpenditureAllUserAndUser(userId, LocalDate.now());
  }

  // 전체 사용자가 더 클 때
  ExpenditureTotalUser expenditureTotalUserLagerTotal() {
    return ExpenditureTotalUser.builder()
        .totalExpenditure(1200000L)
        .userExpenditure(100000L)
        .build();
  }

  ExpenditureTotalUser expenditureTotalUserLagerTotalLager() {
    return ExpenditureTotalUser.builder()
        .totalExpenditure(2200000L)
        .userExpenditure(100000L)
        .build();
  }

  // 전체 사용자가 더 작을 때
  ExpenditureTotalUser expenditureTotalUserLagerUser() {
    return ExpenditureTotalUser.builder()
        .totalExpenditure(100000L)
        .userExpenditure(120000L)
        .build();
  }

  // 전체 사용자가 사용한 금액이 0일 때
  ExpenditureTotalUser expenditureTotalUserZeroTotal() {
    return ExpenditureTotalUser.builder().totalExpenditure(0L).userExpenditure(120000L).build();
  }

  // 사용자 지출이 0일 때
  ExpenditureTotalUser expenditureTotalUserZeroUser() {
    return ExpenditureTotalUser.builder().totalExpenditure(100000L).userExpenditure(0L).build();
  }

  List<ExpenditureDiff> expenditureDiffList() {
    return List.of(
        ExpenditureDiff.builder().categoryId(1L).expenditure(1000000L).build(),
        ExpenditureDiff.builder().categoryId(2L).expenditure(12000L).build(),
        ExpenditureDiff.builder().categoryId(3L).expenditure(22000L).build(),
        ExpenditureDiff.builder().categoryId(4L).expenditure(0L).build());
  }

  List<ExpenditureDiff> lastExpenditureList() {
    return List.of(
        ExpenditureDiff.builder().categoryId(1L).expenditure(10000000L).build(),
        ExpenditureDiff.builder().categoryId(2L).expenditure(1000L).build(),
        ExpenditureDiff.builder().categoryId(3L).expenditure(0L).build(),
        ExpenditureDiff.builder().categoryId(4L).expenditure(100000L).build());
  }
}
