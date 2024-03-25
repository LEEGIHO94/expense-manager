package com.project.expensemanage.notification.discord.mapper;

import com.project.expensemanage.commone.config.property.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("discord")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class DiscordProperties {
  private String username;
  private String baseUrl;
  private String avatarUrl;
  private String content;
  private String authorIcon;
  private String thumbnail;
  private Integer color;
  private String totalCategoryName;
  private String footerText;
  private String embedTitle;
  private String phraseWaring;
  private String phraseSafe;
}
