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
import com.project.expensemanage.domain.user.exception.UserExceptionCode;
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
  void post_expenditure_test() {
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
  void post_expenditure_fail_test() {
    // given
    Long userId = 1L;

    given(categoryRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());

    // when
    Assertions.assertThatThrownBy(() -> service.postExpenditure(mock.getPostDtoMock(), userId))
        .hasMessage(CategoryExceptionCode.CATEGORY_NOT_FOUND.getMessage());
    // then
  }

  @Test
  @DisplayName("지출 삭제 테스트 : 성공")
  void delete_expenditure_success_test() {
    // given
    Long userId = 1L;

    given(repository.findById(anyLong())).willReturn(Optional.of(mock.getEntity()));
    willDoNothing().given(repository).deleteById(anyLong());
    // when
    service.deleteExpenditure(mock.getId(), userId);
    // then
    Mockito.verify(repository, times(1)).deleteById(anyLong());
  }

  @Test
  @DisplayName("지출 삭제 테스트 : 성공[조회한 지출이 없을 경우]")
  void delete_expenditure_success_not_search_expenditure_test() {
    // given
    Long userId = 1L;

    given(repository.findById(anyLong())).willReturn(Optional.empty());
    willDoNothing().given(repository).deleteById(anyLong());
    // when
    service.deleteExpenditure(mock.getId(), userId);
    // then
    Mockito.verify(repository, times(0)).deleteById(anyLong());
    Mockito.verify(repository, times(1)).findById(anyLong());
  }

  @Test
  @DisplayName("지출 삭제 테스트 : 실패[사용자 권한 없음]")
  void delete_expenditure_fail_test() {
    // given
    Long userId = 2L;

    given(repository.findById(anyLong())).willReturn(Optional.of(mock.getEntity()));
    // when
    // then
    Assertions.assertThatThrownBy(() -> service.deleteExpenditure(mock.getId(), userId))
        .hasMessage(UserExceptionCode.USER_NOT_SAME.getMessage());
  }
}
