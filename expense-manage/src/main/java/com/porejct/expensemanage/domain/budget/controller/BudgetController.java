package com.porejct.expensemanage.domain.budget.controller;

import com.porejct.expensemanage.commone.annotation.CurrentUser;
import com.porejct.expensemanage.commone.dto.ResponseDto;
import com.porejct.expensemanage.commone.dto.ResponseStatus;
import com.porejct.expensemanage.commone.utils.response.UrlCreator;
import com.porejct.expensemanage.domain.budget.dto.response.BudgetIdResponse;
import com.porejct.expensemanage.domain.budget.service.BudgetService;
import com.porejct.expensemanage.domain.budget.dto.request.PostBudgetRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private static final String DEFAULT = "/api/budgets";
    private final BudgetService service;

    @PostMapping
    public ResponseEntity<ResponseDto<BudgetIdResponse>> postBudget(@Valid @RequestBody PostBudgetRequest post, @CurrentUser Long userId) {
        ResponseDto<BudgetIdResponse> response = ResponseDto.<BudgetIdResponse>builder()
                .data(service.postBudget(userId, post))
                .status(ResponseStatus.CREATE)
                .build();
        URI location = UrlCreator.createUri(DEFAULT, response.getData().budgetId());
        return ResponseEntity.created(location).body(response);
    }
}
