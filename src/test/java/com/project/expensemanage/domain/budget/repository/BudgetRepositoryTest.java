package com.project.expensemanage.domain.budget.repository;

import com.project.expensemanage.commone.config.QueryDslConfig;
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
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(QueryDslConfig.class)
class BudgetRepositoryTest {

  @Container static final MySQLContainer container = new MySQLContainer("mysql:8");
  @Autowired BudgetRepository repository;

  @Test
  @DisplayName("DB 카테고리 별 총 금액 조회 테스트 : 성공")
  void find_amount_by_category_test() throws Exception {
    List<RecommendedBudgetData> result = repository.findTotalAmountByCategory();

    Map<Long, Long> validMap = new HashMap<>();
    List<Budget> allData = repository.findAll();

    allData.forEach(
        budget ->
            validMap.put(
                budget.getCategory().getId(),
                validMap.getOrDefault(budget.getCategory().getId(), 0L)
                    + budget.getPrice().getValue()));

    result.forEach(
        data ->
            Assertions.assertThat(data.getAmount()).isEqualTo(validMap.get(data.getCategoryId())));
  }
}
