package ceruleansmock.model.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * simple Metadata POJO for a Recipe
 * @author codywang
 *
 */
public class Metadata {

  private final String title;
  private final String subtitle;
  private final String overview;
  private final int servings;
  private final int calories;
  private final int cookTime; // in minutes

  @JsonCreator
  public Metadata(
      @JsonProperty("title") final String title,
      @JsonProperty("subtitle") final String subtitle,
      @JsonProperty("overview") final String overview,
      @JsonProperty("servings") final int servings,
      @JsonProperty("calories") final int calories,
      @JsonProperty("cookTime") final int cookTime) {
    this.title = title;
    this.subtitle = subtitle;
    this.overview = overview;
    this.servings = servings;
    this.calories = calories;
    this.cookTime = cookTime;
  }

  public String getTitle() {
    return this.title;
  }

  public String getSubtitle() {
    return this.subtitle;
  }

  public String getOverview() {
    return this.overview;
  }

  public int getServings() {
    return servings;
  }

  public int getCalories() {
    return calories;
  }

  public int getCookTime() {
    return cookTime;
  }
}
