package com.project.expensemanage.domain.budget.service.dto;

import com.project.expensemanage.domain.budget.repository.dto.RecommendedBudgetData;
import java.util.ArrayList;
import java.util.List;

public class RecommendBudgetHelper {

  private static final int RATE_UNDER_LIMIT_PER = 10;
  private List<RecommendedBudgetData> data;
  private List<RecommendBudget> result;
  private Long requestBudget;
  private RecommendBudget etc;

  public RecommendBudgetHelper(List<RecommendedBudgetData> data, Long requestBudget) {
    this.data = data;
    this.requestBudget = requestBudget;
    this.etc = new RecommendBudget("기타");
    this.result = new ArrayList<>();
  }

  private Long getTotalAmount() {
    return data.stream().mapToLong(RecommendedBudgetData::getAmount).sum();
  }

  /*
   * data 구현 비율은 소숫점을 제거한 정수부만 구현되어 있음
   * 천단위에서 절삭
   * */

  public List<RecommendBudget> getRecommendedData() {
    setTotalData();
    return result;
  }

  private void setTotalData() {
    data.forEach(this::addBudgetList);
    setEtcBudget();
    result.add(etc);
  }

  private void addBudgetList(RecommendedBudgetData data) {
    if (data.getName().equals(etc.getCategoryName())) {
      etc.setCategoryId(data.getCategoryId());
      return;
    }

    if (getRate(data.getAmount()) < RATE_UNDER_LIMIT_PER) {
      return;
    }

    result.add(createRecommendBudget(data));
  }

  private void setEtcBudget() {
    etc.setAmount(createEtcAmount());
  }

  private long createEtcAmount() {
    return (requestBudget - result.stream().mapToLong(RecommendBudget::getAmount).sum());
  }

  private RecommendBudget createRecommendBudget(RecommendedBudgetData budget) {
    return RecommendBudget.builder()
        .amount(ratedAmount(getRate(budget.getAmount())))
        .categoryName(budget.getName())
        .categoryId(budget.getCategoryId())
        .build();
  }

  private Long ratedAmount(int rate) {
    long ratedAmount = requestBudget * rate / 1000;
    return ratedAmount * 1000;
  }

  // 비율 구하기
  private int getRate(Long amount) {
    return (int) (amount * 100 / getTotalAmount());
  }
}
