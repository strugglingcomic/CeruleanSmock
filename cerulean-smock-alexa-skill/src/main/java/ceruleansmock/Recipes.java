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
  public static final String TEST_RECIPE_SERIALIZE_JSON_1 = "{ \"ingredients\": [ { \"quantity\": 1, \"name\": \"French Green Lentils\", \"dimension\": \"Cup\" }, { \"quantity\": 4, \"name\": \"Garlic\", \"dimension\": \"Cloves\" }, { \"quantity\": 1, \"name\": \"Asparagus\", \"dimension\": \"Bunch\" }, { \"quantity\": 1, \"name\": \" Fennel Bulb\", \"dimension\": \"\" }, { \"quantity\": 1, \"name\": \"Lemon\", \"dimension\": \"\" }, { \"quantity\": 1, \"name\": \"Yellow Onion\", \"dimension\": \"\" }, { \"quantity\": 1, \"name\": \"Chives\", \"dimension\": \"Bunch\" }, { \"quantity\": 2, \"name\": \"Tomato Paste\", \"dimension\": \"Tablespoons\" }, { \"quantity\": \"0.25\", \"name\": \" Cup Mayonnaise\", \"dimension\": \"\" }, { \"quantity\": \"0.25\", \"name\": \" Cup Rice Flour\", \"dimension\": \"\" }, { \"quantity\": 2, \"name\": \"Lentil Minestra Spice Blend (Ground Coriander, Sweet Paprika and Garlic Powder)\", \"dimension\": \"Teaspoons\" } ], \"steps\": [ { \"index\": 1, \"text\": \"Wash and dry the fresh produce. Cut off and discard any fennel stems; small dice the bulb. Peel and small dice the onion. Peel and mince the garlic; using the flat side of your knife, smash until it resembles a paste (or use a zester). Quarter and deseed the lemon. Snap off and discard the tough, woody ends of the asparagus. Cut off and reserve the top 3 inches of each asparagus stalk; thinly slice the remaining stalks crosswise. Cut the chives into 0.5-inch pieces.\", \"title\": \"Prepare the ingredients:\" }, { \"index\": 2, \"text\": \"In a large pot, heat 2 teaspoons of olive oil on medium-high until hot. Add the fennel, onion, spice blend and 0.75 of the garlic paste; season with salt and pepper. Cook, stirring occasionally, 3 to 5 minutes, or until softened and fragrant. Add the tomato paste and cook, stirring frequently, 2 to 3 minutes, or until dark red and fragrant.\", \"title\": \"Start the minestra:\" }, { \"index\": 3, \"text\": \"Add the lentils and 4 cups of water to the pot; season with salt and pepper. Heat to boiling on high. Reduce the heat to medium-high; simmer, stirring occasionally, 24 to 26 minutes, or until thickened and the lentils are tender. Stir in the sliced asparagus and the juice of the remaining lemon wedges. Remove from heat; season with salt and pepper to taste. Let stand for 2 minutes.\", \"title\": \"Finish the minestra:\" }, { \"index\": 4, \"text\": \"While the lentils simmer, in a bowl, combine the mayonnaise, remaining garlic paste and the juice of 2 lemon wedges; season with salt and pepper to taste.\", \"title\": \"Make the aioli:\" }, { \"index\": 5, \"text\": \"While the lentils continue to simmer, in a large bowl, whisk together the rice flour and 5 tablespoons of water to create a thin batter; season with salt and pepper. In a medium pan (nonstick, if you have one), heat a thin layer of oil on medium-high until hot. Working in batches, coat the asparagus tops in the batter (letting any excess drip off). Once the oil is hot enough that a drop of batter sizzles immediately when added to the pan, carefully add the coated asparagus. Cook, flipping halfway through, 3 to 5 minutes, or until golden and crispy. Transfer to a paper towel-lined plate; immediately season with salt and pepper.\", \"title\": \"Make the asparagus tempura:\" }, { \"index\": 6, \"text\": \"Divide the finished minestra between 2 bowls. Top with the asparagus tempura. Garnish with the chives. Serve with the aioli on the side. Enjoy!\", \"title\": \"Plate your dish:\" } ], \"metadata\": { \"subtitle\": \"with Asparagus Tempura and Lemon Aioli\", \"title\": \"Lentil and Fennel Minestra \", \"overview\": \"A classic vegetable soup (and minestrone's culinary ancestor), minestra is delicious, rustic Italian fare. In this recipe, we're making the dish with French green lentils, stewed with anise-like fennel and warm spices. For an exciting, seasonal addition, we're tempura-frying springtime asparagus (coating the stalks in rice flour batter and lightly frying them on the stove) as a crunchy, flavorful garnish.\", \"calories\": 700, \"cookTime\": 45, \"servings\": 2 }}";
  public static final String TEST_RECIPE_SERIALIZE_JSON_2 = "{ \"metadata\": {  \"subtitle\": \"with Pea Shoot and Shaved Parmesan Salad\",   \"title\": \"English Pea and Goat Cheese Quiches\",   \"overview\": \"There's nothing more emblematic of spring than sweet, fresh peas. This gourmet recipe uses the crop in two ways: we're baking peas (alongside chard) into our light, creamy quiches, which we're pairing with a quick salad of pea shoots. This delectable, seasonal ingredient - the leaves and tendrils of the pea plant - needs only a splash of lemon vinaigrette to shine. But for extra flavor, we're also adding sharp, shaved Parmesan.\",   \"calories\": 700,   \"cookTime\": 45,   \"servings\": 2 },  \"steps\": [  {   \"index\": 1,    \"title\": \"Prepare the ingredients:\",    \"text\": \"Preheat the oven to 425 degrees F. Wash and dry the fresh produce. Heat a small pot of salted water to boiling on high. Shell the peas. Peel and mince the garlic. Separate the chard leaves and stems; roughly chop the leaves and thinly slice the stems, keeping them separate. Quarter and deseed the lemon. Peel and mince the shallot to get 2 tablespoons of minced shallot (you may have extra); place in a bowl with the juice of all 4 lemon wedges.\"  },   {   \"index\": 2,    \"title\": \"Blanch the peas: \",    \"text\": \"Add the peas to the pot of boiling water and cook 2 to 4 minutes, or until bright green and tender. Drain thoroughly and rinse under cold water to stop the cooking process; transfer to a bowl. Rinse and wipe out the pot.\"  },   {   \"index\": 3,    \"title\": \"Cook the chard:\",    \"text\": \"In the pot used to cook the peas, heat 2 teaspoons of olive oil on medium-high until hot. Add the garlic and chard stems; season with salt and pepper. Cook, stirring occasionally, 1 to 2 minutes, or until lightly browned and fragrant. Add the chard leaves; season with salt and pepper. Cook, stirring occasionally, 2 to 3 minutes, or until the chard leaves have wilted. Remove from heat.\"  },   {   \"index\": 4,    \"title\": \"Make the vinaigrette and filling:\",    \"text\": \"While the chard cooks, season the shallot-lemon juice mixture with salt and pepper to taste. Slowly whisk in 2 tablespoons of olive oil until well combined. Set aside. Crack the eggs into a large bowl; season with salt and pepper and beat until smooth. Add the sour cream and 2 tablespoons of water; whisk until smooth. Add the blanched peas and cooked chard; season with salt and pepper and whisk until thoroughly combined.\"  },   {   \"index\": 5,    \"title\": \"Assemble and bake the quiches: \",    \"text\": \"Place the pie crusts on a sheet pan, leaving them in their tins. Divide the filling between the crusts (you may have extra filling) and top with the goat cheese; season with salt and pepper. Bake 18 to 20 minutes, or until the crusts have browned and the filling is cooked through and slightly crispy on top. Remove from the oven and let stand for at least 5 minutes before serving.\"  },   {   \"index\": 6,    \"title\": \"Make the salad and serve your dish:\",    \"text\": \"Just before serving, in a large bowl, combine the pea shoots and Parmesan cheese; season with salt and pepper. Add enough of the vinaigrette to coat the salad (you may have extra); toss to combine and season with salt and pepper to taste. Serve the baked quiches with the salad on the side. Enjoy!\"  } ],  \"ingredients\": [  {   \"quantity\": 2,    \"dimension\": \"Count\",    \"name\": \" Farm Eggs\"  },   {   \"quantity\": 2,    \"dimension\": \"s\",    \"name\": \"Pie Crust\"  },   {   \"quantity\": 3,    \"dimension\": \"Cloves\",    \"name\": \"Garlic\"  },   {   \"quantity\": 1.5,    \"dimension\": \"Ounces\",    \"name\": \"Pea Shoots\"  },   {   \"quantity\": 1,    \"dimension\": \"Count\",    \"name\": \"Lemon\"  },   {   \"quantity\": 1,    \"dimension\": \"Count\",    \"name\": \"Shallot\"  },   {   \"quantity\": 0.5,    \"dimension\": \"Pound\",    \"name\": \"English Peas\"  },   {   \"quantity\": 0.5,    \"dimension\": \"Bunch\",    \"name\": \"Swiss Chard\"  },   {   \"quantity\": 0.5,    \"dimension\": \"Cup\",    \"name\": \"Sour Cream\"  },   {   \"quantity\": 0.25,    \"dimension\": \"Cup\",    \"name\": \"Crumbled Goat Cheese\"  },   {   \"quantity\": 0.25,    \"dimension\": \"Cup\",    \"name\": \"Shaved Parmesan Cheese\"  } ]}";
  public static final String TEST_LIST_RECIPES_JSON = "[" + TEST_RECIPE_SERIALIZE_JSON_1 + ", " + TEST_RECIPE_SERIALIZE_JSON_2 + "]";

  private static final Random generator = new Random();
  private static final Map<String, Recipe> recipes = new HashMap<String, Recipe>();

  static {
    recipes.put(DEFAULT_RECIPE.getMetadata().getTitle().toLowerCase(), DEFAULT_RECIPE);
    ObjectMapper mapper = new ObjectMapper();
    try {
      List<Recipe> recipeList = mapper.readValue(Recipes.TEST_LIST_RECIPES_JSON, new TypeReference<List<Recipe>>() {});
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
