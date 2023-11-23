package com.project.expensemanage.domain.expenditure.repoistory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public record TotalExpenditureByCategory(Long categoryId, String categoryName, Long amount) {

}
