package com.project.expensemanage.domain.budget.entity;

import com.project.expensemanage.domain.category.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalBudget {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, name = "total_budget_id")
  private Long id;
  private Long totalBudget;
  @OneToOne(mappedBy = "totalBudget")
  private Category category;

  public void addTotalBudget(Long totalBudget){
    this.totalBudget += totalBudget;
  }
}
