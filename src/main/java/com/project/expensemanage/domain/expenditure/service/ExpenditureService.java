package com.project.expensemanage.domain.expenditure.service;

import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.domain.category.service.CategoryValidService;
import com.project.expensemanage.domain.expenditure.controller.dto.request.GetExpenditureList;
import com.project.expensemanage.domain.expenditure.controller.dto.request.PostExpenditureRequest;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureIdResponse;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureListResponse;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureResponse;
import com.project.expensemanage.domain.expenditure.entity.Expenditure;
import com.project.expensemanage.domain.expenditure.exception.ExpenditureExceptionCode;
import com.project.expensemanage.domain.expenditure.mapper.ExpenditureMapper;
import com.project.expensemanage.domain.expenditure.repoistory.ExpenditureRepository;
import com.project.expensemanage.domain.user.exception.UserExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenditureService {

  private final ExpenditureRepository repository;
  private final ExpenditureMapper mapper;
  private final CategoryValidService categoryValidService;

  public ExpenditureIdResponse postExpenditure(PostExpenditureRequest post, Long userId) {
    categoryValidService.validCategory(post.categoryId());
    return mapper.toIdDto(repository.save(mapper.toEntity(post, userId)));
  }

  public void deleteExpenditure(Long expenditureId, Long userId) {
    repository
        .findById(expenditureId)
        .ifPresent(
            expenditure -> {
              if (!expenditure.getUser().getId().equals(userId)) {
                throw new BusinessLogicException(UserExceptionCode.USER_NOT_SAME);
              }
              repository.deleteById(expenditureId);
            });
  }

  // 리팩토링 findById 대신 user까지 한번에 가져오는 패치조인 필수
  public ExpenditureResponse getExpenditureDetails(Long userId, Long expenditureId) {
    Expenditure entity = validExpenditure(expenditureId);
    if (!entity.getUser().getId().equals(userId)) {
      throw new BusinessLogicException(UserExceptionCode.USER_NOT_SAME);
    }
    return mapper.toDto(entity);
  }

  /*
   * 1. 카테고리가 존재하면 해당 카테고리의 데이터만 조회
   * 2. 최대, 최소 금액 설정 시 해당 금액 범위만 조회
   * 3. 특정 기간 조회 (필수) 즉 특정 기간 조회는 필수로 하되 최대 최소 금액, 카테고리가 존재하면 해당 데이터를 받아와라
   * 4. 양방향 매핑을 하지 않아 2회 조회를 들어간 것
   * */
  public ExpenditureListResponse getExpenditureListByCondition(
      GetExpenditureList dto, Long userId) {

    return mapper.toDto(
        repository.findAllExpenditureByCondition(mapper.toRepositoryDto(dto, userId)),
        repository.findTotalExpenditureByCategory(mapper.toRepositoryDto(dto, userId)));
  }

  private Expenditure validExpenditure(Long expenditureId) {
    return repository
        .findById(expenditureId)
        .orElseThrow(() -> new BusinessLogicException(ExpenditureExceptionCode.NOT_FOUND));
  }
}
