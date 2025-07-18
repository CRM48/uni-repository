package cosc202.andie.menu;

import cosc202.andie.core.ImageAction;
import cosc202.andie.transformation.ImageFlip;
import cosc202.andie.transformation.ImageResize;
import cosc202.andie.transformation.ImageRotation;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Actions provided by the Transformations menu.
 *
 * <p>The Transformation menu contains actions that transform the image in some way without actually
 * editing the integrity of the image. This includes, Image rotation, resize and flip.
 *
 * <p><a target="_blank" href="https://icons8.com/icon/83202/replace">Replace</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/98493/flip-horizontal">Flip Horizontal</a>
 * icon by <a target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/98546/resize">Resize</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * @author Curtis Mellsop
 * @version 1.0
 */
public class TransformationActions {

  /** A list of actions for the Transformation menu. */
  protected ArrayList<Action> actions;

  /** A list of actions for the toolbar */
  protected ArrayList<Action> toolbarActions;

  /** Create a set of Transformation menu actions. */
  public TransformationActions() {
    actions = new ArrayList<Action>();
    actions.add(
        new ImageRotationAction(
            "Image Rotation", null, "Rotate an Image", Integer.valueOf(KeyEvent.VK_B)));
    actions.add(
        new ImageResizeAction(
            "Image Resize", null, "Resize the image", Integer.valueOf(KeyEvent.VK_R)));
    actions.add(
        new ImageFlipAction("Image Flip", null, "Flip the Image", Integer.valueOf(KeyEvent.VK_F)));
  }

  /**
   * Create a menu contianing the list of Transformation actions.
   *
   * @return The transformation menu UI element.
   */
  public JMenu createMenu() {
    JMenu transformationMenu = new JMenu("Transformation");

    for (Action action : actions) {
      transformationMenu.add(new JMenuItem(action));
    }

    return transformationMenu;
  }

  /**
   * Adds actions to the menu.
   *
   * @return Arraylist of all the menu actions
   */
  public ArrayList<Action> addActions() {
    toolbarActions = new ArrayList<Action>();
    try {
      ImageIcon rotateIcon = new ImageIcon("./assets/icons/rotate.png");
      ImageIcon resizeIcon = new ImageIcon("./assets/icons/resize.png");
      ImageIcon flipIcon = new ImageIcon("./assets/icons/flip.png");
      toolbarActions.add(new ImageRotationAction(null, rotateIcon, "Rotate the Image", null));
      toolbarActions.add(new ImageResizeAction(null, resizeIcon, "Resize the image", null));
      toolbarActions.add(new ImageFlipAction(null, flipIcon, "Flip the Image", null));
    } catch (Exception ex) {
      System.out.println("No file exists");
    }
    return toolbarActions;
  }

  /**
   * Action to resize an image.
   *
   * @see ImageResize
   */
  public class ImageResizeAction extends ImageAction {

    /**
     * Create a new image resize action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ImageResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the image resize action is triggered.
     *
     * <p>This method is called whenever the ImageResizeAction is triggered.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      int size = 100;
      SpinnerNumberModel resizeModel = new SpinnerNumberModel(100, 1, 200, 5);
      JSpinner resizeSpinner = new JSpinner(resizeModel);
      int option =
          JOptionPane.showOptionDialog(
              null,
              resizeSpinner,
              "Enter resize percentage",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              null,
              null);
      // Check the return value from the dialog box.
      if (option == JOptionPane.CANCEL_OPTION) {
        return;
      } else if (option == JOptionPane.CLOSED_OPTION) {
        return;
      } else if (option == JOptionPane.OK_OPTION) {
        size = resizeModel.getNumber().intValue();
      }
      // Create and apply the resize
      target.getImage().apply(new ImageResize(size));
      target.repaint();
      target.getParent().revalidate();
      target.deselect();
    }
  }

  /**
   * ImageRotationAction class to allow ImageAction to operate under the Transformations tab
   *
   * @see ImageRotation
   */
  public class ImageRotationAction extends ImageAction {

    /**
     * Create a new image rotation action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ImageRotationAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the Image rotation action is triggered.
     *
     * <p>This method is called whenever the ImageaRotationAction is triggered. It prompts the user
     * to select a rotation type, then applys it.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {

      // Determine the type - ask the user.
      int type = 1;
      // Pop-up dialog box to ask for the type.
      String[] choices = {"90° Left", "90° Right", "180°"};
      int option =
          JOptionPane.showOptionDialog(
              null,
              "What type of rotation?",
              "Rotations",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              choices,
              null);

      // Check the return value from the dialog box.
      if (option == JOptionPane.CLOSED_OPTION) {
        return;
      } else {
        type = option + 1;
      }

      // Create and apply the filter
      target.getImage().apply(new ImageRotation(type));
      target.repaint();
      target.getParent().revalidate();
      target.deselect();
    }
  }

  /**
   * ImageFlipAction class to allow ImageAction to operate under the Transformations tab
   *
   * @see ImageFlip
   */
  public class ImageFlipAction extends ImageAction {

    /**
     * Create a new image flip action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ImageFlipAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the convert-to-grey action is triggered.
     *
     * <p>This method is called whenever the ImageFlipAction is triggered. It prompts the user to
     * select a flip type, then applies it.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {

      // Determine the type - ask the user.
      int type = 1;

      // Pop-up dialog box to ask for the type.
      String[] choices = {"Horizontal", "Vertical"};
      int option =
          JOptionPane.showOptionDialog(
              null,
              "Choose your flip method:",
              "Flip",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              choices,
              null);

      // Check the return value from the dialog box.
      if (option == JOptionPane.CLOSED_OPTION) {
        return;
      } else {
        type = option + 1;
      }

      // Create and apply the filter
      target.getImage().apply(new ImageFlip(type));
      target.repaint();
      target.getParent().revalidate();
      target.deselect();
    }
  }
}
