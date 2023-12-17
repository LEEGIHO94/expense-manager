package com.project.expensemanage.commone.security.annotation;

import com.project.expensemanage.domain.user.enums.UserRole;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
  long userId() default 2L;

  UserRole userRole() default UserRole.USER;
}
