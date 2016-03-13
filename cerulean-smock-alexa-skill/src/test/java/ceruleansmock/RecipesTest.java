package ceruleansmock;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import ceruleansmock.model.Recipe;

public class RecipesTest {
  private static final String DEFAULT_RECIPE_SERIALIZED_JSON = "{\"metadata\":{\"title\":\"Spring Bucatini Pasta\",\"subtitle\":\"with Pea Tips, Asparagus and Mint\",\"overview\":\"This dish is full of delicate, fresh flavor, thanks to spring vegetables like asparagus and pea tips. And not only are both these vegetables delicious, but they're also incredibly easy to cook. Quickly sauteing the asparagus with garlic brings out its subtle earthiness and gorgeous color. And adding the pea tips when the dish is almost done preserves their flavor and wilts them just enough. In this dish, we're loving the simplicity of spring.\",\"servings\":2,\"calories\":680,\"cookTime\":35},\"ingredients\":[{\"name\":\"Bucatini Pasta\",\"quantity\":8.0,\"dimension\":\"Ounces\"},{\"name\":\"Garlic\",\"quantity\":3.0,\"dimension\":\"Cloves\"},{\"name\":\"Asparagus\",\"quantity\":1.0,\"dimension\":\"Bunch\"},{\"name\":\"Lemon\",\"quantity\":1.0,\"dimension\":\"Count\"},{\"name\":\"Pea Tips\",\"quantity\":1.0,\"dimension\":\"Ounce\"},{\"name\":\"Mint\",\"quantity\":1.0,\"dimension\":\"Bunch\"},{\"name\":\"Butter\",\"quantity\":2.0,\"dimension\":\"Tablespoons\"},{\"name\":\"Breadcrumbs\",\"quantity\":0.25,\"dimension\":\"Cup\"},{\"name\":\"Grated Pecorino Cheese\",\"quantity\":0.25,\"dimension\":\"Cup\"},{\"name\":\"Crushed Red Pepper Flakes\",\"quantity\":0.25,\"dimension\":\"Teaspoon\"}],\"steps\":[{\"index\":1,\"title\":\"Prepare the ingredients\",\"text\":\"Wash and dry the fresh produce. Heat a large pot of salted water to boiling on high. Snap off and discard the woody ends of the asparagus; thinly slice the asparagus on an angle. Peel and thinly slice the garlic. Using a peeler, remove the yellow rind of the lemon, avoiding the white pith; mince the rind to get 2 teaspoons of zest (or use a zester).  Quarter and deseed the lemon. Pick the mint leaves off the stems; discard the stems.\"},{\"index\":2,\"title\":\"Cook the pasta\",\"text\":\"Add the pasta to the pot of boiling water. Cook 10 to 12 minutes, or until just shy of al dente (still slightly firm to the bite). Reserving 1/2 cup of the pasta cooking water, thoroughly drain the pasta. Set aside.\"},{\"index\":3,\"title\":\"Toast the breadcrumbs\",\"text\":\"In a large pan, heat 2 teaspoons of olive oil on medium-high until hot. Add the breadcrumbs. Cook, stirring occasionally, 2 to 3 minutes, or until golden brown; season with salt and pepper. Transfer to a paper towel-lined plate. Wipe out the pan.\"},{\"index\":4,\"title\":\"Cook the asparagus\",\"text\":\"In the same pan used to toast the breadcrumbs, heat 2 teaspoons of olive oil on medium-high until hot. Add the asparagus and garlic; season with salt and pepper. Cook, stirring occasionally, 2 to 3 minutes, or until softened and fragrant.\"},{\"index\":5,\"title\":\"Finish the pasta\",\"text\":\"To the pan of asparagus, add the butter, lemon zest, pea tips, cooked pasta, half the pecorino cheese, half the reserved pasta cooking water, the juice of all 4 lemon wedges and as much of the red pepper flakes as you’d like, depending on how spicy you’d like the dish to be. Cook, stirring frequently, 2 to 3 minutes, or until thoroughly combined. Season with salt and pepper to taste. (If the sauce seems dry, slowly add the remaining pasta cooking water until you achieve your desired consistency.) Remove from heat.\"},{\"index\":6,\"title\":\"Plate your dish\",\"text\":\"Divide the finished pasta between 2 bowls. Top with the toasted breadcrumbs and remaining pecorino cheese. Garnish with the mint (tearing the leaves just before adding). Enjoy!\"}],\"id\":0}";
  
  @Test
  public void serializeToJSON() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      String jsonStr = mapper.writeValueAsString(Recipes.DEFAULT_RECIPE);
      mapper.readTree(jsonStr);
      Assert.assertTrue(StringUtils.isNotBlank(jsonStr));
      
      System.out.println(jsonStr);
    } catch(Exception e) {
      Assert.fail();
    }
  }
  
  @Test
  public void deserializeFromJSON() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      Recipe deserializedRecipe = mapper.readValue(DEFAULT_RECIPE_SERIALIZED_JSON, Recipe.class);
      Assert.assertEquals("Spring Bucatini Pasta".toLowerCase(), deserializedRecipe.getMetadata().getTitle().toLowerCase());
    } catch(Exception e) {
      System.out.println(e.getMessage());
      Assert.fail();
    }
  }
}
