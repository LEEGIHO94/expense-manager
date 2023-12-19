package com.project.expensemanage.notification.discord.body.embed;

import lombok.Builder;

@Builder
public record DiscordEmbedThumbnail(String url, Integer height, Integer width) {}
