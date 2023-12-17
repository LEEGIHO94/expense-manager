package com.project.expensemanage.domain.analysis.service;

import com.project.expensemanage.commone.utils.date.DateUtils;
import com.project.expensemanage.domain.analysis.controller.dto.ExpenditureAnalysisResponse;
import com.project.expensemanage.domain.analysis.controller.dto.ExpenditureDiffResponse;
import com.project.expensemanage.domain.analysis.repository.AnalysisRepository;
import com.project.expensemanage.domain.analysis.repository.dto.ExpenditureDiff;
import com.project.expensemanage.domain.analysis.repository.dto.ExpenditureTotalUser;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalysisService {
  private final AnalysisRepository repository;
  private final DateUtils dateUtils;

  private Map<Long, Long> initLastRangeExpnditureToMap(
      List<ExpenditureDiff> lastMonthExpenditures) {
    Map<Long, Long> lastMonthExpenditureMap = new HashMap<>();
    lastMonthExpenditures.forEach(
        data -> lastMonthExpenditureMap.put(data.categoryId(), data.expenditure()));
    return lastMonthExpenditureMap;
  }

  public List<ExpenditureDiffResponse> getMonthlyExpenditureComparison(Long userId) {
    List<ExpenditureDiff> currentMonthExpenditures =
        getExpenditureDateRange(userId, dateUtils.getLocalDate(), dateUtils.getLastMonthDate());
    List<ExpenditureDiff> lastMonthExpenditures =
        getExpenditureDateRange(
            userId, dateUtils.getLastMonthDate(), dateUtils.getLastMonthDate().minusMonths(1));

    Map<Long, Long> lastMonthExpenditureMap = initLastRangeExpnditureToMap(lastMonthExpenditures);

    return currentMonthExpenditures.stream()
        .map(
            data ->
                new ExpenditureDiffResponse(
                    data.categoryId(),
                    data.categoryName(),
                    (data.expenditure()
                        * 100
                        / getLasMonthExpenditure(data, lastMonthExpenditureMap))))
        .toList();
  }

  public List<ExpenditureDiffResponse> getWeeklyExpenditureComparison(Long userId) {
    List<ExpenditureDiff> currentMonthExpenditures =
        getExpenditureDateRange(userId, dateUtils.getLocalDate(), dateUtils.getLastWeekDate());
    List<ExpenditureDiff> lastMonthExpenditures =
        getExpenditureDateRange(
            userId, dateUtils.getLastWeekDate(), dateUtils.getLastWeekDate().minusWeeks(1));

    Map<Long, Long> lastMonthExpenditureMap = initLastRangeExpnditureToMap(lastMonthExpenditures);

    return currentMonthExpenditures.stream()
        .map(
            data ->
                new ExpenditureDiffResponse(
                    data.categoryId(),
                    data.categoryName(),
                    (data.expenditure()
                        * 100
                        / getLasMonthExpenditure(data, lastMonthExpenditureMap))))
        .toList();
  }

  private Long getLasMonthExpenditure(
      ExpenditureDiff data, Map<Long, Long> lastMonthExpenditureMap) {
    return lastMonthExpenditureMap.getOrDefault(data.categoryId(), 1L) == 0
        ? 1L
        : lastMonthExpenditureMap.getOrDefault(data.categoryId(), 1L);
  }

  public ExpenditureAnalysisResponse getExpenditureAnalysisByUser(Long userId) {
    ExpenditureTotalUser totalExpenditureAllUserAndUser =
        repository.getTotalExpenditureAllUserAndUser(userId, dateUtils.getLocalDate());

    return ExpenditureAnalysisResponse.builder()
        .expenditureRate(
            totalExpenditureAllUserAndUser.userExpenditure()
                * 100
                / getTotalExpenditureExcludeUser(totalExpenditureAllUserAndUser))
        .analysisDate(dateUtils.getLocalDate())
        .build();
  }

  private List<ExpenditureDiff> getExpenditureDateRange(
      Long userId, LocalDate startDate, LocalDate endDate) {
    return repository.getRateOfExpenditureDiff(userId, startDate, endDate);
  }

  private long getTotalExpenditureExcludeUser(ExpenditureTotalUser totalExpenditureAllUserAndUser) {
    return totalExpenditureAllUserAndUser.totalExpenditure()
                - totalExpenditureAllUserAndUser.userExpenditure()
            <= 0
        ? 1
        : totalExpenditureAllUserAndUser.totalExpenditure()
            - totalExpenditureAllUserAndUser.userExpenditure();
  }
}
