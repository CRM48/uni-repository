package cosc202.andie.macro;

import cosc202.andie.core.ImageOperation;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

/** Macro class for performing a series of operations on demand */
public class Macro {
  /** Whether the macro is currently recording */
  private boolean recording = false;
  /** Original image operation stack when initialised */
  private Stack<ImageOperation> original;
  /** Image operations to apply */
  private Stack<ImageOperation> actions;

  /**
   * New macro from a file
   *
   * @param path The path
   * @throws Exception file IO exception
   */
  @SuppressWarnings("unchecked")
  public Macro(String path) throws Exception {
    FileInputStream fileIn = new FileInputStream(path);
    ObjectInputStream objIn = new ObjectInputStream(fileIn);

    actions = (Stack<ImageOperation>) objIn.readObject();
    objIn.close();
  }

  /** Default macro instance */
  public Macro() {}

  /**
   * Starts recording a macro
   *
   * @param original The original actions applied
   */
  @SuppressWarnings("unchecked")
  public void startRecording(Stack<ImageOperation> original) {
    recording = true;

    this.original = (Stack<ImageOperation>) original.clone();
  }

  /**
   * Finish recording a macro
   *
   * @param end The final action stack
   */
  @SuppressWarnings("unchecked")
  public void finishRecording(Stack<ImageOperation> end) {
    recording = false;

    actions = (Stack<ImageOperation>) end.clone();
    actions.removeAll(original);
  }

  /**
   * Saves a macro to a file
   *
   * @param path The path
   * @throws Exception IOException
   */
  public void saveMacro(String path) throws Exception {
    FileOutputStream fileOut = new FileOutputStream(path + ".macro");
    ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
    objOut.writeObject(actions);
    objOut.close();
    fileOut.close();
  }

  /**
   * Return a copy of the macro actions
   *
   * @return The actions.
   */
  @SuppressWarnings("unchecked")
  public Stack<ImageOperation> getActions() {
    return (Stack<ImageOperation>) actions.clone();
  }

  /**
   * Determines if recording.
   *
   * @return True if recording, False otherwise.
   */
  public boolean isRecording() {
    return recording;
  }
}
