package com.project.expensemanage.recommendation.discord.mapper;

import com.project.expensemanage.recommendation.discord.body.DiscordBody;
import com.project.expensemanage.recommendation.discord.body.DiscordEmbed;
import com.project.expensemanage.recommendation.discord.body.embedObject.DiscordEmbedAuthor;
import com.project.expensemanage.recommendation.discord.body.embedObject.DiscordEmbedField;
import com.project.expensemanage.recommendation.discord.body.embedObject.DiscordEmbedFooter;
import com.project.expensemanage.recommendation.discord.body.embedObject.DiscordEmbedThumbnail;
import com.project.expensemanage.recommendation.dto.RecommendationExpenditure;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscordMapper {
  private final DiscordProperties properties;

  public String getBaseUrl(){
    return properties.getBaseUrl();
  }

  public DiscordBody toDiscordBody(List<RecommendationExpenditure> totalExpenditureList) {
    return DiscordBody.builder()
        .content(properties.getContent())
        .embeds(List.of(createDiscordEmbed(totalExpenditureList)))
        .avatar_url(properties.getAvatarUrl())
        .username(properties.getUsername())
        .build();
  }

  private DiscordEmbed createDiscordEmbed(List<RecommendationExpenditure> totalExpenditureList) {
    return DiscordEmbed.builder()
        .color(properties.getColor())
        .description("Embded 설명")
        .title(properties.getEmbedTitle())
        .footer(createFooter())
        .fields(discordEmbedFieldList(totalExpenditureList))
        .thumbnail(createThumbnail())
        .author(createAuthor())
        .build();
  }

  private List<DiscordEmbedField> discordEmbedFieldList(
      List<RecommendationExpenditure> totalExpenditureList) {
    List<DiscordEmbedField> result = new ArrayList<>();
    for (RecommendationExpenditure recommendationExpenditure : totalExpenditureList) {
      result.add(createField(recommendationExpenditure));
    }
    return result;
  }

  private DiscordEmbedField createField(RecommendationExpenditure expenditure) {
    return DiscordEmbedField.builder()
        .inline(false)
        .name(expenditure.categoryName())
        .value(createValue(expenditure))
        .build();
  }

  private DiscordEmbedFooter createFooter() {
    return DiscordEmbedFooter.builder().text(properties.getFooterText()).build();
  }

  private DiscordEmbedThumbnail createThumbnail() {
    return DiscordEmbedThumbnail.builder().url(properties.getThumbnail()).build();
  }

  private DiscordEmbedAuthor createAuthor() {
    return DiscordEmbedAuthor.builder()
        .iconUrl(properties.getAuthorIcon())
        .name("TESTTTTT")
        .build();
  }

  private String createValue(RecommendationExpenditure expenditure){
    int dayOfMonth = LocalDate.now().getDayOfMonth();
    int endOfMonth = YearMonth.now().atEndOfMonth().getDayOfMonth();

    int restDay = getRestDay(dayOfMonth, endOfMonth);

    StringBuilder sb = new StringBuilder();
    sb.append(getPhrase(expenditure, restDay, endOfMonth))
        .append("\n")
        .append("설정한 예산 : ")
        .append(expenditure.budget())
        .append("\n")
        .append("지출 : ")
        .append(expenditure.totalExpenditure())
        .append("\n")
        .append("금일 권장 지출 : ")
        .append(createRecommendedExpenditure(expenditure, restDay,endOfMonth));

    return sb.toString();
  }


  /*
  * 로직 수정 필요
  * 1. 최소 금액을 설정은 사용자가 설정한 예산을 해당월로 나눈 값, 즉 1일 사용 예상 량으로 한다.
  * */
  private String createRecommendedExpenditure(RecommendationExpenditure expenditure, int restDay,int endOfMonth) {
    long amount =  Math.max(expenditure.budget() - expenditure.totalExpenditure(),0) / restDay;
    long amountOfReference = expenditure.budget() / endOfMonth;
    return getMaxRecommendExpenditure(amount,amountOfReference);
  }

  private String getMaxRecommendExpenditure(Long amount, Long amountOfReference){
    return roundAmount(amount) >= roundAmount(amountOfReference)
        ? roundAmount(amount) + " (**설정 예산 만족**)"
        : roundAmount(amountOfReference) + " (**!!예산 초과 1일 사용 권장량 제공!!**)";
}

  private Long roundAmount(Long num){
    if(num / 1000 != 0){
      return num / 1000 * 1000;
    }

    if(num / 100 != 0){
      return num / 100 * 100;
    }

    if(num / 10 != 0){
      return num / 10 * 10;
    }

    return num;
}

  private String getPhrase(RecommendationExpenditure expenditure, int restDay, int endOfMonth) {
    return checkWaring(expenditure, restDay, endOfMonth) ? properties.getPhraseSafe() : properties.getPhraseWaring();
  }

  /*
  * true : 지출이 남은 날보다 짧다
  * false : 지출이 남은 날 보다 많다.
  * */
  private boolean checkWaring(RecommendationExpenditure expenditure, int restDay, int endOfMonth) {
    return (restDay / endOfMonth) <= (expenditure.budget() - expenditure.totalExpenditure());
  }

  private int getRestDay(int dayOfMonth, int endOfMonth){
    return endOfMonth - dayOfMonth == 0 ? 1 : endOfMonth - dayOfMonth;
  }
}

