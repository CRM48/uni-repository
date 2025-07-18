package cosc202.andie.transformation;

import cosc202.andie.core.ImageOperation;
import java.awt.image.BufferedImage;

/**
 * ImageFlip to apply a Flip on the image. The flip can be Horizontal or Veritcal.
 *
 * @author Adam Aizal
 * @version 1.0
 */
public class ImageFlip implements ImageOperation, java.io.Serializable {

  /** Two types of flip */
  private int type;

  /**
   * Two flip types, Horizontal and Vertical
   *
   * @param type Type of ImageFlip
   */
  public ImageFlip(int type) {
    this.type = type;
  }

  /**
   * Flips the image, then uses new BufferedImage to apply new image onto it
   *
   * @param input The image to apply Flip
   * @return The output flipped image
   */
  public BufferedImage apply(BufferedImage input) {
    int width = input.getWidth();
    int height = input.getHeight();
    int size = width * height;
    int[] pixels = new int[size];
    int i = 0;
    BufferedImage output;

    for (int y = 0; y < height; y++) { // create pixel array
      for (int x = 0; x < width; x++) {
        pixels[i] = input.getRGB(x, y);
        i++;
      }
    }
    i = 0;

    // These are the two flip types, 1 for horizontal and 2 for vertical
    output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    if (type == 1) { // Horizontal Flip
      for (int y = height - 1; y >= 0; y--) {
        for (int x = width - 1; x >= 0; x--) {
          output.setRGB(x, (height - 1) - y, pixels[i]);
          i++;
        }
      }
    }
    if (type == 2) { // Vertical Flip
      for (int y = height - 1; y >= 0; y--) {
        for (int x = width - 1; x >= 0; x--) {
          output.setRGB((width - 1) - x, y, pixels[i]);
          i++;
        }
      }
    }

    return output;
  }
}
