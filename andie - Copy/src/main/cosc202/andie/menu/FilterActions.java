package cosc202.andie.menu;

import cosc202.andie.core.ImageAction;
import cosc202.andie.filter.EdgeDetectionFilter;
import cosc202.andie.filter.EmbossFilter;
import cosc202.andie.filter.GaussianFilter;
import cosc202.andie.filter.MeanFilter;
import cosc202.andie.filter.MedianFilter;
import cosc202.andie.filter.SharpenFilter;
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
 * Actions provided by the Filter menu.
 *
 * <p>The Filter menu contains actions that update each pixel in an image based on some small local
 * neighbourhood. This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/99779/cleanup-noise">Cleanup Noise</a> icon
 * by <a target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/78785/cleanup-edges">Cleanup Edges</a> icon
 * by <a target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/78723/blur">Blur</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/88524/normal-distribution-histogram">Normal
 * Distribution Histogram</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/TGMwYoi2qhe9/wallpaper-roll">Wallpaper
 * Roll</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
 *
 * <p><a target="_blank" href="https://icons8.com/icon/115963/funnel">Funnel</a> icon by <a
 * target="_blank" href="https://icons8.com">Icons8</a>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class FilterActions {

  /** A list of actions for the Filter menu. */
  protected ArrayList<Action> actions;

  /** A list of actions for the toolbar */
  protected ArrayList<Action> toolbarActions;

  /** Create a set of Filter menu actions. */
  public FilterActions() {
    actions = new ArrayList<Action>();
    actions.add(
        new MedianFilterAction(
            "Median filter", null, "Apply a median filter", Integer.valueOf(KeyEvent.VK_W)));
    actions.add(
        new MeanFilterAction(
            "Mean filter", null, "Apply a mean filter", Integer.valueOf(KeyEvent.VK_M)));
    actions.add(
        new SharpenFilterAction(
            "Sharpen filter", null, "Apply a sharpen filter", Integer.valueOf(KeyEvent.VK_K)));
    actions.add(
        new GaussianFilterAction(
            "Gaussian filter", null, "Apply a gaussian filter", Integer.valueOf(KeyEvent.VK_P)));
    actions.add(
        new EmbossFilterAction(
            "Emboss filter", null, "Apply an emboss filter", Integer.valueOf(KeyEvent.VK_I)));
    actions.add(
        new EdgeDetectionFilterAction(
            "Edge Detection filter",
            null,
            "Apply an edge detection filter",
            Integer.valueOf(KeyEvent.VK_U)));
  }

  /**
   * Create a menu contianing the list of Filter actions.
   *
   * @return The filter menu UI element.
   */
  public JMenu createMenu() {
    JMenu filterMenu = new JMenu("Filter");

    for (Action action : actions) {
      filterMenu.add(new JMenuItem(action));
    }

    return filterMenu;
  }

  /**
   * Adds actions to the menu.
   *
   * @return Arraylist of all the actions in the menu
   */
  public ArrayList<Action> addActions() {
    toolbarActions = new ArrayList<Action>();
    try {
      ImageIcon medianIcon = new ImageIcon("./assets/icons/median.png");
      ImageIcon meanIcon = new ImageIcon("./assets/icons/mean.png");
      ImageIcon sharpenIcon = new ImageIcon("./assets/icons/sharpen.png");
      ImageIcon gaussianIcon = new ImageIcon("./assets/icons/gaussian.png");
      ImageIcon embossIcon = new ImageIcon("./assets/icons/emboss.png");
      ImageIcon edgedetectionIcon = new ImageIcon("./assets/icons/edge.png");
      toolbarActions.add(new MedianFilterAction(null, medianIcon, "Apply a median filter", null));
      toolbarActions.add(new MeanFilterAction(null, meanIcon, "Apply a mean filter", null));
      toolbarActions.add(
          new SharpenFilterAction(null, sharpenIcon, "Apply a sharpen filter", null));
      toolbarActions.add(
          new GaussianFilterAction(null, gaussianIcon, "Apply a gaussian filter", null));
      toolbarActions.add(new EmbossFilterAction(null, embossIcon, "Apply an emboss filter", null));
      toolbarActions.add(
          new EdgeDetectionFilterAction(
              null, edgedetectionIcon, "Apply an edge detection filter", null));
    } catch (Exception ex) {
      System.out.println("No file exists");
    }
    return toolbarActions;
  }

  /**
   * Action to blur an image with a median filter.
   *
   * @see MedianFilter
   */
  public class MedianFilterAction extends ImageAction {

    /**
     * Create a new mean-filter action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the convert-to-grey action is triggered.
     *
     * <p>This method is called whenever the MedianFilterAction is triggered. It prompts the user
     * for a filter radius, then applys an appropriately sized {@link MedianFilter}.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {

      // Determine the radius - ask the user.
      int radius = 1;

      // Pop-up dialog box to ask for the radius value.
      SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
      JSpinner radiusSpinner = new JSpinner(radiusModel);
      int option =
          JOptionPane.showOptionDialog(
              null,
              radiusSpinner,
              "Enter filter radius",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              null,
              null);

      // Check the return value from the dialog box.
      if (option == JOptionPane.CANCEL_OPTION) {
        return;
      } else if (option == JOptionPane.OK_OPTION) {
        radius = radiusModel.getNumber().intValue();
      }

      // Create and apply the filter
      target.getImage().apply(new MedianFilter(radius));
      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * Action to blur an image with a mean filter.
   *
   * @see MeanFilter
   */
  public class MeanFilterAction extends ImageAction {

    /**
     * Create a new mean-filter action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the convert-to-grey action is triggered.
     *
     * <p>This method is called whenever the MeanFilterAction is triggered. It prompts the user for
     * a filter radius, then applys an appropriately sized {@link MeanFilter}.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {

      // Determine the radius - ask the user.
      int radius = 1;

      // Pop-up dialog box to ask for the radius value.
      SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
      JSpinner radiusSpinner = new JSpinner(radiusModel);
      int option =
          JOptionPane.showOptionDialog(
              null,
              radiusSpinner,
              "Enter filter radius",
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
        radius = radiusModel.getNumber().intValue();
      }

      // Create and apply the filter
      target.getImage().apply(new MeanFilter(radius));
      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * Action to apply the Gaussian blur to an image
   *
   * @see GaussianFilter
   */
  public class GaussianFilterAction extends ImageAction {
    /**
     * Create a new gaussian-filter action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    GaussianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the gaussian-filter action is triggered.
     *
     * <p>This method is called whenever the gaussian-filter is triggered. It prompts the user for a
     * filter radius, then applys an appropriately sized {@link GaussianFilter}.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {

      // Determine the radius - ask the user.
      int radius = 1;

      // Pop-up dialog box to ask for the radius value.
      SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
      JSpinner radiusSpinner = new JSpinner(radiusModel);
      int option =
          JOptionPane.showOptionDialog(
              null,
              radiusSpinner,
              "Enter filter radius",
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
        radius = radiusModel.getNumber().intValue();
      }

      // Create and apply the filter
      target.getImage().apply(new GaussianFilter(radius));
      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * Action to sharpen an image with a sharpen filter
   *
   * @see SharpenFilter
   */
  public class SharpenFilterAction extends ImageAction {
    /**
     * Create a new sharpen-filter action.
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * Callback for when the sharpen-filter action is triggered.
     *
     * <p>This method is called whenever the SharpenFilterAction is triggered
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {
      target.getImage().apply(new SharpenFilter());
      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * Action to emboss image with an emboss filter
   *
   * @see EmbossFilter
   */
  public class EmbossFilterAction extends ImageAction {

    /**
     * Create a new emboss image action
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    EmbossFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * This method is called whenever the EmbossFilterAction is triggered. It prompts the user to
     * select an emboss direction, then applys it.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {

      // Determine the type - ask the user.
      int direction = 1;
      // Pop-up dialog box to ask for the type.
      String[] choices = {
        "West Emboss",
        "North West Emboss",
        "North Emboss",
        "North East Emboss",
        "East Emboss",
        "South East Emboss",
        "South Emboss",
        "South West Emboss"
      };
      int option =
          JOptionPane.showOptionDialog(
              null,
              "Which direction of emboss?",
              "Emboss",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              choices,
              null);

      // Check the return value from the dialog box.
      if (option == JOptionPane.CLOSED_OPTION) {
        return;
      } else {
        direction = option + 1;
      }

      // Create and apply the filter
      target.getImage().apply(new EmbossFilter(direction));
      target.repaint();
      target.getParent().revalidate();
    }
  }

  /**
   * Action to apply edge detection filter on image with an edge detection filter
   *
   * @see EdgeDetectionFilter
   */
  public class EdgeDetectionFilterAction extends ImageAction {

    /**
     * Create a new edge detection image action
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    EdgeDetectionFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
      super(name, icon, desc, mnemonic);
    }

    /**
     * This method is called whenever the EdgeDetection is triggered. It prompts the user to select
     * an Edge detection direction, then applies it.
     *
     * @param e The event triggering this callback.
     */
    public void actionPerformed(ActionEvent e) {

      // Determine the type - ask the user.
      int direction = 1;
      // Pop-up dialog box to ask for the type.
      String[] choices = {"Horizontal Edge Detection", "Vertical Edge Detection"};
      int option =
          JOptionPane.showOptionDialog(
              null,
              "Which direction of edge detection?",
              "Edge Detection",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              choices,
              null);

      // Check the return value from the dialog box.
      if (option == JOptionPane.CLOSED_OPTION) {
        return;
      } else {
        direction = option + 1;
      }

      // Create and apply the filter
      target.getImage().apply(new EdgeDetectionFilter(direction));
      target.repaint();
      target.getParent().revalidate();
    }
  }
}
