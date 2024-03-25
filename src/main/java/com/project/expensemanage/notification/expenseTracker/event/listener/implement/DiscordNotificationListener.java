package com.project.expensemanage.notification.expenseTracker.event.listener.implement;

import com.project.expensemanage.notification.discord.body.DiscordBody;
import com.project.expensemanage.notification.discord.mapper.DiscordMapper;
import com.project.expensemanage.notification.expenseTracker.event.event.TodayExpenditureEvent;
import com.project.expensemanage.notification.expenseTracker.event.listener.TotalExpenditureNotificationEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class DiscordNotificationListener implements TotalExpenditureNotificationEventListener {
  private final DiscordMapper mapper;

  @Override
  public void handle(TodayExpenditureEvent event) {
    DiscordBody discordBody = mapper.toDiscordTodayTotalExpenditureBody(event.expenditureList());

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.postForEntity(
        mapper.getBaseUrl()
            + "/1179944667001917450/NqrHqNpt0RjWH-RRLryMVZ_JuWe2jlJryW9K2KVvs5nIYp6NboIL3iPDo1Mthdg-0aXN",
        discordBody,
        String.class);
  }
}
