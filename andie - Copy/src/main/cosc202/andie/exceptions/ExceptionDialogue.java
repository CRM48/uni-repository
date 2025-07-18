package cosc202.andie.exceptions;

import javax.swing.JOptionPane;

/**
 * Utility class to show exceptions to the user
 *
 * @author Sam Merton
 */
public class ExceptionDialogue {

  /**
   * Pops up a dialog showing the exception to the user
   *
   * @param e error to show to the user
   */
  public static void exception(Exception e) {
    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
  }
}
