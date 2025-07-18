package cosc202.andie.colour;

import cosc202.andie.core.ImageOperation;
import java.awt.image.BufferedImage;

/**
 * ImageOperation to change an images brightness and contrast
 *
 * <p>This class will allow the images brightness and contrast to either be increased or decreased
 * based on the users input
 *
 * @author Curtis Mellsop
 * @version 1.0
 */
public class BrightnessAndContrast implements ImageOperation, java.io.Serializable {

  /** brightness setting */
  private int brightness;
  /** contrast setting */
  private int contrast;

  /**
   * Creates a new BrightnessAndContrast action.
   *
   * @param brightness The brightness
   * @param contrast The contrast
   */
  public BrightnessAndContrast(int brightness, int contrast) {
    this.brightness = brightness;
    this.contrast = contrast;
  }

  /**
   * Apply the given brightness and contrast specifications
   *
   * <p>This method converts the current pixel (input.getRGB(x, y)) and changes it to meet the
   * specific user requirements. It uses if statements to make sure each pixels RGB stay within
   * 0-255. It then combines the results and produces the modified pixel (which is done for every
   * pixel)
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

        r =
            (int)
                Math.round((1 + contrast / 100.0) * (r - 127.5) + 127.5 * (1 + brightness / 100.0));
        if (r > 255) {
          r = 255;
        } else if (r < 0) {
          r = 0;
        }
        g =
            (int)
                Math.round((1 + contrast / 100.0) * (g - 127.5) + 127.5 * (1 + brightness / 100.0));
        if (g > 255) {
          g = 255;
        } else if (g < 0) {
          g = 0;
        }
        b =
            (int)
                Math.round((1 + contrast / 100.0) * (b - 127.5) + 127.5 * (1 + brightness / 100.0));
        if (b > 255) {
          b = 255;
        } else if (b < 0) {
          b = 0;
        }

        argb = (a << 24) | (r << 16) | (g << 8) | b;
        input.setRGB(x, y, argb);
      }
    }
    return input;
  }
}
