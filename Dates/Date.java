import java.lang.System;

/**
 * Date class takes a date and formats it if valid or prints
 * out the invalid date with ' - INVALID'
 *
 * @author Curtis Mellsop
 */
public class Date {
    private int day;
    private String month;
    private int year;
    private String originalDate;
    private boolean success = true;
    private final int MIN_YEAR = 1753;
    private final int MAX_YEAR = 3000;

    //String of valid months to be returned
    private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    //default constructor
    public Date(){}

    /**
     * Constructor
     * 
     * @param date Date to be checked and formatted
     */
    public Date(String date){
        this.originalDate = date;
    }

    /**
     * DataExtraction method does all the main validity checks
     * 
     * It checks for the correct length and correct sperators
     * It also splits the date into its three components, day, month
     * and year
     */
    public void DataExtraction(){
        String seperator = "error";
        if(originalDate.length() < 5){
            invalid("Date length too short");
            return;
        }
        //figures out the seperator
        for(int i = 1; i < 4; i++){
            if(originalDate.charAt(i) == '-' || originalDate.charAt(i) == '/' || originalDate.charAt(i) == ' '){
                seperator = Character.toString(originalDate.charAt(i));
            }
        }
        //if no seperator
        if(seperator.equals("error")){
            invalid("Invalid Seperator");
            return;
        }
        
        //splits date into components
        String[] figures = originalDate.split(seperator, 3); 

        //if invalid amount of argument in date
        if(figures.length < 3){
            invalid("Too few arguments");
            return;
        }
        else if(figures.length > 3){
            invalid("Too many arguments");
            return;
        }

        //checks validity of year and formats it
        year = yearCalculator(figures[2]);
        //checks validity of month and formats it
        month = monthCalculator(figures[1]);
        //checks validity of day and formats it
        day = dayCalculator(figures[0]);
        //if no errors have been called
        if(success){
            System.out.println(String.format("%02d", day) + " " + month + " " + year);
        }

    }

    /**
     * invalid method
     * 
     * If an invalid date is detected this method is called, it then
     * prints out the original date followed by ' - INVALID' and it
     * prints the error message to stderr.
     * It should only do this once, thus why the if statement is included
     *
     * @param error the error message to be printed to stderr
     */
    public void invalid(String error){
        if(success){
            System.out.println(originalDate + " - INVALID");
            System.err.println(error);
            success = false;
        }
    }

    /**
     * dayCalculator
     * 
     * Checks the day is valid and returns it if it is
     *
     * @param stringDay the day in the form of a String
     * @return the day, if valid, else null
     */
    public int dayCalculator(String stringDay){
        int localDay = 0;
        //checks that the day is a number
        if(!(stringDay.length() == 2 || stringDay.length() == 1)){
            invalid("Invalid day length");
            return 0;
        }
        try{
            localDay = Integer.parseInt(stringDay);
        }catch(NumberFormatException e){
            invalid("Day not a number");
            return 0;
        }

        if(validDay(localDay)){
            return localDay;
        }
        else{
            invalid("Day out of range");
            return 0;
        }
    }


    /**
     * validDay
     * 
     * Checks the validity of the day for given months
     *
     * @param localDay the day
     * @return true, if valid, false, if not
     */
    public boolean validDay(int localDay){
        switch(month){
            case "Jan", "Mar", "May", "Jul", "Aug", "Oct", "Dec":
                if(localDay <= 31 && localDay > 0){
                    return true;
                }
                break;
            case "Apr", "Jun", "Sep", "Nov":
                if(localDay <= 30 && localDay > 0){
                    return true;
                }
                break;
            case "Feb":
                if(localDay <= 28 && localDay > 0){
                    return true;
                }
                else if(localDay == 29){
                    return leapYearCheck();
                }
                break;
        }
        return false;

    }

    /**
     * leapYearCheck
     * 
     * Checks whether the global variable year is a leap year
     *
     * @return true, if leap year, false, if not
     */
    public boolean leapYearCheck(){
        if(year % 4 == 0 && year % 100 != 0){
            return true;
        }
        else if(year % 4 == 0 && year % 400 == 0){
            return true;
        }
        else{
            invalid("Not a leap year");
            return false;
        }
    }

    /**
     * monthCalculator
     * 
     * Checks the valditity of the month and formats it if valid
     *
     * @param localMonth the inputted month in the form of a String
     * @return if valid, the month formatted correctly, else null
     */
    public String monthCalculator(String localMonth){
        boolean isNumber = false;
        int monthInt = 0;
        
        //checks if month is in number format or test format
        try{
            monthInt = Integer.parseInt(localMonth);
            isNumber = true;
        }catch(NumberFormatException e){
            isNumber = false;
        }

        if(isNumber && !(localMonth.length() == 1 || localMonth.length() == 2)){
            invalid("Invalid month length");
            return "";
        }

        //if in number format, check validity and return month in the specific position of the array
        if(isNumber){
            if(monthInt <= 12 && monthInt > 0){
                return(months[monthInt - 1]);
            }
            else{
                invalid("Month out of range");
                return "";
            }
        }

        if(validMonth(localMonth)){
            return toTitleCase(localMonth);
        }

        return "";
    }

    /**
     * validMonth
     * 
     * Calculates the valditity of the month
     *
     * @param monthCheck the inputted month to be checked
     * @return if valid, true, else false
     */
    public boolean validMonth(String monthCheck){
        
        if(monthCheck.length() != 3){ //checks to make sure valid month length
            invalid("Invalid Month length");
            return false;
        }

        //Checks to make sure valid formatting
        if(!(Character.isUpperCase(monthCheck.charAt(0)) || //first case
            (Character.isLowerCase(monthCheck.charAt(0)) && //second case
            Character.isLowerCase(monthCheck.charAt(1)) &&
            Character.isLowerCase(monthCheck.charAt(2))))){
                invalid("Invalid Month formatting");
                return false;
        }

        monthCheck = toTitleCase(monthCheck);

        //then compare formatted month to correct months
        for(int i = 0; i < months.length; i++){
            if(monthCheck.equals(months[i])){
                return true;
            }
        }

        invalid("Invalid Month name");
        return false;
    }

    /**
     * toTitleCase
     * 
     * Converts a string to title case
     *
     * @param monthCheck the string to be converted
     * @return a titlecased String of the parameter
     */
    public String toTitleCase(String month){
        String firstLetter = month.substring(0, 1).toUpperCase();
        String rest = month.substring(1).toLowerCase();
        return firstLetter + rest;
    }

    /**
     * yearCalculator
     * 
     * Checks the valditity of the year and returns it (with formatting if required)
     *
     * @param stringYear the year in the form of a String
     * @return The (formatted) year if valid, else 0
     */
    public int yearCalculator(String stringYear){
        int localYear = 0;

        //checks if year is in the format yy or yyyy
        if(stringYear.length() != 2 && stringYear.length() != 4){
            invalid("Incorrect year length");
            return 0;
        }

        //checks that year is a number
        try{
            localYear = Integer.parseInt(stringYear);
        }catch(NumberFormatException e){
            invalid("Year not a number");
            return 0;
        }

        //if year is 4 digit, check that it is in range
        if(stringYear.length() == 4){
            if(localYear >= MIN_YEAR && localYear <= MAX_YEAR){
                return localYear;
            }
            else{
                invalid("Year out of range");
                return 0;
            }
        }
        //if digit is 2 digit, check to see what century it should be
        else{
            if(localYear < 50 && localYear >= 0){
                return localYear + 2000;
            }
            else if(localYear >= 50 && localYear < 100){
                return localYear + 1900;
            }
            else{
                invalid("Year out of range");
                return 0;
            }
        }
    }
}
