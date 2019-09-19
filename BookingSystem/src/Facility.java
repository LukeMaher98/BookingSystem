import java.io.*;
import java.util.*;
import javax.swing.*;

public class Facility {
    
    public String facilityName;
    public String facilityNum;
    public boolean comissioned;
    public String decomissionedUntil;
    
    public Facility() {
    
        facilityName = "";
        facilityNum = "";
        comissioned = true;
        decomissionedUntil = "";
        
    }
    
     public Facility(String name) {
    
        facilityName = name;
        facilityNum = String.valueOf(BookingSystemMethods.facilityInfo.get(1).get(BookingSystemMethods.facilityInfo.get(0).indexOf(name)));
        comissioned = Boolean.valueOf(BookingSystemMethods.facilityInfo.get(2).get(BookingSystemMethods.facilityInfo.get(0).indexOf(name)));
        decomissionedUntil = String.valueOf(BookingSystemMethods.facilityInfo.get(3).get(BookingSystemMethods.facilityInfo.get(0).indexOf(name)));
        
    }
    
    public void add() throws FileNotFoundException {
        
        JTextField name = new JTextField();

        Object[] message = {"Enter Facility Name:", name};

        int option = JOptionPane.showConfirmDialog(null, message, "New Facility", JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            
            this.facilityName = name.getText();
            this.facilityNum =  String.valueOf(BookingSystemMethods.facilityInfo.get(1).size());
            this.comissioned = true;
            this.decomissionedUntil = "[N/A]";

        }
        
        else if (option == JOptionPane.CLOSED_OPTION) {

            return;

        }
        
        if (BookingSystemMethods.facilityInfo.get(0).contains(this.facilityName)) {
            
            JOptionPane.showMessageDialog(null, "This facility already exists", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
            
            return;
            
        }
        
        else {
            
            BookingSystemMethods.facilityInfo.get(0).add(this.facilityName);
            BookingSystemMethods.facilityInfo.get(1).add(this.facilityNum);
            BookingSystemMethods.facilityInfo.get(2).add(String.valueOf(this.comissioned));
            BookingSystemMethods.facilityInfo.get(3).add(this.decomissionedUntil);
            
        }
        
        JOptionPane.showMessageDialog(null, "Facility successfully added: " + this.facilityName, "New Facility", JOptionPane.ERROR_MESSAGE);
            
        BookingSystemMethods.saveInfo("FacilityData.txt", BookingSystemMethods.getArrayInfo(BookingSystemMethods.facilityInfo));    
        
    }
    //Takes a new facility name in through a window and then assigns it to, and generates the other relevant values for, a Facility object
    //Produces a window displaying an error message before returning in cases where this is necessary
    //Stores these details in the FacilityData.txt file via the facilityInfo Arraylist Arraylist
    //Produces a window displaying an affirmation that the new facility has been added to the system
    
    public void bookings(String date) {
                
    ArrayList<String> bookings = new ArrayList<>();

        if (BookingSystemMethods.bookingInfo.isEmpty()) {

                for (int v = 0; v < 9; v++) {

                    bookings.add("[Empty]");

                }

            }
        
        else {

            for (int b = 0; b < BookingSystemMethods.bookingInfo.get(0).size(); b++) {

                if (BookingSystemMethods.bookingInfo.get(1).get(b).equals(date) && BookingSystemMethods.bookingInfo.get(2).get(b).equals(this.facilityName)) {

                    for (int v = 3; v < 12; v++) {

                        bookings.add(BookingSystemMethods.bookingInfo.get(v).get(b));

                    }

                    break;

                } 

            }
        
        }

        String display = this.facilityName + " for " + date + ":\n\n9am:\t" + bookings.get(0) + "\n10am:\t" + bookings.get(1) + "\n11am:\t" + bookings.get(2) + "\n12am:\t" + bookings.get(3) + "\n1pm:\t" + bookings.get(4)
                     + "\n2pm:\t" + bookings.get(5) + "\n3pm:\t" + bookings.get(6) + "\n4pm:\t"+ bookings.get(7) + "\n5pm:\t" + bookings.get(7) + "\n6pm:\t" + bookings.get(8);

        JTextArea result = new JTextArea(display);
        JOptionPane.showMessageDialog(null, result, "Facility Bookings", JOptionPane.INFORMATION_MESSAGE);
            
    }
    //Produces a window showing the timeslots associated with the details of a Facility object for a passed-in date
    //Shows whether or not the timeslots on this date are empty, and if not shows which user has booked the slot(s)
    
    public int bookingsMonth(String date) {
        
    ArrayList<String> bookings = new ArrayList<>();
        
        if (BookingSystemMethods.bookingInfo.isEmpty()) {

            for (int v = 0; v < 9; v++) {

                bookings.add("[Empty]");

            }

        }
        
        else {

            for (int b = 0; b < BookingSystemMethods.bookingInfo.get(0).size(); b++) {

                if (BookingSystemMethods.bookingInfo.get(1).get(b).equals(date) && BookingSystemMethods.bookingInfo.get(2).get(b).equals(this.facilityName)) {

                    for (int v = 3; v < 12; v++) {

                        bookings.add(BookingSystemMethods.bookingInfo.get(v).get(b));

                    }

                    break;

                } 
                
                else {
                    
                    for (int v = 0; v < 9; v++) {

                        bookings.add("[Empty]");

                    }
                    
                    break;
                    
                }

            }
        
        }

        String display = this.facilityName + " for " + date + ":\n\n9am:\t" + bookings.get(0) + "\n10am:\t" + bookings.get(1) + "\n11am:\t" + bookings.get(2) + "\n12am:\t" + bookings.get(3) + "\n1pm:\t" + bookings.get(4)
                     + "\n2pm:\t" + bookings.get(5) + "\n3pm:\t" + bookings.get(6) + "\n4pm:\t"+ bookings.get(7) + "\n5pm:\t" + bookings.get(7) + "\n6pm:\t" + bookings.get(8);

        JTextArea result = new JTextArea(display);
        Object [] first = {"Next Date"};
        Object [] most = {"Previous Date", "Next Date"};
        
        if (date.substring(0, 2).equals("01")) {
            
            int a = JOptionPane.showOptionDialog(null, result, "Facility Bookings", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, first, null);
            
            if (a == JOptionPane.CLOSED_OPTION) {

                return 0;

            }
            
            return 1;
            
        }
        
        else {
            
            int pick = JOptionPane.showOptionDialog(null, result, "Facility Bookings", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, most, null);
            
            if (pick == 0) {
                
                return -1;
                
            }
            
            else if (pick == 1) {
                
                return 1;
                
            }
            
            else if (pick == JOptionPane.CLOSED_OPTION) {

                return 0;

            }
            
        }
        
    return 0;    
        
    }
    //Similar to the method directly above, however adds "Next Date" and "Previous Date" buttons to the displayed window
    //Returns 1 if "Next Date" is selected and -1 if "Previous Date" is selected in the window
    
    public void remove() {
        
        if (BookingSystemMethods.bookingInfo.get(1).contains(this.facilityName)){
            
            JOptionPane.showMessageDialog(null, "This facility has active bookings and therefore cannot be removed",
                    "Unable to Remove Facility", JOptionPane.ERROR_MESSAGE);
            
            return;
            
        }
        
        int delNum = BookingSystemMethods.facilityInfo.get(0).indexOf(this.facilityName);
        
        for (int x = 0; x < 4; x++) {
            
            BookingSystemMethods.facilityInfo.get(x).set(delNum, "[Del]");
            
        }
        
        JOptionPane.showMessageDialog(null, "Facility successfully removed", "Remove Facility", JOptionPane.INFORMATION_MESSAGE);
        
    }
    //'Deletes' details associated with the details of a Facility object from the FacilityData.txt file via the facilityInfo Arraylist Arraylist
    //Produces a window displaying an error message if there are bookings in the BookingData.txt file associated with the details of the Facility object, before returning
    
    public void book(String date, String regEmail, int timeslot, int paymentStatus) {
        
        if (this.comissioned = false) {
            
            if (Integer.parseInt(date.substring(0, 2) + date.substring(3, 5) + date.substring(6)) < 
                    Integer.parseInt(this.decomissionedUntil.substring(0, 2) + this.decomissionedUntil.substring(3, 5) + this.decomissionedUntil.substring(6))) { 
            
                JOptionPane.showMessageDialog(null, "This facility is currently decomissioned and therefore cannot be booked",
                        "Unable to Book Facility", JOptionPane.ERROR_MESSAGE);
                
                return;
                
            }
            
        } 
        
        int checkNum = 0;
        
        if (BookingSystemMethods.bookingInfo.get(1).contains(date) && BookingSystemMethods.bookingInfo.get(2).contains(this.facilityName)) {
            
            for (int u = 0; u < BookingSystemMethods.bookingInfo.get(0).size(); u ++) {
                
                if (BookingSystemMethods.bookingInfo.get(1).get(u).equals(date) && BookingSystemMethods.bookingInfo.get(2).get(u).equals(this.facilityName)) {

                    checkNum = Integer.parseInt(BookingSystemMethods.bookingInfo.get(0).get(u));

                    if (!(BookingSystemMethods.bookingInfo.get(timeslot).get(checkNum).equals("[Empty]"))) {

                        JOptionPane.showMessageDialog(null, "There is a pre-existing booking for this facility at this time",
                            "Unable to Book Facility", JOptionPane.ERROR_MESSAGE);

                        return;

                    }

                    BookingSystemMethods.bookingInfo.get(timeslot).set(checkNum, regEmail);
                    
                    BookingSystemMethods.statementInfo.get(0).add(regEmail);
                    BookingSystemMethods.statementInfo.get(1).add(this.facilityNum);
                    BookingSystemMethods.statementInfo.get(2).add(date);
                    BookingSystemMethods.statementInfo.get(3).add(String.valueOf(timeslot - 2));
            
                    if (paymentStatus == 0) {

                        BookingSystemMethods.statementInfo.get(4).add("paid");

                    }

                    else {

                        BookingSystemMethods.statementInfo.get(4).add("unpaid");

                    }

                    return;

                }
                
            }
            
        }
            
        BookingSystemMethods.bookingInfo.get(0).add(String.valueOf(BookingSystemMethods.bookingInfo.get(0).size()));
        BookingSystemMethods.bookingInfo.get(1).add(date);
        BookingSystemMethods.bookingInfo.get(2).add(this.facilityName);

        for (int z = 3; z < 12; z++) {

            BookingSystemMethods.bookingInfo.get(z).add("[Empty]");

        }

        BookingSystemMethods.bookingInfo.get(timeslot).set(BookingSystemMethods.bookingInfo.get(0).indexOf(String.valueOf(BookingSystemMethods.bookingInfo.get(0).size() -1)) , regEmail);

        BookingSystemMethods.statementInfo.get(0).add(regEmail);
        BookingSystemMethods.statementInfo.get(1).add(this.facilityNum);
        BookingSystemMethods.statementInfo.get(2).add(date);
        BookingSystemMethods.statementInfo.get(3).add(String.valueOf(timeslot - 2));
            
        if (paymentStatus == 0) {
             
            BookingSystemMethods.statementInfo.get(4).add("paid");
                
        }
            
        else {
                
            BookingSystemMethods.statementInfo.get(4).add("unpaid");
                
        }
        
    }
    //Stores passed-in details for a new booking associated with the details of a Facility object in the bookingInfo Arraylist Arraylist
    //Produces a window displaying an error message before returning in cases where this is necessary
    //Stores details for a statement associated with this booking in the statementInfo Arraylist Arraylist
    
    public void availability(String date) {
        
        String display = this.facilityName + " for " + date + ":\n\nThis facility is commissioned and available for booking on this date";
        
        if (this.comissioned == false) {
            
            if (Integer.parseInt(date.substring(0, 2) + date.substring(3, 5) + date.substring(6)) < 
                    Integer.parseInt(this.decomissionedUntil.substring(0, 2) + this.decomissionedUntil.substring(3, 5) + this.decomissionedUntil.substring(6))) {
                
                display = this.facilityName + " for " + date + ":\n\nThis facility is decommissioned and unavailable for booking on this date";
                
            }
            
        }

        JTextArea result = new JTextArea(display);
        JOptionPane.showMessageDialog(null, result, "Facility Availability", JOptionPane.INFORMATION_MESSAGE);

    }
    //Produces a window displaying whether an existing facility associated with the details of a Facility object is comissioned or not on the passed-in date
    
    public int availabilityMonth(String date) {
        
        String display = this.facilityName + " for " + date + ":\n\nThis facility is commissioned and available for booking on this date";
        
        if (this.comissioned == false) {
            
            if (Integer.parseInt(date.substring(0, 2) + date.substring(3, 5) + date.substring(6)) < 
                    Integer.parseInt(this.decomissionedUntil.substring(0, 2) + this.decomissionedUntil.substring(3, 5) + this.decomissionedUntil.substring(6))) {
                
                display = this.facilityName + " for " + date + ":\n\nThis facility is decommissioned and unavailable for booking on this date";
                
            }
            
        }

        JTextArea result = new JTextArea(display);
        Object [] first = {"Next Date"};
        Object [] most = {"Previous Date", "Next Date"};
        
        if (date.substring(0, 2).equals("01")) {
            
            int a = JOptionPane.showOptionDialog(null, result, "Facility Availability", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, first, null);
            
            if (a == JOptionPane.CLOSED_OPTION) {

                return 0;

            }
            
            return 1;
            
        }
        
        else {
            
            int pick = JOptionPane.showOptionDialog(null, result, "Facility Availability", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, most, null);
            
            if (pick == 0) {
                
                return -1;
                
            }
            
            else if (pick == 1) {
                
                return 1;
                
            }
            
            else if (pick == JOptionPane.CLOSED_OPTION) {

                return 0;

            }
            
        }
        
        return 0;    

    }
    //Similar to the method directly above, however adds "Next Date" and "Previous Date" buttons to the displayed window
    //Returns 1 if "Next Date" is selected and -1 if "Previous Date" is selected in the window
    
    public void decommission (String date) {
        
        if (this.comissioned = false)  {
            
            if (Integer.parseInt(date.substring(0, 2) + date.substring(3, 5) + date.substring(6)) < 
                    Integer.parseInt(this.decomissionedUntil.substring(0, 2) + this.decomissionedUntil.substring(3, 5) + this.decomissionedUntil.substring(6))) { 
                
                JOptionPane.showMessageDialog(null, "The chosen facility is already decomissioned from the current date to the chosen date", "Decomission Facility", JOptionPane.INFORMATION_MESSAGE);
                
            }
        }
        
        else {
            
            this.comissioned = false;
            this.decomissionedUntil = date;
            
            BookingSystemMethods.facilityInfo.get(2).set(Integer.parseInt(this.facilityNum), String.valueOf(this.comissioned));
            BookingSystemMethods.facilityInfo.get(3).set(Integer.parseInt(this.facilityNum), this.decomissionedUntil);
                    
        }
        
    }
    //Sets the "comissioned" variable of a facility object to false and sets the "decomissionedUntil" variable to the passed-in date
    //Produces a window displaying an error message before returning in cases where this is necessary
        
}
    
