package com.porejct.expensemanage.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Phone {
    private String value;
}
