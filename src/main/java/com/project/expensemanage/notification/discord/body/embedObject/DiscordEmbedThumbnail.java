package com.project.expensemanage.notification.discord.body.embedObject;

import lombok.Builder;

@Builder
public record DiscordEmbedThumbnail(String url, Integer height, Integer width) {}
