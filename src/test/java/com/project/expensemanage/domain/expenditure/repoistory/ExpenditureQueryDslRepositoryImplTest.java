package com.project.expensemanage.domain.expenditure.repoistory;



import com.project.expensemanage.commone.config.QueryDslConfig;
import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import com.project.expensemanage.domain.expenditure.repoistory.dto.GetExpenditureDetailsCondition;
import com.project.expensemanage.domain.expenditure.repoistory.dto.TotalExpenditureByCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({ExpenditureQueryDslRepositoryImpl.class, QueryDslConfig.class})
class ExpenditureQueryDslRepositoryImplTest {
  @PersistenceContext
  EntityManager em;

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  ExpenditureRepository repository;
  GetExpenditureDetailsCondition condition;
  @BeforeEach
  void init(){
     condition = GetExpenditureDetailsCondition.builder()
        .userId(1L)
        .startDate(LocalDate.of(2010, 1, 1))
        .endDate(LocalDate.of(2011, 1, 1))
        .minAmount(1000L)
        .maxAmount(100000L)
        .build();
  }

  @Test
  @DisplayName("조건에 따른 조회 Expenditure 데이터 조회")
  void get_expenditure_by_condition_test(){
      // given

    // when
    List<Expenditure> result = repository.findAllExpenditureByCondition(condition);
    // then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isInstanceOf(List.class);
  }

  @Test
  @DisplayName("조건에 따른 조회 Expenditure 데이터 조회")
  void get_total_expenditure_by_category_test(){
    // when
    List<TotalExpenditureByCategory> result = repository.findTotalExpenditureByCategory(condition);
    // then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isInstanceOf(List.class);
  }


}
