package com.project.expensemanage.domain.category.controller;

import com.project.expensemanage.commone.dto.ResponseDto;
import com.project.expensemanage.commone.dto.ResponseStatus;
import com.project.expensemanage.commone.utils.response.UrlCreator;
import com.project.expensemanage.domain.category.dto.GetCategoryResponse;
import com.project.expensemanage.domain.category.dto.request.PostStandardCategoryRequest;
import com.project.expensemanage.domain.category.dto.response.CategoryIdResponse;
import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.domain.category.mapper.CategoryMapper;
import com.project.expensemanage.domain.category.service.CategoryService;
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

  @PostMapping("/admin")
  public ResponseEntity<ResponseDto<CategoryIdResponse>> postCustomCategory(
      @RequestBody @Valid PostStandardCategoryRequest post) {
    ResponseDto<CategoryIdResponse> response =
        ResponseDto.<CategoryIdResponse>builder()
            .data(service.postCategory(post))
            .status(ResponseStatus.CREATE)
            .build();
    URI location = UrlCreator.createUri(DEFAULT, response.getData().categoryId());
    return ResponseEntity.created(location).body(response);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<List<GetCategoryResponse>>> getCategoryList() {
    ResponseDto<List<GetCategoryResponse>> response = ResponseDto.<List<GetCategoryResponse>>builder()
        .status(ResponseStatus.GET)
        .data(service.getCategoryList())
        .build();
    return ResponseEntity.ok(response);
  }
  //  @GetMapping("/{categoryId}")
  //  public ResponseEntity<ResponseDto<GetCategoryResponse>> getCategory(@PathVariable Long
  // categoryId){
  //    service.getCategory(categoryId);
  //  }
}
