package com.project.expensemanage.domain.user.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.project.expensemanage.commone.auditing.BaseTime;
import com.project.expensemanage.domain.user.enums.ServiceSubscriber;
import com.project.expensemanage.domain.user.enums.UserRole;
import com.project.expensemanage.domain.vo.Phone;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class User extends BaseTime {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(updatable = false, name = "user_id")
  private Long id;

  private String email;
  private String password;
  private String url;

  @Enumerated(EnumType.STRING)
  private UserRole userRole;

  @Enumerated(EnumType.STRING)
  private ServiceSubscriber serviceSubscriber;

  @Embedded private Phone phone;
}
