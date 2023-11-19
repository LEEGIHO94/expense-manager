package com.porejct.expensemanage.domain.user.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.porejct.expensemanage.commone.auditing.BaseTime;
import com.porejct.expensemanage.domain.user.enums.UserRole;
import com.porejct.expensemanage.domain.vo.Phone;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
    private UserRole userRole;
    @Embedded
    private Phone phone;
}
