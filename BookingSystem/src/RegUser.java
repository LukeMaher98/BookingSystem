import java.io.FileNotFoundException;
import javax.swing.*;

public class RegUser {
    
    public String email;
    public String password;
    public String userNum;
    
    public RegUser() {
        
        this.email = "";
        this.password = "";
        this.userNum = "";
        
    }
    
    public void createRegUser() throws FileNotFoundException {

        JTextField name = new JTextField();
        JTextField at = new JTextField();
        JTextField dot = new JTextField();

        Object[] message = {"Email Account Name:", name, "@", at, ".", dot, "\ne.g. example@website.com"};

        int option = JOptionPane.showConfirmDialog(null, message, "Enter User Details", JOptionPane.OK_OPTION);

        if (option == JOptionPane.OK_OPTION) {

            this.email = name.getText() + "@" + at.getText() + "." + dot.getText();
            
            int f = 1;
            while (f == 1) {
                
                this.password = String.valueOf((int) (100000 + Math.random() * 900000));
                
                if (!(BookingSystemMethods.accountInfo.get(1).contains(this.password))) {
                    
                    break;
                    
                }
                
            }
            
            this.userNum = String.valueOf(BookingSystemMethods.accountInfo.get(0).size());

        }

        else if (option == JOptionPane.CLOSED_OPTION) {

            return;

        }

        if (this.email.equals("") || !(this.email.contains("@"))) {

            JOptionPane.showMessageDialog(null, "Please enter a valid email address", "Invalid Input", JOptionPane.ERROR_MESSAGE);

        }
        
        else if (BookingSystemMethods.accountInfo.get(0).contains(this.email)) {

            JOptionPane.showMessageDialog(null, "An account already exists for this email address", "Invalid Input", JOptionPane.ERROR_MESSAGE);

        }

        else {

            BookingSystemMethods.accountInfo.get(0).add(this.email) ;
            BookingSystemMethods.accountInfo.get(1).add(this.password) ;
            BookingSystemMethods.accountInfo.get(2).add(this.userNum) ;
            
            JOptionPane.showMessageDialog(null, "User successfully added: " + this.email + "\n\nPassword: " + this.password + "\n\nEnsure that the user is aware of their login details", 
                "New User", JOptionPane.INFORMATION_MESSAGE);

            BookingSystemMethods.saveInfo("AccountData.txt", BookingSystemMethods.getArrayInfo(BookingSystemMethods.accountInfo));
            
        }

    } 
    //Takes a new user email in through a window and then assigns it to, and generates the other relevant values for, a RegUser object
    //Produces a window displaying error before returning in cases where this is necessary
    //Stores these details in the AccountData.txt file via the bookingInfo Arraylist Arraylist
    //Produces a window displaying the details that would need to be given to user of the system

    public void myAccount(String regEmail) {
        
        this.email = regEmail;
        this.userNum = BookingSystemMethods.accountInfo.get(2).get(BookingSystemMethods.accountInfo.get(0).indexOf(this.email));
        
        String accountStatement = "STATEMENT OF ACCOUNT" + "\n\nRegistered email: " + this.email + "\nAccount Number: " + this.userNum 
                + "\n\nWe appreciate your custom and home that you find all of our services and facilities that you avail of to your satisfaction.";
        
        JTextArea out = new JTextArea(accountStatement);
        JOptionPane.showMessageDialog(null, out, "My Statement of Account", JOptionPane.INFORMATION_MESSAGE);
        
    }
    //Assigns the passed-in string to the "email" variable of a RegUser object and then locates and assigns the correct value for the "userNum" variable
    //Produces a window showing a statement of account containing the information mentioned above
    
    public void myBookings(String regEmail){
        
        this.email = regEmail;
        
        Object[] slots = {"9:00am","10:00am","11:00am","12:00am","1:00pm","2:00pm","3:00pm","4:00pm","5:00pm","6:00pm"};
        
        String bookings = "Registered email: " + this.email + "\n\nMy Bookings:\n\n";
        
        if (BookingSystemMethods.statementInfo.size() > 0) {
        
            for(int y = 0; y < BookingSystemMethods.statementInfo.size(); y++) {

                if (BookingSystemMethods.statementInfo.get(y).get(0).equals(this.email)) {

                    bookings = bookings + BookingSystemMethods.statementInfo.get(2).get(y) + ": " +
                        BookingSystemMethods.facilityInfo.get(0).get(BookingSystemMethods.facilityInfo.get(1).indexOf(BookingSystemMethods.statementInfo.get(1).get(y))) +
                        " @ " + slots[Integer.parseInt(BookingSystemMethods.statementInfo.get(3).get(y))] + " [" + BookingSystemMethods.statementInfo.get(4).get(y) + "]\n";

                }

            }
            
        }

        JTextArea display = new JTextArea(bookings);
        JOptionPane.showMessageDialog(null, display, "My Bookings", JOptionPane.INFORMATION_MESSAGE);
        
    }
    //Assigns the passed-in string to the "email" variable of a RegUser object
    //Searchs the information in the BookingData.txt file via the bookingInfo Arraylist Arraylist for bookings associated with the email for this RegUser object
    //Produces a window displaying all bookings associated with the email for this RegUser object
    
}
