package cosc202.andie.menu;

import cosc202.andie.core.ImageAction;
import cosc202.andie.exceptions.ExceptionDialogue;
import cosc202.andie.exceptions.NullFileException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Actions provided by the View menu.
 *
 * <p>The View menu contains actions that affect how the image is displayed in the application.
 * These actions do not affect the contents of the image itself, just the way it is displayed.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/88106/zoom-in">Zoom In</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/96897/zoom-out">Zoom Out</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class ViewActions {

  /** A list of actions for the View menu. */
  protected ArrayList<Action> actions;

  /** A list of actions for the toolbar */
  protected ArrayList<Action> toolbarActions;

  /** Create a set of View menu actions. */
  public ViewActions() {
    actions = new ArrayList<Action>();
    actions.add(new ZoomInAction("Zoom In", null, "Zoom In", Integer.valueOf(KeyEvent.VK_EQUALS)));
    actions.add(
        new ZoomOutAction("Zoom Out", null, "Zoom Out", Integer.valueOf(KeyEvent.VK_MINUS)));
    actions.add(new ZoomFullAction("Zoom Full", null, "Zoom Full", Integer.valueOf(KeyEvent.VK_1)));
  }

  /**
   * Create a menu containing the list of View actions.
   *
   * @return The view menu UI element.
   */
  public JMenu createMenu() {
    JMenu viewMenu = new JMenu("View");

    for (Action action : actions) {
      viewMenu.add(new JMenuItem(action));
    }

    return viewMenu;
  }

  /**
   * Adds actions to the menu.
   *
   * @return Arraylist of all the menu actions
   */
  public ArrayList<Action> addActions() {
    toolbarActions = new ArrayList<Action>();
    try {
      ImageIcon zoomInIcon = new ImageIcon("./assets/icons/zoomin.png");
      ImageIcon zoomOutIcon = new ImageIcon("./assets/icons/zoomout.png");
      toolbarActions.add(new ZoomInAction(null, zoomInIcon, "Zoom In", null));
      toolbarActions.add(new ZoomOutAction(null, zoomOutIcon, "Zoom Out", null));
    } catch (Exception ex) {
      System.out.println("No file exists");
    }
    return toolbarActions;
  }

  /**
   * Action to zoom in on an image.
   *
   * <p>Note that this action only affects the way the image is displayed, not its actual contents.
   */
  public class ZoomInAction extends ImageAction {

    /**
     * Create a new zoom-in action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the zoom-in action is triggered.
     *
     * <p>This method is called whenever the ZoomInAction is triggered. It increases the zoom level
     * by 10%, to a maximum of 200%.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (!ImageAction.getTarget().getImage().hasImage()) {
        ExceptionDialogue.exception(
            new NullFileException("Tried to apply an operation without selecting an image"));
        return;
      }
      target.setZoom(target.getZoom() + 10);
      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * Action to zoom out of an image.
   *
   * <p>Note that this action only affects the way the image is displayed, not its actual contents.
   */
  public class ZoomOutAction extends ImageAction {

    /**
     * Create a new zoom-out action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the zoom-iout action is triggered.
     *
     * <p>This method is called whenever the ZoomOutAction is triggered. It decreases the zoom level
     * by 10%, to a minimum of 50%.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (!ImageAction.getTarget().getImage().hasImage()) {
        ExceptionDialogue.exception(
            new NullFileException("Tried to apply an operation without selecting an image"));
        return;
      }
      target.setZoom(target.getZoom() - 10);
      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * Action to reset the zoom level to actual size.
   *
   * <p>Note that this action only affects the way the image is displayed, not its actual contents.
   */
  public class ZoomFullAction extends ImageAction {

    /**
     * Create a new zoom-full action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the zoom-full action is triggered.
     *
     * <p>This method is called whenever the ZoomFullAction is triggered. It resets the Zoom level
     * to 100%.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (!ImageAction.getTarget().getImage().hasImage()) {
        ExceptionDialogue.exception(
            new NullFileException("Tried to apply an operation without selecting an image"));
        return;
      }
      target.setZoom(100);
      target.revalidate();
      target.getParent().revalidate();
    }
  }
}
