package com.project.expensemanage.domain.category.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.project.expensemanage.commone.redis.repository.RedisRepository;
import com.project.expensemanage.domain.category.dto.GetCategoryResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryServiceTest2 {
  @Autowired
  CategoryService service;

  RedisRepository repository;


  @Test
  @DisplayName("테스트")
  void test() throws Exception{
    List<GetCategoryResponse> categoryList = service.getCategoryList();
        service.getCategory(1L);
    //    repository.findByKey();

    System.out.println("categoryList = " + categoryList);
  }
}
