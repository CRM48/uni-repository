package cosc202.andie.filter;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * ImageOperation to apply a Mean (simple blur) filter.
 *
 * <p>A Mean filter blurs an image by replacing each pixel by the average of the pixels in a
 * surrounding neighbourhood, and can be implemented by a convolution.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * @see java.awt.image.ConvolveOp
 * @author Steven Mills
 * @version 2.0
 */
public class MeanFilter extends KernelFilter implements java.io.Serializable {

  /**
   * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so
   * forth.
   */
  private int internalRadius;

  /**
   * Construct a Mean filter with the given size.
   *
   * <p>The size of the filter is the 'radius' of the convolution kernel used. A size of 1 is a 3x3
   * filter, 2 is 5x5, and so on. Larger filters give a stronger blurring effect.
   *
   * @param radius The radius of the newly constructed MeanFilter
   */
  public MeanFilter(int radius) {
    super(null, radius);
    this.internalRadius = radius;
  }

  /**
   * Construct a Mean filter with the default size.
   *
   * <p>By default, a Mean filter has radius 1.
   *
   * @see MeanFilter(int)
   */
  MeanFilter() {
    this(1);
  }

  /**
   * Apply a Mean filter to an image.
   *
   * <p>As with many filters, the Mean filter is implemented via convolution. The size of the
   * convolution kernel is specified by the {@link radius}. Larger radii lead to stronger blurring.
   *
   * @param input The image to apply the Mean filter to.
   * @return The resulting (blurred)) image.
   */
  public BufferedImage apply(BufferedImage input) {
    int size = (2 * internalRadius + 1) * (2 * internalRadius + 1);
    float[] array = new float[size];
    Arrays.fill(array, 1.0f / size);

    set(array, internalRadius);

    return super.apply(input);
  }
}
