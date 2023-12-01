package com.project.expensemanage.recommendation.event.listener.implement;

import com.project.expensemanage.recommendation.discord.mapper.DiscordMapper;
import com.project.expensemanage.recommendation.event.event.DailyRecommendationExpenditureEvent;
import com.project.expensemanage.recommendation.event.listener.ExpenditureRecommendationEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
@RequiredArgsConstructor
@Slf4j
public class DiscordEventListener implements ExpenditureRecommendationEventListener {
  private final DiscordMapper mapper;
  @Override
  public void handle(DailyRecommendationExpenditureEvent event) {
    mapper.toDiscordBody(event.recommendationList());
  }
}
