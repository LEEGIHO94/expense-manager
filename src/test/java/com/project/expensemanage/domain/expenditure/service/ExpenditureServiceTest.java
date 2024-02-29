package com.project.expensemanage.domain.expenditure.service;

import static org.mockito.BDDMockito.*;

import com.project.expensemanage.domain.category.exception.CategoryExceptionCode;
import com.project.expensemanage.domain.category.mock.CategoryMock;
import com.project.expensemanage.domain.category.repository.CategoryRepository;
import com.project.expensemanage.domain.expenditure.config.ExpenditureTestConfig;
import com.project.expensemanage.domain.expenditure.controller.dto.request.GetExpenditureList;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureIdResponse;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureListResponse;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureResponse;
import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import com.project.expensemanage.domain.expenditure.exception.ExpenditureExceptionCode;
import com.project.expensemanage.domain.expenditure.mock.ExpenditureMock;
import com.project.expensemanage.domain.expenditure.repoistory.ExpenditureRepository;
import com.project.expensemanage.domain.expenditure.repoistory.dto.GetExpenditureDetailsCondition;
import com.project.expensemanage.domain.user.exception.UserExceptionCode;
import java.time.LocalDate;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

  @Test
  @DisplayName("지출 단건 조회 테스트 : 성공")
  void get_expenditure_success_test() {
    // given
    Long userId = 1L;
    given(repository.findById(anyLong())).willReturn(Optional.of(mock.getEntity()));
    // when
    ExpenditureResponse result = service.getExpenditureDetails(userId, mock.getId());
    // then
    Assertions.assertThat(result.expenditureId()).isEqualTo(mock.getId());
    Assertions.assertThat(result.amount()).isEqualTo(100000L);
    Assertions.assertThat(result.memo()).isEqualTo("메모");
    Assertions.assertThat(result.category().categoryId()).isEqualTo(1L);
  }

  @Test
  @DisplayName("지출 단건 조회 테스트 : 실패[사용자 인증 실패]")
  void get_expenditure_fail_test() {
    // given
    Long userId = 2L;
    given(repository.findById(anyLong())).willReturn(Optional.of(mock.getEntity()));
    // when
    // then
    Assertions.assertThatThrownBy(() -> service.getExpenditureDetails(userId, mock.getId()))
        .hasMessage(UserExceptionCode.USER_NOT_SAME.getMessage());
  }

  @Test
  @DisplayName("지출 단건 조회 테스트 : 실패[조회 실패]")
  void get_expenditure_no_search_fail_test() {
    // given
    Long userId = 2L;
    given(repository.findById(anyLong())).willReturn(Optional.empty());
    // when
    // then
    Assertions.assertThatThrownBy(() -> service.getExpenditureDetails(userId, mock.getId()))
        .hasMessage(ExpenditureExceptionCode.NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("조건별 리스트 조회 : 성공")
  void get_expenditure_list_by_condition_test() {
    // given
    Long userId = 2L;
    GetExpenditureList dto = mock.getGetExpenditureListDto();

    given(
            repository.findAllExpenditureByCondition(
                Mockito.any(GetExpenditureDetailsCondition.class)))
        .willReturn(mock.getEntityList());
    given(
            repository.findTotalExpenditureByCategory(
                Mockito.any(GetExpenditureDetailsCondition.class)))
        .willReturn(mock.getTotalExpenditureByCategory());
    // when
    ExpenditureListResponse result = service.getExpenditureListByCondition(dto,1L);
    // then
    Assertions.assertThat(result.getExpenditureList()).isNotEmpty();
    Assertions.assertThat(result.getExpenditureList().get(0).getBudgetId()).isEqualTo(1L);
    Assertions.assertThat(result.getExpenditureList().get(0).getExpendedDate()).isAfterOrEqualTo(LocalDate.of(2024,02,01));
    Assertions.assertThat(result.getExpenditureList().get(0).getExpendedDate()).isBefore(LocalDate.of(2024,03,01));
  }
}
