package com.porejct.expensemanage.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Price {
    private int value;
}
