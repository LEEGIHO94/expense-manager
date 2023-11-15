package com.porejct.expensemanage.commone.utils.response;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieUtils {
    private final CookieProperties cookieProperties;
    public Cookie createCookie(String key) {
        Cookie cookie = new Cookie(cookieProperties.getCookieName(), key);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(cookieProperties.getAcceptedUrl());
        cookie.setMaxAge(60 * cookieProperties.getLimitTime());
        return cookie;
    }


}
