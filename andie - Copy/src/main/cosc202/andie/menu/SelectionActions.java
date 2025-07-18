package cosc202.andie.menu;

import cosc202.andie.core.ImageAction;
import cosc202.andie.core.JFontChooser;
import cosc202.andie.exceptions.ExceptionDialogue;
import cosc202.andie.selection.AddText;
import cosc202.andie.selection.Crop;
import cosc202.andie.selection.DrawingOperations;
import cosc202.andie.selection.SelectionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Actions based on the users selection
 *
 * <p><a target="_blank" href="https://icons8.com/icon/98551/crop">Crop</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/99744/text">Text</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/NIa7Mm2og7yP/shapes">Shapes</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 */
public class SelectionActions {
  /** a list of actions for the Selection menu */
  protected ArrayList<Action> actions;

  /** A list of actions for the toolbar */
  protected ArrayList<Action> toolbarActions;

  /** Constructs a new instance. */
  public SelectionActions() {
    actions = new ArrayList<Action>();
    actions.add(new CropAction("Crop", null, "Crop the image", Integer.valueOf(KeyEvent.VK_X)));
    actions.add(
        new AddTextAction("Add text", null, "Add text to image", Integer.valueOf(KeyEvent.VK_T)));
    actions.add(
        new DrawingOperationsAction(
            "Drawing Operations", null, "Draw a shape", Integer.valueOf(KeyEvent.VK_L)));
    actions.add(
        new DeselectAction("Deselect", null, "Deselect", Integer.valueOf(KeyEvent.VK_BACK_SPACE)));
  }

  /**
   * Creates a menu.
   *
   * @return The j menu.
   */
  public JMenu createMenu() {
    JMenu selectionMenu = new JMenu("Selection");

    for (Action action : actions) selectionMenu.add(new JMenuItem(action));

    return selectionMenu;
  }

  /**
   * Adds actions to the menu.
   *
   * @return Arraylist of all the menu actions
   */
  public ArrayList<Action> addActions() {
    toolbarActions = new ArrayList<Action>();
    try {
      ImageIcon cropIcon = new ImageIcon("./assets/icons/crop.png");
      ImageIcon textIcon = new ImageIcon("./assets/icons/text.png");
      ImageIcon shapesIcon = new ImageIcon("./assets/icons/shapes.png");
      toolbarActions.add(new CropAction(null, cropIcon, "Crop the image", null));
      toolbarActions.add(new AddTextAction(null, textIcon, "Add text", null));
      toolbarActions.add(new DrawingOperationsAction(null, shapesIcon, "Add a shape", null));
    } catch (Exception ex) {
      System.out.println("No file exists");
    }
    return toolbarActions;
  }

  /** Action to add text to the image */
  public class AddTextAction extends ImageAction {
    /**
     * Constructs a new AddTextAction instance.
     *
     * @param name The name
     * @param icon The icon
     * @param desc The description
     * @param mnemonic The mnemonic
     */
    public AddTextAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback to perform add the text to the image
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      if (!SelectionListener.get().screen.hasSelection()) {
        ExceptionDialogue.exception(
            new Exception("Tried to run a selection operation without a current selection"));
        return;
      }
      Color colour = JColorChooser.showDialog(null, "Choose colour", new Color(0, 0, 0));
      if (colour == null) return;

      JFontChooser fchooser = new JFontChooser();
      Font font;
      int option = fchooser.showDialog(null);
      if (option == JFontChooser.OK_OPTION) {
        font = fchooser.getSelectedFont();
      } else {
        return;
      }

      if (font == null) return;

      String text = JOptionPane.showInputDialog("Enter Text");

      if (text == null) return;

      target
          .getImage()
          .apply(
              new AddText(
                  text, colour, font, SelectionListener.get().getSelection(), target.getScale()));
      target.repaint();
      target.getParent().revalidate();
    }
  }

  /** This class describes a deselect action. */
  public class DeselectAction extends ImageAction {
    /**
     * Constructs a new DeselectAction instance.
     *
     * @param name The name
     * @param icon The icon
     * @param desc The description
     * @param mnemonic The mnemonic
     */
    public DeselectAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for the action to be performed
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      if (target.hasSelection()) {
        target.deselect();
      }
    }
  }

  /**
   * DrawingOperationAction class to add drawing to the image
   *
   * @see DrawingOperations
   */
  public class DrawingOperationsAction extends ImageAction {

    /**
     * Create a new Drawing operation action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    DrawingOperationsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the Drawing operation action is triggered.
     *
     * <p>This method is called whenever the DrawingOperationAction is triggered. It prompts the
     * user to select a shape, fill and colour then applys it to the selection.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (!SelectionListener.get().screen.hasSelection()) {
        ExceptionDialogue.exception(
            new Exception("Tried to run a selection operation without a current selection"));
        return;
      }
      // Determine the shape - ask the user.
      int shape = 1;

      // Determine if filled - ask the user.
      boolean fill = false;

      // Determine the colour - ask the user.
      Color colour = null;

      // Pop-up dialog box to ask for the shape.
      String[] shapeChoices = {"Rectangle", "Oval", "Line"};
      int shapeOption =
          JOptionPane.showOptionDialog(
              null,
              "What shape?",
              "Shapes",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              shapeChoices,
              null);

      // Check the return value from the dialog box.
      if (shapeOption == JOptionPane.CLOSED_OPTION) {
        return;
      } else {
        shape = shapeOption + 1;
      }

      // fill only for rectangle and oval
      if (shape == 1 || shape == 2) {
        // Pop-up dialog box to ask whether fill or not
        String[] fillChoices = {"Fill", "Empty"};
        int fillOption =
            JOptionPane.showOptionDialog(
                null,
                "Fill or Empty?",
                "Fill",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                fillChoices,
                null);

        // Check the return value from the dialog box.
        if (fillOption == JOptionPane.CLOSED_OPTION) {
          return;
        } else {
          if (fillOption == 0) {
            fill = true;
          } else {
            fill = false;
          }
        }
      }

      // Pop-up dialog box to ask for the colour
      Color colourOption = JColorChooser.showDialog(null, "Choose colour", new Color(0, 0, 0));
      if (colourOption == null) return;

      colour = colourOption;

      // Create and apply the filter
      target
          .getImage()
          .apply(
              new DrawingOperations(
                  shape, fill, colour, SelectionListener.get().getSelection(), target.getScale()));
      target.deselect();
      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * Action to Crop the image
   *
   * @see Crop
   */
  public class CropAction extends ImageAction {

    /**
     * Create a new crop action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    CropAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the crop action is triggered.
     *
     * <p>This method is called whenever the CropAction is triggered. It crops the image
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (!SelectionListener.get().screen.hasSelection()) {
        ExceptionDialogue.exception(
            new Exception("Tried to run a selection operation without a current selection"));
        return;
      }
      target.getImage().apply(new Crop(SelectionListener.get().getSelection(), target.getScale()));
      target.deselect();
      target.repaint();
      target.getParent().revalidate();
    }
  }
}
