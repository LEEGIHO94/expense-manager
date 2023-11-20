package com.porejct.expensemanage.domain.budget.repository;

import com.porejct.expensemanage.domain.budget.entity.Budget;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query("""
                select b from Budget b 
                where b.user.id =:userId and year(b.date) = year(:inputDate) and month(b.date) = month(:inputDate) and
                b.category.id = :categoryId
            """)
    Optional<Budget> findByDateAndUserIdAndCategoryId(@Param("inputDate") LocalDate inputDate,
            @Param("categoryId") Long categoryId, @Param("userId") Long userId);
}
