package cosc202.andie.selection;

import cosc202.andie.core.ImageOperation;
import cosc202.andie.exceptions.ExceptionDialogue;
import cosc202.andie.exceptions.SelectionOutOfBoundsException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Adds text to a given image using the selection box
 *
 * @author Callum Cooper
 * @version 1.0
 */
public class AddText implements ImageOperation, java.io.Serializable {
  /** Text to add */
  private String text;

  /** Colour of text */
  private Color colour;

  /** Font of the text */
  private Font font;

  /** Rectangle to draw in */
  private Rectangle selection;

  /** Scale of the zoom */
  private double scale;

  /**
   * Constructs a new AddText action
   *
   * @param text The text
   * @param color The color
   * @param font The font
   * @param selection The selection
   */
  public AddText(String text, Color color, Font font, Rectangle selection, double scale) {
    this.text = text;
    this.colour = color;
    this.font = font;
    this.selection = selection;
    this.scale = scale;
  }

  /**
   * Add the text to the image
   *
   * @param input The input
   * @return The new buffered image.
   */
  public BufferedImage apply(BufferedImage input) {
    if (text == null || colour == null || font == null || selection == null) return input;
    if (!SelectionListener.get().isOnImage(selection)) {
      ExceptionDialogue.exception(new SelectionOutOfBoundsException());
      return input;
    }
    Graphics2D g = input.createGraphics();
    g.scale(1 / scale, 1 / scale);
    g.setColor(colour);

    float fontSize = font.getSize();
    float stringWidth = g.getFontMetrics(font).stringWidth(text);
    float stringHeight = g.getFontMetrics(font).getMaxAscent();

    if (stringWidth > selection.getWidth())
      font = font.deriveFont((float) selection.getWidth() / stringWidth * fontSize);

    g.setFont(font);

    g.drawString(
        text,
        (int) selection.getX(),
        (int) (selection.getY() + (selection.getHeight() + stringHeight) / 2));

    SelectionListener.get().screen.deselect();
    return input;
  }
}
