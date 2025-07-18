package cosc202.andie.transformation;

import cosc202.andie.core.ImageOperation;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * ImageOperation to apply a resize on an image It resizes the image according to a percentage
 *
 * @author Sam Merton
 */
public class ImageResize implements ImageOperation, java.io.Serializable {
  /** Percentage to resize image by */
  private double size;

  /**
   * Create a new resize operation
   *
   * @param size The new size
   */
  public ImageResize(double size) {
    this.size = size;
  }

  /**
   * Applies the resize to the image, uses a new BufferedImage and draws the new resized image onto
   * it
   *
   * @return the resized image
   */
  @Override
  public BufferedImage apply(BufferedImage input) {
    BufferedImage output;
    int newWidth, newHeight;
    newWidth = (int) Math.round((size / 100) * input.getWidth());
    newHeight = (int) Math.round((size / 100) * input.getHeight());

    output = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
    Image result;
    if (size < 100) {
      result = input.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING);
    } else {
      result = input.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }
    Graphics2D g = output.createGraphics();
    g.drawImage(result, 0, 0, newWidth, newHeight, null);
    g.dispose();

    return output;
  }
}
