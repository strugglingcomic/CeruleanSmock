package ceruleansmock.model.recipe;

/**
 * simple Metadata POJO for a Recipe
 * @author codywang
 *
 */
public class Metadata {

  private final String title;
  private final String subtitle;
  private final String overview;
  private final Integer servings;
  private final Integer calories;
  private final Integer cookTime; // in minutes
  
  public Metadata(final String title, final String subtitle, final String overview, final Integer servings, final Integer calories, final Integer cookTime) {
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

  public Integer getServings() {
    return servings;
  }

  public Integer getCalories() {
    return calories;
  }

  public Integer getCookTime() {
    return cookTime;
  }
}
