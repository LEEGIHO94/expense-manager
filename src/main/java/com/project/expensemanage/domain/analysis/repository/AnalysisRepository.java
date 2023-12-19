package com.project.expensemanage.domain.analysis.repository;

import com.project.expensemanage.domain.analysis.repository.dto.ExpenditureTotalUser;
import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnalysisRepository
    extends JpaRepository<Expenditure, Long>, AnalysisQueryDslRepository {
  @Query(
      """
    select
    new com.project.expensemanage.domain.analysis.repository.dto.ExpenditureTotalUser(
    (select sum(e.price.value) from Expenditure e where e.expendedDate = :now),
    (select sum(e.price.value) from Expenditure e where e.user.id = :userId and e.expendedDate = :now)
    )
""")
  ExpenditureTotalUser getTotalExpenditureAllUserAndUser(
      @Param("userId") Long userId, @Param("now") LocalDate now);
}
