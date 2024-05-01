package com.project.expensemanage.recommendation.event.listener.implement;

import static org.mockito.Mockito.*;

import com.project.expensemanage.commone.utils.date.DateUtils;
import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.user.enums.ServiceSubscriber;
import com.project.expensemanage.domain.user.repository.UserRepository;
import com.project.expensemanage.notification.discord.mapper.DiscordMapper;
import com.project.expensemanage.notification.discord.mapper.DiscordProperties;
import com.project.expensemanage.notification.recommendation.dto.RecommendationExpenditure;
import com.project.expensemanage.notification.recommendation.event.event.DailyRecommendationExpenditureEvent;
import com.project.expensemanage.notification.recommendation.event.listener.implement.DiscordEventListener;
import com.project.expensemanage.notification.recommendation.event.publisher.DailyExpenditureRecommendationPublisher;
import com.project.expensemanage.notification.recommendation.repository.RecommendationRepository;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@RecordApplicationEvents
@Import({DailyExpenditureRecommendationPublisher.class, DiscordMapper.class})
@EnableConfigurationProperties(value = DiscordProperties.class)
class DiscordEventListenerTest {
  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  DailyExpenditureRecommendationPublisher publisher;

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  DiscordMapper mapper;

  @MockBean DateUtils dateUtils;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private ApplicationEvents events;

  @MockBean private UserRepository userRepository;
  @MockBean private RecommendationRepository recommendationRepository;

  private List<RecommendationExpenditure> recommendExpenditureStub() {
    RecommendationExpenditure stub = new RecommendationExpenditure(1L, "test001", 1000L, 100000L,1000L);
    RecommendationExpenditure stubTwo =
        new RecommendationExpenditure(2L, "test002", 2000L, 200000L,1000L);
    RecommendationExpenditure stubThree =
        new RecommendationExpenditure(3L, "test003", 3000L, 300000L,1000L);

    List<RecommendationExpenditure> mockExpenditureList = List.of(stub, stubTwo, stubThree);
    return mockExpenditureList;
  }

  private User userStub() {
    return User.builder()
        .email("test@gmail.com")
        .id(1L)
        .serviceSubscriber(ServiceSubscriber.RECOMMENDATION)
        .build();
  }

  @Test
  @DisplayName("전송 테스트")
  void recommend_send_test() throws Exception {
    // given
    User mockUser = userStub();

    List<RecommendationExpenditure> mockExpenditureList = recommendExpenditureStub();

    BDDMockito.given(dateUtils.endOfMonth()).willReturn(LocalDate.of(2020, 01, 31));
    BDDMockito.given(dateUtils.startOfMonth()).willReturn(LocalDate.of(2020, 01, 01));
    BDDMockito.given(userRepository.findUserByServiceSubscriber(any(ServiceSubscriber.class)))
        .willReturn(List.of(mockUser));
    BDDMockito.given(
            recommendationRepository.findTotalExpenditureByCategoryAndDateAndId(
                any(LocalDate.class), any(LocalDate.class), anyLong()))
        .willReturn(mockExpenditureList);

    // when
    publisher.sendDailyRecommendation();
    // then
    long result = events.stream(DailyRecommendationExpenditureEvent.class).count();

    Assertions.assertThat(result).isEqualTo(1L);
    verify(userRepository, times(1)).findUserByServiceSubscriber(any(ServiceSubscriber.class));
    verify(recommendationRepository, times(1))
        .findTotalExpenditureByCategoryAndDateAndId(
            any(LocalDate.class), any(LocalDate.class), anyLong());
  }

  @Test
  @DisplayName("리스너 테스트")
  void litener_test() throws Exception {
    // given
    DiscordEventListener discordEventListener = new DiscordEventListener(mapper);
    DailyRecommendationExpenditureEvent dto =
        new DailyRecommendationExpenditureEvent(getListStub(), userStub());
    // when
    discordEventListener.handle(dto);
  }

  List<RecommendationExpenditure> getListStub() {
    return List.of(
        new RecommendationExpenditure(1L, "카테고리이름1", 100000L, 100L,1000L),
        new RecommendationExpenditure(1L, "카테고리이름2", 200000L, 200L,1000L),
        new RecommendationExpenditure(1L, "카테고리이름3", 300000L, 300L,1000L));
  }
}
