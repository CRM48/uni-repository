package cosc202.andie.exceptions;

/** Exception that occurs when attempting to perform an action with an invalid selection */
public class SelectionOutOfBoundsException extends Exception {

  private static final String MESSAGE = "Error: Selection box is out of bounds of the image.";

  /**
   * Constructs a new Selection out of bounds error.
   *
   * @param message Exception message
   */
  public SelectionOutOfBoundsException() {
    super(MESSAGE);
  }
}
