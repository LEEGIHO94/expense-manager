package com.project.expensemanage.optimization.repository;

import com.project.expensemanage.commone.config.QueryDslConfig;
import com.project.expensemanage.domain.budget.repository.BudgetRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({QueryDslConfig.class})
class BudgetRepositoryOptimizationTest {

  @Container static final MySQLContainer container = new MySQLContainer("mysql:8");
  @Autowired BudgetRepository repository;

  /*
   * 기존의 DB 구조와 변경된 DB 구조의 속도 차이 테스트
   * */
  @Test
  @Disabled("성능 테스트 활용 이후 미사용으로 인한 disable")
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
  }
}
