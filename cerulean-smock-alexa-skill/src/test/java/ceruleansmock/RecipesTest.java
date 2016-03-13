package ceruleansmock;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ceruleansmock.model.Recipe;

public class RecipesTest {
  private static final String TEST_DEFAULT_RECIPE_SERIALIZED_JSON = "{\"metadata\":{\"title\":\"Spring Bucatini Pasta\",\"subtitle\":\"with Pea Tips, Asparagus and Mint\",\"overview\":\"This dish is full of delicate, fresh flavor, thanks to spring vegetables like asparagus and pea tips. And not only are both these vegetables delicious, but they're also incredibly easy to cook. Quickly sauteing the asparagus with garlic brings out its subtle earthiness and gorgeous color. And adding the pea tips when the dish is almost done preserves their flavor and wilts them just enough. In this dish, we're loving the simplicity of spring.\",\"servings\":2,\"calories\":680,\"cookTime\":35},\"ingredients\":[{\"name\":\"Bucatini Pasta\",\"quantity\":8.0,\"dimension\":\"Ounces\"},{\"name\":\"Garlic\",\"quantity\":3.0,\"dimension\":\"Cloves\"},{\"name\":\"Asparagus\",\"quantity\":1.0,\"dimension\":\"Bunch\"},{\"name\":\"Lemon\",\"quantity\":1.0,\"dimension\":\"Count\"},{\"name\":\"Pea Tips\",\"quantity\":1.0,\"dimension\":\"Ounce\"},{\"name\":\"Mint\",\"quantity\":1.0,\"dimension\":\"Bunch\"},{\"name\":\"Butter\",\"quantity\":2.0,\"dimension\":\"Tablespoons\"},{\"name\":\"Breadcrumbs\",\"quantity\":0.25,\"dimension\":\"Cup\"},{\"name\":\"Grated Pecorino Cheese\",\"quantity\":0.25,\"dimension\":\"Cup\"},{\"name\":\"Crushed Red Pepper Flakes\",\"quantity\":0.25,\"dimension\":\"Teaspoon\"}],\"steps\":[{\"index\":1,\"title\":\"Prepare the ingredients\",\"text\":\"Wash and dry the fresh produce. Heat a large pot of salted water to boiling on high. Snap off and discard the woody ends of the asparagus; thinly slice the asparagus on an angle. Peel and thinly slice the garlic. Using a peeler, remove the yellow rind of the lemon, avoiding the white pith; mince the rind to get 2 teaspoons of zest (or use a zester).  Quarter and deseed the lemon. Pick the mint leaves off the stems; discard the stems.\"},{\"index\":2,\"title\":\"Cook the pasta\",\"text\":\"Add the pasta to the pot of boiling water. Cook 10 to 12 minutes, or until just shy of al dente (still slightly firm to the bite). Reserving 1/2 cup of the pasta cooking water, thoroughly drain the pasta. Set aside.\"},{\"index\":3,\"title\":\"Toast the breadcrumbs\",\"text\":\"In a large pan, heat 2 teaspoons of olive oil on medium-high until hot. Add the breadcrumbs. Cook, stirring occasionally, 2 to 3 minutes, or until golden brown; season with salt and pepper. Transfer to a paper towel-lined plate. Wipe out the pan.\"},{\"index\":4,\"title\":\"Cook the asparagus\",\"text\":\"In the same pan used to toast the breadcrumbs, heat 2 teaspoons of olive oil on medium-high until hot. Add the asparagus and garlic; season with salt and pepper. Cook, stirring occasionally, 2 to 3 minutes, or until softened and fragrant.\"},{\"index\":5,\"title\":\"Finish the pasta\",\"text\":\"To the pan of asparagus, add the butter, lemon zest, pea tips, cooked pasta, half the pecorino cheese, half the reserved pasta cooking water, the juice of all 4 lemon wedges and as much of the red pepper flakes as you’d like, depending on how spicy you’d like the dish to be. Cook, stirring frequently, 2 to 3 minutes, or until thoroughly combined. Season with salt and pepper to taste. (If the sauce seems dry, slowly add the remaining pasta cooking water until you achieve your desired consistency.) Remove from heat.\"},{\"index\":6,\"title\":\"Plate your dish\",\"text\":\"Divide the finished pasta between 2 bowls. Top with the toasted breadcrumbs and remaining pecorino cheese. Garnish with the mint (tearing the leaves just before adding). Enjoy!\"}],\"id\":0}";
  //private static final String TEST_RECIPE_SERIALIZE_JSON_2 = "{ \"ingredients\": [ { \"quantity\": 1, \"name\": \"French Green Lentils\", \"dimension\": \"Cup\" }, { \"quantity\": 4, \"name\": \"Garlic\", \"dimension\": \"Cloves\" }, { \"quantity\": 1, \"name\": \"Asparagus\", \"dimension\": \"Bunch\" }, { \"quantity\": 1, \"name\": \" Fennel Bulb\", \"dimension\": \"\" }, { \"quantity\": 1, \"name\": \"Lemon\", \"dimension\": \"\" }, { \"quantity\": 1, \"name\": \"Yellow Onion\", \"dimension\": \"\" }, { \"quantity\": 1, \"name\": \"Chives\", \"dimension\": \"Bunch\" }, { \"quantity\": 2, \"name\": \"Tomato Paste\", \"dimension\": \"Tablespoons\" }, { \"quantity\": \"0.25\", \"name\": \" Cup Mayonnaise\", \"dimension\": \"\" }, { \"quantity\": \"0.25\", \"name\": \" Cup Rice Flour\", \"dimension\": \"\" }, { \"quantity\": 2, \"name\": \"Lentil Minestra Spice Blend (Ground Coriander, Sweet Paprika & Garlic Powder)\", \"dimension\": \"Teaspoons\" } ], \"steps\": [ { \"index\": 1, \"text\": \"Wash and dry the fresh produce. Cut off and discard any fennel stems; small dice the bulb. Peel and small dice the onion. Peel and mince the garlic; using the flat side of your knife, smash until it resembles a paste (or use a zester). Quarter and deseed the lemon. Snap off and discard the tough, woody ends of the asparagus. Cut off and reserve the top 3 inches of each asparagus stalk; thinly slice the remaining stalks crosswise. Cut the chives into 0.5-inch pieces.\", \"title\": \"Prepare the ingredients:\" }, { \"index\": 2, \"text\": \"In a large pot, heat 2 teaspoons of olive oil on medium-high until hot. Add the fennel, onion, spice blend and 0.75 of the garlic paste; season with salt and pepper. Cook, stirring occasionally, 3 to 5 minutes, or until softened and fragrant. Add the tomato paste and cook, stirring frequently, 2 to 3 minutes, or until dark red and fragrant.\", \"title\": \"Start the minestra:\" }, { \"index\": 3, \"text\": \"Add the lentils and 4 cups of water to the pot; season with salt and pepper. Heat to boiling on high. Reduce the heat to medium-high; simmer, stirring occasionally, 24 to 26 minutes, or until thickened and the lentils are tender. Stir in the sliced asparagus and the juice of the remaining lemon wedges. Remove from heat; season with salt and pepper to taste. Let stand for 2 minutes.\", \"title\": \"Finish the minestra:\" }, { \"index\": 4, \"text\": \"While the lentils simmer, in a bowl, combine the mayonnaise, remaining garlic paste and the juice of 2 lemon wedges; season with salt and pepper to taste.\", \"title\": \"Make the aioli:\" }, { \"index\": 5, \"text\": \"While the lentils continue to simmer, in a large bowl, whisk together the rice flour and 5 tablespoons of water to create a thin batter; season with salt and pepper. In a medium pan (nonstick, if you have one), heat a thin layer of oil on medium-high until hot. Working in batches, coat the asparagus tops in the batter (letting any excess drip off). Once the oil is hot enough that a drop of batter sizzles immediately when added to the pan, carefully add the coated asparagus. Cook, flipping halfway through, 3 to 5 minutes, or until golden and crispy. Transfer to a paper towel-lined plate; immediately season with salt and pepper.\", \"title\": \"Make the asparagus tempura:\" }, { \"index\": 6, \"text\": \"Divide the finished minestra between 2 bowls. Top with the asparagus tempura. Garnish with the chives. Serve with the aioli on the side. Enjoy!\", \"title\": \"Plate your dish:\" } ], \"metadata\": { \"subtitle\": \"with Asparagus Tempura & Lemon Aioli\", \"title\": \"Lentil & Fennel Minestra \", \"overview\": \"A classic vegetable soup (and minestrone's culinary ancestor), minestra is delicious, rustic Italian fare. In this recipe, we're making the dish with French green lentils, stewed with anise-like fennel and warm spices. For an exciting, seasonal addition, we're tempura-frying springtime asparagus (coating the stalks in rice flour batter and lightly frying them on the stove) as a crunchy, flavorful garnish.\", \"calories\": 700, \"cookTime\": 45, \"servings\": 2 }}";
  
  private static final String TEST_LIST_RECIPES_JSON = "[" + TEST_DEFAULT_RECIPE_SERIALIZED_JSON + ", " + TEST_DEFAULT_RECIPE_SERIALIZED_JSON + "]";
  @Test
  public void serializeToJSON() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      String jsonStr = mapper.writeValueAsString(Recipes.DEFAULT_RECIPE);
      mapper.readTree(jsonStr);
      Assert.assertTrue(StringUtils.isNotBlank(jsonStr));
    } catch(Exception e) {
      Assert.fail();
    }
  }
  
  @Test
  public void deserializeFromJSON() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      Recipe deserializedRecipe = mapper.readValue(TEST_DEFAULT_RECIPE_SERIALIZED_JSON, Recipe.class);
      Assert.assertEquals("Spring Bucatini Pasta".toLowerCase(), deserializedRecipe.getMetadata().getTitle().toLowerCase());
      Assert.assertEquals(Recipes.DEFAULT_RECIPE, deserializedRecipe);
    } catch(Exception e) {
      Assert.fail(e.getMessage());
    }
  }
  
  @Test
  public void deserializeFromJSONList() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      List<Recipe> recipes = mapper.readValue(TEST_LIST_RECIPES_JSON, new TypeReference<List<Recipe>>() {});
  
      Assert.assertEquals("Spring Bucatini Pasta".toLowerCase(), recipes.get(0).getMetadata().getTitle().toLowerCase());
      Assert.assertEquals(Recipes.DEFAULT_RECIPE, recipes.get(0));
      Assert.assertEquals("Spring Bucatini Pasta".toLowerCase(), recipes.get(1).getMetadata().getTitle().toLowerCase());
      Assert.assertEquals(Recipes.DEFAULT_RECIPE, recipes.get(1));
    } catch(Exception e) {
      Assert.fail(e.getMessage());
    }
  }
}
