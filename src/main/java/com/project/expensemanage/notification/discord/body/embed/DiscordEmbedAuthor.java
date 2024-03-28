package com.project.expensemanage.notification.discord.body.embed;

import lombok.Builder;

/*
 * name : name of author
 * url : url of author
 * icon_url : url of author icon
 * */
@Builder
public record DiscordEmbedAuthor(String name, String url, String iconUrl) {}
