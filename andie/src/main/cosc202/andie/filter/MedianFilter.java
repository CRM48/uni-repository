package cosc202.andie.filter;

import cosc202.andie.core.ImageOperation;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ImageOperation to apply a Median (blur) filter.
 *
 * <p>A Median filter blurs an image by replacing each pixel by the median of the pixels in a
 * surrounding neighbourhood.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * @see java.awt.image.ConvolveOp
 * @author Callum Cooper
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

  /**
   * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so
   * forth.
   */
  private int radius;

  /**
   * Construct a Median filter with the given size.
   *
   * <p>The size of the filter is the 'radius' of the pixels used. A size of 1 is a 3x3 filter, 2 is
   * 5x5, and so on. Larger filters give a stronger blurring effect.
   *
   * @param radius The radius of the newly constructed MedianFilter
   */
  public MedianFilter(int radius) {
    this.radius = radius;
  }

  /**
   * Construct a Median filter with the default size.
   *
   * <p>By default, a Median filter has radius 1.
   *
   * @see MedianFilter(int)
   */
  MedianFilter() {
    this(1);
  }

  /**
   * Take the median of a sortable array
   *
   * @param <T> Generic type must extend comparable
   * @param arr The array to find the median of
   * @return median value
   */
  public static <T extends Comparable<? super T>> T median(T[] arr) {
    List<T> list = Arrays.asList(arr);
    Collections.sort(list);
    return list.get(arr.length / 2);
  }

  /**
   * Take the median of a sortable list
   *
   * @param <T> Generic type must extend comparable
   * @param list The list to find the median of
   * @return median value
   */
  public static <T extends Comparable<? super T>> T median(List<T> list) {
    Collections.sort(list);
    return list.get(list.size() / 2);
  }

  /**
   * Apply a Median filter to an image.
   *
   * <p>As with many filters, the Median filter is implemented via convolution. The size of the
   * convolution kernel is specified by the {@link radius}. Larger radii lead to stronger blurring.
   *
   * @param input The image to apply the Median filter to.
   * @return The resulting (blurred)) image.
   */
  public BufferedImage apply(BufferedImage input) {
    BufferedImage output =
        new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);

    // Loop through pixels using the radius
    for (int x = 0; x < input.getWidth(); x++) {
      for (int y = 0; y < input.getHeight(); y++) {
        // Setup channels
        ArrayList<Integer> a = new ArrayList<Integer>();
        ArrayList<Integer> r = new ArrayList<Integer>();
        ArrayList<Integer> g = new ArrayList<Integer>();
        ArrayList<Integer> b = new ArrayList<Integer>();

        // Get all pixels in local domain
        for (int dx = -radius; dx < radius; dx++) {
          for (int dy = -radius; dy < radius; dy++) {
            // Catch edge conditions
            // Pixels outside of an image take their nearest edge bound pixel
            int tempX = x + dx;
            int tempY = y + dy;

            if (tempX < 0) tempX = 0;
            else if (tempX >= input.getWidth()) tempX = input.getWidth() - 1;
            if (tempY < 0) tempY = 0;
            else if (tempY >= input.getHeight()) tempY = input.getHeight() - 1;

            int pixel = input.getRGB(tempX, tempY);

            // Convert to colour pixel values
            Integer av = (pixel & 0xFF000000) >>> 24;
            Integer rv = (pixel & 0x00FF0000) >> 16;
            Integer gv = (pixel & 0x0000FF00) >> 8;
            Integer bv = (pixel & 0x000000FF);

            a.add(av);
            r.add(rv);
            g.add(gv);
            b.add(bv);
          }
        }

        // Calculate medians
        int aMedian = median(a);
        int rMedian = median(r);
        int gMedian = median(g);
        int bMedian = median(b);

        // Convert back
        int argb = (aMedian << 24) | (rMedian << 16) | (gMedian << 8) | bMedian;

        output.setRGB(x, y, argb);
      }
    }

    return output;
  }
}
