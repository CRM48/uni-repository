package cosc202.andie;

import cosc202.andie.core.ImageAction;
import cosc202.andie.core.ImageOperation;
import cosc202.andie.core.ImagePanel;
import cosc202.andie.menu.ColourActions;
import cosc202.andie.menu.EditActions;
import cosc202.andie.menu.FileActions;
import cosc202.andie.menu.FilterActions;
import cosc202.andie.menu.MacroActions;
import cosc202.andie.menu.SelectionActions;
import cosc202.andie.menu.TransformationActions;
import cosc202.andie.menu.ViewActions;
import cosc202.andie.selection.SelectionListener;
import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

/**
 * Main class for A Non-Destructive Image Editor (ANDIE).
 *
 * <p>This class is the entry point for the program. It creates a Graphical User Interface (GUI)
 * that provides access to various image editing and processing operations.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class Andie {

  /**
   * Launches the main GUI for the ANDIE program.
   *
   * <p>This method sets up an interface consisting of an active image (an {@code ImagePanel}) and
   * various menus which can be used to trigger operations to load, save, edit, etc. These
   * operations are implemented {@link ImageOperation}s and triggered via {@code ImageAction}s
   * grouped by their general purpose into menus.
   *
   * @see ImagePanel
   * @see ImageAction
   * @see ImageOperation
   * @see FileActions
   * @see EditActions
   * @see ViewActions
   * @see FilterActions
   * @see ColourActions
   * @throws Exception if something goes wrong.
   */
  private static void createAndShowGUI() throws Exception {
    // Set up the main GUI frame
    JFrame frame = new JFrame("ANDIE");

    Image image = ImageIO.read(new File("./assets/icon.png"));
    frame.setIconImage(image);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // The main content area is an ImagePanel
    ImagePanel imagePanel = new ImagePanel();
    ImageAction.setTarget(imagePanel);
    JScrollPane scrollPane = new JScrollPane(imagePanel);
    frame.add(scrollPane, BorderLayout.CENTER);

    // Add in menus for various types of action the user may perform.
    JMenuBar menuBar = new JMenuBar();

    // File menus are pretty standard, so things that usually go in File menus go here.
    FileActions fileActions = new FileActions();
    menuBar.add(fileActions.createMenu());

    // Likewise Edit menus are very common, so should be clear what might go here.
    EditActions editActions = new EditActions();
    menuBar.add(editActions.createMenu());

    // View actions control how the image is displayed, but do not alter its actual content
    ViewActions viewActions = new ViewActions();
    menuBar.add(viewActions.createMenu());

    // Filters apply a per-pixel operation to the image, generally based on a local window
    FilterActions filterActions = new FilterActions();
    menuBar.add(filterActions.createMenu());

    // Actions that affect the representation of colour in the image
    ColourActions colourActions = new ColourActions();
    menuBar.add(colourActions.createMenu());

    // Transformations to the image,vthat don't change the integrity of the image itself
    TransformationActions transformationActions = new TransformationActions();
    menuBar.add(transformationActions.createMenu());

    // Macro actions to save & perform operations when wanted
    MacroActions macroActions = new MacroActions();
    menuBar.add(macroActions.createMenu());

    // Selection actions to use in combination with the selection tool
    SelectionActions selectionActions = new SelectionActions();
    menuBar.add(selectionActions.createMenu());

    // Adding tool bar actions
    ArrayList<Action> allToolbarActions = new ArrayList<Action>();
    allToolbarActions.addAll(editActions.addActions());
    allToolbarActions.addAll(viewActions.addActions());
    // allToolbarActions.addAll(filterActions.addActions());
    allToolbarActions.addAll(colourActions.addActions());
    allToolbarActions.addAll(transformationActions.addActions());
    allToolbarActions.addAll(selectionActions.addActions());
    JToolBar toolBar = new JToolBar();
    for (Action i : allToolbarActions) {
      toolBar.add(i);
    }

    SelectionListener listener = new SelectionListener(imagePanel);
    imagePanel.addMouseMotionListener(listener);
    imagePanel.addMouseListener(listener);
    frame.setJMenuBar(menuBar);
    frame.add(toolBar, BorderLayout.PAGE_START);
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Main entry point to the ANDIE program.
   *
   * <p>Creates and launches the main GUI in a separate thread. As a result, this is essentially a
   * wrapper around {@code createAndShowGUI()}.
   *
   * @param args Command line arguments, not currently used
   * @throws Exception If something goes awry
   * @see #createAndShowGUI()
   */
  public static void main(String[] args) throws Exception {
    javax.swing.SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            try {
              createAndShowGUI();
            } catch (Exception ex) {
              System.exit(1);
            }
          }
        });
  }
}
