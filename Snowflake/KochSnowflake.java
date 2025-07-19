import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * KochSnowflake creates the GUI and draws the snowflake
 *
 * The snowflake is interactive so it will draw the specific
 * order snowflake using the calculated dimensions
 *
 * @author Curtis Mellsop
 */
public class KochSnowflake extends JPanel{
   
   

   //first two variables display the order
   private JLabel orderText = new JLabel("0");
   private JLabel orderLabel = new JLabel("Order: ");
   //next two allows a specific order to be inputted
   private JLabel specificLabel = new JLabel("Specific order: ");
   private JTextField specificOrder = new JTextField(3);

   //adds an order
   private JButton plus = new JButton("Plus");
   //subtracts an order
   private JButton minus = new JButton("Minus");
   //stores the dimensions of the lines to be drawn
   private ArrayList<double[]> lineDimensions = new ArrayList<double[]>();
   //stored Arraylists of dimensions to reuse if orders are reused
   private ArrayList<ArrayList<double[]>> snowflakes = new ArrayList<ArrayList<double[]>>();
   //the two panels for the frame
   private JPanel selectionPanel = new JPanel(true);
   private JPanel displayPanel = new JPanel(true);
   //the current snowflake order
   int order = 0;

   //To keep track of the displayPanel side length
   private int dPDimension = 400;
   //Required when the display panel is not on the left of the screen
   private int addX = 0;
   //Keeps track of old addX coordinate if component is maximised
   private int oldX = -1;

   //frame to access main frame
   private JFrame frame = new JFrame();

   /**
     * Constructor
     * 
     * Does all the original GUI display and adds listeners to
     * the buttons. All so sets the original triangle dimensions
     * to be drawn when order = 1
     *
     * @param dimensions initialises the dimensions Arraylist
     */
   public KochSnowflake(){
      
      displayPanel.setPreferredSize(new Dimension(dPDimension, dPDimension));
      selectionPanel.setPreferredSize(new Dimension(140, 100));
      displayPanel.setOpaque(false);

      orderLabel.setPreferredSize(new Dimension(50, 30));
      selectionPanel.add(orderLabel);
      selectionPanel.add(orderText);

      Font f = orderText.getFont();
      f = new Font(f.getFontName(), Font.BOLD, (f.getSize()+2));
      orderText.setFont(f);
      orderLabel.setFont(f);
      
      ButtonListener bL = new ButtonListener();
      TextListener tL = new TextListener();
      ResizeListener rL = new ResizeListener();
      plus.addActionListener(bL);
      minus.addActionListener(bL);
      specificOrder.addActionListener(tL);
      addComponentListener(rL);

      selectionPanel.add(minus);
      selectionPanel.add(plus);
      selectionPanel.add(specificLabel);
      selectionPanel.add(specificOrder);

      setDoubleBuffered(true);

      add(displayPanel);
      add(selectionPanel);
      setPreferredSize(new Dimension(dPDimension, dPDimension+110));

      setSnowflake();
   }

   /**
     * setFrame
     *
     * Used to access the frame from the main class
     * 
     * @param frame The frame for the gui
     */
   public void setFrame(JFrame frame){
      this.frame = frame;
   }

   /**
     * setSnowflake
     *
     * Changes the dimensions of the snowflake based on
     * the size of the display panel
     */
   public void setSnowflake(){
      snowflakes.clear();
      lineDimensions.clear();
      double sideLength = dPDimension*1/2;
      double mid = dPDimension/2;

      double x1 = mid;
      double y1 = mid*7/8 - sideLength/2;

      double x2 = mid - sideLength/2;
      double y2 = mid*7/8 + sideLength/2;

      double x3 = mid + sideLength/2;
      double y3 = mid*7/8 + sideLength/2;

      double[] d1 = {x2, y2, x1, y1, 6};
      double[] d2 = {x1, y1, x3, y3, 2};
      double[] d3 = {x2, y2, x3, y3, 4};
      
      
      snowflakes.add(new ArrayList<double[]>()); //empty: order = 0
      lineDimensions.add(d1);
      lineDimensions.add(d2);
      lineDimensions.add(d3);
      snowflakes.add(lineDimensions); //original triangle: order = 1

      DimensionCreator dc = new DimensionCreator(null);
      for(int i = snowflakes.size(); i <= order; i++){
         dc = new DimensionCreator(lineDimensions);
         //calculates dimensions for new snowflake
         lineDimensions = dc.create();
         snowflakes.add(lineDimensions);
      }
      lineDimensions = snowflakes.get(order);
   }

   /**
   * Draws all the lines using dimensions in lineDimensions
   *
   * @param g allows for graphics to be drawn
   */
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      /**
       * Graphics2D allows for doubles to be inputted in line
       * Talked more about in the read me file
       */
      Graphics2D g2d = (Graphics2D) g;
      g2d.setColor(getBackground());
      g2d.setColor(Color.BLACK);
      for(int i = 0; i < lineDimensions.size(); i++){
         double[] dimensions = lineDimensions.get(i);
         double x1 = dimensions[0]+addX;
         double y1 = dimensions[1];
         double x2 = dimensions[2]+addX;
         double y2 = dimensions[3];
         g2d.draw(new Line2D.Double(x1, y1, x2, y2));
      }
   }

   /**
    * Button listener to listen to the plus and minus button
    *
    * @author Curtis Mellsop
    */
   private class ButtonListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         JButton source = (JButton)e.getSource();
         
         if(source == minus){//button = minus
            if(order >= 1){
               order--;
               //dimensions will already be stored in triangles
               lineDimensions = snowflakes.get(order);
               paintComponent(getGraphics());
            }
         }
         else{ //button = plus (as only two buttons)
            order++;
            //if dimensions not in triangles ArrayList
            if(order >= snowflakes.size()){
               DimensionCreator dc = new DimensionCreator(lineDimensions);
               //calculates dimensions for new snowflake
               lineDimensions = dc.create();
               snowflakes.add(lineDimensions);
            }
            //if dimensions are in triangles ArrayList
            else{
               lineDimensions = snowflakes.get(order);
            }
            paintComponent(getGraphics());
         }
         orderText.setText(Integer.toString(order));
         KochSnowflake.this.revalidate();
         KochSnowflake.this.repaint();
      }
   }

   /**
     * TextListener
     *
     * Called everytime there is input in the orderText
     * Used to change the order accordingly
     */
   private class TextListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         int input = order;
         
         try{
            input = Integer.parseInt(specificOrder.getText());
         }
         catch(NumberFormatException w){
            System.err.println("Invalid input");
            return;
         }
         specificOrder.setText("");

         if(order == input){
            return;
         }
         if(input < 0){
            System.err.println("Invalid input");
            return;
         }
         else if(input > order){
            if(input >= snowflakes.size()){
               lineDimensions = snowflakes.get(order);
               for(int i = order; i < input+1; i++){
                  if(i == 0){}
                  else{
                     paintComponent(getGraphics());
                     DimensionCreator dc = new DimensionCreator(lineDimensions);
                     //calculates dimensions for new snowflake
                     lineDimensions = dc.create();
                     snowflakes.add(lineDimensions);
                  }
                  try{Thread.sleep(500);}
                  catch(InterruptedException w){}
               }
            }
            else{
               for(int i = order; i < input; i++){
                  lineDimensions = snowflakes.get(i+1);
                  paintComponent(getGraphics());

                  try{Thread.sleep(500);}
                  catch(InterruptedException w){}
               }
            }
            order = input;
         }
         else{
            for(int i = order-1; i >= input; i--){
               lineDimensions = snowflakes.get(i);
               paintComponent(getGraphics());

               try{Thread.sleep(500);}
               catch(InterruptedException w){}
              
            }
         }
         order = input;
         orderText.setText(Integer.toString(order));
         KochSnowflake.this.revalidate();
         KochSnowflake.this.repaint();
         
      }
   }

   /**
     * ResizeListener
     *
     * Called everytime the component is resized, changes dimensions
     * and coordinates of relative components accordingly
     */
   private class ResizeListener implements ComponentListener{
      @Override
      public void componentResized(ComponentEvent e){
         int frameWidth = e.getComponent().getWidth();
         int frameHeight = e.getComponent().getHeight();

         if(oldX != -1){
            addX = oldX;
            oldX = -1;
         }
         else if(KochSnowflake.this.frame.getExtendedState() == Frame.MAXIMIZED_BOTH){
            oldX = addX;
            addX = 0;
         }
         else{
            addX = displayPanel.getX();
         }

         if(frameHeight >= frameWidth){
            if(frameHeight - frameWidth < (selectionPanel.getHeight())){
               dPDimension = frameHeight - (selectionPanel.getHeight()+20);
            }
            else{
               dPDimension = frameWidth;
            }
         }
         else{
            if(frameWidth - frameHeight < selectionPanel.getWidth()){
               dPDimension = frameWidth - (selectionPanel.getWidth()+20);
            }
            else{
               dPDimension = frameHeight;
            }
         }
         displayPanel.setPreferredSize(new Dimension(dPDimension, dPDimension));
         
         setSnowflake();
         KochSnowflake.this.revalidate();
         KochSnowflake.this.repaint();
      }

      @Override
      public void componentHidden(ComponentEvent e){
         repaint();
      }

      @Override
      public void componentMoved(ComponentEvent e){
         repaint();
      }
      @Override
      public void componentShown(ComponentEvent e){
         repaint();
      }
      
   }
}