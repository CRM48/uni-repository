package cosc202.andie.transformation;

import cosc202.andie.core.ImageOperation;
import java.awt.image.BufferedImage;

/**
 * ImageRotation to apply a Rotate image option.
 *
 * <p>The Rotate option rotates the image either 90 degrees left, 90 degrees right or 180 degrees.
 *
 * @author Curtis Mellsop
 * @version 1.0
 */
public class ImageRotation implements ImageOperation, java.io.Serializable {

  /**
   * The type of rotation to apply, 1 is 90 degrees left, 2 is 90 degrees right, 3 is 180 degrees.
   */
  private int type;

  /**
   * Rotate based on the users choice.
   *
   * <p>The type of rotation is denoted by 'type', there are three options (detailed above) which
   * will give the desired outcome for the user
   *
   * @param type The type of the newly constructed ImageRotation
   */
  public ImageRotation(int type) {
    this.type = type;
  }

  /**
   * Rotate the image accordingly.
   *
   * <p>To apply a rotation, you must first adjust the size of the image (for 90 degree rotations),
   * then adjust the pixels
   *
   * @param input The image to apply the Rotation to.
   * @return The resulting (Rotated) image.
   */
  public BufferedImage apply(BufferedImage input) {
    int width = input.getWidth();
    int height = input.getHeight();
    int size = width * height;
    int[] pixels = new int[size];
    int i = 0;
    BufferedImage output;

    for (int y = 0; y < height; y++) { // creates an array of all the pixels
      for (int x = 0; x < width; x++) {
        pixels[i] = input.getRGB(x, y);
        i++;
      }
    }

    i = 0;

    if (type == 1 || type == 2) { // These are the two 90 degree options
      // meaning the width and height must be changed
      int placeholder = width;
      width = height;
      height = placeholder;
      output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

      if (type == 1) {
        for (int x = 0; x < width; x++) {
          for (int y = height - 1; y >= 0; y--) {
            output.setRGB(x, y, pixels[i]); // sets the RGB at (x, y) to pixels[i]
            i++;
          }
        }
      } else {
        for (int x = width - 1; x >= 0; x--) {
          for (int y = 0; y < height; y++) {
            output.setRGB(x, y, pixels[i]);
            i++;
          }
        }
      }
    } else { // 180 degree option
      output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      for (int y = height - 1; y >= 0; y--) {
        for (int x = width - 1; x >= 0; x--) {
          output.setRGB(x, y, pixels[i]);
          i++;
        }
      }
    }

    return output;
  }
}
