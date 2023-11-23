package com.project.expensemanage.domain.expenditure.service;

import com.project.expensemanage.domain.category.service.CategoryValidService;
import com.project.expensemanage.domain.expenditure.controller.dto.request.PostExpenditureRequest;
import com.project.expensemanage.domain.expenditure.controller.dto.response.ExpenditureIdResponse;
import com.project.expensemanage.domain.expenditure.mapper.ExpenditureMapper;
import com.project.expensemanage.domain.expenditure.repoistory.ExpenditureRepository;
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

}
