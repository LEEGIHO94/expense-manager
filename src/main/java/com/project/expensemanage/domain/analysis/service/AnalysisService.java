package com.project.expensemanage.domain.analysis.service;

import com.project.expensemanage.commone.utils.date.DateUtils;
import com.project.expensemanage.domain.analysis.controller.dto.ExpenditureDiffResponse;
import com.project.expensemanage.domain.analysis.repository.AnalysisRepository;
import com.project.expensemanage.domain.analysis.repository.dto.ExpenditureDiff;
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

  public List<ExpenditureDiffResponse> tempName(Long userId) {
    List<ExpenditureDiff> currentMonthExpenditures =
        repository.getRateOfExpenditureDiff(userId, dateUtils.getLocalDate());
    List<ExpenditureDiff> lastMonthExpenditures =
        repository.getRateOfExpenditureDiff(userId, dateUtils.getLastMonthDate());

    Map<Long, Long> lastMonthExpenditureMap = initLastMonthExpnditureToMap(lastMonthExpenditures);

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

  private static Map<Long, Long> initLastMonthExpnditureToMap(List<ExpenditureDiff> lastMonthExpenditures) {
    Map<Long, Long> lastMonthExpenditureMap = new HashMap<>();
    lastMonthExpenditures.forEach(
        data -> lastMonthExpenditureMap.put(data.categoryId(), data.rate()));
    return lastMonthExpenditureMap;
  }
}
