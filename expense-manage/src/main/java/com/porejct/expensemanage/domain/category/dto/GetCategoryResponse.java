package com.porejct.expensemanage.domain.category.dto;

import lombok.Builder;

@Builder
public record GetCategoryResponse(Long id,String name) {

}
