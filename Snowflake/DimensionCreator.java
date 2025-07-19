import java.util.ArrayList;
import java.lang.Math;


/**
 * DimensionCreater gets the line dimensions to be drawn
 *
 * @author Curtis Mellsop
 */
public class DimensionCreator {

    /**
     * Arraylist to store all current dimensions in
     */
    private ArrayList<double[]> dimensions = new ArrayList<double[]>();
    private double multiply;
    
    /**
     * Constructor
     *
     * @param dimensions initialises the dimensions Arraylist
     */
    public DimensionCreator(ArrayList<double[]> dimensions){
        this.dimensions = dimensions;
        this.multiply = multiply;
    }
    
    /**
     * Create method calculates all the dimensions and adds
     * them to an ArrayList<double[]>
     *
     * @return the ArrayList with the new calculated dimensions
     */
    public ArrayList<double[]> create(){
        ArrayList<double[]> higherOrder = new ArrayList<double[]>();
        int x = dimensions.size();
        for(int i = 0; i < x; i++){
            //variables
            double[] temp = dimensions.get(i);
            double x1 = temp[0];
            double y1 = temp[1];
            double x2 = temp[2];
            double y2 = temp[3];
            double lineNo = temp[4];
            double x3 = 0;
            double y3 = 0;

            //first calculations
            double xthird = ((x2 - x1)/3);
            double ythird = ((y2 - y1)/3);
            double lineDistance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2))/3;

            //first two dimensions
            double[] d1 = {x1, y1, (x1 + xthird), (y1 + ythird), lineNo};
            double[] d2 = {x1+2*xthird, y1+2*ythird, x2, y2, lineNo};
            higherOrder.add(d1);
            higherOrder.add(d2);

            

            //if line is flat
            if(lineNo == 1 || lineNo == 4){
                double xmid = ((x2 - x1)/2);
                double height = lineDistance*Math.sqrt(3)/2;
                //if line points upwards
                if(lineNo == 1){
                    x3 = xmid+x1;
                    y3 = y1 - height;
                }
                //if line points downwards
                else{
                    x3 = xmid+x1;
                    y3 = y1 + height;
                }
            }
            //if line points towards top right
            else if(lineNo == 2){
                x3 = x1+xthird+lineDistance;
                y3 = y1+ythird;
            }
            //if line points towards bottom right
            else if(lineNo == 3){
                x3 = x1+xthird+lineDistance;
                y3 = y1+ythird;
            }
            //if line points towards bottom left
            else if(lineNo == 5){
                x3 = x1+2*xthird-lineDistance;
                y3 = y1+2*ythird;
            }
            //if line points towards top left
            else if(lineNo == 6){
                x3 = x1+2*xthird-lineDistance;
                y3 = y1+2*ythird;
            }
            //shouldn't be called, for testing purposes
            else{
                System.out.println("error: " + lineNo);
            }


            double newLineNo;
            //if statement to determine the type (direction) of line being formed
            if(lineNo == 6 || lineNo == 1 || lineNo == 2){
                newLineNo = lineNo - 1;
            }
            else{
                newLineNo = lineNo + 1;
            }

            //if statement to ensure first x coordinate is always leftmost one
            if((x1+xthird) < x3){
                double[] d3 = {(x1 + xthird), (y1 + ythird), x3, y3, newLineNo};
                //exception case
                if(lineNo == 1){
                    d3[4] = 6;
                }
                higherOrder.add(d3);
            }
            else{
                double[] d3 = {x3, y3, (x1 + xthird), (y1 + ythird), newLineNo};
                if(lineNo == 1){
                    d3[4] = 6;
                }
                higherOrder.add(d3);
            }

            
            if(lineNo == 6 || lineNo == 1 || lineNo == 2){
                newLineNo = lineNo + 1;
            }
            else{
                newLineNo = lineNo - 1;
            }
            if(x3 < (x1+2*xthird)){
                double[] d4 = {x3, y3, x1+2*xthird, y1+2*ythird, newLineNo};
                if(lineNo == 6){
                    d4[4] = 1;
                }
                higherOrder.add(d4);
            }
            else{
                double[] d4 = {x1+2*xthird, y1+2*ythird, x3, y3, newLineNo};
                if(lineNo == 6){
                    d4[4] = 1;
                }
                higherOrder.add(d4);
            } 
        }
        //returns the new ArrayList<double[]> with all the new dimensions
        return higherOrder;
    }
}
