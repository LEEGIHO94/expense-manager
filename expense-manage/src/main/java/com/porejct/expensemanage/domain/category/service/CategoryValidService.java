package com.porejct.expensemanage.domain.category.service;

import static com.porejct.expensemanage.domain.category.exception.CategoryExceptionCode.CATEGORY_NOT_FOUND;

import com.porejct.expensemanage.commone.exception.BusinessLogicException;
import com.porejct.expensemanage.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryValidService {

    private final CategoryRepository repository;

    public void validCategory(Long categoryId) {
        repository.findById(categoryId).orElseThrow(() -> new BusinessLogicException(
                CATEGORY_NOT_FOUND));
    }
}
