package com.project.expensemanage.domain.analysis.controller.dto;

import lombok.Builder;

@Builder
public record ExpenditureDiffResponse(Long categoryId, String categoryName, Long rate) {}
