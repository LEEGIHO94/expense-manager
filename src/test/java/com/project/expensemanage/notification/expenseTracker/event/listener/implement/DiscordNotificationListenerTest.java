package com.project.expensemanage.notification.expenseTracker.event.listener.implement;

import static com.project.expensemanage.domain.user.enums.ServiceSubscriber.*;
import static org.mockito.BDDMockito.*;

import com.project.expensemanage.commone.utils.date.DateUtils;
import com.project.expensemanage.domain.expenditure.repoistory.ExpenditureRepository;
import com.project.expensemanage.domain.expenditure.repoistory.dto.TotalExpenditureByCategory;
import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.user.repository.UserRepository;
import com.project.expensemanage.notification.discord.mapper.DiscordMapper;
import com.project.expensemanage.notification.discord.mapper.DiscordProperties;
import com.project.expensemanage.notification.expenseTracker.event.event.TodayExpenditureEvent;
import com.project.expensemanage.notification.expenseTracker.event.publisher.TodayTotalExpenditurePublisher;
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
@Import({TodayTotalExpenditurePublisher.class, DiscordMapper.class})
@EnableConfigurationProperties(value = DiscordProperties.class)
class DiscordNotificationListenerTest {
  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  TodayTotalExpenditurePublisher publisher;
  @MockBean
  DateUtils dateUtils;
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private ApplicationEvents events;
  @MockBean
  private UserRepository userRepository;
  @MockBean
  private ExpenditureRepository expenditureRepository;

  private User userStub() {
    return User.builder()
        .email("test@gmail.com")
        .id(1L)
        .serviceSubscriber(RECOMMENDATION)
        .build();
  }

  @Test
  @DisplayName("토탈 집계 함수 구현")
  void send_today_total_expenditure_test() throws Exception{
    // given
    var dateOne = new TotalExpenditureByCategory(1L,"카테고리1",10000L);
    var dateTwo = new TotalExpenditureByCategory(2L,"카테고리2",20000L);
    var dateThree = new TotalExpenditureByCategory(3L,"카테고리3",30000L);

    List<TotalExpenditureByCategory> mockList = List.of(dateOne, dateTwo, dateThree);

    BDDMockito.given(userRepository.findUserByServiceSubscriber(EVALUATION)).willReturn(List.of(userStub()));
    BDDMockito.given(expenditureRepository.findDailyTotalExpenditureByUserId(anyLong(),any(LocalDate.class) )).willReturn(mockList);
    BDDMockito.given(dateUtils.getLocalDate()).willReturn(LocalDate.of(2020,01,01));
    // when
    publisher.sendTodayExpenditure();
    // then
    long result = events.stream(TodayExpenditureEvent.class).count();

    Assertions.assertThat(result).isEqualTo(1L);
    verify(userRepository, times(1)).findUserByServiceSubscriber(EVALUATION);
    verify(expenditureRepository, times(1)).findDailyTotalExpenditureByUserId(anyLong(), any(LocalDate.class));
  }


}
