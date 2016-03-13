package ceruleansmock.model.recipe;

import com.amazonaws.util.StringUtils;
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
    this.title = StringUtils.trim(title);
    this.subtitle = StringUtils.trim(subtitle);
    this.overview = StringUtils.trim(overview);
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + calories;
    result = prime * result + cookTime;
    result = prime * result + ((overview == null) ? 0 : overview.hashCode());
    result = prime * result + servings;
    result = prime * result + ((subtitle == null) ? 0 : subtitle.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Metadata other = (Metadata) obj;
    if (calories != other.calories) {
      return false;
    }
    if (cookTime != other.cookTime) {
      return false;
    }
    if (overview == null) {
      if (other.overview != null) {
        return false;
      }
    } else if (!overview.equals(other.overview)) {
      return false;
    }
    if (servings != other.servings) {
      return false;
    }
    if (subtitle == null) {
      if (other.subtitle != null) {
        return false;
      }
    } else if (!subtitle.equals(other.subtitle)) {
      return false;
    }
    if (title == null) {
      if (other.title != null) {
        return false;
      }
    } else if (!title.equals(other.title)) {
      return false;
    }
    return true;
  }
}
