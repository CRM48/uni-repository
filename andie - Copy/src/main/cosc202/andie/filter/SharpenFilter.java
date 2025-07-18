package cosc202.andie.filter;

/**
 * ImageOperation to apply a Sharpen Filter.
 *
 * <p>A Sharpen Filter enhances the differences between neighbouringh pixels to make them stand out
 * more
 *
 * @author Callum Cooper
 * @version 1.0
 */
public class SharpenFilter extends KernelFilter implements java.io.Serializable {
  private static final float[] matrix = {0f, -0.5f, 0f, -0.5f, 3f, -0.5f, 0, -0.5f, 0f};

  /** Constructs a new sharpen filter instance. */
  public SharpenFilter() {
    super(matrix, 1);
  }
}
