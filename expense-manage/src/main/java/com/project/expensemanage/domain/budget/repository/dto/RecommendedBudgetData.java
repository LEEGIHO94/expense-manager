package com.project.expensemanage.domain.budget.repository.dto;

import com.project.expensemanage.domain.vo.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendedBudgetData {
    private String name;
    private Price amount;
}

/*
* 카테고리 별 총 합을 구해야한다. -> 카테고리별 총합을 간단하게 구하는 방법을 고려해 보는 것이 좋을 것 같다 이 때 카테고리가 고정되어
* */