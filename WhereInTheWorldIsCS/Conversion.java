public class Conversion{
    private String errorMessage = null;
    private String coordinate;
    private boolean latitude;
    private boolean positive = true;

    /**
     * Constructor
     * 
     * Takes in the coordinate and sets the global coordinate to
     * a version that works with the codes instructions, then checks
     * for any excess or unidentified characters
     * 
     * @param local_coordinate inputted coordinate from Coordinates class
     */
    public Conversion(String local_coordinate){
        coordinate = fix(local_coordinate);
        String stripped = removeExcess(coordinate);
        if(!(isNumber(stripped))){
            errorMessage = "Unidentified characters";
            return;
        }
    }

    /**
     * getCoordinate
     * 
     * Returns coordinate
     * 
     * @return the variable coordinate
     */
    public String getCoordinate(){
        return coordinate;
    }

    /**
     * getErrorMessage
     * 
     * Returns errorMessage
     * 
     * @return the variable errorMessage
     */
    public String getErrorMessage(){
        return errorMessage;
    }

    /**
     * setCoordinate
     * 
     * Sets latitude = local_latitude
     * 
     * @param local_latitude the state that you wish to set latitude to
     */
    public void setLatitude(boolean local_latitude){
        latitude = local_latitude;
    }

    /**
     * getLatitude
     * 
     * Returns latitude
     * 
     * @return the variable latitude
     */
    public boolean getLatitude(){
        return latitude;
    }

    /**
     * fix
     * 
     * Swaps any possible unidentified characters with ones recognised
     * and used in the program
     * 
     * @param local_coordinates the coordinates to be fixed
     * @return the fixed coordinates
     */
    public String fix(String local_coordinate){
        local_coordinate = local_coordinate.replace("′", "\'");
        local_coordinate = local_coordinate.replace("ï", "\'");
        local_coordinate = local_coordinate.replace("″", "\"");
        local_coordinate = local_coordinate.replace("ø", "°");
        local_coordinate = local_coordinate.replace("d", "°");
        return local_coordinate;

    }

    /**
     * removeExcess
     * 
     * Removes any characters that might be in the string for a valid input.
     * Allows to check validity with whether output is a number or not
     * 
     * @param local_coordinates the coordinates
     * @return local_coordinates without any valid characters
     */
    public String removeExcess(String local_coordinate){
        local_coordinate = local_coordinate.replaceFirst("N", "");
        local_coordinate = local_coordinate.replaceFirst("S", "");
        local_coordinate = local_coordinate.replaceFirst("E", "");
        local_coordinate = local_coordinate.replaceFirst("W", "");
        
        local_coordinate = local_coordinate.replaceFirst("\"", "");
        local_coordinate = local_coordinate.replaceFirst("\'", "");
        local_coordinate = local_coordinate.replaceFirst("°", "");
        
        local_coordinate = local_coordinate.replace(" ", "");
        
        for(int i = 0; i < 2; i++){
            local_coordinate = local_coordinate.replaceFirst(":", "");
        }
        return local_coordinate;
    }

    /**
     * isNumber
     * 
     * Checks if parameter is a number
     * 
     * @param local_coordinates the coordinates to be checked
     * @return if local_coordinates is number or not
     */
    public static boolean isNumber(String local_coordinate){
        try{
            Double.parseDouble(local_coordinate);
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * coordinateType
     * 
     * Determines the type of coordinate
     * 
     * @return The coordinate type
     */
    public String coordinateType(){
        if(coordinate.split(":").length == 3){
            return "DMS";
        }
        else if(coordinate.split(":").length == 2){
            return "DegMin";
        }

        if(coordinate.contains("°") && coordinate.contains("\"") && coordinate.contains("\'")){
            return "DMS";
        }
        else if(coordinate.contains("°") || coordinate.contains("\'")){
            return "DegMin";
        }
        return "DecDeg";
    }

    /**
     * removeSpaces
     * 
     * removes all spaces from the input. Used to make it more clear
     * to the user the point of the call when it is called
     * 
     * @param local_coordinate the coordinate
     * @return The coordinate without any spaces
     */
    public String removeSpaces(String local_coordinate){
        return local_coordinate.replace(" ", "");
    }

    /**
     * replaceSymbols
     * 
     * replaces the three symbols for DMS input with a space
     * 
     * @param local_coordinate the coordinate
     * @return The changed coordinate
     */
    public String replaceSymbols(String local_coordinates){
        local_coordinates = local_coordinates.replaceFirst("°", " ");
        local_coordinates = local_coordinates.replaceFirst("\"", " ");
        local_coordinates = local_coordinates.replaceFirst("\'", " ");
        return local_coordinates;
    }

    /**
     * checkNSEW
     * 
     * Checks to see if there is any NSEW characters and sets the relevant
     * variables to their specific state.
     * It also removes any of these characters from the coordinate string
     * 
     * @param local_coordinate the coordinate
     */
    public void checkNSEW(String local_coordinate){
        if(local_coordinate.contains("N") || local_coordinate.contains("S")){
            latitude = true;
            if(local_coordinate.contains("N")){
                positive = true;
            }
            else{
                positive = false;
            }
            String ns = "[NS]";
            coordinate = coordinate.replaceFirst(ns, "");

        }
        else if(local_coordinate.contains("W") || local_coordinate.contains("E")){
            latitude = false;
            if(local_coordinate.contains("E")){
                positive = true;
            }
            else{
                positive = false;
            }
            String we = "[WE]";
            coordinate = coordinate.replaceFirst(we, "");
        }
        else{}
    }



    /**
     * DMStoDegDec
     * 
     * Converts DMS formatted coordinate to DegDec format
     * 
     * @return The coordinate in DegDec format
     */
    public double DMStoDegDec(){
        double[] dms = new double[3];
        double result = 0;
        if(coordinate.contains("°") && coordinate.contains("\"") && coordinate.contains("\'")){
            coordinate = removeSpaces(coordinate);
            coordinate = replaceSymbols(coordinate);
            checkNSEW(coordinate);
        }
        else if(coordinate.contains(":")){
            coordinate = removeSpaces(coordinate);
            coordinate = coordinate.replace(":", " ");
            checkNSEW(coordinate);

        }
        else if(coordinate.contains(" ")){
            checkNSEW(coordinate);
        }
        else{
            return 0.0;
        }
        try{
            String[] s = coordinate.split(" ");
            
            for(int i = 0; i < 3; i++){
                dms[i] = Double.parseDouble(s[i]);
            }
        }
        catch(NumberFormatException e){
            errorMessage = "Excess Character/s";
            return 0.0;
        }

        if(dms[0] < 0){
            positive = false;
            dms[0] = dms[0]*-1;
        }
        for(int i = 0; i < 3; i++){
            if(i != 0 && dms[i] >= 60){
                errorMessage = "Minutes/Seconds not rounded";
                return 0.0;
            }
            result += (dms[i]/(Math.pow(60, i)));
        }
        if(positive == false){
            result *= -1;
        }
        return result;
    }

    /**
     * DegMintoDegDec
     * 
     * Converts DegMin formatted coordinate to DegDec format
     * 
     * @return The coordinate in DegDec format
     */
    public double DegMintoDegDec(){
        double[] dms = new double[2];
        double result = 0;
        if(coordinate.contains("°")){
            coordinate = removeSpaces(coordinate);
            coordinate = coordinate.replaceFirst("°", " ");
            coordinate = coordinate.replaceFirst("\'", " ");
            checkNSEW(coordinate);
        }
        else if(coordinate.contains(":")){
            coordinate = removeSpaces(coordinate);
            coordinate = coordinate.replace(":", " ");
            checkNSEW(coordinate);

        }
        else if(coordinate.contains(" ")){
            checkNSEW(coordinate);
        }
        else{
            return 0.0;
        }
        try{
            String[] s = coordinate.split(" ");
            for(int i = 0; i < 2; i++){
                dms[i] = Double.parseDouble(s[i]);
            }
        }
        catch(NumberFormatException e){
            errorMessage = "Excess Character/s";
            return 0.0;
        }


        if(dms[0] < 0){
            positive = false;
            dms[0] = dms[0]*-1;
        }
        for(int i = 0; i < 2; i++){
            if(i != 0 && dms[i] >= 60){
                errorMessage = "Minutes/Seconds not rounded";
                return 0.0;
            }
            result += (dms[i]/(Math.pow(60, i)));
        }
        if(positive == false){
            result *= -1;
        }
        return result;
    }

    /**
     * DegDectoDegDec
     * 
     * Does the other relevant calculations required to turn input
     * to output (even when coordinate type is correct for output)
     * 
     * @return The coordinate output format
     */
    public double DegDectoDegDec(){
        double degDec;
        coordinate = removeSpaces(coordinate);
        checkNSEW(coordinate);
        try{
            
            degDec = Double.parseDouble(coordinate);
        }
        catch(NumberFormatException e){
            return 0.0;
        }
        if(!positive){
            degDec = degDec*-1;
        }
        return degDec;
    }

    /**
     * calculate
     * 
     * Determines the type of method to run
     * 
     * @return The coordinate in DegDec format
     */
    public double calculate(){
        if(coordinateType().equals("DMS")){
            return DMStoDegDec();
        }
        else if(coordinateType().equals("DegMin")){
            return DegMintoDegDec();
        }
        else{
            return DegDectoDegDec();
        }
    }
    
}