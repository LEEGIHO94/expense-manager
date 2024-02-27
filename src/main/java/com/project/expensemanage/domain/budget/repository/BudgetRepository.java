package com.project.expensemanage.domain.budget.repository;

import com.project.expensemanage.domain.budget.entity.Budget;
import com.project.expensemanage.domain.budget.repository.dto.RecommendedBudgetData;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

  @Query(
      """
                select b from Budget b
                where b.user.id =:userId and year(b.date) = year(:inputDate) and month(b.date) = month(:inputDate) and
                b.category.id = :categoryId
            """)
  Optional<Budget> findByDateAndUserIdAndCategoryId(
      @Param("inputDate") LocalDate inputDate,
      @Param("categoryId") Long categoryId,
      @Param("userId") Long userId);

  @Query(
      """
             select new com.project.expensemanage.domain.budget.repository.dto.RecommendedBudgetData
             (b.category.id,b.category.name,sum(b.price.value))
             from Budget b
             where b.category.categoryType = com.project.expensemanage.domain.category.enums.CategoryType.STANDARD
             group by b.category.id

        """)
  List<RecommendedBudgetData> findTotalAmountByCategory();

  @Query("select b from Budget b where b.id = :budgetId and b.user.id = :userId")
  Optional<Budget> findByBudgetIdAndUserId(
      @Param("budgetId") Long budgetId, @Param("userId") Long userId);

  @Query("select b from Budget b where b.user.id = :userId")
  List<Budget> findByUserId(@Param("userId") Long userId);

  @Query(
          """
              select new com.project.expensemanage.domain.budget.repository.dto.RecommendedBudgetData
              (c.id,c.name,tb.totalBudget)
              from TotalBudget tb
              join tb.category c
          """)
  List<RecommendedBudgetData> findTotalBudgetByCategory();

}
