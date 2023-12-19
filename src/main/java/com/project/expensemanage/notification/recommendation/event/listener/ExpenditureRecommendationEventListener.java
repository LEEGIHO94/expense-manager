package com.project.expensemanage.notification.recommendation.event.listener;

import com.project.expensemanage.notification.recommendation.event.event.DailyRecommendationExpenditureEvent;
import org.springframework.context.event.EventListener;

public interface ExpenditureRecommendationEventListener {
  @EventListener
  void handle(DailyRecommendationExpenditureEvent event);
}
