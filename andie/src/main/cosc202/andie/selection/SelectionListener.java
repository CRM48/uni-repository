package cosc202.andie.selection;

import cosc202.andie.core.ImagePanel;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

/** Listener class for an image panel to select parts of an image. */
public class SelectionListener implements MouseInputListener {
  /** Singleton selection listener for the GUI */
  private static SelectionListener listener = null;

  /** Selection rectangle */
  Rectangle rect = null;

  /** Coordinates of the selection */
  int x, y, h, w = 0;

  /** Image panel the listener is attached to */
  public ImagePanel screen = null;

  /**
   * Sets the xy from a mouse event.
   *
   * @param e The mouse event
   */
  public void setXY(MouseEvent e) {
    int newX = e.getX();
    int newY = e.getY();
    if (newX > screen.getImage().getCurrentImage().getWidth() * screen.getScale()) {
      newX = (int) (screen.getImage().getCurrentImage().getWidth() * screen.getScale());
    }
    if (newY > screen.getImage().getCurrentImage().getHeight() * screen.getScale()) {
      newY = (int) (screen.getImage().getCurrentImage().getHeight() * screen.getScale());
    }
    // If mouse is north west of original starting point
    if (x > newX && y > newY) {
      rect.setBounds(newX, newY, x - newX, y - newY);
    }
    // If mouse is north east of original starting point
    else if (x < newX && y > newY) {
      rect.setBounds(x, newY, newX - x, y - newY);
    }
    // If mouse is south west of original starting point
    else if (x > newX && y < newY) {
      rect.setBounds(newX, y, x - newX, newY - y);
    }
    // If mouse is south east of original starting point
    else if (x < newX && y < newY) {
      rect.setBounds(x, y, newX - x, newY - y);
    }
  }

  /**
   * Constructs a new singleton instance.
   *
   * @param screen The screen
   */
  public SelectionListener(ImagePanel screen) {
    if (listener == null) {
      this.screen = screen;
      listener = this;
    }
  }

  /**
   * Gets the singleton instance
   *
   * @return The selection listener.
   */
  public static SelectionListener get() {
    if (listener == null) return null;
    return listener;
  }

  /**
   * Check if listener has selection
   *
   * @return True if selection, False otherwise.
   */
  public boolean hasSelection() {
    Rectangle r = rect;
    if (r == null) {
      return false;
    }
    if (r.getWidth() == 0 || r.getHeight() == 0) {
      return false;
    }
    return true;
  }

  /**
   * Gets the selection.
   *
   * @return The selection.
   */
  public Rectangle getSelection() {
    return rect;
  }

  /**
   * Gets the x coordinate.
   *
   * @return The x.
   */
  public int getX() {
    if (!hasSelection()) {
      return -1;
    }
    return (int) rect.getX();
  }

  /**
   * Gets the y coordinate.
   *
   * @return The y.
   */
  public int getY() {
    if (!hasSelection()) {
      return -1;
    }
    return (int) rect.getY();
  }

  /**
   * Gets the width.
   *
   * @return The width.
   */
  public int getWidth() {
    if (!hasSelection()) {
      return -1;
    }
    return (int) rect.getWidth();
  }

  /**
   * Gets the height.
   *
   * @return The height.
   */
  public int getHeight() {
    if (!hasSelection()) {
      return -1;
    }
    return (int) rect.getHeight();
  }

  /** {@inheritDoc} */
  public void mouseClicked(MouseEvent e) {
    screen.deselect();
  }

  /**
   * Determines if selection is on image.
   *
   * @param e the mouse event
   * @param screen The screen
   * @return True if on image, False otherwise.
   */
  private static boolean isOnImage(MouseEvent e, ImagePanel screen) {
    if (screen == null || screen.getImage() == null || !screen.getImage().hasImage()) {
      return false;
    }
    if (e.getX() > screen.getImage().getCurrentImage().getWidth()
        || e.getY() > screen.getImage().getCurrentImage().getHeight()) {
      return false;
    }

    return true;
  }

  public boolean isOnImage(Rectangle selection) {
    if (selection.getX() < 0
        || selection.getX() + selection.getWidth() > screen.getImage().getCurrentImage().getWidth())
      return false;
    if (selection.getY() < 0
        || selection.getY() + selection.getHeight()
            > screen.getImage().getCurrentImage().getHeight()) return false;
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public void mousePressed(MouseEvent e) {
    if (!isOnImage(e, screen)) {
      return;
    }
    rect = new Rectangle(e.getX(), e.getY(), 0, 0);
    x = e.getX();
    y = e.getY();
    setXY(e);
  }

  /** {@inheritDoc} */
  @Override
  public void mouseReleased(MouseEvent e) {}

  /** {@inheritDoc} */
  @Override
  public void mouseEntered(MouseEvent e) {}

  /** {@inheritDoc} */
  @Override
  public void mouseExited(MouseEvent e) {}

  /** {@inheritDoc} */
  @Override
  public void mouseDragged(MouseEvent e) {
    if (rect != null && screen.getImage().hasImage()) {
      setXY(e);
      repaint();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void mouseMoved(MouseEvent e) {}

  /** Repaint the selection */
  public void repaint() {
    screen.setSelection(rect);
    screen.repaint();
  }
}
