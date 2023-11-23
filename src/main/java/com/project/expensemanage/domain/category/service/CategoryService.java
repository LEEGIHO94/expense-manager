package com.project.expensemanage.domain.category.service;

import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.domain.category.exception.CategoryExceptionCode;
import com.project.expensemanage.domain.category.repository.CategoryRepository;
import java.util.List;
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

    public List<Category> getCategoryList() {
        return repository.findAllByType();
    }
}
