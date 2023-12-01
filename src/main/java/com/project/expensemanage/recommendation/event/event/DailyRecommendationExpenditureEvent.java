package com.project.expensemanage.recommendation.event.event;

import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.recommendation.dto.RecommendationExpenditure;
import java.util.List;

public record DailyRecommendationExpenditureEvent(List<RecommendationExpenditure> recommendationList, User user) {
}
