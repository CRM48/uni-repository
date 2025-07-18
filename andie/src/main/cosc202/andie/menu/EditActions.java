package cosc202.andie.menu;

import cosc202.andie.core.EditableImage;
import cosc202.andie.core.ImageAction;
import cosc202.andie.core.ImageOperation;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Actions provided by the Edit menu.
 *
 * <p>The Edit menu is very common across a wide range of applications. There are a lot of
 * operations that a user might expect to see here. In the sample code there are Undo and Redo
 * actions, but more may need to be added.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/85974/undo">Undo</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/98295/redo">Redo</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class EditActions {

  /** A list of actions for the Edit menu. */
  protected ArrayList<Action> actions;

  /** A list of actions for the toolbar */
  protected ArrayList<Action> toolbarActions;

  /** Create a set of Edit menu actions. */
  public EditActions() {
    actions = new ArrayList<Action>();
    actions.add(new UndoAction("Undo", null, "Undo", Integer.valueOf(KeyEvent.VK_Z)));
    actions.add(new RedoAction("Redo", null, "Redo", Integer.valueOf(KeyEvent.VK_Y)));
  }

  /**
   * Create a menu contianing the list of Edit actions.
   *
   * @return The edit menu UI element.
   */
  public JMenu createMenu() {
    JMenu editMenu = new JMenu("Edit");

    for (Action action : actions) {
      editMenu.add(new JMenuItem(action));
    }

    return editMenu;
  }

  /**
   * Adds all actions to the menu.
   *
   * @return Arraylist of all menu actions
   */
  public ArrayList<Action> addActions() {
    toolbarActions = new ArrayList<Action>();
    try {
      ImageIcon undoIcon = new ImageIcon("./assets/icons/undo.png");
      ImageIcon redoIcon = new ImageIcon("./assets/icons/redo.png");
      toolbarActions.add(new UndoAction(null, undoIcon, "Undo", null));
      toolbarActions.add(new RedoAction(null, redoIcon, "Redo", null));
    } catch (Exception ex) {
      System.out.println("No file exists");
    }
    return toolbarActions;
  }

  /**
   * Action to undo an {@link ImageOperation}.
   *
   * @see EditableImage#undo()
   */
  public class UndoAction extends ImageAction {

    /**
     * Create a new undo action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the undo action is triggered.
     *
     * <p>This method is called whenever the UndoAction is triggered. It undoes the most recently
     * applied operation.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      target.getImage().undo();
      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * Action to redo an {@link ImageOperation}.
   *
   * @see EditableImage#redo()
   */
  public class RedoAction extends ImageAction {

    /**
     * Create a new redo action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the redo action is triggered.
     *
     * <p>This method is called whenever the RedoAction is triggered. It redoes the most recently
     * undone operation.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      target.getImage().redo();
      target.repaint();
      target.getParent().revalidate();
    }
  }
}
