package com.project.expensemanage.notification.recommendation.repository;

import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.notification.recommendation.dto.CategoryIdAndExpenditure;
import com.project.expensemanage.notification.recommendation.dto.RecommendationExpenditure;
import com.project.expensemanage.notification.recommendation.dto.RecommendationExpenditureAllUser;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecommendationRepository extends JpaRepository<Category, Long> {

  @Query(
      """
                select new com.project.expensemanage.notification.recommendation.dto.RecommendationExpenditure(
                c.id,c.name,sum(e.price.value),b.price.value, (
                                                                SELECT MIN(e2.price.value)
                                                                FROM Expenditure e2
                                                                WHERE e2.expendedDate BETWEEN :startDate AND :endDate
                                                                  AND e2.user.id = :userId
                                                                  AND e2.price.value > 0
                                                                  AND e2.category.id = c.id
                                                              ))
                from Category c
                join Budget b on c.id = b.category.id
                join Expenditure e on c.id = e.category.id
                where
                b.date between :startDate and :endDate and b.user.id = :userId
                and
                e.expendedDate between :startDate and :endDate and e.user.id = :userId
                group by c.id, c.name,b.price.value
            """)
  List<RecommendationExpenditure> findTotalExpenditureByCategoryAndDateAndId(
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("userId") Long userId);

  @Query(
      """
                select new com.project.expensemanage.notification.recommendation.dto.RecommendationExpenditureAllUser(c.id,e.user.id,c.name,sum(e.price.value),b.price.value)
                from Category c
                join Budget b on c.id = b.category.id
                join Expenditure e on c.id = e.category.id
                where
                b.date between :startDate and :endDate
                and
                e.expendedDate between :startDate and :endDate
                group by c.id, c.name,b.price.value , e.user.id
            """)
  List<RecommendationExpenditureAllUser> findTotalExpenditureByCategoryAndDate(
      @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query(
      """
              select new com.project.expensemanage.notification.recommendation.dto.CategoryIdAndExpenditure(c.id,e.price.value)
              from Expenditure e
              join Category c on c.id = e.category.id
              where
              e.expendedDate between :startDate and :endDate
              group by c.id
          """)
  List<CategoryIdAndExpenditure> findMonthlyCategoryTotalExpenditure(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
