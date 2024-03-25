package com.project.expensemanage.domain.analysis.config;

import com.project.expensemanage.commone.utils.date.DateUtils;
import com.project.expensemanage.domain.analysis.repository.AnalysisRepository;
import com.project.expensemanage.domain.analysis.service.AnalysisService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AnalysisConfig {
  @Bean
  public AnalysisRepository analysisRepository() {
    return Mockito.mock(AnalysisRepository.class);
  }

  @Bean
  public DateUtils dateUtils() {
    return new DateUtils();
  }

  @Bean
  public AnalysisService analysisService() {
    return new AnalysisService(analysisRepository(), dateUtils());
  }
}
