package com.project.expensemanage.domain.expenditure.controller;

import com.project.expensemanage.commone.annotation.CurrentUser;
import com.project.expensemanage.commone.dto.ResponseDto;
import com.project.expensemanage.commone.dto.ResponseStatus;
import com.project.expensemanage.commone.utils.response.UrlCreator;
import com.project.expensemanage.domain.expenditure.controller.dto.request.GetExpenditureList;
import com.project.expensemanage.domain.expenditure.controller.dto.request.PostExpenditureRequest;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureIdResponse;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureListResponse;
import com.project.expensemanage.domain.expenditure.service.ExpenditureResponse;
import com.project.expensemanage.domain.expenditure.service.ExpenditureService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @DeleteMapping("/{expenditureId}")
    public ResponseEntity<Void> deleteExpenditure(@PathVariable Long expenditureId,@CurrentUser Long userId) {
        service.deleteExpenditure(expenditureId,userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{expenditureId}")
    public ResponseEntity<ResponseDto<ExpenditureResponse>> getDetails(@PathVariable Long expenditureId,@CurrentUser Long userId) {
        var response = ResponseDto.<ExpenditureResponse>builder()
                .data(service.getExpenditureDetails(userId, expenditureId))
                .status(ResponseStatus.GET)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<ResponseDto<ExpenditureListResponse>> getList(@ModelAttribute GetExpenditureList dto, @CurrentUser Long userId) {
        var response = ResponseDto.<ExpenditureListResponse>builder()
                .data(service.getExpenditureListByCondition(dto, userId))
                .status(ResponseStatus.GET)
                .build();

        return ResponseEntity.ok(response);
    }
}
