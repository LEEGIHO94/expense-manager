package com.project.expensemanage.commone.utils.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.project.expensemanage.commone.config.property.YamlPropertySourceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = CookieProperties.class)
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
class CookiePropertiesTest {

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  private CookieProperties cookieProperties;

  @Test
  @DisplayName("yml 설정 파일 읽어 오기")
  void jwtPropertiesCheck() {
    // given
    // when
    // then
    assertAll(
        () -> assertThat(cookieProperties.getCookieName()).isEqualTo("Refresh"),
        () -> assertThat(cookieProperties.getAcceptedUrl()).isEqualTo("/api/auth/reissue"),
        () -> assertThat(cookieProperties.getLimitTime()).isNotZero());
  }
}
