package com.project.expensemanage.domain.analysis.controller.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ExpenditureAnalysisResponse(Long expenditureRate, LocalDate analysisDate) {}
