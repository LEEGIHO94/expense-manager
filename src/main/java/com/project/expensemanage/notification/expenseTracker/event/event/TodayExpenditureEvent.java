package com.project.expensemanage.notification.expenseTracker.event.event;

import com.project.expensemanage.domain.expenditure.repoistory.dto.TotalExpenditureByCategory;
import com.project.expensemanage.domain.user.entity.User;
import java.util.List;

public record TodayExpenditureEvent(List<TotalExpenditureByCategory> expenditureList, User user) {}
