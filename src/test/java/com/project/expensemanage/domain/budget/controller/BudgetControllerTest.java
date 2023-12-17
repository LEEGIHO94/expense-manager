package com.project.expensemanage.domain.budget.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.expensemanage.commone.config.JacksonConfig;
import com.project.expensemanage.commone.config.SecurityConfig;
import com.project.expensemanage.commone.security.annotation.WithMockCustomUser;
import com.project.expensemanage.commone.security.config.AuthTestConfig;
import com.project.expensemanage.domain.budget.mock.BudgetMock;
import com.project.expensemanage.domain.budget.service.BudgetService;
import com.project.expensemanage.domain.budget.dto.request.PostBudgetRequest;
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

@WebMvcTest(BudgetController.class)
@Import({
  AuthTestConfig.class,
  BudgetController.class,
  BudgetMock.class,
  JacksonConfig.class,
  SecurityConfig.class
})
class BudgetControllerTest {

  @Autowired MockMvc mvc;

  @MockBean BudgetService service;
  @Autowired BudgetMock mock;
  @Autowired ObjectMapper objectMapper;

  @Test
  @DisplayName("예산 등록 테스트 : 성공")
  @WithMockCustomUser
  void post_budget_success_test() throws Exception {
    String content = objectMapper.writeValueAsString(mock.postDtoMock());
    // given
    BDDMockito.given(service.postBudget(anyLong(), any(PostBudgetRequest.class)))
        .willReturn(mock.idDtoMock());
    // when
    ResultActions perform =
        mvc.perform(
            MockMvcRequestBuilders.post("/api/budgets")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }
}
