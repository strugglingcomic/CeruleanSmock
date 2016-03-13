package ceruleansmock.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ceruleansmock.model.recipe.Ingredient;
import ceruleansmock.model.recipe.Metadata;
import ceruleansmock.model.recipe.Step;

/**
 * simple Recipe POJO
 * @author codywang
 *
 */
public class Recipe {
  // TODO: replace with something better... UUID? Dynamo keys?
  private static long NEXT_AVAILABLE_ID = 0;
  private final long id;
  
  private final Metadata metadata;
  private final List<Ingredient> ingredients;
  private final List<Step> steps;
  
  // TODO: consider using factory method
  @JsonCreator
  public Recipe(
      @JsonProperty("metadata") final Metadata metadata,
      @JsonProperty("ingredients") final List<Ingredient> ingredients,
      @JsonProperty("steps") final List<Step> steps) {
    this.id = NEXT_AVAILABLE_ID++;
    this.metadata = metadata;
    this.ingredients = ingredients;
    this.steps = steps;
  }
  
  /**
   * Default recipe uses: https://www.blueapron.com/recipes/spring-bucatini-pasta-with-baby-artichokes-asparagus-mint
   */
  public Recipe() {
    String title = "Spring Bucatini Pasta";
    String subtitle = "with Pea Tips, Asparagus and Mint";
    String overview = "This dish is full of delicate, fresh flavor, thanks to spring vegetables like asparagus and pea tips. And not only are both these vegetables delicious, but they're also incredibly easy to cook. Quickly sauteing the asparagus with garlic brings out its subtle earthiness and gorgeous color. And adding the pea tips when the dish is almost done preserves their flavor and wilts them just enough. In this dish, we're loving the simplicity of spring.";
    int servings = 2;
    int calories = 680;
    int cookTime = 35;
    Metadata metadata = new Metadata(title, subtitle, overview, servings, calories, cookTime);
    
    Ingredient pasta = new Ingredient("Bucatini Pasta", 8.0, "Ounces");
    Ingredient garlic = new Ingredient("Garlic", 3.0, "Cloves");
    Ingredient asparagus = new Ingredient("Asparagus", 1.0, "Bunch");
    Ingredient lemon = new Ingredient("Lemon", 1.0, Ingredient.DEFAULT_DIMENSION);
    Ingredient peaTips = new Ingredient("Pea Tips", 1.0, "Ounce");
    Ingredient mint = new Ingredient("Mint", 1.0, "Bunch");
    Ingredient butter = new Ingredient("Butter", 2.0, "Tablespoons");
    Ingredient breadcrumbs = new Ingredient("Breadcrumbs", 0.25, "Cup");
    Ingredient gratedPecorinoCheese = new Ingredient("Grated Pecorino Cheese", 0.25, "Cup");
    Ingredient crushedRedPepperFlakes = new Ingredient("Crushed Red Pepper Flakes", 0.25, "Teaspoon");
    
    List<Ingredient> ingredients = new ArrayList<Ingredient>();
    ingredients.add(pasta);
    ingredients.add(garlic);
    ingredients.add(asparagus);
    ingredients.add(lemon);
    ingredients.add(peaTips);
    ingredients.add(mint);
    ingredients.add(butter);
    ingredients.add(breadcrumbs);
    ingredients.add(gratedPecorinoCheese);
    ingredients.add(crushedRedPepperFlakes);
    
    List<Step> steps = new ArrayList<Step>();
    Step step1 = new Step(1, "Prepare the ingredients", "Wash and dry the fresh produce. Heat a large pot of salted water to boiling on high. Snap off and discard the woody ends of the asparagus; thinly slice the asparagus on an angle. Peel and thinly slice the garlic. Using a peeler, remove the yellow rind of the lemon, avoiding the white pith; mince the rind to get 2 teaspoons of zest (or use a zester).  Quarter and deseed the lemon. Pick the mint leaves off the stems; discard the stems.");
    Step step2 = new Step(2, "Cook the pasta", "Add the pasta to the pot of boiling water. Cook 10 to 12 minutes, or until just shy of al dente (still slightly firm to the bite). Reserving 1/2 cup of the pasta cooking water, thoroughly drain the pasta. Set aside.");
    Step step3 = new Step(3, "Toast the breadcrumbs", "In a large pan, heat 2 teaspoons of olive oil on medium-high until hot. Add the breadcrumbs. Cook, stirring occasionally, 2 to 3 minutes, or until golden brown; season with salt and pepper. Transfer to a paper towel-lined plate. Wipe out the pan.");
    Step step4 = new Step(4, "Cook the asparagus", "In the same pan used to toast the breadcrumbs, heat 2 teaspoons of olive oil on medium-high until hot. Add the asparagus and garlic; season with salt and pepper. Cook, stirring occasionally, 2 to 3 minutes, or until softened and fragrant.");
    Step step5 = new Step(5, "Finish the pasta", "To the pan of asparagus, add the butter, lemon zest, pea tips, cooked pasta, half the pecorino cheese, half the reserved pasta cooking water, the juice of all 4 lemon wedges and as much of the red pepper flakes as you’d like, depending on how spicy you’d like the dish to be. Cook, stirring frequently, 2 to 3 minutes, or until thoroughly combined. Season with salt and pepper to taste. (If the sauce seems dry, slowly add the remaining pasta cooking water until you achieve your desired consistency.) Remove from heat.");
    Step step6 = new Step(6, "Plate your dish", "Divide the finished pasta between 2 bowls. Top with the toasted breadcrumbs and remaining pecorino cheese. Garnish with the mint (tearing the leaves just before adding). Enjoy!");
    steps.add(step1);
    steps.add(step2);
    steps.add(step3);
    steps.add(step4);
    steps.add(step5);
    steps.add(step6);
    
    this.id = NEXT_AVAILABLE_ID++;
    this.metadata = metadata;
    this.ingredients = ingredients;
    this.steps = steps;
  }

  public long getId() {
    return this.id;
  }
  
  public Metadata getMetadata() {
    return this.metadata;
  }

  public List<Ingredient> getIngredients() {
    return this.ingredients;
  }

  public List<Step> getSteps() {
    return this.steps;
  }
  
  public String toRecipeFullTitleString() {
    return this.getMetadata().getTitle() + " " + this.getMetadata().getSubtitle();
  }
  
  public String toRecipeTitleAndOverviewString() {
    return this.toRecipeFullTitleString() + ". " + this.getMetadata().getOverview();
  }
  
  public String toIngredientsString() {
    StringBuilder sb = new StringBuilder();
    for(Ingredient ing : this.ingredients) {
      sb.append(ing + ", ");
    }
    return sb.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
    result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
    result = prime * result + ((steps == null) ? 0 : steps.hashCode());
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
    Recipe other = (Recipe) obj;
    if (ingredients == null) {
      if (other.ingredients != null) {
        return false;
      }
    } else if (!ingredients.equals(other.ingredients)) {
      return false;
    }
    if (metadata == null) {
      if (other.metadata != null) {
        return false;
      }
    } else if (!metadata.equals(other.metadata)) {
      return false;
    }
    if (steps == null) {
      if (other.steps != null) {
        return false;
      }
    } else if (!steps.equals(other.steps)) {
      return false;
    }
    return true;
  }
}
