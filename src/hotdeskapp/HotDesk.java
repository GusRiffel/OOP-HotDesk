package hotdeskapp;

public class HotDesk {
    
    private int deskNo;
    private double price;
    private User currentUser = null;
    private int bookingDays;
    private String deskNote;
    
    
    public HotDesk(int dNumber, double pric, User cUser,int bDays, String dNote){ //constructor
    
        deskNo = dNumber;
        price = pric;
        currentUser = cUser;
        bookingDays = bDays;
        deskNote = dNote;
    }
      
    public void printDeskDetails() { // print the hotdesk details
        
        System.out.println("Desk Number: "+deskNo);
        System.out.println("Price per day £"+price);       
        System.out.println("Description: "+ deskNote);
        if (currentUser == null){
            System.out.println("This table is available to book!");
        }
        if(currentUser != null){ // using some ifs to print only the information we want to be shown if there is user or not on that desk
            System.out.println("Current User: "+ currentUser.getUserName());
            System.out.println("User ID: "+ currentUser.getUserId());
            totalToBePaid();
        }    
    }
    
    public boolean isAvailable(){ // boolean method to let us know if there is a user using the desk or not
        
        return currentUser == null;
    }
    
    public void totalToBePaid(){ // calculte the total that will need to be paid after the rented days
        System.out.println("Total to be paid is £"+ price * bookingDays);
    }
    
    //           Setters
    
    public void setUser(User cUser){ // to set user when the desk is booked
    
        currentUser = cUser;
    }
    public void setDays(int bDays){ // set days when asked on company
    
        bookingDays = bDays;
    }
    
    
    //       Getters
    
    public double getPrice(){
    
        return price;
    }
    
    public User getUser() {
    
        return currentUser;
    }
    
    public int getDays() {
    
        return bookingDays;
    }
       
}
