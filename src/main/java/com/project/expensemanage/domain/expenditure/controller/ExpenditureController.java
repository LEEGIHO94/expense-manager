package com.project.expensemanage.domain.expenditure.controller;

import com.project.expensemanage.commone.annotation.CurrentUser;
import com.project.expensemanage.commone.dto.ResponseDto;
import com.project.expensemanage.commone.dto.ResponseStatus;
import com.project.expensemanage.commone.utils.response.UrlCreator;
import com.project.expensemanage.domain.expenditure.controller.dto.request.PostExpenditureRequest;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureIdResponse;
import com.project.expensemanage.domain.expenditure.service.ExpenditureService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenditures")
@RequiredArgsConstructor
public class ExpenditureController {

    private static final String DEFAULT = "/api/expenditures";
    private final ExpenditureService service;

    @PostMapping
    public ResponseEntity<ResponseDto<ExpenditureIdResponse>> postExpenditure(
            @Valid @RequestBody PostExpenditureRequest post, @CurrentUser Long userId) {
        var response = ResponseDto.<ExpenditureIdResponse>builder()
                .data(service.postExpenditure(post, userId))
                .status(ResponseStatus.CREATE)
                .build();

        URI location = UrlCreator.createUri(DEFAULT, response.getData().expenditureId());
        return ResponseEntity.created(location).body(response);
    }
}
