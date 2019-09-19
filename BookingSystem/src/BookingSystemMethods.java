import javax.swing.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class BookingSystemMethods {
    
    public static ArrayList<ArrayList<String>> facilityInfo = new ArrayList<ArrayList<String>>();
    //Facility Name/Facility Number/Comission Status ("true"/"false")/Date of Recomission ("[N/A]" if facility has not been decomissioned)
    public static ArrayList<ArrayList<String>> accountInfo = new ArrayList<ArrayList<String>>();
    //Email/Password/User Number (The admin account is always the account with user number 0)
    public static ArrayList<ArrayList<String>> bookingInfo = new ArrayList<ArrayList<String>>();
    //Booking Number/Date/Facility Name/Timeslot 1/Timeslot 2/Timeslot 3/Timeslot 4/Timeslot 5/Timeslot 6/Timeslot 7/Timeslot 8/Timeslot 9
    public static ArrayList<ArrayList<String>> statementInfo = new ArrayList<ArrayList<String>>();
    //Email/Facility Number/Date/Timeslot Number/Payment Status
    static int loginFails = 0;
    static String currentRegUser = "";
    
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {

        int loop = 1;

        readFilesIntoArrayLists("AccountData.txt", accountInfo, 3);
        readFilesIntoArrayLists("FacilityData.txt", facilityInfo, 4);
        readFilesIntoArrayLists("BookingData.txt", bookingInfo, 12);
        readFilesIntoArrayLists("StatementData.txt", statementInfo, 5);

        while (loop == 1){

            BookingSystemMethods.login();

        }    

    }
    
    public static String getArrayInfo(ArrayList<ArrayList<String>> in)  {
        
        String out = "";
        
        if (in.toArray().length == 0) {
            
            return "";
        }
        
        else {

            for (int i = 0; i < in.get(0).toArray().length; i++) {

                for (int j = 0; j < in.toArray().length; j++) {

                out = out + in.get(j).get(i) + ",";

                }

                out = out + "\n";

            }
            
        }
        
        return out ;
        
    }
    //Takes is a 2D arraylist, formats the information and outputs it as a string for storage in a .txt file
    
    public static void saveInfo(String txtFile, String info) throws FileNotFoundException {
        
        PrintWriter pw = new PrintWriter(txtFile);
        pw.close();
        
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(txtFile, true)))) {
            
            out.print(info);
            
        }
        
        catch (IOException e) {
            
            System.err.println(e);
            
        }
        
    }
    //Writes a specified string to a specified .txt file
  
    public static void readFilesIntoArrayLists(String file, ArrayList<ArrayList<String>> array, int numCheck) throws IOException {
        
    String filename1 = file;
    String fileElements[];
    File inputFile1 = new File(filename1);
    Scanner in = new Scanner(inputFile1);
    
    for(int i = 0; i < numCheck; i++)  {
        
        array.add(new ArrayList<>());
        
    }
    
    if (inputFile1.exists()) {

        while(in.hasNext()) {

            fileElements = (in.nextLine()).split(",");

            for (int g = 0; g < numCheck; g++) {

                array.get(g).add(fileElements[g]);

            }

        } 

    in.close();

    }
        
    } 
    //Reads a specified .txt file into a specified 2D arraylist, with a specified number of rows
        
    public static void login() throws FileNotFoundException, ParseException {
        
        if (loginFails == 3) {
            
            JOptionPane.showMessageDialog(null, "Multiple login failures detected\nProgram termination imminent", "Login Failure", JOptionPane.ERROR_MESSAGE);
            
            System.exit(0);
            
        }
        
        Object[] choices = {"Administrator","User"};
        
        JTextField email = new JTextField();
        JTextField pass = new JTextField();
        
        String accountType = "";
        
        Object[] messages = {"Enter email address:", email, "Enter password:", pass};
        
        try {
        
            accountType = (String) JOptionPane.showInputDialog(null, messages, "Login", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

            int checkNum = 0;

            if (accountType.equals(choices[0])){

                if(!(email.getText().equals(BookingSystemMethods.accountInfo.get(0).get(0)))){

                    JOptionPane.showMessageDialog(null, "The entered email address is not associated with the administrative account for this system",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);

                    loginFails++;

                    return;
                }

                else if (!(pass.getText().equals(BookingSystemMethods.accountInfo.get(1).get(0)))){

                    JOptionPane.showMessageDialog(null, "Incorrect password entered","Invalid Input",JOptionPane.ERROR_MESSAGE);

                    loginFails++;

                    return;

                }

                JOptionPane.showMessageDialog(null,"Login successful\n\nWelcome, Admin","Login",JOptionPane.INFORMATION_MESSAGE);

                adminMenu();

            }

            else if (accountType.equals(choices[1])){

                if(BookingSystemMethods.accountInfo.get(0).contains(email.getText())) {

                    checkNum = BookingSystemMethods.accountInfo.get(0).indexOf(email.getText());

                }

                else {

                    JOptionPane.showMessageDialog(null, "The entered email address is not associated with an account on this system",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);

                    loginFails++;

                    return;

                }

                if (!(pass.getText().equals(BookingSystemMethods.accountInfo.get(1).get(checkNum)))){

                    JOptionPane.showMessageDialog(null, "Incorrect password entered","Invalid Input",JOptionPane.ERROR_MESSAGE);

                    loginFails++;

                    return;

                }

                JOptionPane.showMessageDialog(null,"Login successful\n\nWelcome, User","Login",JOptionPane.INFORMATION_MESSAGE);
                currentRegUser = email.getText();

                userMenu();

            }
            
        }
        
         catch (NullPointerException e) {

                System.exit(0);

        }
       
    }
    //Allows for entry and validation of login information(up to 3 incorrect attempts)
    //Produces a window displaying an error message before returning in cases where this is necessary
    //After succesful validation of details, produces a window displaying an affirmation message before calling the appropriate menu method
    
    public static void viewBookingsDay() throws ParseException {
        
        Object[] facs = BookingSystemMethods.facilityInfo.get(0).toArray();
        
        String fac = "";
        
        try {
        
            if (facs.length == 0) {

                JOptionPane.showMessageDialog(null, "There are currently no facilities registered to the system", "Unable to View Bookings", JOptionPane.ERROR_MESSAGE);

            }

            int loop = 1;

            while (loop == 1) {

                fac = (String) JOptionPane.showInputDialog(null, "Choose Facility to View Bookings","View Bookings", JOptionPane.QUESTION_MESSAGE, null, facs, facs[0]); 

                if (fac.equals("[Del]")) {

                    JOptionPane.showMessageDialog(null, "The selected facility no longer exists", "Unable to View Bookings", JOptionPane.ERROR_MESSAGE);

                    continue;

                }

                break;

            }

            String date = validDateSingle();
            if (date.equals("")) {

                JOptionPane.showMessageDialog(null, "The entered date is invalid", "Unable to View Bookings", JOptionPane.ERROR_MESSAGE);

                return;

            }

            Facility check = new Facility(fac);
            check.bookings(date);
            
        }
            
        catch (NullPointerException e) {
                
        }

    }
    //Displays a window allowing for the selection of a pre-existing facility from the FacilityData.txt file via the facilityInfo Arraylist Arraylist
    //Calls the validDateSingle() method for a date string
    //Produces a window displaying an error message before returning in cases where this is necessary
    //Creates a Facility object, passing in the previously selected facility
    //Calls the bookings() method from the Facility class, passing in the previously selected date string
    
    public static void viewBookingsMonth() throws ParseException {
        
        Object[] facs = BookingSystemMethods.facilityInfo.get(0).toArray();
        
        String fac = "";
        
        try {
        
            if (facs.length == 0) {

                JOptionPane.showMessageDialog(null, "There are currently no facilities registered to the system", "Unable to View Bookings", JOptionPane.ERROR_MESSAGE);

            }

            int loop = 1;

            while (loop == 1) {

                fac = (String) JOptionPane.showInputDialog(null, "Choose facility to view bookings","View Bookings", JOptionPane.QUESTION_MESSAGE, null, facs, facs[0]); 

                if (fac.equals("[Del]")) {

                    JOptionPane.showMessageDialog(null, "The selected facility no longer exists", "Unable to View Bookings", JOptionPane.ERROR_MESSAGE);

                    continue;

                }

                break;

            }

            int dayNum = 0;
            ArrayList<String> days = new ArrayList<String>();

            String month = validDateMonth();

            if (month.substring(0, 2).equals("04") || month.substring(0, 2).equals("06") || month.substring(0, 2).equals("09") || month.substring(0, 2).equals("11")) {

                dayNum = 31;

            }

            else if (month.substring(0, 2).equals("02")) {

                dayNum = 29;

            }

            else if (month.substring(0, 2).equals("02") && Integer.parseInt(month.substring(4)) % 4 == 0) {

                dayNum = 30;

            }

            else {

                dayNum = 32;

            }

            for (int t = 1; t < dayNum; t++) {

                if (t < 10) {

                    days.add("0" + String.valueOf(t));

                }

                else {

                    days.add(String.valueOf(t));

                }

            }

            int v = 1;

            while (v < days.size() + 1) {

                String d = String.valueOf(v);

                if (v < 10) {

                    d = "0" + String.valueOf(v);

                }

                Facility check = new Facility(fac);
                int x = check.bookingsMonth(d + month);
                v = v + x;

                if (x == 0) {

                    return;

                }

            }
            
        }
        
        catch (NullPointerException e) {
            
        }
            
    }
    //Displays a window allowing for the selection of a pre-existing facility from the FacilityData.txt file via the facilityInfo Arraylist Arraylist
    //Calls the validDateMonth() method for a month string
    //Produces a window displaying an error message before returning in cases where this is necessary
    //Creates a Facility object, passing in the previously selected facility
    //Calls the bookingsMonth() method from the Facility class, passing in the previously selected month string
    //Uses the int returned by this method to cycle through the chosen facility's bookings over the span of the chosen month
    
    public static String validDateSingle() throws ParseException {
        
        int dayNum = 0;
        ArrayList<String> days = new ArrayList<String>();
        ArrayList<String> months = new ArrayList<String>();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Date date = new Date();
        
        for (int q = 1; q < 13; q++) {
            
            if (q < 10) {
                
                months.add("0" + String.valueOf(q));
                
            }
            
            else {
                
                months.add(String.valueOf(q));
                
            }
            
        }
        
        JTextField day = new JTextField();
        JTextField month = new JTextField();
        JTextField year = new JTextField();

        Object[] message = {"Day(DD):", day, "Month(MM):", month, "Year(YYYY):", year};

        int option = JOptionPane.showConfirmDialog(null, message, "Enter Date to Book", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {

            if (month.getText().equals("04") || month.getText().equals("06") || month.getText().equals("09") || month.getText().equals("11")) {

                dayNum = 31;

            }

            else if (month.getText().equals("02")) {

                dayNum = 29;

            }

            else if (month.getText().equals("02") && Integer.parseInt(year.getText()) % 4 == 0) {

                dayNum = 30;

            }

            else {

                dayNum = 32;

            }

            for (int t = 1; t < dayNum; t++) {

                if (t < 10) {

                    days.add("0" + String.valueOf(t));

                }

                else {

                    days.add(String.valueOf(t));

                }

            }

            if (!(days.contains(day.getText())) || !(months.contains(month.getText()))) {

                JOptionPane.showMessageDialog(null, "Please a valid date in a numerical format: DD/MM/YYYY", "Invalid input", JOptionPane.ERROR_MESSAGE);

                return "";

            }

            Date d = dateFormat.parse(day.getText() + "/" + month.getText() + "/" + year.getText());
            
            if (d.before(date)) {
                
                JOptionPane.showMessageDialog(null, "Please a valid date in a numerical format: DD/MM/YYYY", "Invalid input", JOptionPane.ERROR_MESSAGE);

                return "";
                
            }

            return day.getText() + "/" + month.getText() + "/" + year.getText();
            
        }
        
        return "";
        
    }
    //Displays a window accepting figures for a specific date
    //Validates these figures and checks that the date is after the current date
    //Produces a window displaying an error message before returning in cases where this is necessary
    //Returns the date as a string (dd/mm/yyyy)
    
    public static String validDateMonth() throws ParseException {
        
        ArrayList<String> months = new ArrayList<String>();
        
        for (int q = 1; q < 13; q++) {
            
            if (q < 10) {
                
                months.add("0" + String.valueOf(q));
                
            }
            
            else {
                
                months.add(String.valueOf(q));
                
            }
            
        }
        
        JTextField month = new JTextField();
        JTextField year = new JTextField();
        
        DateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
	Date date = new Date();

        Object[] message = {"Month:", month, "Year:", year,};

        int option = JOptionPane.showConfirmDialog(null, message, "Enter Month to Book", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            
            if (!(months.contains(month.getText()))) {

                JOptionPane.showMessageDialog(null, "Please a valid date in a numerical format: MM/YYYY", "Invalid input", JOptionPane.ERROR_MESSAGE);

                return "";
                
            }
            
            Date d = dateFormat.parse(month.getText() + "/" + year.getText());
            
            if (d.before(date)) {
                
                JOptionPane.showMessageDialog(null, "Please a valid date in a numerical format: DD/MM/YYYY", "Invalid input", JOptionPane.ERROR_MESSAGE);

                return "";
                
            }

            return "/" + month.getText() + "/" + year.getText();
        
        }
        
        return "";
    
    }
    //Displays a window accepting figures for a specific month
    //Validates these figures and checks that the month is after the current month
    //Produces a window displaying an error message before returning in cases where this is necessary
    //Returns the month as a string (mm/yyyy)
    
    public static void bookFacility() throws FileNotFoundException, ParseException {
        
        Object[] facs = BookingSystemMethods.facilityInfo.get(0).toArray();
        
        String fac = "";
        
        try {
        
            if (facs.length == 0) {

                JOptionPane.showMessageDialog(null, "There are currently no facilities registered to the system", "Unable to Book Facility", JOptionPane.ERROR_MESSAGE);

            }

            int loop = 1;

            while (loop == 1) {

                fac = (String) JOptionPane.showInputDialog(null, "Choose facility to book","Book Facility", JOptionPane.QUESTION_MESSAGE, null, facs, facs[0]); 

                if (fac.equals("[Del]")) {

                    JOptionPane.showMessageDialog(null, "The selected facility no longer exists", "Unable to Book Facility", JOptionPane.ERROR_MESSAGE);

                    continue;

                }

                break;

            }

            String date = validDateSingle();
            Object[] slots = {"9:00am","10:00am","11:00am","12:00am","1:00pm","2:00pm","3:00pm","4:00pm","5:00pm","6:00pm"};
            Object[] users = BookingSystemMethods.accountInfo.get(0).toArray();
            Object[] paid = {"Payment in advance", "Payment on date"};

            if (date.equals("")) {

                JOptionPane.showMessageDialog(null, "The entered date is invalid", "Unable to View Bookings", JOptionPane.ERROR_MESSAGE);

                return;

            }

            String slot = (String) JOptionPane.showInputDialog(null, "Select requested time slot", "New Booking", JOptionPane.QUESTION_MESSAGE, null, slots, slots[0]);
            String user = (String) JOptionPane.showInputDialog(null, "Select requesting user", "New Booking", JOptionPane.QUESTION_MESSAGE, null, users, users[0]);
            String pay = (String) JOptionPane.showInputDialog(null, "Select payment type", "New Booking", JOptionPane.QUESTION_MESSAGE, null, paid, paid[0]);

            int slotNum = Arrays.asList(slots).indexOf(slot) + 3;
            int payType = Arrays.asList(paid).indexOf(pay);

            Facility toBook = new Facility(fac);
            toBook.book(date, user, slotNum, payType);

            saveInfo("BookingData.txt", getArrayInfo(bookingInfo));  
            saveInfo("StatementData.txt", getArrayInfo(statementInfo)); 
            
        }
        
        catch (NullPointerException e) {
            
        }
       
    }
    //Displays a window allowing for the selection of a pre-existing facility from the FacilityData.txt file via the facilityInfo Arraylist Arraylist
    //Calls the validDateSingle() method for a date string
    //Shows windows for the selection of a timeslot, the requesting user and the payment status
    //Produces a window displaying an error message before returning in cases where this is necessary
    //Creates a Facility object, passing in the previously selected facility
    //Calls the book() method from the Facility class, passing in the four parameters selected previously
    //Stores the data in the bookingInfo Arraylist Arraylist in the BookingData.txt file
    
    public static void removeFacility() throws FileNotFoundException {
        
        Object[] facs = BookingSystemMethods.facilityInfo.get(0).toArray();
        
        String fac = "";
        
        try {
        
            if (facs.length == 0) {

                JOptionPane.showMessageDialog(null, "There are currently no facilities registered to the system", "Unable to Remove Facility", JOptionPane.ERROR_MESSAGE);

            }

            int loop = 1;

            while (loop == 1) {

                fac = (String) JOptionPane.showInputDialog(null, "Choose facility to remove","Facility Removal", JOptionPane.QUESTION_MESSAGE, null, facs, facs[0]); 

                if (fac.equals("[Del]")) {

                    JOptionPane.showMessageDialog(null, "The selected facility no longer exists", "Unable to Remove Facility", JOptionPane.ERROR_MESSAGE);

                    continue;

                }

                break;

            }

            Facility toRemove = new Facility(fac);
            toRemove.remove();

            saveInfo("FacilityData.txt", getArrayInfo(facilityInfo));
            
        }
        
        catch (NullPointerException e) {
            
        }
        
    }
    //Displays a window allowing for the selection of a pre-existing facility from the FacilityData.txt file via the facilityInfo Arraylist Arraylist
    //Produces a window displaying an error message before returning in cases where this is necessary
    //Creates a Facility object, passing in the previously selected facility
    //Calls the remove() method from the Facility class
    //Stores the data in the bookingInfo Arraylist Arraylist in the BookingData.txt file
    
    public static void viewAllStatements() {
        
        Object[] slots = {"9:00am","10:00am","11:00am","12:00am","1:00pm","2:00pm","3:00pm","4:00pm","5:00pm","6:00pm"};
        
        String sttmnts = "All Current Facility Booking Statements:\n\n";
        
        if (BookingSystemMethods.statementInfo.get(0).size() > 0) {
        
            for (int y = 0; y < BookingSystemMethods.statementInfo.get(0).size(); y++) {
                    
                sttmnts = sttmnts + BookingSystemMethods.statementInfo.get(2).get(y) + ": " + BookingSystemMethods.statementInfo.get(0).get(y) + " in " + 
                        BookingSystemMethods.facilityInfo.get(0).get(BookingSystemMethods.facilityInfo.get(1).indexOf(BookingSystemMethods.statementInfo.get(1).get(y))) +
                        " @ " + slots[Integer.parseInt(BookingSystemMethods.statementInfo.get(3).get(y))] + " [" + BookingSystemMethods.statementInfo.get(4).get(y) + "]\n";
                        
            }
            
        }
        
        JTextArea display = new JTextArea(sttmnts);
        JOptionPane.showConfirmDialog(null, display, "Booking Statements", JOptionPane.OK_OPTION);
        
    }
    //Produces a window displaying all bookings stored in the BookingData.txt file, via the bookingInfo Arraylist Arraylist
  
    public static void viewAvailabilityDay() throws ParseException{
        
        Object[] facs = BookingSystemMethods.facilityInfo.get(0).toArray();
        
        String fac = "";
        
        try {
        
            if (facs.length == 0) {

                JOptionPane.showMessageDialog(null, "There are currently no facilities registered to the system", "Unable to View Availability", JOptionPane.ERROR_MESSAGE);

            }

            int loop = 1;

            while (loop == 1) {

                fac = (String) JOptionPane.showInputDialog(null, "Choose facility to view availability","View Availability", JOptionPane.QUESTION_MESSAGE, null, facs, facs[0]); 

                if (fac.equals("[Del]")) {

                    JOptionPane.showMessageDialog(null, "The selected facility no longer exists", "Unable to View Availability", JOptionPane.ERROR_MESSAGE);

                    continue;

                }

                break;

            }

            String date = validDateSingle();
            if (date.equals("")) {

                JOptionPane.showMessageDialog(null, "The entered date is invalid", "Unable to View Availability", JOptionPane.ERROR_MESSAGE);

                return;

            }

            Facility check = new Facility(fac);
            check.availability(date);
            
        }
            
        catch (NullPointerException e) {
            
        }

    }
    //Displays a window allowing for the selection of a pre-existing facility from the FacilityData.txt file via the facilityInfo Arraylist Arraylist
    //Calls the validDateSingle() method for a date string
    //Produces a window displaying an error message before returning in cases where this is necessary
    //Creates a Facility object, passing in the previously selected facility
    //Calls the availability() method from the Facility class, passing in the date string selected previously
    
    public static void viewAvailabilityMonth() throws ParseException{
        
        Object[] facs = BookingSystemMethods.facilityInfo.get(0).toArray();
        
        String fac = "";
        
        try {
        
            if (facs.length == 0) {

                JOptionPane.showMessageDialog(null, "There are currently no facilities registered to the system", "Unable to View Availability", JOptionPane.ERROR_MESSAGE);

            }

            int loop = 1;

            while (loop == 1) {

                fac = (String) JOptionPane.showInputDialog(null, "Choose facility to view availability","View Availability", JOptionPane.QUESTION_MESSAGE, null, facs, facs[0]); 

                if (fac.equals("[Del]")) {

                    JOptionPane.showMessageDialog(null, "The selected facility no longer exists", "Unable to View Availability", JOptionPane.ERROR_MESSAGE);

                    continue;

                }

                break;

            } 

            int dayNum = 0;
            ArrayList<String> days = new ArrayList<String>();

            String month = validDateMonth();

            if (month.substring(0, 2).equals("04") || month.substring(0, 2).equals("06") || month.substring(0, 2).equals("09") || month.substring(0, 2).equals("11")) {

                dayNum = 31;

            }

            else if (month.substring(0, 2).equals("02")) {

                dayNum = 29;

            }

            else if (month.substring(0, 2).equals("02") && Integer.parseInt(month.substring(4)) % 4 == 0) {

                dayNum = 30;

            }

            else {

                dayNum = 32;

            }

            for (int t = 1; t < dayNum; t++) {

                if (t < 10) {

                    days.add("0" + String.valueOf(t));

                }

                else {

                    days.add(String.valueOf(t));

                }

            }

            int v = 1;

            while (v < days.size() + 1) {

                String d = String.valueOf(v);

                if (v < 10) {

                    d = "0" + String.valueOf(v);

                }

                Facility check = new Facility(fac);
                v = v + check.availabilityMonth(d + month);

            }
            
        }
        
        catch (NullPointerException e) {
            
        }
        
    }
    //Displays a window allowing for the selection of a pre-existing facility from the FacilityData.txt file via the facilityInfo Arraylist Arraylist
    //Calls the validDateSingle() method for a date string
    //Produces a window displaying an error message before returning in cases where this is necessary
    //Creates a Facility object, passing in the previously selected facility
    //Calls the availabilityMonth() method from the Facility class, passing in the date string selected previously
    //Uses the int returned by this method to cycle through the chosen facility's availability over the span of the chosen month
    
    public static void decomissionFacility() throws FileNotFoundException, ParseException{
        
        Object[] facs = BookingSystemMethods.facilityInfo.get(0).toArray();
        
        String fac = "";
        
        try {
        
            if (facs.length == 0) {

                JOptionPane.showMessageDialog(null, "There are currently no facilities registered to the system", "Unable to Decomission Facility", JOptionPane.ERROR_MESSAGE);

            }

            int loop = 1;

            while (loop == 1) {

                fac = (String) JOptionPane.showInputDialog(null, "Choose facility to decomission","Decomission Facility", JOptionPane.QUESTION_MESSAGE, null, facs, facs[0]); 

                if (fac.equals("[Del]")) {

                    JOptionPane.showMessageDialog(null, "The selected facility no longer exists", "Unable to Decomission Facility", JOptionPane.ERROR_MESSAGE);

                    continue;

                }

                break;

            }

            String date = validDateSingle();

            Facility toDecomission = new Facility(fac);
            toDecomission.decommission(date);

            saveInfo("FacilityData.txt", getArrayInfo(facilityInfo));  
            
        }
        
        catch (NullPointerException e) {
            
        }
       
    }
    //Displays a window allowing for the selection of a pre-existing facility from the FacilityData.txt file via the facilityInfo Arraylist Arraylist
    //Produces a window displaying an error message before returning in cases where this is necessary
    //Creates a Facility object, passing in the previously selected facility
    //Calls the decomission() method from the Facility class, passing in the date string selected previously
    //Stores the data in the facilityInfo Arraylist Arraylist in the FacilityData.txt file

    public static void adminMenu() throws FileNotFoundException, ParseException{
        
        Object[] actions = {"Register a user", "Add a facility","View facility availability","View facility bookings","Remove a facility","Decomission a facility","Make a booking","View account statements"};
        int loop = 1;
        
        while (loop == 1) {
        
            int option = JOptionPane.showOptionDialog(null, "Welcome back, Admin", "User Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, actions, null);

            if (option == 0) {

                RegUser newUser = new RegUser();
                newUser.createRegUser();

            }

            else if (option == 1) {

                Facility newFacility = new Facility();
                newFacility.add();

            }
            
            else if (option == 2) {
                
                Object[] choice = {"View single date availablity", "View month availability"};
                
                String either = (String) JOptionPane.showInputDialog(null, "Choose display option", "Facility Availability", JOptionPane.QUESTION_MESSAGE, null, choice, choice[0]);
                
                if (either == choice[0]) {
                    
                    viewAvailabilityDay();
                    
                }
                
                else {
                    
                    viewAvailabilityMonth();
                    
                }
                
            }
            
            else if (option == 3) {
                
                Object[] choice = {"View single date bookings", "View month bookings"};
                
                String either = (String) JOptionPane.showInputDialog(null, "Choose display option", "Facility Bookings", JOptionPane.QUESTION_MESSAGE, null, choice, choice[0]);
                
                if (either == choice[0]) {
                    
                    viewBookingsDay();
                    
                }
                
                else {
                    
                    viewBookingsMonth();
                    
                }
                
            }
            
            else if (option == 4) {
                
                removeFacility();
                
            }
            
            else if (option == 5) {
                
                decomissionFacility();
                
            }
             
            else if (option == 6) {
                
                bookFacility();
                
            }
            
            else if (option == 6) {
                
                bookFacility();
                
            }
            
            else if (option == 7) {
                
                viewAllStatements();
                
            }

            else if (option == JOptionPane.CLOSED_OPTION) {

                saveInfo("AccountData.txt", getArrayInfo(accountInfo));
                saveInfo("FacilityData.txt", getArrayInfo(facilityInfo));
                saveInfo("BookingData.txt", getArrayInfo(bookingInfo));  
                saveInfo("StatementData.txt", getArrayInfo(statementInfo)); 
                    
                System.exit(0);
                    
            }

        }

    }
    //Displays a window allowing for an administrator to access the full functinality of the program
    //Calls the majority of the above methods via button selection
    //Also creates RegUser and Facility objects to access the createRegUser() and add() methods from their respective classes
    //closing the menu window stores all data in the public Arraylist Arraylists into their respective .txt files
    
    public static void userMenu() throws FileNotFoundException{
        
        Object[] actions = {"View my Statement of Account", "View my Bookings"};
        int loop = 1;
        
        while (loop == 1) {
        
            int option = JOptionPane.showOptionDialog(null, "Welcome back, " + currentRegUser, "User Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, actions, null);

            if (option == 0) {

                RegUser toView = new RegUser();
                toView.myAccount(currentRegUser);

            }

            else if (option == 1) {

                RegUser toView = new RegUser();
                toView.myBookings(currentRegUser);

            }

            else if (option == JOptionPane.CLOSED_OPTION) {

                saveInfo("AccountData.txt", getArrayInfo(accountInfo));
                saveInfo("FacilityData.txt", getArrayInfo(facilityInfo));
                saveInfo("BookingData.txt", getArrayInfo(bookingInfo));  
                saveInfo("StatementData.txt", getArrayInfo(statementInfo)); 
                    
                System.exit(0);

            }
            
        }
        
    }
    //Displays a window allowing for a user to access the functinality of the program
    //creates RegUser objects to access the myBookings() and myAccount() methods from the RegUser class, passing in the public currentRegUser string assigned during login
    //closing the menu window stores all data in the public Arraylist Arraylists into their respective .txt files
}

    
