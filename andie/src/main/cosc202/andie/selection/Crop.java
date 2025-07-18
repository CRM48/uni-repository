package cosc202.andie.selection;

import cosc202.andie.core.ImageOperation;
import cosc202.andie.exceptions.ExceptionDialogue;
import cosc202.andie.exceptions.SelectionOutOfBoundsException;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Crop to crop an image
 *
 * <p>This crops the image to the given selection
 *
 * @author Curtis Mellsop
 * @version 1.0
 */
public class Crop implements ImageOperation, java.io.Serializable {

  /** Rectangle with the selection */
  private Rectangle selection;

  /** Scale of the zoom */
  private double scale;

  /**
   * Crop based on the users selection.
   *
   * <p>Selection is the rectangle of the users selection
   *
   * @param selection Selection on image
   */
  public Crop(Rectangle selection, double scale) {
    this.selection = selection;
    this.scale = scale;
  }

  /**
   * Crop the shape
   *
   * <p>It gets the pixels in the selected area and puts them on a new buffered image the same size
   * as the selection
   *
   * @param input The image to crop.
   * @return The resulting cropped image.
   */
  public BufferedImage apply(BufferedImage input) {
    if (selection == null) return input;
    if (!SelectionListener.get().isOnImage(selection)) {
      ExceptionDialogue.exception(new SelectionOutOfBoundsException());
      return input;
    }
    int selectionX = (int) (selection.x / scale);
    int selectionY = (int) (selection.y / scale);
    int width = (int) (selection.width / scale);
    int height = (int) (selection.height / scale);
    int count = 0;
    BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    int[] pixels = new int[width * height];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        pixels[count] = input.getRGB((x + selectionX), (y + selectionY));
        output.setRGB(x, y, pixels[count]);
        count++;
      }
    }
    SelectionListener.get().screen.deselect();
    return output;
  }
}
