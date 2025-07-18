package cosc202.andie.menu;

import cosc202.andie.core.ImageAction;
import cosc202.andie.core.ImageOperation;
import cosc202.andie.exceptions.ExceptionDialogue;
import cosc202.andie.macro.Macro;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Actions provided by the macro menu
 *
 * @author Callum Cooper
 * @version 1.0
 */
public class MacroActions {
  /** a list of actions for the Macro menu */
  protected ArrayList<Action> actions;

  /** Create a set of macro actions */
  public MacroActions() {
    actions = new ArrayList<Action>();
    actions.add(
        new MacroRecordAction(
            "Stop/Record macro",
            null,
            "Record/Stop Recording a macro",
            Integer.valueOf(KeyEvent.VK_H)));
    actions.add(
        new MacroPlayAction("Play macro", null, "Play a macro", Integer.valueOf(KeyEvent.VK_D)));
  }

  /**
   * Creates a menu.
   *
   * @return The j menu.
   */
  public JMenu createMenu() {
    JMenu macroMenu = new JMenu("Macros");

    for (Action action : actions) macroMenu.add(new JMenuItem(action));

    return macroMenu;
  }

  /** Action to record a new macro */
  public class MacroRecordAction extends ImageAction {
    /** Currently in process macro recording */
    private static Macro macro;

    /**
     * Create a new Macro record action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    MacroRecordAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the Macro record action is triggered.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      if (macro != null) {
        if (macro.isRecording()) {
          macro.finishRecording(getTarget().getImage().getStack());
          save();
          macro = null;
          return;
        }
      }
      macro = new Macro();
      macro.startRecording(getTarget().getImage().getStack());
    }

    /** Open a menu to save the macro */
    private static void save() {
      JFileChooser fileChooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Macro file", "macro");
      fileChooser.setFileFilter(filter);
      int result = fileChooser.showSaveDialog(target);

      if (result == JFileChooser.APPROVE_OPTION) {
        try {
          String path = fileChooser.getSelectedFile().getCanonicalPath();
          macro.saveMacro(path);
        } catch (Exception e) {
          ExceptionDialogue.exception(e);
        }
      }
    }
  }

  /** Play a macro */
  public class MacroPlayAction extends ImageAction {
    /**
     * Create a new Macro play action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    MacroPlayAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the Macro record action is triggered.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Macro file", "macro");
      fileChooser.setFileFilter(filter);
      int result = fileChooser.showSaveDialog(target);

      if (result == JFileChooser.APPROVE_OPTION) {
        try {
          String path = fileChooser.getSelectedFile().getCanonicalPath();
          Macro macro = new Macro(path);

          // Stack is incorrect for replaying a macro - treat it like an arraylist
          Stack<ImageOperation> actions = macro.getActions();

          for (int i = 0; i < actions.size(); i++)
            getTarget().getImage().apply(actions.elementAt(i));

          target.repaint();
          target.getParent().revalidate();
        } catch (Exception ex) {
          ExceptionDialogue.exception(ex);
        }
      }
    }
  }
}
