package cosc202.andie.filter;

import java.awt.image.BufferedImage;

/**
 * ImageOperation to apply a Emboss filter.
 *
 * <p>An Emboss filter creates the effect of the image being pressed into or raised out of a sheet
 * of paper.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * @see java.awt.image.ConvolveOp
 * @author Adam Aizal
 * @version 1.0
 */
public class EmbossFilter extends KernelFilter implements java.io.Serializable {

  /** The direction of emboss to apply. 1 is West, 2 is Northwest, 3 is North, and so on. */
  private int direction; // default

  /**
   * Construct an Emboss filter with the default size and direction based on users choice.
   *
   * <p>By default, an Emboss filter has radius 1. The direction of emboss is denoted by
   * 'direction'. There are eight options (detailed above) which will give the desired outcome for
   * the user
   *
   * @param direction The direction of emboss filter
   * @see EmbossFilter(int)
   */
  public EmbossFilter(int direction) {
    super(null, 1, HALF_OFFSET);
    this.direction = direction;
  }

  /**
   * Apply an Emboss filter to an image.
   *
   * @param input The image to apply the Emboss filter to.
   * @return The resulting image.
   */
  public BufferedImage apply(BufferedImage input) {

    // Eight kernels for different Emboss directions
    float[] WEST = {0f, 1f, 0f, 0f, 0f, 0f, 0f, -1f, 0f};
    float[] NWEST = {1f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, -1f};
    float[] NORTH = {0f, 0f, 0f, 1f, 0f, -1f, 0f, 0f, 0f};
    float[] NEAST = {0f, 0f, -1f, 0f, 0f, 0f, 1f, 0f, 0f};
    float[] EAST = {0f, -1f, 0f, 0f, 0f, 0f, 0f, 1f, 0f};
    float[] SEAST = {-1f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 1f};
    float[] SOUTH = {0f, 0f, 0f, -1f, 0f, 1f, 0f, 0f, 0f};
    float[] SWEST = {0f, 0f, 1f, 0f, 0f, 0f, -1f, 0f, 0f};

    switch (direction) {
      case 1:
        set(WEST, 1);
        break;
      case 2:
        set(NWEST, 1);
        break;
      case 3:
        set(NORTH, 1);
        break;
      case 4:
        set(NEAST, 1);
        break;
      case 5:
        set(EAST, 1);
        break;
      case 6:
        set(SEAST, 1);
        break;
      case 7:
        set(SOUTH, 1);
        break;
      case 8:
        set(SWEST, 1);
        break;
    }

    return super.apply(input);
  }
}
