package com.project.expensemanage.domain.analysis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.expensemanage.commone.config.JacksonConfig;
import com.project.expensemanage.commone.config.SecurityConfig;
import com.project.expensemanage.commone.security.annotation.WithMockCustomUser;
import com.project.expensemanage.commone.security.config.AuthTestConfig;
import com.project.expensemanage.domain.analysis.controller.dto.ExpenditureAnalysisResponse;
import com.project.expensemanage.domain.analysis.controller.dto.ExpenditureDiffResponse;
import com.project.expensemanage.domain.analysis.service.AnalysisService;
import com.project.expensemanage.domain.budget.mock.BudgetMock;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AnalysisController.class)
@Import({
  AuthTestConfig.class,
  AnalysisController.class,
  BudgetMock.class,
  JacksonConfig.class,
  SecurityConfig.class
})
class AnalysisControllerTest {

  final String DEFAULT = "/api/analysis";
  @Autowired MockMvc mvc;
  @MockBean AnalysisService service;
  @Autowired BudgetMock mock;
  @Autowired ObjectMapper objectMapper;

  @Test
  @DisplayName("지난 주 대비 이번 주 지출 비율 테스트")
  @WithMockCustomUser
  void get_weekly_expenditure_comparison_test() throws Exception {
    // given
    BDDMockito.given(service.getWeeklyExpenditureComparison(Mockito.anyLong()))
        .willReturn(expenditureDiffResponseList());
    // when
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT + "/weekly"));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").isString())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].categoryId").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].categoryName").isString())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].rate").isNumber());
  }

  @Test
  @DisplayName("지난 달 대비 이번 달 지출 비율 테스트")
  @WithMockCustomUser
  void get_monthly_expenditure_comparison_test() throws Exception {
    // given
    BDDMockito.given(service.getMonthlyExpenditureComparison(Mockito.anyLong()))
        .willReturn(expenditureDiffResponseList());
    // when
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT + "/monthly"));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").isString())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].categoryId").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].categoryName").isString())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].rate").isNumber());
  }

  @Test
  @DisplayName("전체 사용자 대비 사용자 지출 비율 테스트")
  @WithMockCustomUser
  void get_total_expenditure_comparison_test() throws Exception {
    // given
    BDDMockito.given(service.getExpenditureAnalysisByUser(Mockito.anyLong()))
        .willReturn(expenditureAnalysisResponse());
    // when
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap())
        .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").isString())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.analysisDate").isString())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.expenditureRate").isNumber());
  }

  List<ExpenditureDiffResponse> expenditureDiffResponseList() {
    return List.of(
        ExpenditureDiffResponse.builder().categoryId(1L).categoryName("카테고리이름1").rate(10L).build(),
        ExpenditureDiffResponse.builder().categoryId(2L).categoryName("카테고리이름2").rate(20L).build(),
        ExpenditureDiffResponse.builder().categoryId(3L).categoryName("카테고리이름3").rate(30L).build());
  }

  ExpenditureAnalysisResponse expenditureAnalysisResponse() {
    return ExpenditureAnalysisResponse.builder()
        .analysisDate(LocalDate.now())
        .expenditureRate(90L)
        .build();
  }
}
