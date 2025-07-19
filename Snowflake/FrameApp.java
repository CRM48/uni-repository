import java.awt.BorderLayout;
import javax.swing.*;


/**
 * FrameApp is the application class for KochSnowflake
 *
 * It create a Jframe and outputs the contentpane from
 * KochSnowflake on it 
 * 
 * @author Curtis Mellsop
 */
public class FrameApp{
   public static void main(String[]args){
      JFrame frame = new JFrame("Interactive Koch Snowflake");
      KochSnowflake ks = new KochSnowflake();
      ks.setFrame(frame);
      frame.getContentPane().add(ks);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);      
      frame.pack();
      frame.setVisible(true);
   }
}