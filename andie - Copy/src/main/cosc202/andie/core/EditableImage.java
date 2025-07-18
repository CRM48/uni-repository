package cosc202.andie.core;

import cosc202.andie.exceptions.ExceptionDialogue;
import cosc202.andie.exceptions.NullFileException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;
import javax.imageio.ImageIO;

/**
 * An image with a set of operations applied to it.
 *
 * <p>The EditableImage represents an image with a series of operations applied to it. It is fairly
 * core to the ANDIE program, being the central data structure. The operations are applied to a copy
 * of the original image so that they can be undone. THis is what is meant by "A Non-Destructive
 * Image Editor" - you can always undo back to the original image.
 *
 * <p>Internally the EditableImage has two {@link BufferedImage}s - the original image and the
 * result of applying the current set of operations to it. The operations themselves are stored on a
 * {@link Stack}, with a second {@link Stack} being used to allow undone operations to be redone.
 *
 * <p><a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class EditableImage {

  /** The original image. This should never be altered by ANDIE. */
  private BufferedImage original;
  /** The current image, the result of applying {@link ops} to {@link original}. */
  private BufferedImage current;
  /** The sequence of operations currently applied to the image. */
  private Stack<ImageOperation> ops;
  /** A memory of 'undone' operations to support 'redo'. */
  private Stack<ImageOperation> redoOps;
  /** The file where the original image is stored/ */
  private String imageFilename;
  /** The file where the operation sequence is stored. */
  private String opsFilename;

  /**
   * Create a new EditableImage.
   *
   * <p>A new EditableImage has no image (it is a null reference), and an empty stack of operations.
   */
  public EditableImage() {
    original = null;
    current = null;
    ops = new Stack<ImageOperation>();
    redoOps = new Stack<ImageOperation>();
    imageFilename = null;
    opsFilename = null;
  }

  /**
   * Check if there is an image loaded.
   *
   * @return True if there is an image, false otherwise.
   */
  public boolean hasImage() {
    return current != null;
  }

  /**
   * Make a 'deep' copy of a BufferedImage.
   *
   * <p>Object instances in Java are accessed via references, which means that assignment does not
   * copy an object, it merely makes another reference to the original. In order to make an
   * independent copy, the {@code clone()} method is generally used. {@link BufferedImage} does not
   * implement {@link Cloneable} interface, and so the {@code clone()} method is not accessible.
   *
   * <p>This method makes a cloned copy of a BufferedImage. This requires knoweldge of some details
   * about the internals of the BufferedImage, but essentially comes down to making a new
   * BufferedImage made up of copies of the internal parts of the input.
   *
   * <p>This code is taken from StackOverflow: <a
   * href="https://stackoverflow.com/a/3514297">https://stackoverflow.com/a/3514297</a> in response
   * to <a
   * href="https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage">https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage</a>.
   * Code by Klark used under the CC BY-SA 2.5 license.
   *
   * <p>This method (only) is released under <a
   * href="https://creativecommons.org/licenses/by-sa/2.5/">CC BY-SA 2.5</a>
   *
   * @param bi The BufferedImage to copy.
   * @return A deep copy of the input.
   */
  private static BufferedImage deepCopy(BufferedImage bi) {
    ColorModel cm = bi.getColorModel();
    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
    WritableRaster raster = bi.copyData(null);
    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
  }

  /**
   * Open an image from a file.
   *
   * <p>Opens an image from the specified file. Also tries to open a set of operations from the file
   * with <code>.ops</code> added. So if you open <code>some/path/to/image.png</code>, this method
   * will also try to read the operations from <code>some/path/to/image.png.ops</code>.
   *
   * @param filePath The file to open the image from.
   * @throws Exception If something goes wrong.
   */
  public void open(String filePath) throws Exception {
    imageFilename = filePath;
    opsFilename = imageFilename + ".ops";
    File imageFile = new File(imageFilename);
    original = ImageIO.read(imageFile);
    current = deepCopy(original);

    try {
      FileInputStream fileIn = new FileInputStream(this.opsFilename);
      ObjectInputStream objIn = new ObjectInputStream(fileIn);

      // Silence the Java compiler warning about type casting.
      // Understanding the cause of the warning is way beyond
      // the scope of COSC202, but if you're interested, it has
      // to do with "type erasure" in Java: the compiler cannot
      // produce code that fails at this point in all cases in
      // which there is actually a type mismatch for one of the
      // elements within the Stack, i.e., a non-ImageOperation.
      @SuppressWarnings("unchecked")
      Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
      ops = opsFromFile;
      redoOps.clear();
      objIn.close();
      fileIn.close();
    } catch (Exception ex) {
      // Could be no file or something else. Carry on for now.
    }
    this.refresh();
  }

  /**
   * Save an image to file.
   *
   * <p>Saves an image to the file it was opened from, or the most recent file saved as. Also saves
   * a set of operations from the file with <code>.ops</code> added. So if you save to <code>
   * some/path/to/image.png</code>, this method will also save the current operations to <code>
   * some/path/to/image.png.ops</code>.
   *
   * @throws Exception If something goes wrong.
   */
  public void save() throws Exception {
    if (this.opsFilename == null) {
      this.opsFilename = this.imageFilename + ".ops";
    }
    // Write image file based on file extension
    String extension = imageFilename.substring(1 + imageFilename.lastIndexOf(".")).toLowerCase();
    ImageIO.write(original, extension, new File(imageFilename));
    // Write operations file
    FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
    ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
    objOut.writeObject(this.ops);
    objOut.close();
    fileOut.close();
  }

  /**
   * Save an image to a speficied file.
   *
   * <p>Saves an image to the file provided as a parameter. Also saves a set of operations from the
   * file with <code>.ops</code> added. So if you save to <code>some/path/to/image.png</code>, this
   * method will also save the current operations to <code>some/path/to/image.png.ops</code>.
   *
   * @param imageFilename The file location to save the image to.
   * @throws Exception If something goes wrong.
   */
  public void saveAs(String imageFilename) throws Exception {
    this.imageFilename = imageFilename;
    this.opsFilename = imageFilename + ".ops";
    save();
  }

  /**
   * Exports an image to a specified file.
   *
   * @param path The path
   * @param extension The extension
   * @return Boolean Result of ImageIO.write
   * @throws Exception Exception of export result
   */
  public Boolean export(String path, String extension) throws Exception {
    File out = new File(path + "." + extension);
    if (ImageIO.write(current, extension, out)) return true;

    // Bitmap and JPG may fail with alpha layer
    // Redraw without the alpha layer
    BufferedImage outImg =
        new BufferedImage(current.getWidth(), current.getHeight(), BufferedImage.TYPE_INT_RGB);
    outImg.createGraphics().drawImage(current, 0, 0, Color.white, null);

    return ImageIO.write(outImg, extension, out);
  }

  /**
   * Apply an {@link ImageOperation} to this image.
   *
   * @param op The operation to apply.
   */
  public void apply(ImageOperation op) {
    if (!ImageAction.getTarget().getImage().hasImage()) {
      ExceptionDialogue.exception(
          new NullFileException("Tried to apply an operation without selecting an image"));
      return;
    }
    current = op.apply(current);
    ops.add(op);
  }

  /** Undo the last {@link ImageOperation} applied to the image. */
  public void undo() {
    if (!ImageAction.getTarget().getImage().hasImage()) {
      ExceptionDialogue.exception(
          new NullFileException("Tried to apply an operation without selecting an image"));
      return;
    }
    if (ops.isEmpty()) {
      ExceptionDialogue.exception(new Exception("Nothing to undo"));
      return;
    }
    redoOps.push(ops.pop());
    refresh();
  }

  /** Reapply the most recently {@link undo}ne {@link ImageOperation} to the image. */
  public void redo() {
    if (!ImageAction.getTarget().getImage().hasImage()) {
      ExceptionDialogue.exception(
          new NullFileException("Tried to apply an operation without selecting an image"));
      return;
    }
    if (redoOps.isEmpty()) {
      ExceptionDialogue.exception(new Exception("Nothing to redo"));
      return;
    }
    apply(redoOps.pop());
  }

  /**
   * Get the current image after the operations have been applied.
   *
   * @return The result of applying all of the current operations to the {@link original} image.
   */
  public BufferedImage getCurrentImage() {
    return current;
  }

  /**
   * Gets the operation stack.
   *
   * @return The stack.
   */
  public Stack<ImageOperation> getStack() {
    return ops;
  }

  /**
   * Reapply the current list of operations to the original.
   *
   * <p>While the latest version of the image is stored in {@link current}, this method makes a
   * fresh copy of the original and applies the operations to it in sequence. This is useful when
   * undoing changes to the image, or in any other case where {@link current} cannot be easily
   * incrementally updated.
   */
  private void refresh() {
    current = deepCopy(original);
    for (ImageOperation op : ops) {
      current = op.apply(current);
    }
  }
}
