package com.project.expensemanage.domain.expenditure.repoistory;

import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenditureRepository extends JpaRepository<Expenditure,Long>, ExpenditureQueryDslRepository {

}
