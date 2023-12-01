package com.project.expensemanage.recommendation.discord.body.embedObject;

import lombok.Builder;


/*
 * name : name of author
 * url : url of author
 * icon_url : url of author icon
 * */
@Builder
public record DiscordEmbedAuthor(String name, String url,String iconUrl) {}
