package com.project.expensemanage.recommendation.discord.body;

import com.project.expensemanage.recommendation.discord.body.embedObject.DiscordEmbedAuthor;
import com.project.expensemanage.recommendation.discord.body.embedObject.DiscordEmbedField;
import com.project.expensemanage.recommendation.discord.body.embedObject.DiscordEmbedFooter;
import com.project.expensemanage.recommendation.discord.body.embedObject.DiscordEmbedImage;
import com.project.expensemanage.recommendation.discord.body.embedObject.DiscordEmbedThumbnail;
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
