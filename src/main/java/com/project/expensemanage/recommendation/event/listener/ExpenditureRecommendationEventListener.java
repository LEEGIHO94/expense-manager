package com.project.expensemanage.recommendation.event.listener;

import com.project.expensemanage.recommendation.event.event.DailyRecommendationExpenditureEvent;
import org.springframework.context.event.EventListener;

public interface ExpenditureRecommendationEventListener {
  @EventListener
  void handle(DailyRecommendationExpenditureEvent event);

}
