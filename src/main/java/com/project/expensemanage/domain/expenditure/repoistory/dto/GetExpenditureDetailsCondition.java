package com.project.expensemanage.domain.expenditure.repoistory.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record GetExpenditureDetailsCondition(Long categoryId, Long userId, LocalDate startDate,
                                             LocalDate endDate, Long minAmount, Long maxAmount) {

}
