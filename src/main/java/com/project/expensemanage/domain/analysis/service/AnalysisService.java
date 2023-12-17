package com.project.expensemanage.domain.analysis.service;

import com.project.expensemanage.commone.utils.date.DateUtils;
import com.project.expensemanage.domain.analysis.controller.dto.ExpenditureDiffResponse;
import com.project.expensemanage.domain.analysis.repository.AnalysisQueryDslRepositoryImpl;
import com.project.expensemanage.domain.analysis.repository.dto.ExpenditureDiff;
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
  private final AnalysisQueryDslRepositoryImpl repository;
  private final DateUtils dateUtils;

  public List<ExpenditureDiffResponse> getMonthlyExpenditureComparison(Long userId) {
    List<ExpenditureDiff> currentMonthExpenditures = getExpenditureDateRange(userId,dateUtils.getLocalDate(),dateUtils.getLastMonthDate());
    List<ExpenditureDiff> lastMonthExpenditures = getExpenditureDateRange(userId,dateUtils.getLastMonthDate(),dateUtils.getLastMonthDate().minusMonths(1));

    Map<Long, Long> lastMonthExpenditureMap = initLastRangeExpnditureToMap(lastMonthExpenditures);

    return currentMonthExpenditures.stream()
        .map(
            data ->
                new ExpenditureDiffResponse(
                    data.categoryId(),
                    data.categoryName(),
                    (data.rate()
                        * 100
                        / lastMonthExpenditureMap.getOrDefault(data.categoryId(), 1L))))
        .toList();
  }

  public List<ExpenditureDiffResponse> getWeeklyExpenditureComparison(Long userId){
    List<ExpenditureDiff> currentMonthExpenditures = getExpenditureDateRange(userId,dateUtils.getLocalDate(),dateUtils.getLastWeekDate());
    List<ExpenditureDiff> lastMonthExpenditures = getExpenditureDateRange(userId,dateUtils.getLastWeekDate(),dateUtils.getLastWeekDate().minusWeeks(1));

    Map<Long, Long> lastMonthExpenditureMap = initLastRangeExpnditureToMap(lastMonthExpenditures);

    return currentMonthExpenditures.stream()
        .map(
            data ->
                new ExpenditureDiffResponse(
                    data.categoryId(),
                    data.categoryName(),
                    (data.rate()
                        * 100
                        / lastMonthExpenditureMap.getOrDefault(data.categoryId(), 1L))))
        .toList();
  }

  
  private List<ExpenditureDiff> getExpenditureDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
    return repository.getRateOfExpenditureDiff(userId, startDate,endDate);
  }

  private static Map<Long, Long> initLastRangeExpnditureToMap(List<ExpenditureDiff> lastMonthExpenditures) {
    Map<Long, Long> lastMonthExpenditureMap = new HashMap<>();
    lastMonthExpenditures.forEach(
        data -> lastMonthExpenditureMap.put(data.categoryId(), data.rate()));
    return lastMonthExpenditureMap;
  }
}