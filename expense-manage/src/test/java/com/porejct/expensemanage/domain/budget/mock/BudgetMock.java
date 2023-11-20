package com.porejct.expensemanage.domain.budget.mock;

import com.porejct.expensemanage.domain.budget.entity.Budget;
import com.porejct.expensemanage.domain.budget.service.PostBudgetRequest;
import com.porejct.expensemanage.domain.user.entity.User;
import com.porejct.expensemanage.domain.vo.Phone;
import com.porejct.expensemanage.domain.vo.Price;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class BudgetMock {

    private final Long id = 1L;
    private final Long amount = 100000L;
    private final Price price = new Price(amount);
    private final LocalDate date = LocalDate.of(2024, 1, 1);
    private final User serviceUser = entity();
    private final User requestUser = postEntity();
    private final Long categoryId = 1L;

    public Budget entityMock() {
        return Budget.builder()
                .id(id)
                .price(price)
                .date(date)
                .user(serviceUser)
                .build();
    }

    public Budget entityPostMock() {
        return Budget.builder()
                .price(price)
                .date(date)
                .user(requestUser)
                .build();
    }

    public PostBudgetRequest postDtoMock() {
        return PostBudgetRequest.builder()
                .budgetDate(date)
                .amount(amount)
                .categoryId(categoryId)
                .build();
    }

    public LocalDate getDate() {
        return date;
    }

    private User entity() {
        return User.builder()
                .phone(new Phone("010-1234-1234"))
                .email("email001@gmail.com")
                .id(1L)
                .password("encryptedPassword001")
                .build();
    }

    private User postEntity() {
        return User.builder()
                .id(1L)
                .build();
    }
}
