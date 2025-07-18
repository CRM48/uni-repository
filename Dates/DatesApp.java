import java.util.Scanner;

public class DatesApp{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String date = sc.nextLine();
            Date dt = new Date(date);
            dt.DataExtraction();
        }
        sc.close();
    }
}