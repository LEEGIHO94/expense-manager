package com.project.expensemanage.notification.recommendation.event.publisher;

import com.project.expensemanage.commone.utils.date.DateUtils;
import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.user.enums.ServiceSubscriber;
import com.project.expensemanage.domain.user.repository.UserRepository;
import com.project.expensemanage.notification.recommendation.dto.RecommendationExpenditure;
import com.project.expensemanage.notification.recommendation.event.event.DailyRecommendationExpenditureEvent;
import com.project.expensemanage.notification.recommendation.repository.RecommendationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DailyExpenditureRecommendationPublisher {
  private final UserRepository userRepository;
  private final ApplicationEventPublisher publisher;
  private final RecommendationRepository recommendationRepository;
  private final DateUtils dateUtils;

  @Scheduled(cron = "0 0 8 * * *")
  public void sendDailyRecommendation() {
    findSubscriber().forEach(this::publishEvent);
  }

  private void publishEvent(User user) {
    publisher.publishEvent(
        new DailyRecommendationExpenditureEvent(getTotalExpenditureMonthlyByUser(user), user));
  }

  private List<User> findSubscriber() {
    return userRepository.findUserByServiceSubscriber(ServiceSubscriber.RECOMMENDATION);
  }

  private List<RecommendationExpenditure> getTotalExpenditureMonthlyByUser(User user) {
    return recommendationRepository.findTotalExpenditureByCategoryAndDateAndId(
        dateUtils.startOfMonth(), dateUtils.endOfMonth(), user.getId());
  }
}
