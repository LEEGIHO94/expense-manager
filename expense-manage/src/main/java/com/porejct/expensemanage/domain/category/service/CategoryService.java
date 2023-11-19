package com.porejct.expensemanage.domain.category.service;

import com.porejct.expensemanage.commone.exception.BusinessLogicException;
import com.porejct.expensemanage.domain.category.entity.Category;
import com.porejct.expensemanage.domain.category.exception.CategoryExceptionCode;
import com.porejct.expensemanage.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public Category postCategory(Category category){
        validCategoryExist(category.getName());

        return repository.save(category);
    }

    private void validCategoryExist(String name){
       repository.findByName(name).ifPresent(d -> {throw new BusinessLogicException(
               CategoryExceptionCode.CATEGORY_EXIST);});
    }
}
