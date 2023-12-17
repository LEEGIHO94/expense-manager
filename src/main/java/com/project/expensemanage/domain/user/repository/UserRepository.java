package com.project.expensemanage.domain.user.repository;

import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.user.enums.ServiceSubscriber;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  List<User> findUserByServiceSubscriber(ServiceSubscriber serviceSubscriber);
}
