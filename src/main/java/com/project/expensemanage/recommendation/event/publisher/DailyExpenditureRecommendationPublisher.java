package com.project.expensemanage.recommendation.event.publisher;

import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.user.enums.ServiceSubscriber;
import com.project.expensemanage.domain.user.repository.UserRepository;
import com.project.expensemanage.recommendation.dto.RecommendationExpenditure;
import com.project.expensemanage.recommendation.event.event.DailyRecommendationExpenditureEvent;
import com.project.expensemanage.recommendation.repository.RecommendationRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DailyExpenditureRecommendationPublisher {
  private final UserRepository userRepository;
  private final ApplicationEventPublisher publisher;
  private final RecommendationRepository recommendationRepository;

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
        startOfMonth(), endOfMonth(), user.getId());
  }

  // 역할의 분리가 필요, 해당 역할은 따로 빼주는 것이 좋아보임 -> 추후 리팩토링 예정
  private LocalDate startOfMonth() {
    return YearMonth.now().atDay(1);
  }

  private LocalDate endOfMonth() {
    return YearMonth.now().atEndOfMonth();
  }
}
