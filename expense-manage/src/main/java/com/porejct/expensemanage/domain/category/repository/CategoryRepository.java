package com.porejct.expensemanage.domain.category.repository;

import com.porejct.expensemanage.domain.category.entity.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByName(String name);

    @Query("select c from Category c where c.categoryType = com.porejct.expensemanage.domain.category.enums.CategoryType.STANDARD")
    List<Category> findAllByType();
}
