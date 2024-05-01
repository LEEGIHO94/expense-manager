package com.project.expensemanage.notification.recommendation.dto;

public record RecommendationExpenditure(
    Long categoryId, String categoryName, Long totalExpenditure, Long budget,long minExpenditure) {}
