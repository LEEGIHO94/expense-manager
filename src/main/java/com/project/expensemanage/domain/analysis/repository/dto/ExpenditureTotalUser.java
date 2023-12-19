package com.project.expensemanage.domain.analysis.repository.dto;

import lombok.Builder;

@Builder
public record ExpenditureTotalUser(Long totalExpenditure, Long userExpenditure) {}
