package com.project.expensemanage.notification.discord.body;

import com.project.expensemanage.notification.discord.body.embed.DiscordEmbedAuthor;
import com.project.expensemanage.notification.discord.body.embed.DiscordEmbedField;
import com.project.expensemanage.notification.discord.body.embed.DiscordEmbedFooter;
import com.project.expensemanage.notification.discord.body.embed.DiscordEmbedImage;
import com.project.expensemanage.notification.discord.body.embed.DiscordEmbedThumbnail;
import java.util.List;
import lombok.Builder;

/*
 * title : title of embed
 * type : type of embed (always "rich" for webhook embeds
 * description : description of embed
 * url : url of embed
 * timestamp : timestamp of embed content
 * color : color code of the embed
 * footer : Object
 * image : Object
 * thumbnail : Object
 * author : Object
 * fields : Array of embed field object
 * provier : Object
 * */
@Builder
public record DiscordEmbed(
    String title,
    String description,
    String url,
    String timestamp,
    Integer color,
    DiscordEmbedFooter footer,
    DiscordEmbedImage image,
    DiscordEmbedThumbnail thumbnail,
    DiscordEmbedAuthor author,
    List<DiscordEmbedField> fields) {}
