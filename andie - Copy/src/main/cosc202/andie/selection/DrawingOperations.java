package cosc202.andie.selection;

import cosc202.andie.core.ImageOperation;
import cosc202.andie.exceptions.ExceptionDialogue;
import cosc202.andie.exceptions.SelectionOutOfBoundsException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * DrawingOperation draws an shape onto the image
 *
 * <p>It'll draw either a rectangle, oval or line based on the selection, the rectangle and oval
 * will fill the selection, the line will be from top left to bottom right corner.
 *
 * @author Curtis Mellsop
 * @version 1.0
 */
public class DrawingOperations implements ImageOperation, java.io.Serializable {

  /* The type of shape 1: rectangle, 2: oval, 3: line */
  private int shape;

  /* Fill, true: fill, false, empty */
  private boolean fill;

  /* Colour of shape */
  private Color colour;

  /* Rectangle with the selection */
  private Rectangle selection;

  /** Scale of the zoom */
  private double scale;

  /**
   * Draw shape based on the users choice.
   *
   * <p>User chooses a shape, fill or not fill (for rectangles and ovals) and colour in various
   * menus. The selection should be selected prior else no shape will draw
   *
   * @param shape The shape to be drawn
   * @param fill Filled or not filled shape
   * @param colour Colour of the shape
   * @param selection Selection on image
   */
  public DrawingOperations(
      int shape, boolean fill, Color colour, Rectangle selection, double scale) {
    this.shape = shape;
    this.fill = fill;
    this.colour = colour;
    this.selection = selection;
    this.scale = scale;
  }

  /**
   * Draw the shape
   *
   * <p>It draws a shape at the selections x and y coordinates with the selections width and height
   *
   * @param input The image to draw the shape on.
   * @return The resulting image with the shape drawn on.
   */
  public BufferedImage apply(BufferedImage input) {
    if (colour == null || selection == null) return input;
    if (!SelectionListener.get().isOnImage(selection)) {
      ExceptionDialogue.exception(new SelectionOutOfBoundsException());
      return input;
    }
    int x = selection.x;
    int y = selection.y;
    int width = selection.width;
    int height = selection.height;
    BufferedImage image = input;
    Graphics2D g2d = image.createGraphics();
    g2d.scale(1 / scale, 1 / scale);
    g2d.setColor(colour);

    if (shape == 1) { // rectangle
      if (fill) {
        g2d.fillRect(x, y, width, height);
      } else {
        g2d.drawRect(x, y, width, height);
      }
    } else if (shape == 2) { // oval
      if (fill) {
        g2d.fillOval(x, y, width, height);
      } else {
        g2d.drawOval(x, y, width, height);
      }
    } else { // line
      g2d.drawLine(x, y, (x + width), (y + height));
    }
    g2d.dispose();

    SelectionListener.get().screen.deselect();
    return image;
  }
}
