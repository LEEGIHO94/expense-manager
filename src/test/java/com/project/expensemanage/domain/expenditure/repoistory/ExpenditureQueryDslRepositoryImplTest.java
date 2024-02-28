package com.project.expensemanage.domain.expenditure.repoistory;



import com.project.expensemanage.commone.config.QueryDslConfig;
import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import com.project.expensemanage.domain.expenditure.enums.ExcludeSpendingTotal;
import com.project.expensemanage.domain.expenditure.repoistory.dto.GetExpenditureDetailsCondition;
import com.project.expensemanage.domain.vo.Price;
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
  Expenditure expenditure;
  @BeforeEach
  void init(){
    expenditure = Expenditure.builder()
        .id(1L)
        .price(new Price(1000L))
        .category(Category.builder().id(2L).build())
        .expendedDate(LocalDate.of(2020,4,2))
        .excludeSpendingTotal(ExcludeSpendingTotal.INCLUDE)
        .build();
  }

  @Test
  @DisplayName("조건에 따른 조회 Expenditure 데이터 조회")
  void get_expenditure_by_condition_test() throws Exception{
      // given
    GetExpenditureDetailsCondition dto = GetExpenditureDetailsCondition.builder()
        .userId(1L)
        .startDate(LocalDate.of(2010, 1, 1))
        .endDate(LocalDate.of(2011, 1, 1))
        .minAmount(1000L)
        .maxAmount(100000L)
        .build();
    // when
    List<Expenditure> result = repository.findAllExpenditureByCondition(dto);
    // then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isInstanceOf(List.class);
  }
}
