package com.porejct.expensemanage.domain.user.entity;

import static jakarta.persistence.GenerationType.*;

import com.porejct.expensemanage.commone.auditing.BaseTime;
import com.porejct.expensemanage.domain.user.enums.UserRole;
import jakarta.persistence.Column;
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
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String email;
    private String password;
    private UserRole userRole;

}
