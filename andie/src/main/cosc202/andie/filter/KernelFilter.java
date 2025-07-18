package cosc202.andie.filter;

import cosc202.andie.core.ImageOperation;
import cosc202.andie.exceptions.ExceptionDialogue;
import java.awt.image.BufferedImage;

/**
 * Abstract class to apply a generic kernel filter to an image. Handles out of bounds by using the
 * closes possible pixel values.
 *
 * @see ImageOperation
 * @author Callum Cooper
 */
public abstract class KernelFilter implements ImageOperation, java.io.Serializable {
  /** No offset for resulting values */
  public static final int DEFAULT_OFFSET = 0;
  /** Offset to account for negative results from a filter 0 becomes 127 */
  public static final int HALF_OFFSET = 127;

  /** Matrix for applying a convolution */
  private float[] matrix;
  /** Radius of the matrix */
  private int radius;
  /** Offset value */
  private int offset;

  /**
   * Clamps a RGB value to 255.
   *
   * @param c input rgb
   * @return Clamped value
   */
  private final int clamp(int c) {
    return (c > 255 ? 255 : (c < 0 ? 0 : c));
  }

  /**
   * Constructs a new kernel filter
   *
   * @param matrix The matrix
   * @param radius The radius
   * @param offset The amount to offset values by
   */
  protected KernelFilter(float[] matrix, int radius, int offset) {
    set(matrix, radius);
    setOffset(offset);
  }

  /**
   * Constructs a new kernel filter
   *
   * @param matrix The matrix
   * @param radius The radius
   */
  protected KernelFilter(float[] matrix, int radius) {
    this(matrix, radius, DEFAULT_OFFSET);
  }

  /**
   * Gets the offset.
   *
   * @return The offset.
   */
  public int getOffset() {
    return offset;
  }

  /**
   * Sets the offset.
   *
   * @param offset The offset
   */
  protected void setOffset(int offset) {
    this.offset = offset % 255;
  }

  /**
   * Sets the matrix and radius values
   *
   * @param matrix The matrix
   * @param radius The radius
   */
  protected void set(float[] matrix, int radius) {
    if (matrix == null || radius == 0) return;
    if (matrix.length / (2 * radius + 1) != (2 * radius + 1)) return;
    this.matrix = matrix;
    this.radius = radius;
  }

  /**
   * Gets the matrix value from the given co-ordinates
   *
   * @param x x co-ordinate
   * @param y y co-ordinate
   * @return Matrix value
   */
  private float matrixAt(int x, int y) {
    if (x < 0 || x > (2 * radius + 1) || y < 0 || y > (2 * radius + 1)) return 0;
    return matrix[(x * (2 * radius + 1)) + y];
  }

  /**
   * Gets the matrix.
   *
   * @return The matrix.
   */
  public float[] getMatrix() {
    return matrix;
  }

  /**
   * Gets the radius.
   *
   * @return The radius.
   */
  public int getRadius() {
    return radius;
  }

  /**
   * Checks to see if matrix and radius values are valid
   *
   * @return True if valid, False otherwise.
   */
  public boolean isValid() {
    if (matrix == null) return false;
    return (matrix.length / (2 * radius + 1) == (2 * radius + 1));
  }

  /** {@inheritDoc} */
  public BufferedImage apply(BufferedImage input) {
    if (!isValid()) {
      ExceptionDialogue.exception(new Exception("Invalid matrix/radius values"));
      return input;
    }

    BufferedImage output =
        new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);

    for (int x = 0; x < input.getWidth(); x++) {
      for (int y = 0; y < input.getHeight(); y++) {

        // Setup channels
        float a = 0;
        float r = 0;
        float g = 0;
        float b = 0;

        for (int dx = -radius; dx <= radius; dx++) {
          for (int dy = -radius; dy <= radius; dy++) {
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
            int av = (pixel & 0xFF000000) >>> 24;
            int rv = (pixel & 0x00FF0000) >> 16;
            int gv = (pixel & 0x0000FF00) >> 8;
            int bv = (pixel & 0x000000FF);

            a += matrixAt(dx + radius, dy + radius) * av;
            r += matrixAt(dx + radius, dy + radius) * rv;
            g += matrixAt(dx + radius, dy + radius) * gv;
            b += matrixAt(dx + radius, dy + radius) * bv;
          }
        }

        // Apply the offset values
        a += offset;
        r += offset;
        g += offset;
        b += offset;

        // Create int value
        int argb =
            (clamp((int) a) << 24)
                | (clamp((int) r) << 16)
                | (clamp((int) g) << 8)
                | clamp((int) b);

        output.setRGB(x, y, argb);
      }
    }

    return output;
  }
}
