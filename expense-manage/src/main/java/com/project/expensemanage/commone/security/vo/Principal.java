package com.project.expensemanage.commone.security.vo;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Getter
public class Principal extends User {
    private final Long id;

    public Principal(com.project.expensemanage.domain.user.entity.User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getUserRole().getRoll()));
        this.id = user.getId();
    }

    public Principal(Claims claims) {
        super(claims.getSubject(), "", AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get("authorities",String.class)));
        this.id = claims.get("id",Long.class);
    }


}
