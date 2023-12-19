package com.project.expensemanage.domain.user.mapper;

import com.project.expensemanage.domain.user.dto.request.UserPostRequest;
import com.project.expensemanage.domain.user.dto.response.UserIdResponse;
import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.user.enums.UserRole;
import com.project.expensemanage.domain.vo.Phone;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public static User toIdEntity(Long userId) {
    return User.builder().id(userId).build();
  }

  public UserIdResponse toIdDto(User user) {
    return UserIdResponse.builder().userId(user.getId()).build();
  }

  public User toEntity(UserPostRequest dto, String password) {
    return User.builder()
        .password(password)
        .phone(new Phone(dto.phone()))
        .userRole(UserRole.USER)
        .email(dto.email())
        .build();
  }
}
