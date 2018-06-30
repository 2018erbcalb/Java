/**
 * This is a Java Program used to input Excersizes, Weights, Reps, and Sets
 *  ...into a workout log
 *  
 *  @caleberb
 *  v1.0.1
 */
import java.io.FileWriter;
import java.util.*;
import java.time.*;
public class WorkoutLog {
    public static void main(String[] args) throws Exception {
        // Create scanner object
        Scanner scan = new Scanner(System.in);
        
        // Prompts user for their name
        System.out.print("Please input your name: ");
        String name = scan.nextLine();
        
        //Prompts for username of computer user for file path purposes
        System.out.print("Please input the username of the current logged in user: ");
        String usrName = scan.nextLine();
        
        //Prompts the user whether they want to open/write current or past log
        int usrOper = promptOper(scan);
        
        //Follows through with the users prompt
        switch(usrOper){
            case 1: 
                // Open today
                openToday(scan, name, usrName);
                break;
            case 2: 
                // Write today
                writeToday(scan, name, usrName);
                break;
            case 3: 
                // Open past
                System.out.print("Input date in YYYY-DD-MM format: ");
                String date = scan.nextLine();
                openPast(scan, name, usrName, date);
                break;
            case 4: 
                // Write past
                System.out.print("Input date in YYYY-DD-MM format: ");
                date = scan.nextLine();
                writePast(scan, name, usrName, date);
                break;
            default: 
                break;
        }
        
        //Returns home after program ends if user wishes
        System.out.println("Return home? (y/n): ");
        String returnHome = scan.nextLine();
        returnHome = returnHome.toLowerCase();
        switch(returnHome){
            //
            case "y":
                main(args);
                break;
            case "n":
                break;
        }
    }
    
    /**
     * @param Scanner
     * Lists the availible functions and asks the user for input
     * @return int
     */
    private static int promptOper(Scanner scan){
        //Prompts the user whether they want to open/write current or past log
        System.out.println("Please select an option");
        System.out.println("1) Open today's log");
        System.out.println("2) Write today's log");
        System.out.println("3) Open a past day's log");
        System.out.println("4) Write a past day's log");
        int usrOper = scan.nextInt();
        int oper = 0;
        switch(usrOper){
            case 1:
                oper = 1;
                break;
            case 2:
                oper = 2;
                break;
            case 3:
                oper = 3;
                break;
            case 4:
                oper = 4;
                break;
            default:
                System.out.println("Error! Invalid Option. Please enter a valid option: ");
                usrOper = promptOper(scan);
        }
        return usrOper;
    }
    
    /**
     * @param Scanner
     * @param String
     * @param String
     * @param String
     * Reads the file for today and prints it out on screen
     * @return void
     */
    private static void openToday (Scanner scan, String name, String usrName) {
        String fileLoc = promptOpSys(scan, name, usrName, "");
        CSVUtils.readFile(fileLoc);
    }
    
    /**
     * @param Scanner
     * @param String
     * @param String
     * @param String
     * Asks the user for input and writes their data into today's log
     * @return void
     */
    private static void writeToday (Scanner scan, String name, String usrName) throws java.io.IOException{
        // Prompts user for number of exercises
        System.out.println("Please input the number of excerises you will be logging for the day: ");
        int numOfEx = scan.nextInt();
        String throwAway = scan.nextLine(); //Only included because next scan after numOfEx will not ask input
        
        // Creates CSV file and location based on operating system
        String csvFile = promptOpSys(scan, name, usrName, "");
        
        // Selects created file as file to write to
        FileWriter writer = new FileWriter(csvFile);
        
        // Create and add headers to the CSV file
        ArrayList<String> headers = new ArrayList<String>();
        headers.add("Exercise");
        headers.add("Weight");
        headers.add("Reps");
        headers.add("Sets");
        CSVUtils.writeLine(writer, headers);
        
        /*
         * Continuously asks user for input
         * Adds input to ArrayList "input"
         * Writes "input" to CSV
         * Clears "input"
         */
        ArrayList<String> input = new ArrayList<String>();
        for(int i = 0; i < numOfEx; i++){
            System.out.println("\n");
            System.out.print("Exercise: ");
            String ex = scan.nextLine();
            System.out.print("Weight: ");
            String we = scan.nextLine();
            System.out.print("Reps: ");
            String re = scan.nextLine();
            System.out.print("Sets: ");
            String se = scan.nextLine();
            input.add(ex);
            input.add(we);
            input.add(re);
            input.add(se);
            CSVUtils.writeLine(writer, input);
            input.remove(0);
            input.remove(0);
            input.remove(0);
            input.remove(0);
        }
        
        // Close the file writer and end the program
        writer.flush();
        writer.close();
    }
    
    /**
     * @param Scanner
     * @param String
     * @param String
     * @param String
     * Reads the file for a given date and prints it out on screen
     * @return void
     */
    private static void openPast (Scanner scan, String name, String usrName, String date){    
        String fileLoc = promptOpSys(scan, name, usrName, date);
        CSVUtils.readFile(fileLoc);
    }
    
    /**
     * @param Scanner
     * @param String
     * @param String
     * @param String
     * Asks the user for input and writes their data into the log for the given date
     * @return void
     */
    private static void writePast (Scanner scan, String name, String usrName, String date) throws java.io.IOException{
        // Prompts user for number of exercises
        System.out.println("Please input the number of excerises you will be logging for the day: ");
        int numOfEx = scan.nextInt();
        String throwAway = scan.nextLine(); //Only included because next scan after numOfEx will not ask input
        
        // Creates CSV file and location based on operating system
        String csvFile = promptOpSys(scan, name, usrName, date);
        
        // Selects created file as file to write to
        FileWriter writer = new FileWriter(csvFile);
        
        // Create and add headers to the CSV file
        ArrayList<String> headers = new ArrayList<String>();
        headers.add("Exercise");
        headers.add("Weight");
        headers.add("Reps");
        headers.add("Sets");
        CSVUtils.writeLine(writer, headers);
        
        /*
         * Continuously asks user for input
         * Adds input to ArrayList "input"
         * Writes "input" to CSV
         * Clears "input"
         */
        ArrayList<String> input = new ArrayList<String>();
        for(int i = 0; i < numOfEx; i++){
            System.out.println("\n");
            System.out.print("Exercise: ");
            String ex = scan.nextLine();
            System.out.print("Weight: ");
            String we = scan.nextLine();
            System.out.print("Reps: ");
            String re = scan.nextLine();
            System.out.print("Sets: ");
            String se = scan.nextLine();
            input.add(ex);
            input.add(we);
            input.add(re);
            input.add(se);
            CSVUtils.writeLine(writer, input);
            input.remove(0);
            input.remove(0);
            input.remove(0);
            input.remove(0);
        }
        
        // Close the file writer and end the program
        writer.flush();
        writer.close();
    }
    
    /**
     * @param Scanner
     * @param String
     * @param String
     * @param String
     * Asks the user for what operating system they are using in order to 
     *    Establish which file navigation system to use.
     * @return String
     */
    private static String promptOpSys(Scanner scan, String name, String usrName, String date){
        System.out.println("Enter your operating system (linux, windows, mac): ");
        String tempOpSys = scan.nextLine();
        String opSys = tempOpSys.toLowerCase();
        String csvFile = "";
        if(date.equals("")){
            switch(opSys){
                case "linux": 
                    csvFile = "/home/" + usrName + "/Desktop/Workout/" + name + " - " + LocalDate.now() + ".csv";
                    break;
                case "windows":
                    csvFile = "C:/Users/" + usrName + "/Desktop/Workout/" + name + " - " + LocalDate.now() + ".csv";
                    break;
                case "mac":
                    csvFile = "/Users/" + usrName + "/Desktop/Workout" + name + " - " + LocalDate.now() + ".csv";
                    break;
                default: 
                    System.out.println("Error! Invalid Option. Please enter a valid option: ");
                    csvFile = promptOpSys(scan, name, usrName, date);
            }
        } else {
            switch(opSys){
                case "linux": 
                    csvFile = "/home/" + usrName + "/Desktop/Workout/" + name + " - " + date + ".csv";
                    break;
                case "windows":
                    csvFile = "C:/Users/" + usrName + "/Desktop/Workout/" + name + " - " + date + ".csv";
                    break;
                case "mac":
                    csvFile = "/Users/" + usrName + "/Desktop/Workout" + name + " - " + date + ".csv";
                    break;
                default: 
                    System.out.println("Error! Invalid Option. Please enter a valid option: ");
                    csvFile = promptOpSys(scan, name, usrName, date);
            }
        }
        return csvFile;
    }
}