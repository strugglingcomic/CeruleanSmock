package ceruleansmock.model.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
  private final double quantity;
  private final String dimension;
  
  @JsonCreator
  public Ingredient(
      @JsonProperty("name") final String name,
      @JsonProperty("quantity") final double quantity,
      @JsonProperty("dimension") final String dimension) {
    this.name = name;
    this.quantity = quantity;
    this.dimension = dimension;
  }

  public String getName() {
    return this.name;
  }

  public double getQuantity() {
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
