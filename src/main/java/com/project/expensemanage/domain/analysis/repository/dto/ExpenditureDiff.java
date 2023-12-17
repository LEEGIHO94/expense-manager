package com.project.expensemanage.domain.analysis.repository.dto;

import lombok.Builder;

@Builder
public record ExpenditureDiff(Long categoryId, String categoryName, Long expenditure){

}
