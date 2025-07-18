package cosc202.andie.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 * UI display element for {@link EditableImage}s.
 *
 * <p>This class extends {@link JPanel} to allow for rendering of an image, as well as zooming in
 * and out.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class ImagePanel extends JPanel {

  /** The image to display in the ImagePanel. */
  private EditableImage image;

  /** Selection rectangle */
  private Rectangle selection = null;

  /**
   * The zoom-level of the current view. A scale of 1.0 represents actual size; 0.5 is zoomed out to
   * half size; 1.5 is zoomed in to one-and-a-half size; and so forth.
   *
   * <p>Note that the scale is internally represented as a multiplier, but externally as a
   * percentage.
   */
  private double scale;

  /**
   * Create a new ImagePanel.
   *
   * <p>Newly created ImagePanels have a default zoom level of 100%
   */
  public ImagePanel() {
    image = new EditableImage();
    scale = 1.0;
  }

  /**
   * Get the currently displayed image
   *
   * @return the image currently displayed.
   */
  public EditableImage getImage() {
    return image;
  }

  /**
   * Get the current zoom level as a percentage.
   *
   * <p>The percentage zoom is used for the external interface, where 100% is the original size, 50%
   * is half-size, etc.
   *
   * @return The current zoom level as a percentage.
   */
  public double getZoom() {
    return 100 * scale;
  }

  /**
   * Gets the scale of the image.
   *
   * @return The scale.
   */
  public double getScale() {
    return scale;
  }

  /**
   * Set the current zoom level as a percentage.
   *
   * <p>The percentage zoom is used for the external interface, where 100% is the original size, 50%
   * is half-size, etc. The zoom level is restricted to the range [50, 200].
   *
   * @param zoomPercent The new zoom level as a percentage.
   */
  public void setZoom(double zoomPercent) {
    if (zoomPercent < 50) {
      zoomPercent = 50;
    }
    if (zoomPercent > 200) {
      zoomPercent = 200;
    }
    scale = zoomPercent / 100;
    deselect();
  }

  /**
   * Sets the selection.
   *
   * @param rect The rectangle
   */
  public void setSelection(Rectangle rect) {
    selection = rect;
  }

  /**
   * Gets the preferred size of this component for UI layout.
   *
   * <p>The preferred size is the size of the image (scaled by zoom level), or a default size if no
   * image is present.
   *
   * @return The preferred size of this component.
   */
  @Override
  public Dimension getPreferredSize() {
    if (image.hasImage()) {
      return new Dimension(
          (int) Math.round(image.getCurrentImage().getWidth() * scale),
          (int) Math.round(image.getCurrentImage().getHeight() * scale));
    } else {
      return new Dimension(450, 450);
    }
  }

  /**
   * (Re)draw the component in the GUI.
   *
   * @param g The Graphics component to draw the image on.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (image.hasImage()) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.scale(scale, scale);
      g2.drawImage(image.getCurrentImage(), null, 0, 0);
      if (selection != null) {
        g2.scale(1 / scale, 1 / scale);
        g2.setColor(Color.red);
        g2.draw(selection);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.fill(selection);
      }
      g2.dispose();
    }
  }

  /**
   * Check if panel has a selection active
   *
   * @return True if selection, False otherwise.
   */
  public boolean hasSelection() {
    return (selection != null
        && selection.getSize().getWidth() != 0
        && selection.getSize().getHeight() != 0);
  }

  /** Deselects the selection */
  public void deselect() {
    selection = null;
    this.repaint();
  }
}
