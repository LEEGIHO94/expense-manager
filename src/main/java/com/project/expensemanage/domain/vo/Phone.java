package com.project.expensemanage.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Phone {
    private String value;

    public Phone(String value) {
        this.value = value;
    }

    public Phone() {
    }
}
