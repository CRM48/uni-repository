package cosc202.andie.colour;

import cosc202.andie.core.ImageOperation;
import java.awt.image.BufferedImage;

/**
 * ImageOperation to posterise an image
 *
 * <p>This class will allow the image to be posterised. This reduces the number of colour tones in
 * the image.
 *
 * @author Curtis Mellsop
 * @version 1.0
 */
public class Posterisation implements ImageOperation, java.io.Serializable {

  /**
   * The type of rotation to apply, 1 is 90 degrees left, 2 is 90 degrees right, 3 is 180 degrees.
   */
  private int bands;

  /**
   * Create a new Posterisation operation.
   *
   * @param bands The number of colour bands
   */
  public Posterisation(int bands) {
    this.bands = bands;
  }

  /**
   * Apply the Posterisation
   *
   * <p>This method converts the current pixel (input.getRGB(x, y)) and changes it to meet the
   * Posterisation requirements. It uses a loop to change each r, g and b value based on their
   * current value. It then combines the results and produces the modified pixel (which is done for
   * every pixel)
   *
   * @param input The image to be adjusted
   * @return The resulting image.
   */
  public BufferedImage apply(BufferedImage input) {
    for (int y = 0; y < input.getHeight(); y++) {
      for (int x = 0; x < input.getWidth(); x++) {
        int argb = input.getRGB(x, y);
        int a = (argb & 0xFF000000) >> 24;
        int r = (argb & 0x00FF0000) >> 16;
        int g = (argb & 0x0000FF00) >> 8;
        int b = (argb & 0x000000FF);

        int[] colours = {r, g, b};

        int[] regions = new int[bands];
        regions[0] = 0;
        for (int i = 1; i < regions.length; i++) {
          regions[i] = (int) Math.round((i * (255 / (double) (bands - 1))));
        }

        for (int i = 0; i < colours.length; i++) {
          for (int j = 0; j < regions.length; j++) {
            if (Math.abs(regions[j] - colours[i])
                <= (int) Math.round(255 / ((double) (bands - 1) * 2))) {
              colours[i] = regions[j];
              break;
            }
          }
        }

        r = colours[0];
        g = colours[1];
        b = colours[2];

        argb = (a << 24) | (r << 16) | (g << 8) | b;
        input.setRGB(x, y, argb);
      }
    }
    return input;
  }
}
