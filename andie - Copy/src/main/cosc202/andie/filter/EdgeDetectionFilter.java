package cosc202.andie.filter;

import java.awt.image.BufferedImage;

/**
 * ImageOperation to apply a Emboss filter.
 *
 * <p>An edge detection filter creates the effect of the image being pressed into or raised out of a
 * sheet of paper.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * @see java.awt.image.ConvolveOp
 * @author Adam Aizal
 * @version 1.0
 */
public class EdgeDetectionFilter extends KernelFilter implements java.io.Serializable {

  /** The direction of edgedetection to apply. 1 is Horizontal and 2 is Vertical */
  private int direction; // default

  /**
   * Construct an EdgeDetection filter with the default size and direction based on users choice.
   *
   * <p>By default, an Emboss filter has radius 1. The direction of edge detection is denoted by
   * 'direction'. There are two options (detailed above) which will give the desired outcome for the
   * user
   *
   * @param direction The direction of edge detection filter
   * @see EdgeDetectionFilter(int)
   */
  public EdgeDetectionFilter(int direction) {
    super(null, 1, 128);
    this.direction = direction;
  }

  /**
   * Apply an edge detection filter to an image.
   *
   * @param input The image to apply the edge detection filter to.
   * @return The resulting image.
   */
  public BufferedImage apply(BufferedImage input) {

    // Two kernels for two edge detection filters
    float[] HORI = {-0.5f, -1f, -0.5f, 0f, 0f, 0f, 0.5f, 1f, 0.5f};
    float[] VERT = {-0.5f, 0f, 0.5f, -1f, 0f, 1f, -0.5f, 0f, 0.5f};

    if (direction == 1) {
      set(HORI, 1);
    } else {
      set(VERT, 1);
    }

    return super.apply(input);
  }
}
