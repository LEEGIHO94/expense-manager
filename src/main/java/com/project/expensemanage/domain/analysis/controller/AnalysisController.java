package com.project.expensemanage.domain.analysis.controller;

import com.project.expensemanage.commone.annotation.CurrentUser;
import com.project.expensemanage.commone.dto.ResponseDto;
import com.project.expensemanage.commone.dto.ResponseStatus;
import com.project.expensemanage.domain.analysis.controller.dto.ExpenditureAnalysisResponse;
import com.project.expensemanage.domain.analysis.controller.dto.ExpenditureDiffResponse;
import com.project.expensemanage.domain.analysis.service.AnalysisService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {
  private final AnalysisService service;

  @GetMapping("/monthly")
  public ResponseEntity<ResponseDto<List<ExpenditureDiffResponse>>> tempName(@CurrentUser Long userId){
    ResponseDto<List<ExpenditureDiffResponse>> response = ResponseDto.<List<ExpenditureDiffResponse>>builder()
        .data(service.getMonthlyExpenditureComparison(userId))
        .status(ResponseStatus.GET)
        .build();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/weekly")
  public ResponseEntity<ResponseDto<List<ExpenditureDiffResponse>>> tempNameWeek(@CurrentUser Long userId){
    ResponseDto<List<ExpenditureDiffResponse>> response = ResponseDto.<List<ExpenditureDiffResponse>>builder()
        .data(service.getWeeklyExpenditureComparison(userId))
        .status(ResponseStatus.GET)
        .build();
    return ResponseEntity.ok(response);
  }

  @GetMapping()
  public ResponseEntity<ResponseDto<ExpenditureAnalysisResponse>> getExpenditureAnalysisByUser(@CurrentUser Long userId){
    ResponseDto<ExpenditureAnalysisResponse> response = ResponseDto.<ExpenditureAnalysisResponse>builder()
        .data(service.getExpenditureAnalysisByUser(userId))
        .status(ResponseStatus.GET)
        .build();
    return ResponseEntity.ok(response);
  }
}
