import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Desktop;

public class Coordinates{
    private static String name;
    private static Scanner sc = new Scanner(System.in);
    private static String errorMessage = null;
    private static double[] coordinateArray = new double[2];
    private static String[] outputCoordinates = new String[2];
    private static ArrayList<String> features = new ArrayList<String>();
    private static boolean isAllTogether = false;
    public static void main(String[] args){
        inputType();
        
        if(!(features.isEmpty())){
            createFile(createFeatureCollection());
            printToWeb();
        }
        else{
            System.out.println("No coordinates entered");
        }
    }

    /**
     * toSixDP
     * 
     * Turns a double number into 6 dp
     * 
     * @param local_coordinate the coordinate to be rounded
     * @return The rounded double
     */
    public static String toSixDP(double local_coordinate){
        DecimalFormat df = new DecimalFormat("0.000000");
        return df.format(local_coordinate);
    }

    /**
     * inputType
     * 
     * Asks the uer how they would like to input data and allows for
     * input in different ways based on their choice
     * 
     */
    public static void inputType(){
        String more;
        do{
            more = answer("Would you like to input features all together or individually (All/Indv)?");
        }while(!(more.equals("All") || more.equals("Indv")));
        if(more.equals("All")){
            String[] stringArray;
            String currentInput;
            isAllTogether = true;
            System.out.println("Please enter the data in the form \'coordinate1, coordinate2, name\'");
            System.out.print("When you are finished inputting type DONE\n");
            while(true){
                currentInput = sc.nextLine();
                if(currentInput.equals("DONE")){
                    break;
                }
                currentInput = currentInput.trim();
                stringArray = split(currentInput);
                if(errorMessage != null){
                    System.out.println("Unable to proces: " + currentInput + "\n" + errorMessage);
                    System.out.println("Please input in the form \'coordinate1, coordinate2, name\'");
                    errorMessage = null;
                    continue;
                }
                coordinateArray = isValid(stringArray);
                if(errorMessage != null){
                    System.out.println("Unable to proces: " + currentInput + "\n" + errorMessage);
                    System.out.println("Please input in the form \'coordinate1, coordinate2, name\'");
                    errorMessage = null;
                    continue;
                }
                outputCoordinates[0] = toSixDP(coordinateArray[0]);
                outputCoordinates[1] = toSixDP(coordinateArray[1]);
                features.add(createFeature(outputCoordinates));
            }
        }
        else if(more.equals("Indv")){
            do {
                name();
                coordinateArray = coordinates();
                outputCoordinates[0] = toSixDP(coordinateArray[0]);
                outputCoordinates[1] = toSixDP(coordinateArray[1]);
                features.add(createFeature(outputCoordinates));
            }while(cont() == true);
        }
        else{
            System.out.println("Invalid Answer");
        }
        

    }

    /**
     * cont
     * 
     * Checks whether the user want's to continue inputting coordinates
     * 
     * @return The users responce (in boolean form)
     */
    public static boolean cont(){
        while(true){
            String more = answer("Would you like to output more coordinates (y/n)?");
            if(more.equals("y")){
                return true;
            }
            else if(more.equals("n")){
                return false;
            }
            else{
                System.out.println("Invalid Answer");
            }
        }
    }

    /**
     * name
     * 
     * A method that asks if the user wants a name for their feature,
     * and then asks for the name if they do (and sets name = input)
     */
    public static void name(){
        String confirmation = answer("Would you like a name for your coordinates (y/n)?");
        if(confirmation.equals("y")){
            name = answer("What would you like your name to be?");
            return;
        }
        else if(confirmation.equals("n")){
            return;
        }
        else{
            System.out.println("Invalid responce");
            name();
        }
    }

    /**
     * coordinates
     * 
     * Asks the user for the coordinates they wish to use, checks
     * if valid, returns if is, otherwise asks again
     * 
     * @return the inputted coordinates in standard form
     */
    public static double[] coordinates(){
        String[] coordinates = new String[2];
        while(true){
            if(errorMessage != null){
                System.out.println("Invalid Coordinates: " + errorMessage);
                errorMessage = null;
            }
            String input = answer("Please enter valid coordinates:");
            input = input.trim();
            //split the input into a string array:

            coordinates = split(input);
            if(errorMessage != null || coordinates == null){
                continue;
            }
            else if(isValid(coordinates) == null){
                 continue;
            }
            else{
                
                return isValid(coordinates);
            }
        }
    }

    /**
     * answer
     * 
     * Returns the users input from a question
     * 
     * @param question The question to be printed
     * @return the users answer
     */
    public static String answer(String question){
        System.out.println(question);
        return sc.nextLine();
    }
    
    /**
     * split
     * 
     * Splits the input into each of the two coordinates
     * Checks validity along the way
     * 
     * @param input users input
     * @return the two coordinates (unless error, in which case null)
     */
    public static String[] split(String input){
        String[] result = input.split(",");
        //first case, ',' used as seperator
        if(result.length == 2){
            return result;
        }
        else if(result.length == 3 && isAllTogether){
            name = result[2];
            String[] temp = new String[2];
            temp[0] = result[0];
            temp[1] = result[1];
            return temp;
        }
        else if(result.length != 1){
            errorMessage = "Incorrect number of arguments";
            return null;
        }

        //second case, ' ' used as seperator, with no extra spaces
        result = input.split(" ");
        
        if(result.length == 2){
            return result;
        }
        else if(result.length == 3 && isAllTogether){
            name = result[2];
            String[] temp = new String[2];
            temp[0] = result[0];
            temp[1] = result[1];
            return temp;
        }

        //third case, spaces seperating everything + NSEW coordinates
        if(locations(input) != null){
            result = new String[2];
            int[] locationsNSEW = locations(input);
            if(locationsNSEW[1] == 0){
                errorMessage = "Inconsistent arguments";
                return null;
            }
            if(locationsNSEW[0] == 0 && !isAllTogether){
                result[0] = input.substring(0, locationsNSEW[1] - 1);
                result[1] = input.substring(locationsNSEW[1]);
            }
            else if(isAllTogether){
                result[0] = input.substring(0, locationsNSEW[0] + 1);
                result[1] = input.substring(locationsNSEW[0] + 2, locationsNSEW[1]+1);
                name = input.substring(locationsNSEW[0] + 2, locationsNSEW[1]+1);

            }
            else{
                result[0] = input.substring(0, locationsNSEW[0] + 1);
                result[1] = input.substring(locationsNSEW[0] + 2);
            }
            return result;
        }

        //fourth case, spaces seperating everything + no NSEW + correct DMS characters
        if(input.contains("\'")){
            int first = input.indexOf("\'");
            int second = input.indexOf("\'", first + 1);
            int check = input.indexOf("\'", second + 1);
            if(first != -1 && second != -1 && check == -1){
                result[0] = input.substring(0, input.indexOf("\'") + 1);
                result[1] = input.substring(input.indexOf("\'") + 2, input.indexOf("\'") + 1);
                name = input.substring(input.indexOf("\'") + 2);
            }
            return result;
        }
        errorMessage = "Invalid formatting";
        return null;
    }

    /**
     * locations
     * 
     * Outputs the locations of NSEW in the string
     * 
     * @param input users input
     * @return the NSEW locations (if relevant, else null)
     */
    public static int[] locations(String input){
        int count = 0;
        int[] locations = new int[2];
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == 'N' || input.charAt(i) == 'S' || input.charAt(i) == 'W' || input.charAt(i) == 'E'){
                locations[count] = i;
                count++;
            }
            if(count == 3){
                errorMessage = "To many directional characters";
                coordinates();
                return null;
            }
        }
        //No directional character
        if(count == 0){
            return null;
        }
        return locations;
    }


    /**
     * isValid
     * 
     * Checks validity of coordinates in various ways and converts
     * the coordinates to standard form using various methods
     * 
     * @param local_coordinates the inputted coordinates seperated
     * @return the standardized coordinates (if valid, else null)
     */
    public static double[] isValid(String[] local_coordinates){
        double[] standardCoordinates = new double[2];
        Conversion coordinate1 = new Conversion(local_coordinates[0]);
        Conversion coordinate2 = new Conversion(local_coordinates[1]);
        coordinate1.setLatitude(true);
        coordinate2.setLatitude(false);

        if(coordinate1.getErrorMessage() != null){
            errorMessage = coordinate1.getErrorMessage();
            return null;
        }
        else if(coordinate2.getErrorMessage() != null){
            errorMessage = coordinate2.getErrorMessage();
            return null;
        }

        if(coordinate1.coordinateType() != coordinate2.coordinateType()){
            errorMessage = "Type mismatch";
            return null;
        }
        
        standardCoordinates[0] = coordinate1.calculate();
        if(!coordinate1.getLatitude()){
            coordinate2.setLatitude(true);
        }
        standardCoordinates[1] = coordinate2.calculate();

        if(coordinate1.getLatitude() == coordinate2.getLatitude()){
            errorMessage = "Same directional type";
            return null;
        }

        if(!coordinate1.getLatitude()){
            double temp = standardCoordinates[0];
            standardCoordinates[0] = standardCoordinates[1];
            standardCoordinates[1] = temp;
        }

        if(coordinate1.getErrorMessage() != null){
            errorMessage = coordinate1.getErrorMessage();
            return null;
        }
        else if(coordinate2.getErrorMessage() != null){
            errorMessage = coordinate2.getErrorMessage();
            return null;
        }
        else{
            if(!(standardCoordinates[0] <= 90 && standardCoordinates[0] >= -90) ||
            !(standardCoordinates[1] <= 180 && standardCoordinates[1] >= -180)){
                errorMessage = "Coordinate/s out of range";
                return null;
            }
            return standardCoordinates;
        }

    }

    /**
     * createFeature
     * 
     * Creates a GeoJSON feature with the current name and coordinates
     * 
     * @return the feature, in the form of a string
     */
    public static String createFeature(String[] local_coordinates){
        String geoJson;
        if(coordinateArray == null){
            return null;
        }
        local_coordinates = swapCoordinates(local_coordinates);
        if(name != null){
            geoJson = "    {\n" +
                    "      \"type\": \"Feature\",\n" +
                    "      \"geometry\": {\n" +
                    "        \"type\": \"Point\",\n" +
                    "        \"coordinates\": [" + local_coordinates[0] + ", " + local_coordinates[1] + "]\n" +
                    "      },\n" +
                    "      \"properties\": {\n" +
                    "        \"name\": \"" + name + "\"\n" +
                    "      }\n" +
                    "    }";
            name = null;
        }
        else{
            geoJson = "    {\n" +
                    "      \"type\": \"Feature\",\n" +
                    "      \"geometry\": {\n" +
                    "        \"type\": \"Point\",\n" +
                    "        \"coordinates\": [" + local_coordinates[0] + ", " + local_coordinates[1] + "]\n" +
                    "      },\n" +
                    "      \"properties\": {}\n" +
                    "    }";
        }
        return geoJson;
    }

    public static String[] swapCoordinates(String[] local_coordinates){
        String temp = local_coordinates[0];
        local_coordinates[0] = local_coordinates[1];
        local_coordinates[1] = temp;
        return local_coordinates;
    }

    /**
     * createFeatureCollection
     * 
     * Combines the features into a feature collection
     * 
     * @return the featurecollection, in the form of a string
     */
    public static String createFeatureCollection(){
        String allFeatures = "";
        for(int i = 0; i < features.size(); i++){
            if(i == features.size()-1){
                allFeatures = allFeatures + features.get(i) + "\n";
            }
            else{
                allFeatures = allFeatures + features.get(i) + ",\n";
            } 
        }
        String featureCollection = "{\n" +
                "  \"type\": \"FeatureCollection\",\n" +
                "  \"features\": [\n" + allFeatures +
                "  ]\n" +
                "}";
        return featureCollection;

    }

    /**
     * createFile
     * 
     * Creates a GeoJSON file with the features provided
     * 
     * @param featureCollection the featurecollection to be outputted
     */
    public static void createFile(String featureCollection){
        try(FileWriter fw = new FileWriter("featurecollection.geojson")){
            fw.write(featureCollection);
        }
        catch(IOException e){
            System.out.println("Error creating file");
        }
    }

    /**
     * printToWeb
     * 
     * Locates the geoJSON file and opens a url in the desktop that represents
     * that collection on geojson.io
     */
    public static void printToWeb(){
        String fileData = "featurecollection.geojson";
        String data = getEncodedURL(fileData);
        try{
            String url = "http://geojson.io/#data=data:application/json," + data;
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e){
            e.printStackTrace();
        }
        
    }

    public static String getEncodedURL(String fileName){
        String finalData = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            StringBuilder data = new StringBuilder();
            
            while ((line = reader.readLine()) != null){
                data.append(line);
            }
            finalData = data.toString();
        } catch (IOException e){
            e.printStackTrace();
        }
        finalData = finalData.replace("/", "%2F");
        finalData = finalData.replace(":", "%3A");
        finalData = finalData.replace(" ", "%20");
        finalData = finalData.replace("\"", "%22");
        finalData = finalData.replace("{", "%7B");
        finalData = finalData.replace("}", "%7D");
        finalData = finalData.replace("[", "%5B");
        finalData = finalData.replace("]", "%5D");

        return finalData;

    }
}
