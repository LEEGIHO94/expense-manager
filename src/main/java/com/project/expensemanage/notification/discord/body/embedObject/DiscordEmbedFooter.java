package com.project.expensemanage.notification.discord.body.embedObject;

/*
* text : footer text
* icon_uri : 	url of footer icon (only supports http(s) and attachments)
* */

import lombok.Builder;

@Builder
public record DiscordEmbedFooter(String text,String icon_url) {}
