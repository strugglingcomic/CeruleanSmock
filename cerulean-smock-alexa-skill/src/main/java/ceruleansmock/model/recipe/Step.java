package ceruleansmock.model.recipe;

/**
 * simple Step POJO for a Recipe
 * @author codywang
 *
 */
public class Step {
  private final Integer index;
  private final String title;
  private final String text;
  
  public Step(final Integer index, final String title, final String text) {
    this.index = index;
    this.title = title;
    this.text = text;
  }

  public Integer getIndex() {
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
