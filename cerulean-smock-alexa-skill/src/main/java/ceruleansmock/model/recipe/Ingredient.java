package ceruleansmock.model.recipe;

import com.amazonaws.util.StringUtils;
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
    this.name = StringUtils.trim(name);
    this.quantity = quantity;
    this.dimension = StringUtils.trim(dimension);
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dimension == null) ? 0 : dimension.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    long temp;
    temp = Double.doubleToLongBits(quantity);
    result = prime * result + (int) (temp ^ (temp >>> 32));
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
    Ingredient other = (Ingredient) obj;
    if (dimension == null) {
      if (other.dimension != null) {
        return false;
      }
    } else if (!dimension.equals(other.dimension)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (Double.doubleToLongBits(quantity) != Double.doubleToLongBits(other.quantity)) {
      return false;
    }
    return true;
  }
}
