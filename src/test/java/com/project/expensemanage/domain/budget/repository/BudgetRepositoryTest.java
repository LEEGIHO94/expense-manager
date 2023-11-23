package com.project.expensemanage.domain.budget.repository;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.project.expensemanage.domain.budget.entity.Budget;
import com.project.expensemanage.domain.budget.repository.dto.RecommendedBudgetData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BudgetRepositoryTest {
    @Autowired
    BudgetRepository repository;

    @Test
    @DisplayName("Test")
    void TEst() throws Exception{
        List<RecommendedBudgetData> result = repository.findTotalAmountByCategory();

        Map<Long,Long> validMap = new HashMap<>();
        List<Budget> allData = repository.findAll();

        allData.forEach(budget -> validMap.put(budget.getCategory().getId(),validMap.getOrDefault(budget.getCategory().getId(),0L) + budget.getPrice().getValue()));

        result.forEach(data -> Assertions.assertThat(data.getAmount()).isEqualTo(validMap.get(data.getCategoryId())));

    }
}