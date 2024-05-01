package com.project.expensemanage.notification.scheduler;

import com.project.expensemanage.commone.utils.date.DateUtils;
import com.project.expensemanage.notification.recommendation.dto.CategoryIdAndExpenditure;
import com.project.expensemanage.notification.recommendation.repository.RecommendationRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheScheduler {

  private RecommendationRepository repository;
  private DateUtils dateUtils;

  @Scheduled(cron = "0 0 7 * * *")
  public void setExpenditureAvgByCategory() {
    List<CategoryIdAndExpenditure> monthlyCategoryTotalExpenditure = findExpenditure();

    for (CategoryIdAndExpenditure expenditure : monthlyCategoryTotalExpenditure) {
      setCache(expenditure);
    }
  }
  @Scheduled(cron = "0 0 9 * * *")
  public void clearCache(){
    ExpenditureAvgCache.clear();
  }

  private Long setCache(CategoryIdAndExpenditure expenditure) {
    return ExpenditureAvgCache.monthlyCategoryAverageExpenditureCache.put(
        expenditure.getCategoryId(),
        getAvgExpenditure(expenditure));
  }

  private long getAvgExpenditure(CategoryIdAndExpenditure expenditure) {
    if(expenditure.getExpenditure() / LocalDate.now().getDayOfMonth() >= 100)
      return 0;
    else
      return expenditure.getExpenditure() / LocalDate.now().getDayOfMonth() / 100 * 100;
  }

  private List<CategoryIdAndExpenditure> findExpenditure() {
    List<CategoryIdAndExpenditure> monthlyCategoryTotalExpenditure = repository.findMonthlyCategoryTotalExpenditure(
        dateUtils.startOfMonth(), dateUtils.getLocalDate());
    return monthlyCategoryTotalExpenditure;
  }
}
