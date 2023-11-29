package com.project.expensemanage.recommendation.event;

import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.recommendation.dto.RecommendationExpenditure;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecommendationRepo extends JpaRepository<Category, Long> {

    @Query("""
                select new com.project.expensemanage.recommendation.dto.RecommendationExpenditure(c.id,c.name,sum(e.price.value),b.price.value) 
                from Category c
                join Budget b on c.id = b.category.id
                join Expenditure e on c.id = e.category.id
                where 
                b.date between :startDate and :endDate and b.user.id = :userId 
                and 
                e.expendedDate between :startDate and :endDate and e.user.id = :userId
                group by c.id, c.name,b.price.value
            """)
    List<RecommendationExpenditure> find(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate, @Param("userId") Long userId);
}
