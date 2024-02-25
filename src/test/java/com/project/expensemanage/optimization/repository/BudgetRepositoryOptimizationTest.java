package com.project.expensemanage.optimization.repository;

import com.project.expensemanage.commone.config.QueryDslConfig;
import com.project.expensemanage.domain.budget.repository.BudgetRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({QueryDslConfig.class})
class BudgetRepositoryOptimizationTest {
  @Autowired BudgetRepository repository;

  /*
   * 기존의 DB 구조와 변경된 DB 구조의 속도 차이 테스트
   * */
  @Test
  @DisplayName("예산 추천 서비스 실행 시간 테스트")
  void check_recommend_method_test() {
    // given
    StopWatch stopWatch = new StopWatch();
    stopWatch.start("SUM()을 활용해 조회");
    repository.findTotalAmountByCategory();
    stopWatch.stop();

    stopWatch.start("totalBudget 테이블 구현 조회");
    repository.findTotalBudgetByCategory();
    stopWatch.stop();
    repository.flush();
    System.out.println(stopWatch.prettyPrint());
    System.out.println("코드 실행 시간 (s): " + stopWatch.getTotalTimeSeconds());

    TaskInfo[] taskInfo = stopWatch.getTaskInfo();

    Assertions.assertThat(taskInfo[0].getTimeNanos()).isGreaterThan(taskInfo[1].getTimeNanos());
  }
}
