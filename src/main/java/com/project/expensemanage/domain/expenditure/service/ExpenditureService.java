package com.project.expensemanage.domain.expenditure.service;

import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.domain.category.service.CategoryValidService;
import com.project.expensemanage.domain.expenditure.controller.dto.request.PostExpenditureRequest;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureIdResponse;
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

    public void deleteExpenditure(Long expenditureId,Long userId) {
        repository.findById(expenditureId).ifPresent(expenditure -> {
            if (!expenditure.getUser().getId().equals(userId)) {
                throw new BusinessLogicException(UserExceptionCode.USER_NOT_SAME);
            }
            repository.deleteById(expenditureId);
        });
    }
}
