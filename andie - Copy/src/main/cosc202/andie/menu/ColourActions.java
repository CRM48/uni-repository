package cosc202.andie.menu;

import cosc202.andie.colour.BrightnessAndContrast;
import cosc202.andie.colour.ConvertToGrey;
import cosc202.andie.colour.Posterisation;
import cosc202.andie.core.ImageAction;
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
 * Actions provided by the Colour menu.
 *
 * <p>The Colour menu contains actions that affect the colour of each pixel directly without
 * reference to the rest of the image. This includes conversion to greyscale in the sample code, but
 * more operations will need to be added.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/78759/brightness">Brightness</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/78791/grayscale">Grayscale</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/30697/poster">Poster</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {

  /** A list of actions for the Colour menu. */
  protected ArrayList<Action> actions;

  /** A list of actions for the toolbar */
  protected ArrayList<Action> toolbarActions;

  /** Create a set of Colour menu actions. */
  public ColourActions() {
    actions = new ArrayList<Action>();
    actions.add(
        new ConvertToGreyAction(
            "Greyscale", null, "Convert to greyscale", Integer.valueOf(KeyEvent.VK_G)));
    actions.add(
        new BrightnessAndContrastAction(
            "Brightness/Contrast",
            null,
            "Change the Brightness and/or Contrast",
            Integer.valueOf(KeyEvent.VK_N)));
    actions.add(
        new PosterisationAction(
            "Posterisation", null, "Posterise the image", Integer.valueOf(KeyEvent.VK_C)));
  }

  /**
   * Create a menu contianing the list of Colour actions.
   *
   * @return The colour menu UI element.
   */
  public JMenu createMenu() {
    JMenu colourMenu = new JMenu("Colour");

    for (Action action : actions) colourMenu.add(new JMenuItem(action));

    return colourMenu;
  }

  /**
   * Adds actions to the menu.
   *
   * @return Arraylist of all the menu actions
   */
  public ArrayList<Action> addActions() {
    toolbarActions = new ArrayList<Action>();
    try {
      ImageIcon greyscaleIcon = new ImageIcon("./assets/icons/greyscale.png");
      ImageIcon brightnessIcon = new ImageIcon("./assets/icons/brightness.png");
      ImageIcon posterizationIcon = new ImageIcon("./assets/icons/posterization.png");
      toolbarActions.add(
          new ConvertToGreyAction(null, greyscaleIcon, "Convert to greyscale", null));
      toolbarActions.add(
          new BrightnessAndContrastAction(
              null, brightnessIcon, "Change the Brightness and/or Contrast", null));
      toolbarActions.add(
          new PosterisationAction(null, posterizationIcon, "Posterise the image", null));
    } catch (Exception ex) {
      System.out.println("No file exists");
    }
    return toolbarActions;
  }

  /**
   * Action to convert an image to greyscale.
   *
   * @see ConvertToGrey
   */
  public class ConvertToGreyAction extends ImageAction {

    /**
     * Create a new convert-to-grey action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the convert-to-grey action is triggered.
     *
     * <p>This method is called whenever the ConvertToGreyAction is triggered. It changes the image
     * to greyscale.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      target.getImage().apply(new ConvertToGrey());
      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * Action to change the brightness and/or the contrast of the image
   *
   * @see BrightnessAndContrast
   */
  public class BrightnessAndContrastAction extends ImageAction {

    /**
     * Create a new brightness-and-contrast action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    BrightnessAndContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the convert-to-grey action is triggered.
     *
     * <p>This method is called whenever the BrightnessAndContrast class is triggered. It changes
     * the images brightness and the images contrast
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {

      int brightness = 0;
      int contrast = 0;

      SpinnerNumberModel brightnessModel = new SpinnerNumberModel(0, -100, 100, 5);
      SpinnerNumberModel contrastModel = new SpinnerNumberModel(0, -100, 100, 5);
      JSpinner brightnessSpinner = new JSpinner(brightnessModel);
      JSpinner contrastSpinner = new JSpinner(contrastModel);

      Object[] options = {"Brightness (%):", brightnessSpinner, "Contrast (%):", contrastSpinner};

      int option =
          JOptionPane.showOptionDialog(
              null,
              options,
              "Enter Brightness/Contrast",
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
        brightness = brightnessModel.getNumber().intValue();
        contrast = contrastModel.getNumber().intValue();
      }

      target.getImage().apply(new BrightnessAndContrast(brightness, contrast));
      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * Action to posterise an image.
   *
   * @see Posterisation
   */
  public class PosterisationAction extends ImageAction {

    /**
     * Create a new Posterisation action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    PosterisationAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    public void actionPerformed(ActionEvent e) {

      // Number of colour bands, default to 2
      int bands = 2;

      // Pop-up dialog box to ask for the bands value.
      SpinnerNumberModel bandsModel = new SpinnerNumberModel(2, 2, 10, 1);
      JSpinner bandsSpinner = new JSpinner(bandsModel);
      int option =
          JOptionPane.showOptionDialog(
              null,
              bandsSpinner,
              "Enter number of colour bands",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              null,
              null);

      // Check the return value from the dialog box.
      if (option == JOptionPane.CANCEL_OPTION) {
        return;
      } else if (option == JOptionPane.OK_OPTION) {
        bands = bandsModel.getNumber().intValue();
      }

      // Create and apply the filter
      target.getImage().apply(new Posterisation(bands));
      target.repaint();
      target.getParent().revalidate();
    }
  }
}
