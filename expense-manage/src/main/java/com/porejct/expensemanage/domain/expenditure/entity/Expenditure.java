package com.porejct.expensemanage.domain.expenditure.entity;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.porejct.expensemanage.domain.expenditure.enums.ExcludeSpendingTotal;
import com.porejct.expensemanage.domain.vo.Price;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Expenditure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,name = "expenditure_id")
    private Long id;
    private LocalDate expendedDate;
    private String memo;
    private LocalDate expenditureDate;

    @Embedded
    private Price price;

    @Enumerated(STRING)
    private ExcludeSpendingTotal excludeSpendingTotal;
}
