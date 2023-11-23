package com.project.expensemanage.domain.category.controller;

import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.expensemanage.commone.config.SecurityConfig;
import com.project.expensemanage.commone.security.config.AuthTestConfig;
import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.domain.category.mapper.CategoryMapper;
import com.project.expensemanage.domain.category.mock.CategoryMock;
import com.project.expensemanage.domain.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CategoryController.class)
@Import({SecurityConfig.class, AuthTestConfig.class, CategoryService.class, CategoryMock.class,
        CategoryMapper.class})
class CategoryControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    CategoryMock mock;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    CategoryService service;

    @Test
    @DisplayName("카테고리 사용자 등록 : 성공")
    void post_custom_category_test() throws Exception {
        // given
        String content = objectMapper.writeValueAsString(mock.customCategoryPostDto());

        BDDMockito.given(service.postCategory(any(Category.class)))
                .willReturn(mock.customEntityMock());

        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post("/api/categories/client").content(content).contentType(
                        MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.categoryId").isNumber());
    }

    @Test
    @DisplayName("카테고리 관리자 등록 : 성공")
    void post_standard_category_test() throws Exception {
        // given
        String content = objectMapper.writeValueAsString(mock.standardCategoryPostDto());

        BDDMockito.given(service.postCategory(any(Category.class)))
                .willReturn(mock.customEntityMock());

        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post("/api/categories/admin").content(content).contentType(
                        MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.categoryId").isNumber());
    }


    @Test
    @DisplayName("카테고리 조회 : 성공")
    void get_category_list_test() throws Exception {
        // given
        BDDMockito.given(service.getCategoryList()).willReturn(mock.EntityListMock());
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/categories"));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].categoryId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").isString());
    }
}