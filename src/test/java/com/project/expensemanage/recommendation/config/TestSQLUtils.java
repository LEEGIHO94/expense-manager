package com.project.expensemanage.recommendation.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class TestSQLUtils {

    @PersistenceContext
    private EntityManager manager;

    public Long getTotalExpenditureByCategoryAndUser(Long userId, Long categoryId, LocalDate startDate, LocalDate endDate) {
        return manager.createQuery("""
                        select sum(e.price.value) from Expenditure e 
                        where 
                            e.user.id =:userId 
                        and 
                            e.expendedDate between :startDate and :endDate 
                        and 
                            e.category.id = :categoryId
                        """, Long.class)
                .setParameter("userId", userId)
                .setParameter("categoryId", categoryId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
    }
}
