package cosc202.andie.filter;

import java.awt.image.BufferedImage;

/**
 * ImageOperation to apply a Gaussian Blur filter.
 *
 * <p>A gaussian blur is a reasonable approximation to the blurring caused by out of focus camera
 * lenses.
 *
 * <p>See <a href="https://en.wikipedia.org/wiki/Gaussian_blur">wikipedia</a> for details
 *
 * @see java.awt.image.ConvolveOp
 * @author Callum Cooper
 * @version 1.0
 */
public class GaussianFilter extends KernelFilter implements java.io.Serializable {
  /** Internal radius used for calculating the matrix */
  private int internalRadius;

  /**
   * Constructs a new Gaussian blur filter with the given radius
   *
   * @param radius The radius
   */
  public GaussianFilter(int radius) {
    super(null, radius);
    internalRadius = radius;
  }

  /** Constructs a new Gaussian blur filter with a radius of 1 */
  GaussianFilter() {
    this(1);
  }

  /**
   * Gaussian function implementation see the <a
   * href="https://en.wikipedia.org/wiki/Gaussian_blur#Mathematics">definition</a>.
   *
   * @param x x coordinate
   * @param y y coordinate
   * @param sigma sigma variable
   * @return Function output
   */
  public static float Gaussian(double x, double y, double sigma) {
    return (float)
        ((1.0 / (2 * Math.PI * Math.pow(sigma, 2)))
            * Math.pow(Math.E, -((Math.pow(x, 2) + Math.pow(y, 2)) / (2.0 * Math.pow(sigma, 2)))));
  }

  /**
   * Normalize a float array
   *
   * @param array The array to normalize
   */
  public static void normalize(float[] array) {
    float sum = 0f;
    for (float f : array) sum += f;
    if (sum == 0f) return; // Ensure no zero division
    for (int i = 0; i < array.length; i++) array[i] = array[i] / sum;
  }

  /**
   * Apply a Gaussian blur filter to an image.
   *
   * @param input The image to apply the filter to
   * @return The resulting blurred image.
   */
  public BufferedImage apply(BufferedImage input) {
    double sigma = internalRadius / 3.0;
    int width = (2 * internalRadius + 1);
    int size = (int) Math.pow(width, 2);
    float[] matrix = new float[size];

    for (int i = 0; i < size; i++) {
      int x = (int) Math.floor(i / width) - internalRadius;
      int y = internalRadius - ((i + width) % width);

      matrix[i] = Gaussian(x, y, sigma);
    }
    // Normalize the matrix
    normalize(matrix);

    set(matrix, internalRadius);

    return super.apply(input);
  }
}
