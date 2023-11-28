package com.project.expensemanage.domain.expenditure.repoistory;

import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import com.project.expensemanage.domain.expenditure.repoistory.dto.GetExpenditureDetailsCondition;
import com.project.expensemanage.domain.expenditure.repoistory.dto.TotalExpenditureByCategory;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ExpenditureQueryDslRepository {
    List<Expenditure> findAllExpenditureByCondition(GetExpenditureDetailsCondition condition);
    List<TotalExpenditureByCategory> findTotalExpenditureByCategory(GetExpenditureDetailsCondition condition);
}
