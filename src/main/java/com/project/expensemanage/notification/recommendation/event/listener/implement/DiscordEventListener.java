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

//        WebClient.create(mapper.getBaseUrl())
//            .post()
//            .uri(
//
//     "/1179944667001917450/NqrHqNpt0RjWH-RRLryMVZ_JuWe2jlJryW9K2KVvs5nIYp6NboIL3iPDo1Mthdg-0aXN")
//            .contentType(MediaType.APPLICATION_JSON)
//            .bodyValue(discordBody)
//            .retrieve()
//            .bodyToMono(String.class)
//            .subscribe();

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.postForEntity(
        mapper.getBaseUrl()
            + "/1179944667001917450/NqrHqNpt0RjWH-RRLryMVZ_JuWe2jlJryW9K2KVvs5nIYp6NboIL3iPDo1Mthdg-0aXN",
        discordBody,
        String.class);
  }
}
