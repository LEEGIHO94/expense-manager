package com.project.expensemanage.notification.recommendation.dto;

public record RecommendationExpenditureAllUser(
    Long categoryId, Long userId, String categoryName, Long totalExpenditure, Long budget) {}
