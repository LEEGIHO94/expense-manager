package com.project.expensemanage.domain.expenditure.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.*;

import com.project.expensemanage.domain.category.exception.CategoryExceptionCode;
import com.project.expensemanage.domain.category.mock.CategoryMock;
import com.project.expensemanage.domain.category.repository.CategoryRepository;
import com.project.expensemanage.domain.category.service.CategoryValidService;
import com.project.expensemanage.domain.expenditure.config.ExpenditureTestConfig;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureIdResponse;
import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import com.project.expensemanage.domain.expenditure.mock.ExpenditureMock;
import com.project.expensemanage.domain.expenditure.repoistory.ExpenditureRepository;
import com.project.expensemanage.domain.user.mock.UserMock;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({ExpenditureTestConfig.class})
class ExpenditureServiceTest {
  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  ExpenditureMock mock;

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  CategoryMock categoryMock;

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  ExpenditureService service;

  @MockBean ExpenditureRepository repository;
  @MockBean CategoryRepository categoryRepository;

  @Test
  @DisplayName("지출 등록 테스트 : 성공")
  void post_expenditure_test() throws Exception {
    // given
    Long userId = 1L;

    given(repository.save(Mockito.any(Expenditure.class))).willReturn(mock.getEntity());
    given(categoryRepository.findById(Mockito.anyLong()))
        .willReturn(Optional.of(categoryMock.standardEntityPostMock()));

    // when
    ExpenditureIdResponse result = service.postExpenditure(mock.getPostDtoMock(), userId);
    // then
    Assertions.assertThat(result.expenditureId()).isEqualTo(mock.getEntity().getId());
    Mockito.verify(repository, times(1)).save(Mockito.any(Expenditure.class));
  }

  @Test
  @DisplayName("지출 등록 테스트 : 실패[카테고리 조회 실패]")
  void post_expenditure_fail_test() throws Exception {
    // given
    Long userId = 1L;

    given(categoryRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());

    // when
    Assertions.assertThatThrownBy(() -> service.postExpenditure(mock.getPostDtoMock(), userId))
        .hasMessage(CategoryExceptionCode.CATEGORY_NOT_FOUND.getMessage());
    // then
  }


}
