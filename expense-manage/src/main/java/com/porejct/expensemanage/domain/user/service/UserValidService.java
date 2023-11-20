package com.porejct.expensemanage.domain.user.service;

import com.porejct.expensemanage.commone.exception.BusinessLogicException;
import com.porejct.expensemanage.domain.user.entity.User;
import com.porejct.expensemanage.domain.user.exception.UserExceptionCode;
import com.porejct.expensemanage.domain.user.repository.UserRepository;
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
