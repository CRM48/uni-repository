package cosc202.andie.menu;

import cosc202.andie.core.EditableImage;
import cosc202.andie.core.ImageAction;
import cosc202.andie.exceptions.ExceptionDialogue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Actions provided by the File menu.
 *
 * <p>The File menu is very common across applications, and there are several items that the user
 * will expect to find here. Opening and saving files is an obvious one, but also exiting the
 * program.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class FileActions {

  /** A list of actions for the File menu. */
  protected ArrayList<Action> actions;

  /** Create a set of File menu actions. */
  public FileActions() {
    actions = new ArrayList<Action>();
    actions.add(new FileOpenAction("Open", null, "Open a file", Integer.valueOf(KeyEvent.VK_O)));
    actions.add(new FileSaveAction("Save", null, "Save the file", Integer.valueOf(KeyEvent.VK_S)));
    actions.add(
        new FileSaveAsAction("Save As", null, "Save a copy", Integer.valueOf(KeyEvent.VK_A)));
    actions.add(
        new FileExportAction("Export", null, "Export the file", Integer.valueOf(KeyEvent.VK_E)));
    actions.add(
        new FileExitAction("Exit", null, "Exit the program", Integer.valueOf(KeyEvent.VK_Q)));
  }

  /**
   * Create a menu contianing the list of File actions.
   *
   * @return The File menu UI element.
   */
  public JMenu createMenu() {
    JMenu fileMenu = new JMenu("File");

    for (Action action : actions) fileMenu.add(new JMenuItem(action));

    return fileMenu;
  }

  /**
   * Action to open an image from file.
   *
   * @see EditableImage#open(String)
   */
  public class FileOpenAction extends ImageAction {

    /**
     * Create a new file-open action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the file-open action is triggered.
     *
     * <p>This method is called whenever the FileOpenAction is triggered. It prompts the user to
     * select a file and opens it as an image.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showOpenDialog(target);

      if (result == JFileChooser.APPROVE_OPTION) {
        try {
          String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
          target.getImage().open(imageFilepath);
        } catch (Exception ex) {
          if (ex instanceof NullPointerException) {
            ExceptionDialogue.exception(new Exception("Invalid image format"));
          }
        }
      }

      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * Action to save an image to its current file location.
   *
   * @see EditableImage#save()
   */
  public class FileSaveAction extends ImageAction {

    /**
     * Create a new file-save action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the file-save action is triggered.
     *
     * <p>This method is called whenever the FileSaveAction is triggered. It saves the image to its
     * original filepath.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      try {
        target.getImage().save();
      } catch (Exception ex) {
        ExceptionDialogue.exception(new Exception("You need to open an image before saving it"));
      }
    }
  }

  /**
   * Action to save an image to a new file location.
   *
   * @see EditableImage#saveAs(String)
   */
  public class FileSaveAsAction extends ImageAction {

    /**
     * Create a new file-save-as action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the file-save-as action is triggered.
     *
     * <p>This method is called whenever the FileSaveAsAction is triggered. It prompts the user to
     * select a file and saves the image to it.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showSaveDialog(target);

      if (result == JFileChooser.APPROVE_OPTION) {
        try {
          String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
          target.getImage().saveAs(imageFilepath);
        } catch (Exception ex) {
          ExceptionDialogue.exception(new Exception("You need to open an image before saving it"));
        }
      }
    }
  }

  /** Action to export the file. */
  public class FileExportAction extends ImageAction {
    /**
     * Creates a new file-export action
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the file-exports action is triggered.
     *
     * <p>This method is called whenever the FileExitAction is triggered. It quits the program.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Export an image");

      fileChooser.setAcceptAllFileFilterUsed(false);
      FileNameExtensionFilter bmpFilter = new FileNameExtensionFilter("Bitmap image", "bmp");
      FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG image", "png");
      FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPG image", "jpg");
      fileChooser.addChoosableFileFilter(bmpFilter);
      fileChooser.addChoosableFileFilter(pngFilter);
      fileChooser.addChoosableFileFilter(jpgFilter);

      int result = fileChooser.showSaveDialog(target);

      if (result == JFileChooser.APPROVE_OPTION) {
        try {
          String path = fileChooser.getSelectedFile().getCanonicalPath();
          String ext = ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0];

          target.getImage().export(path, ext);
        } catch (Exception exc) {
          if (exc instanceof IllegalArgumentException) {
            ExceptionDialogue.exception(exc);
          }
        }
      }
    }
  }

  /** Action to quit the ANDIE application. */
  public class FileExitAction extends AbstractAction {

    /**
     * Create a new file-exit action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon);
      putValue(SHORT_DESCRIPTION, desc);
      putValue(MNEMONIC_KEY, mnemonic);
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
    }

    /**
     * Callback for when the file-exit action is triggered.
     *
     * <p>This method is called whenever the FileExitAction is triggered. It quits the program.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  }
}
