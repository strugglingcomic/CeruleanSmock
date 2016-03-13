package ceruleansmock.model.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * simple Step POJO for a Recipe
 * @author codywang
 *
 */
public class Step {
  private final int index;
  private final String title;
  private final String text;
  
  @JsonCreator
  public Step(
      @JsonProperty("index") final int index,
      @JsonProperty("title") final String title,
      @JsonProperty("text") final String text) {
    this.index = index;
    this.title = title;
    this.text = text;
  }

  public int getIndex() {
    return this.index;
  }

  public String getTitle() {
    return this.title;
  }

  public String getText() {
    return this.text;
  }
  
  @Override
  public String toString() {
    return this.title + ": " + this.text;
  }
}
