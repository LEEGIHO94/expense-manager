package com.project.expensemanage.domain.analysis.repository;

import com.project.expensemanage.domain.analysis.repository.dto.ExpenditureDiff;
import java.time.LocalDate;
import java.util.List;

public interface AnalysisQueryDslRepository {
  List<ExpenditureDiff> getRateOfExpenditureDiff(
      Long userId, LocalDate startDate, LocalDate endDate);
}
