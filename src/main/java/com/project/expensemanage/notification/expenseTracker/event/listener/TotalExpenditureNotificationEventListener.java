package com.project.expensemanage.notification.expenseTracker.event.listener;

import com.project.expensemanage.notification.expenseTracker.event.event.TodayExpenditureEvent;
import org.springframework.context.event.EventListener;

public interface TotalExpenditureNotificationEventListener {
  @EventListener
  void handle(TodayExpenditureEvent event);

}
