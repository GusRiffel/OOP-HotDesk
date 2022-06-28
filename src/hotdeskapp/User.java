package hotdeskapp;

public class User {
    
    private int id;
    private String firstName;
    private String surname;
    private String dateBirth;
    private char gender;
    private String genNotes;
    
    public User(int idd, String fName, String sName, String dBirth, char gnder, String gNotes){ //constructor
    
        id = idd;
        firstName = fName;
        surname = sName;
        dateBirth = dBirth;
        gender = gnder;
        genNotes = gNotes;
    }
    
    public void printUserDetails() { 
    
        System.out.println("User ID: "+ id);
        System.out.println("User name is: "+firstName);
        System.out.println("Surname: "+surname);
        System.out.println("Date of birth: "+dateBirth);
        System.out.println("Gender: "+gender);
        System.out.println("General Notes: "+genNotes);
    }
    
    public String getUserName(){ //to get the user name from the object and make it possible to print
    
        return firstName;
    }
    
    public int getUserId(){
        
        return id;
    }
}    
    
    