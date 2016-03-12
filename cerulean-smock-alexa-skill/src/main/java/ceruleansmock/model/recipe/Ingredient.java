package ceruleansmock.model.recipe;

//import javax.measure.Measure;

/**
 * simple Ingredient POJO for a Recipe
 * @author codywang
 *
 */
public class Ingredient {
  public static final String DEFAULT_DIMENSION = "Count";
  
  private final String name;

  // TODO: REPLACE WITH javax.measure.Measure, dummy!
  private final Double quantity;
  private final String dimension;
  
  public Ingredient(final String name, final Double quantity, final String dimension) {
    this.name = name;
    this.quantity = quantity;
    this.dimension = dimension;
  }

  public String getName() {
    return this.name;
  }

  public Double getQuantity() {
    return this.quantity;
  }

  public String getDimension() {
    return this.dimension;
  }
  
  @Override
  public String toString() {
    return this.quantity + " " + this.dimension + " " + this.name;
  }
}
