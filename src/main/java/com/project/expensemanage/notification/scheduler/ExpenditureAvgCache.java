package com.project.expensemanage.notification.scheduler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExpenditureAvgCache {

  public static final Map<Long, Long> monthlyCategoryAverageExpenditureCache = new ConcurrentHashMap<>();

  public static void add(Long categoryId,Long minExpenditure){
    monthlyCategoryAverageExpenditureCache.put(categoryId,minExpenditure);
  }
  public static void clear(){
    monthlyCategoryAverageExpenditureCache.clear();
  }
  public static Long get(Long categoryId){
    return monthlyCategoryAverageExpenditureCache.getOrDefault(categoryId, Long.MAX_VALUE);
  }
}