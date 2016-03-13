package ceruleansmock.model.recipe;

import com.amazonaws.util.StringUtils;
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
    this.title = StringUtils.trim(title);
    this.text = StringUtils.trim(text);
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + index;
    result = prime * result + ((text == null) ? 0 : text.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
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
    Step other = (Step) obj;
    if (index != other.index) {
      return false;
    }
    if (text == null) {
      if (other.text != null) {
        return false;
      }
    } else if (!text.equals(other.text)) {
      return false;
    }
    if (title == null) {
      if (other.title != null) {
        return false;
      }
    } else if (!title.equals(other.title)) {
      return false;
    }
    return true;
  }
}
