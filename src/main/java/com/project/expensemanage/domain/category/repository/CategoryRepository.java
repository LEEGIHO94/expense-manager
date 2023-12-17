package com.project.expensemanage.domain.category.repository;

import com.project.expensemanage.domain.category.entity.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findByName(String name);

  @Query(
      "select c from Category c where c.categoryType = com.project.expensemanage.domain.category.enums.CategoryType.STANDARD")
  List<Category> findAllByType();
}
