package ceruleansmock;

import java.util.HashMap;
import java.util.Map;

import ceruleansmock.model.Recipe;

/** 
 * Static class to store and provide access to Recipe data
 * @author codywang
 *
 */
public final class Recipes {
  public static final Recipe DEFAULT_RECIPE = new Recipe();
  
  private static final Map<String, Recipe> recipes = new HashMap<String, Recipe>();

  static {
    recipes.put(DEFAULT_RECIPE.getMetadata().getTitle().toLowerCase(), DEFAULT_RECIPE);
  }
  
  /**
   * recipe titles are case-insensitive
   * @param recipeTitle
   * @return
   */
  public static Recipe get(String recipeTitle) {
    return recipes.get(recipeTitle.toLowerCase());
  }

  private Recipes() {}
}
