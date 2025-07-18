package cosc202.andie.core;

import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 * Abstract class representing actions the user might take in the interface.
 *
 * <p>This class uses Java's AbstractAction approach for Actions that can be applied to images. The
 * key difference from a generic AbstractAction is that an ImageAction contains a reference to an
 * image (via an ImagePanel interface element).
 *
 * <p>A distinction should be made between an ImageAction and an {@link ImageOperation}. An
 * ImageOperation is applied to an image in order to change it in some way. An ImageAction provides
 * support for the user doing something to an image in the user interface. ImageActions may apply an
 * ImageOperation, but do not have to do so.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * @author Steven Mills
 * @version 1.0
 */
public abstract class ImageAction extends AbstractAction {

  /**
   * The user interface element containing the image upon which actions should be performed. This is
   * common to all ImageActions.
   */
  protected static ImagePanel target;

  /**
   * Constructor for ImageActions.
   *
   * <p>To construct an ImageAction you provide the information needed to integrate it with the
   * interface. Note that the target is not specified per-action, but via the static member {@link
   * target} via {@link setTarget}.
   *
   * @param name The name of the action (ignored if null).
   * @param icon An icon to use to represent the action (ignored if null).
   * @param desc A brief description of the action (ignored if null).
   * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
   */
  protected ImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
    super(name, icon);
    if (desc != null) {
      putValue(SHORT_DESCRIPTION, desc);
    }
    if (mnemonic != null) {
      putValue(MNEMONIC_KEY, mnemonic);
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
    }
  }

  /**
   * Set the target for ImageActions.
   *
   * @param newTarget The ImagePanel to apply ImageActions to.
   */
  public static void setTarget(ImagePanel newTarget) {
    target = newTarget;
  }

  /**
   * Get the target for ImageActions.
   *
   * @return The ImagePanel to which ImageActions are currently being applied.
   */
  public static ImagePanel getTarget() {
    return target;
  }
}
