package com.porejct.expensemanage.domain.category.controller;

import com.porejct.expensemanage.commone.dto.ResponseDto;
import com.porejct.expensemanage.commone.utils.response.UrlCreator;
import com.porejct.expensemanage.domain.category.dto.GetCategoryResponse;
import com.porejct.expensemanage.domain.category.dto.request.PostCustomCategoryRequest;
import com.porejct.expensemanage.domain.category.dto.request.PostStandardCategoryRequest;
import com.porejct.expensemanage.domain.category.dto.response.CategoryIdResponse;
import com.porejct.expensemanage.domain.category.entity.Category;
import com.porejct.expensemanage.domain.category.mapper.CategoryMapper;
import com.porejct.expensemanage.domain.category.service.CategoryService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private static final String DEFAULT = "/api/categories";
    private final CategoryService service;
    private final CategoryMapper mapper;

    @PostMapping("/client")
    public ResponseEntity<ResponseDto<CategoryIdResponse>> postCustomCategory(@RequestBody @Valid PostCustomCategoryRequest post) {
        Category result = service.postCategory(mapper.toEntity(post));
        ResponseDto<CategoryIdResponse> responseDto = mapper.toDto(result);
        URI location = UrlCreator.createUri(DEFAULT, result.getId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @PostMapping("/admin")
    public ResponseEntity<ResponseDto<CategoryIdResponse>> postCustomCategory(@RequestBody @Valid PostStandardCategoryRequest post) {
        Category result = service.postCategory(mapper.toEntity(post));
        ResponseDto<CategoryIdResponse> responseDto = mapper.toDto(result);
        URI location = UrlCreator.createUri(DEFAULT, result.getId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<GetCategoryResponse>>> getCategoryList() {
        List<Category> result = service.getCategoryList();

        return ResponseEntity.ok(mapper.toDto(result));
    }
}
