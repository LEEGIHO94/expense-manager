package com.project.expensemanage.notification.expenseTracker.event.publisher;

import com.project.expensemanage.commone.utils.date.DateUtils;
import com.project.expensemanage.domain.expenditure.repoistory.ExpenditureRepository;
import com.project.expensemanage.domain.expenditure.repoistory.dto.TotalExpenditureByCategory;
import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.user.enums.ServiceSubscriber;
import com.project.expensemanage.domain.user.repository.UserRepository;
import com.project.expensemanage.notification.expenseTracker.event.event.TodayExpenditureEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TodayTotalExpenditurePublisher {
  private final ApplicationEventPublisher publisher;
  private final ExpenditureRepository expenditureRepository;
  private final UserRepository userRepository;
  private final DateUtils dateUtils;

  @Scheduled(cron = "0 0 20 * * *")
  public void sendTodayExpenditure() {
    findSubscriber().forEach(this::publishEvent);
  }

  private void publishEvent(User user) {
    publisher.publishEvent(new TodayExpenditureEvent(getTotalExpenditureByUser(user), user));
  }

  private List<User> findSubscriber() {
    return userRepository.findUserByServiceSubscriber(ServiceSubscriber.EVALUATION);
  }

  private List<TotalExpenditureByCategory> getTotalExpenditureByUser(User user) {
    return expenditureRepository.findDailyTotalExpenditureByUserId(
        user.getId(), dateUtils.getLocalDate());
  }
}
