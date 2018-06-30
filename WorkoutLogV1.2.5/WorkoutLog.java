/*
 * This is a Java Program used to input Excersizes, Weights, Reps, and Sets
 *  ...into a workout log
 *  
 *  @caleberb
 *  v2.1.6
 */
// import statments
import java.io.*;
import java.io.File.*;
import java.nio.file.*;
import java.util.*;
import java.time.*;
import java.time.format.*;

public class WorkoutLog {
    // Creates static Scanner Objects for every class to have access to
    static Scanner scan = new Scanner(System.in);
    static Scanner intScan = new Scanner(System.in);
    
    /**
     * @param args
     * Initializes the constant variables used throughout the class
     * They are not changed by any part of the code
     * @return void
     */
    public static void main(String[] args) throws Exception {
        // Converts Object LocalDate to String in format MM-DD-YYYY
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-d-yyyy");
        String date = localDate.format(formatter);
        
        // The variable logSign is a variable that will take input from the user and decide whether to
        // Log in, sign out, or quit
        int logSign = 0;
        println("Please choose an option: ");
        println("1) Log in");
        println("2) Sign up");
        println("3) Quit");
        
        // Ensure the input is an integer
        if(intScan.hasNextInt()){
            logSign = intScan.nextInt();
        }
        switch(logSign){
            case 1:
                // Resets value to zero for next use
                logSign = 0;
                // Logs the user in
                logIn(date, args);
                break;
            case 2:
                // Resets value to zero for next use
                logSign = 0;
                // Signs the user up
                signUp(args);
                main(args);
                break;
            case 3:
                // Resets value to zero for next use
                logSign = 0;
                // Quits the program
                System.exit(0);
            default:
                // Resets value to zero for next use
                logSign = 0;
                // Default option, gives error, calls recursively
                println("Error. Please enter a valid option.");
                intScan.next();
                main(args);
                break;
        }
    }
    
    /**
     * @param String
     * @param String[]args
     * Acts as a log in screen for the user, passing them to the home method
     * @return void
     */
    private static void logIn(String date, String[] args) throws Exception{
        // Sets the usrList CSV to wherever the java class is stored
        String csvFile = sysCheckDir();
        csvFile += "usrList.CSV";
        String passHash = "";
        
        // Prompts user for their username
        print("Please input your username or \'000\' to cancel: ");
        String uName = scan.nextLine();
        
        // Quits if user enters '000'
        if(uName.equals("000")){
            main(args);
        }
        
        // If the user exists, retrive the passHash
        // If not, give an error and recursively call itself
        if(usrCheck(csvFile, uName)){
            passHash = passCheck(csvFile, uName);
        } else {
            println("Error. Username does not exist. Try logging in again.");
            logIn(date, args);
        }
        
        // Prompts the user for their password
        print("Please input your password: ");
        String pWord = scan.nextLine();
        
        // Decrypts the passHash retrives from the usrList file
        String decryptedPass = Protected.decryptPass(passHash);
        
        // If the user entered password matches the one stores on file
        // Proceed to home
        // If not, give error statement and call recursively
        if(pWord.equals(decryptedPass)){
            // Enter main program ... not to be confused with main method
            home(uName, date, args);
        } else {
            println("Error. Incorrect Password. Try logging in again.");
            logIn(date, args);
        }
    }
    
    /**
     * @param String
     * @param String
     * Checks if the inputted username exists in the user log
     * @return boolean
     */
    private static boolean usrCheck(String csvFile, String usrName) throws Exception{
        // Retrives user list from usrList File
        String[][] usrListArr = CSVUtils.readFile(csvFile);
        
        // usrExists is set to false until it is found
        boolean usrExists = false;
        
        // Search through the first index of every row to see if the 
        // Entered username matches the stored one
        for(int i = 0; i < usrListArr.length; i++){
            if(usrListArr[i][0].equals(usrName)){
                usrExists = true;
            }
        }
        return usrExists;
    }
    
    /**
     * @param String
     * @param String
     * Retrieves the passWord hash from the usrList CSV file
     * @return String
     */
    private static String passCheck(String csvFile, String usrName) throws Exception{
        // Retrives user list from usrList File
        String[][] usrListArr = CSVUtils.readFile(csvFile);
        String pass = "";
        
        // Search through the second index of every row to 
        // Retrieve the passHash stored with the given username
        for(int i = 0; i < usrListArr.length; i++){
            if(usrListArr[i][0].equals(usrName)){
                pass = usrListArr[i][1];
            }
        }
        return pass;
    }
    
    /**
     * @param String
     * @param String
     * @param String[]args
     * Acts as the main method, an calls itself recursively until user quits
     * @return void
     */
    private static void home(String name, String todayDate, String[] args) throws Exception{
        // Prompts the user whether they want to open/write current or past log
        int usrOper = promptOper();
        
        //Follows through with the users prompt
        switch(usrOper){
            case 1: 
                // Open today
                openLog(scan, name, todayDate);
                break;
            case 2: 
                // Write today
                writeLog(scan, intScan, name, todayDate);
                break;
            case 3: 
                // Open past with given date
                print("Input date in MM-DD-YYYY format: ");
                String openPastDate = scan.nextLine();
                openLog(scan, name, openPastDate);
                break;
            case 4: 
                // Write past with given date
                print("Input date in MM-DD-YYYY format: ");
                String writePastDate = scan.nextLine();
                writeLog(scan, intScan, name, writePastDate);
                break;
            case 5:
                // Logs out and returns to main
                main(args);
                break;
            default: 
                break;
        }
        
        // Returns home after program ends if user wishes
        print("\nReturn home? (y/n): ");
        String returnHome = scan.nextLine();
        returnHome = returnHome.toLowerCase();
        switch(returnHome){
            case "y":
                // Returns
                home(name, todayDate, args);
                break;
            case "n":
                //Closes
                break;
            default: 
                // Returns to home if user enters invalid option
                println("Invalid input. Returning to home...");
                home(name, todayDate, args);
        }
    }
    
    /**
     * Lists the availible functions and asks the user for input
     * @return int
     */
    private static int promptOper(){
        //Prompts the user whether they want to open/write current or past log
        println("Please select an option");
        println("1) Open today's log");
        println("2) Write today's log");
        println("3) Open a past day's log");
        println("4) Write a past day's log");
        println("5) Log out");
        
        //Initializes variables
        int usrOper = 0;
        int oper = 0;
        
        // Ensures user enters an int
        if(intScan.hasNextInt()){
            usrOper = intScan.nextInt();
        } else {
            println("Error! Invalid Option. Please enter a valid option: ");
            usrOper = promptOper();
        }
        
        // Sets the 'oper' int to reflect which operation was chosen by the user
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
            case 5:
                oper = 5;
                break;
            default:
                println("Error! Invalid Option. Please enter a valid option: ");
                usrOper = promptOper();
        }
        return oper;
    }
    
    /**
     * @param String
     * @param String
     * Reads the file for today and prints it out on screen
     * @return void
     */
    private static void openLog (Scanner scan, String name, String date) throws java.io.IOException{
        // Locates appropriate file path and stores it in csvFile
        String csvFile = sysCheck(name, date);
        
        // Checks if the log file exists or not
        if(checkLogExists(csvFile)){
            // If log file exists, read the file
            String arr[][] = CSVUtils.readFile(csvFile);
            println("");
            for(int i = 0; i < arr.length;i++){
                for(int j = 0; j < arr[i].length; j++){
                    if(j == arr[i].length - 1){
                        print(arr[i][j] + "");
                    } else {
                        print(arr[i][j] + " : ");
                    }
                }
                println("");
            }
        }
        
        // Checks if the log file exists or not
        if(!checkLogExists(csvFile)){
            // If the log file does not exist, print error and end
            println("Sorry. There is no log for the given date and user.");
        }
    }
    
    /**
     * @param String
     * @param String
     * Asks the user for input and writes their data into today's log
     * @return void
     */
    private static void writeLog (Scanner scan, Scanner intScan, String name, String date) throws java.io.IOException{
        // Prompts user for number of exercises
        println("Please input the number of excerises you will be logging for the day: ");
        int numOfEx = intScan.nextInt();
        
        // Creates CSV file and location based on operating system
        String csvFile = sysCheck(name, date);
        
        // Checks if the log file exists or not
        if(!checkLogExists(csvFile)){
            // Creates file if it did not exist previously
            createLog(csvFile);
        }
        
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
         * Adds input to ArrayList 'input'
         * Writes 'input' to FileWriter
         * Clears 'input'
         */
        ArrayList<String> input = new ArrayList<String>();
        for(int i = 0; i < numOfEx; i++){
            // Prompts for input
            println("\n");
            print("Exercise: ");
            String ex = scan.nextLine();
            print("Weight: ");
            String we = scan.nextLine();
            print("Reps: ");
            String re = scan.nextLine();
            print("Sets: ");
            String se = scan.nextLine();
            
            // Adds input to ArrayList
            input.add(ex);
            input.add(we);
            input.add(re);
            input.add(se);
            
            // Writes ArrayList to CSV file
            CSVUtils.writeLine(writer, input);
            input.remove(0);
            input.remove(0);
            input.remove(0);
            input.remove(0);
        }
        
        // Exports all date in the FileWrite Object to the CSV file
        writer.flush();
        
        // Close the file writer and ends method
        writer.close();
    }
    
    /**
     * @param String
     * @param String
     * Asks the user for what operating system they are using in order to 
     *    Establish which file navigation system to use.
     * @return String
     */
    private static String sysCheck(String name, String date) throws java.io.IOException{
        // Gets first part of file directory (the location of the Workout folder on the Desktop)
        String csvFile = sysCheckDir();
        
        // Sets csvFile to path of full CSV log file
        csvFile += name + " - " + date + ".csv";
        return csvFile;
    }
    
    /**
     * Determines correct path to the 'Workout' directory on the user's desktop
     * @return String
     */
    private static String sysCheckDir() throws java.io.IOException{
        
        
        // Reads the system for the computer user for file path purposes
        String sysUsrName = System.getProperty("user.name");
        
        // Initializes variables
        String csvFile = "";
        String customDirStr = "";
        int opCode = 0;
        
        //Checks which file navigation system the user's computer uses
        File linuxDir = new File("/home/" + sysUsrName + "/Desktop/");
        File windowsDir = new File("C:/Users/" + sysUsrName + "/Desktop/");
        File macDir = new File("/Users/" + sysUsrName + "/Desktop/");
        boolean linuxDirExists = linuxDir.exists();
        boolean windowsDirExists = windowsDir.exists();
        boolean macDirExists = macDir.exists();
        
        // Sets opCode relational to which file navigation system the computer uses
        if(linuxDirExists){
            // Use linux file navigation system
            opCode = 1;
        } else if (windowsDirExists){
            // Use windows file navigation system
            opCode = 2;
        } else if (macDirExists){
            // Use mac file navigation system
            opCode = 3;
        } else {
            // Use custom file navigation system
            opCode = 4;
            println("Please input the full path to your desktop folder: ");
            customDirStr = scan.nextLine();
        }
        
        // Sets the csvFile string to contain the path of the system Desktop folder
        switch(opCode){
            case 1: 
                // linux Desktop folder
                Path filePath = linuxDir.toPath();
                csvFile = filePath.toString();
                break;
            case 2:
                // window Desktop folder
                filePath = windowsDir.toPath();
                csvFile = filePath.toString();
                break;
            case 3:
                // mac Desktop folder
                filePath = macDir.toPath();
                csvFile = filePath.toString();
                break;
            case 4:
                // custom Desktop folder
                csvFile = customDirStr;
                break;
        }
        
        // Check if Workout folder on Desktop exists
        // If not, creates it
        String workoutDirStr = csvFile + "/Workout/";
        File workoutDir = new File(workoutDirStr);
        if(!workoutDir.exists()){
            new File(workoutDirStr).mkdir();
        }
        
        // Check if usrList file exists
        // If not, creates it
        String usrListStr = workoutDirStr + "usrList.CSV";
        File usrList = new File(usrListStr);
        if(!usrList.exists()){
            usrList.createNewFile();
        }
        
        // Sets csvFile to location of workout folder
        csvFile = workoutDirStr;
        return csvFile;
    }
    
    /**
     * @param String
     * Checks if the given log exists
     * @return boolean
     */
    private static boolean checkLogExists(String csvFile){
        // Default if log doesn't exist
        boolean logExists = false;
        
        //Creates File object with path of log
        File logFile = new File(csvFile);
        
        //Checks if the log exists
        if(logFile.exists()){
            // Sets boolean to true: the file does exist
            logExists = true;
        }
        
        return logExists;
    }
    
    /**
     * @param String
     * Creates the given log file if it does not exist
     * @return void
     */
    private static void createLog(String csvFile) throws java.io.IOException{
        // Creates File object with path of log 
        File logFile = new File(csvFile);
        
        // Creates an actual file based off of the File object
        logFile.createNewFile();
    }
    
    /**
     * @param String
     * @param String
     * @param String
     * Adds user to the usrList CSV file
     * @return void
     */
    private static void addUser(String usrName, String pass) throws java.io.IOException, Exception{
        // Creates File Object for the usrList file
        String csvFileStr = sysCheckDir();
        csvFileStr += "usrList.CSV";
        File csvFile = new File(csvFileStr);
        
        // If the usrList file does not exist, create it
        if(!csvFile.exists()){
            csvFile.createNewFile();
        }
        
        // Reads CSV file into 2D array 'usrListArray'
        String[][] arr = CSVUtils.readFile(csvFileStr);
        
        // Encrypts password
        pass = Protected.encryptPass(pass);
        
        // Adds new username and encrypted password (passHash) to the ArrayList
        ArrayList<String> newUser = new ArrayList<String>();
        newUser.add(usrName);
        newUser.add(pass);
        
        // Creates FileWriter Object to export to CSV format
        FileWriter writer  = new FileWriter(csvFile);
        
        // Creates ArrayList to pass the user info pair into the FileWriter Object
        ArrayList<String> arrL = new ArrayList<String>();
        
        // Converts 2D array to ArrayList two at a time, then passes them into the FileWriter Object
        for(int i =0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length;j++){
                arrL.add(arr[i][j]);
            }
            CSVUtils.writeLine(writer, arrL);
            
            // Clear already added contents from ArrayList
            arrL.remove(0);
            arrL.remove(0);
        }
        
        // Adds new user to the FileWriter Object
        CSVUtils.writeLine(writer, newUser);
        
        // Exports date from FileWriter Object to CSV file
        writer.flush();
        
        // Close writer
        writer.close();
    }
    
    /**
     * @param String[]args
     * Calls the addUser method to add a user to the CSV file
     * @return void
     */
    private static void signUp(String[]args) throws Exception{
        // Creates seperate Scanner for signUp method
        Scanner signScan = new Scanner(System.in);
        
        // Intializes variables and prompts user for their desired username
        String csvFile = sysCheckDir() + "usrList.CSV";
        String usrName = "";
        print("Enter a username or \'000\' to cancel: ");
        
        // Creates a loop in which the user enters a username
        // If that usename already exists in the database, 
        // It will output an error and ask for another username
        // This will continue forever until the user inputs an original username
        while(true){
            usrName = signScan.nextLine();
            
            // Quits if user enters '000'
            if(usrName.equals("000")){
                main(args);
            }
            if(usrCheck(csvFile, usrName)){
                println("Error. User exists. Please enter another. ");
            } else {
                break;
            }
        }
        
        // Prompts the user for a password 
        print("Enter a password: ");
        String passWord = scan.nextLine();
        
        // Passes the variables along to the 'addUser' method
        addUser(usrName, passWord);
    }
    
    /**
     * @param String
     * Calls the system print method
     * @return void
     */
    private static void print(String print){
        // Print statment
        System.out.print(print);
    }
    
    /**
     * @param String
     * Calls the system println method
     * @return void
     */
    private static void println(String println){
        // Print statment and then a new line
        System.out.println(println);
    }
}