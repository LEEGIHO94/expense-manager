package com.project.expensemanage.notification.recommendation.event.listener.implement;

import com.project.expensemanage.notification.discord.body.DiscordBody;
import com.project.expensemanage.notification.discord.mapper.DiscordMapper;
import com.project.expensemanage.notification.recommendation.event.event.DailyRecommendationExpenditureEvent;
import com.project.expensemanage.notification.recommendation.event.listener.ExpenditureRecommendationEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@EnableAsync
@Component
@RequiredArgsConstructor
@Slf4j
public class DiscordEventListener implements ExpenditureRecommendationEventListener {
  private final DiscordMapper mapper;

  @Override
  public void handle(DailyRecommendationExpenditureEvent event) {
    DiscordBody discordBody = mapper.toDiscordBody(event.recommendationList());

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.postForEntity(
        mapper.getBaseUrl() + event.user().getUrl(),
        discordBody,
        String.class);
  }
}
