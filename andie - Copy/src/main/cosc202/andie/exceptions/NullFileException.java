package cosc202.andie.exceptions;

/** Exception occurs when attempting to perform an action when no image is loaded */
public class NullFileException extends Exception {

  /**
   * Constructs a new NullFileException
   *
   * @param message Exception message
   */
  public NullFileException(String message) {
    super(message);
  }
}
