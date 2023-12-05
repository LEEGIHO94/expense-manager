package com.project.expensemanage.notification.recommendation.discord.body;

import java.util.List;
import lombok.Builder;

@Builder
public record DiscordBody(
    String username, String avatar_url, String content, List<DiscordEmbed> embeds) {}
