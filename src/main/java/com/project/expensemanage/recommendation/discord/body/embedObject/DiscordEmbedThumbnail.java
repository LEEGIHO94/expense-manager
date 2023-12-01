package com.project.expensemanage.recommendation.discord.body.embedObject;

import lombok.Builder;

@Builder
public record DiscordEmbedThumbnail(String url, Integer height, Integer width) {}
