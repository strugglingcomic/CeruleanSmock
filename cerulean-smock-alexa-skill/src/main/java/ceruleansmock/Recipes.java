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
    recipes.put(DEFAULT_RECIPE.getMetadata().getTitle(), DEFAULT_RECIPE);
  }
  
  public static Recipe get(String item) {
    return recipes.get(item);
  }

  private Recipes() {}
}
