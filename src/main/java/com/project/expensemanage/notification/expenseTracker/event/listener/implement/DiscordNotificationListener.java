package com.project.expensemanage.notification.expenseTracker.event.listener.implement;

import com.project.expensemanage.notification.discord.mapper.DiscordMapper;
import com.project.expensemanage.notification.expenseTracker.event.event.TodayExpenditureEvent;
import com.project.expensemanage.notification.expenseTracker.event.listener.TotalExpenditureNotificationEventListener;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiscordNotificationListener implements TotalExpenditureNotificationEventListener {
  private final DiscordMapper mapper;
  @Override
  public void handle(TodayExpenditureEvent event) {
    mapper.toDiscordTodayTotalExpenditureBody(event.expenditureList());
  }
}
