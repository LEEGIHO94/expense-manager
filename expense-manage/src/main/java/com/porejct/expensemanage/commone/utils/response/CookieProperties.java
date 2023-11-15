package com.porejct.expensemanage.commone.utils.response;

import com.porejct.expensemanage.commone.config.property.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("cookie")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class CookieProperties {
    private int limitTime;
    private String acceptedUrl;
    private String cookieName;
}
