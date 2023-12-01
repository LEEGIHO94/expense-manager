package com.project.expensemanage.recommendation.discord.body.embedObject;

/*
 * name : name of the field
 * value : value of the field
 * inline : whether or not this field should display inline
 * */

import lombok.Builder;

@Builder
public record DiscordEmbedField(String name, String value,boolean inline) {}
