
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

/** A subclass of Canvas that displays an Image. */
public class ImageCanvas extends Canvas {
  /** Constructs an ImageCanvas of a given size.
   * @param width the width of the Canvas in pixels. If this does not match the
   * size of the Image, it will be scaled.
   * @param height the height of the Canvas in pixels.
   * @param image the Image to display. Note that in order to obtain an Image
   * before knowing on what Component it will be displayed it will probably need
   * to be a subclass of BufferedImage.
   */
  public ImageCanvas(int width, int height, Image image) {
    this.width = width; this.height = height; this.image = image;
    this.setSize(width, height);
  }
  
  /** Constructs an ImageCanvas of the specified size and displays it in a Frame
   * with the specified title. When the Frame is closed, calls 'System.exit(0)'.
   * */
  public ImageCanvas(
    int width, int height,
    Image image,
    String title
  ) {
    this(width, height, image);
    this.display(title);
  }
  
  /* New API. */
  
  /** The width and height passed to the constructor. */
  public final int width, height;
  
  /** The Image passed to the constructor. */
  public final Image image;

  /** Displays this ImageCanvas in its own window with the specified title. When
   * the window is closed, calls 'System.exit(0)'. */
  public void display(String title) {
    final java.awt.Frame frame = new java.awt.Frame(title);
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) { System.exit(0); }
    });
    frame.add(this);
    frame.pack();
    frame.setVisible(true);
    this.requestFocus();
  }
  
  /* Override things in Canvas. */
  
  /** Called to repair damage. Plots the Image. */
  public void paint(Graphics g) {
    g.drawImage(this.image, 0, 0, this.width, this.height, this);
  }
  
  /** Call this to redraw the Image immediately. This is useful for running
   * animations, for example. */
  public void doFrame() {
    final Graphics g = this.getGraphics();
    if (g!=null) {
      this.paint(g);
      g.dispose();
    }
  }
}
