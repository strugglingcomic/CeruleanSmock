package ceruleansmock;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ceruleansmock.model.Recipe;

/** 
 * Static class to store and provide access to Recipe data
 * @author codywang
 *
 */
public final class Recipes {
  public static final Recipe DEFAULT_RECIPE = new Recipe();
  
  private static final Random generator = new Random();
  private static final Map<String, Recipe> recipes = new HashMap<String, Recipe>();

  static {
    recipes.put(DEFAULT_RECIPE.getMetadata().getTitle().toLowerCase(), DEFAULT_RECIPE);
    ObjectMapper mapper = new ObjectMapper();
    try {
      StringBuilder sb = new StringBuilder();
      // TODO: create valid JSON list of recipes using StrinbBuilder, for deserialization

      List<Recipe> recipeList = mapper.readValue(sb.toString(), new TypeReference<List<Recipe>>() {});
      for(Recipe recipe : recipeList) {
        recipes.put(recipe.getMetadata().getTitle().toLowerCase().trim(), recipe);
      }
    } catch (JsonParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * recipe titles are case-insensitive
   * @param recipeTitle
   * @return
   */
  public static Recipe get(String recipeTitle) {
    if(StringUtils.isBlank(recipeTitle)) {
      return null;
    } else {
      return recipes.get(recipeTitle.toLowerCase().trim());
    }
  }
  /**
   * return a random recipe from the map
   */
  public static Recipe random() {
    Object[] values = recipes.values().toArray();
    return (Recipe)values[generator.nextInt(values.length)];
  }

  private Recipes() {}

}
