package com.project.expensemanage.notification.discord.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscordEmbedTypes {
  RICH("rich","generic embed rendered from embed attributes"),
  IMAGE("image","image embed"),
  VIDEO("video","video embed"),
  GIFV("gifv", "\tanimated gif image embed rendered as a video embed"),
  ARTICLE("article","article embed"),
  LINK("link","link embed");

  private String type;
  private String description;
}
