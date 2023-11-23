package com.project.expensemanage.domain.user.service;

import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.user.exception.UserExceptionCode;
import com.project.expensemanage.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserValidService {
    private final UserRepository repository;

    public User validUser(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new BusinessLogicException(
                UserExceptionCode.USER_NOT_FOUND));
    }
}
