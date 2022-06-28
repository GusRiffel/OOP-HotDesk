package hotdeskapp;

import java.util.Scanner;

public class Company {
    Scanner input = new Scanner(System.in); // Scanner tool to capture input
    private int id = 1; // id to be used as a unique form of indentification of our users, will start as 1 and the increasing acording to the registered user
    private User userArray[] = new User[11]; // array with 11 slots created so we can simulate a situation where all tables are booked
    private HotDesk deskArray[] = { // array alredy initialized with all our tables since they will always be there
        new HotDesk(1, 5.50, null, 0,"Standard"),
        new HotDesk(2, 8.50, null, 0, "High-tech"),
        new HotDesk(3, 5.50, null, 0, "Standard"),
        new HotDesk(4, 6.50, null, 0, "Window"),
        new HotDesk(5, 5.50, null, 0, "Standard"),
        new HotDesk(6, 6.00, null, 0, "Ultra-fast Ethernet"),
        new HotDesk(7, 5.50, null, 0, "Standard"),
        new HotDesk(8, 8.50, null, 0, "High-tech"),
        new HotDesk(9, 5.50, null, 0, "Standard"),
        new HotDesk(10, 6.50, null, 0, "Window")
    };
    
    public Company(){}; //constructor to be used on the main method
    
    public void startMenu() { //Starting menu with all the options that will be captured by a switch method.
    
       boolean isValid = false; // variable set to pass the validation
        
        do{ // loop to make the user go through till the conditions are satisfacted
            try{ // try and catch to handle execptions
              
                System.out.println("Welcome to Piping hot! Please select and option");
                System.out.println("1- Check desks availability");
                System.out.println("2- Register User   *This option will automatically look for a hotdesk to book");
                System.out.println("3- Print desks in use at the moment");
                System.out.println("4- Print desk details by number ");
                System.out.println("5- Print registered users");
                System.out.println("6- Delete user / empty hotdesk");
                System.out.println("7- Exit");
        
                int option = Integer.parseInt(input.nextLine());
        
                switch(option){ // get the input from user and compare to the case to select an option

                    case 1:
                        isValid = true;
                        availTableDetails();
                        System.out.println();
                        startMenu();

                    case 2:
                        isValid = true;
                        registerUser();
                        System.out.println();
                        startMenu();

                    case 3:
                        isValid = true;
                        printTablesBooked();
                        System.out.println();
                        startMenu();

                    case 4:
                        isValid = true;
                        printTableByNo();
                        System.out.println();
                        startMenu();

                    case 5:   
                        isValid = true;
                        printUsers();
                        System.out.println();
                        startMenu();

                    case 6:
                        isValid = true;
                        deleteUser();
                        System.out.println();
                        startMenu();

                    case 7:
                        System.exit(0);

                    default:
                        System.out.println("Please select a valid option");
                        System.out.println();
                }
            }catch(Exception e){     
                System.out.println("Please select a valid option");
            }
        }while(!isValid); //Ill be using this form of validation thoughout the code so i wont be repeting the comments on every do while try catch  
           
    }
    
    public void registerUser(){ //will create the user object after getting data from the user input
        String firstName;
        String surname;
        String dateBirth;
        char gender;
        
        do{
            System.out.println("Your first name: ");
            firstName = input.nextLine();
        }while(!onlyLettersValidation(firstName)); // here there is no boolean is valid created as before because we are using a boolean method instead to do the validation   
        
        do{
            System.out.println("Your surname: ");
            surname = input.nextLine();
        }while(!onlyLettersValidation(surname));    
        
        do{
            System.out.println("Your date of birth: DD/MM/YYYY ");
            dateBirth = input.nextLine();    
        }while(!dBirthValidation(dateBirth));    
        
        do{
            System.out.println("Your gender M/F: ");
            String genderInput = input.nextLine();// had to check if the string is not empty first, otherwise the char.At(0) would complicate the validation
            if(genderInput.length() != 1){ // even with char.at(0) capturing the first character, i wanted to force the user to type the rigth information thats why it its needs to be one char only
                gender = 0; // values assigned to make it not valid
            }else{
                gender = genderInput.charAt(0);
            }
            
        }while(!genderValidation(gender));    
        
        System.out.println("Your notes: ");
        String notes = input.nextLine();
        
        User cUser = new User (id++, firstName, surname, dateBirth, gender, notes); // creation of the object following constructor pattern
        
        for(int i = 0; i < userArray.length; i++){ // using a for to find a null index on our array that can be replace with the user object created to store it.
        
            if(userArray[i] == null){
                userArray[i] = cUser;
                System.out.println("Register Successful!");
                break;
            }
        }
        
        int availTables = checkAvailability(); // check if there are tables available before proceeding to next step
        if(availTables >= 1){
            bookTable(cUser); // booktable called with user object as parameter so with have access to all the user inf inside the booktable method
        }else{               
            System.out.println("Sorry, no tables available at the moment");
            for(int j = 0; j < userArray.length; j++){ //using a for to find the index equal to our user id created and set it to null again, since there was no table available
            
                if(cUser.getUserId() == userArray[j].getUserId()){
                    userArray[j] = null;
                }
            }    
        }    
    }
    
    public void bookTable(User cUser) { // method to find a table and book it to the User
        int days = 0;
        boolean isValid = false; 
        boolean isUserSet = false;
        
        do{
            try{
                System.out.println("How many days would you like to book a table for?");
                days = Integer.parseInt(input.nextLine());
                if(days <= 0){ // just to make sure the user doens try to book for 0 o less days
                    System.out.println("You can't book for less than 1 day, please select a valid number of days you would like to book.");
                }else{
                    isValid = true;
                }    
            }catch(Exception e){
                System.out.println("Invalid input, please type a valid number.");
            } 
        }while(isValid == false);    
        
        do{
            for (int i = 0; i < deskArray.length; i++){ //for to start looping through our desk array a find a vailable table

                HotDesk desk = deskArray[i]; // created a HotDesk object to make the for more readable

                if(desk.isAvailable()){ //isAvailable check if the current table has the user = null. This means it free to use
                    desk.setDays(days); //using setter to modify the bookingDays variable, the days set were captured by user input
                    System.out.println("Good news! We found you a table!");
                    System.out.println("Would you like to book this table ? Y / N");
                    System.out.println("Table details: ");
                    desk.printDeskDetails(); // get the details from the table found and display to our customer
                    desk.totalToBePaid(); // calculate how much is gonna be for the days he wants to book
                    String answer = input.nextLine();
                    if(!confirmValidation(answer)){ // if the user fails on answering yes or no we break out the loop and start the search for a table again with the do while
                        break;
                    }else{
                        if(answer.equalsIgnoreCase("Y")){
                            desk.setUser(cUser); // Using a getter to set user of that table = to the user object we received from parameter 
                            System.out.println("Table book successful!");
                            isUserSet = true;
                            break;
                        }
                    }
                }
            }
        }while(isUserSet == false);    
    }
    
    public void deleteUser() { // method to delete user and empty desk after deleted
        HotDesk desk = null;
        boolean isValid = false;
        
        do{
            try{
                System.out.println("What is the user's table number ?");
                int number = Integer.parseInt(input.nextLine()); // have to provide the table number the user you want to delete is using
                desk = deskArray[number - 1]; // here we need to user -1 to get the right array index
                if(!desk.isAvailable()){ // opposite of user = null what means this table has an user
                    System.out.println("These are the table details:");
                    desk.printDeskDetails(); // get the user details to make sure we are deleting the right person
                    System.out.println("The table has been booked for "+ desk.getDays() +" days.");            
                    System.out.println("Are you sure you want to delete this user ? Y / N");
                    String answer = input.nextLine();
                    confirmValidation(answer);
                    if(answer.equalsIgnoreCase("Y")){
                        for (int j = 0; j < userArray.length; j++){ // for to first index with a user registered and check if this user has the id of the person we are looking for then set to null to delete it

                            if(userArray[j] != null && desk.getUser().getUserId() == userArray[j].getUserId()){            
                                userArray[j] = null;
                                break;
                            }
                        }
                            desk.setDays(0); // here we had to hard to the days and user on the table to reset it
                            desk.setUser(null);
                            isValid = true;
                            System.out.println("User deleted!");  
                    }else if(answer.equalsIgnoreCase("N")){
                        isValid = true;
                    }
                }else{
                    isValid = true;
                    System.out.println("Sorry, there are no current users in this table"); // in case there are no user on that table number specified
                }        
            }catch(Exception e){
                 System.out.println("Invalid input, please type a valid number between 1 and "+deskArray.length);
            }    
        }while(!isValid);    
    }
    
    public void printUsers(){ // get all user registered on our user Array
        int avail = checkAvailability(); //the method checkAvailability return an int of how many tables are available
        
        if(avail == deskArray.length){ // comparing the number o tables available with the size of the array if they are equals there are no user at the moment. I can only have user when a table is booked
            System.out.println("No users registered at the moment");
        }else{
            System.out.println("These are the current registered users: ");
            for (int j = 0; j < userArray.length; j++){ //using a for to print the user index != null. To only print registered users no null indexes.
            
                if(userArray[j] != null){
                    userArray[j].printUserDetails();
                }    
            }
        }    
    }
    
    public void printTablesBooked(){ // print all the tables used at the moment
        int avail = checkAvailability();
        
        if(avail == deskArray.length){ // logic used in printUsers
            System.out.println("No tables are in use at the moment");
        }else{
            System.out.println("The following tables are in use at the moment: ");
            for (int i = 0; i < deskArray.length; i++){ // a for to find the tables in use at the moment using the negative form isAvailabe validation to get the tables != null

                HotDesk desk = deskArray[i];
                if(!desk.isAvailable()){                   
                    desk.printDeskDetails();
                    System.out.println("The table has been booked for "+ desk.getDays() +" days.");
                }
            } 
        }    
    }
    
    public void printTableByNo(){ // method to print a table by number regardless of having an user or not
        int number = 0;
        HotDesk desk = null;
        boolean isValid = false;
        
        do{
            try{
                System.out.println("Type the number of the table");
                number = Integer.parseInt(input.nextLine());  
                desk = deskArray[number - 1]; // getting the user number and doing -1 to get the right index
                desk.printDeskDetails(); // printing the table tables 
                isValid = true;      
            }catch(Exception e){
                System.out.println("Invalid input, please type a valid number between 1 and "+deskArray.length);
            }
        }while(isValid == false);  
         
        do{
            if(!desk.isAvailable()){ // if the table is in use at the moment we can also print the user details if needed  
                System.out.println("Would like to also print User full details ?  Y/N");
                String answer = input.nextLine();
                confirmValidation(answer);
                if(answer.equalsIgnoreCase("Y")){    
                    User user = desk.getUser(); // using getter method on a User object created to make it more readable how I had access to user methods
                    user.printUserDetails(); // finally using a user method to print the details
                    isValid = false;
                }else if(answer.equalsIgnoreCase("N")){
                    isValid = false;
                }
            }else{
                isValid = false;
            }
        }while(isValid == true);    
    }
    
    public void availTableDetails(){ // method to get the table details when checked if there are tables available option 2 menu
        int avail = checkAvailability();
        boolean isValid = false;
        
        if(avail == 0){
            System.out.println("Sorry we don't have any tables available at the moment.");
        }else{
            System.out.println("We currently have "+avail+" desks available");
            System.out.println("Would you like more details? Y / N"); // after getting the number of tables available from the checkAvailability we can print the details if desired
            String answer = input.nextLine();
            confirmValidation(answer);

            if(answer.equalsIgnoreCase("Y")){
                for(int i = 0; i < deskArray.length; i++){ // looping through deskArray and using isAvailable to show us exactly which tables are available at the moment
                    if(deskArray[i].isAvailable()){
                        System.out.println("Desk number "+(i+1)+" is available !");
                    }
                }
                do{            
                    try{
                        System.out.println("To get table details just enter the tables number, otherwise press 0 to exit");
                        int number = Integer.parseInt(input.nextLine()); // after knowing which tables are available we can print that desk details
                        if(number == 0){
                            isValid = true;
                        }else if(!deskArray[number - 1].isAvailable()){
                            System.out.println("Sorry this table is in use at the moment, you can only print available tables in this option");
                        }else if(deskArray[number - 1].isAvailable()){
                            deskArray[number - 1].printDeskDetails();
                            isValid = true;
                        }
                        }catch(Exception e){
                        System.out.println("Invalid input, please type an available table number");
                    } 
                }while(isValid == false);
            }else if(answer.equalsIgnoreCase("N")){
                isValid = true;
            }
        }             
    }
    
    //     Validations
    
    public int checkAvailability() { // method to run through the deskArray an count how many tables are available returning an int
        int available = 0; // this variable will be increase everytime the for loops
         
        for(int i=0; i < deskArray.length; i++){
        
            HotDesk desk = deskArray[i];
            if(desk.isAvailable()){ 
                available++;
            }
        }
        return available;       
    }
    
    public boolean confirmValidation(String answer) { // method to make the user inputs the Y or N letter when requested
               
        if(!"Y".equalsIgnoreCase(answer) && !"N".equalsIgnoreCase(answer)){ // and if comparing the input registered and then returning a Boolean to make is usable in if statements
            System.out.println("Invalid option. Please type Y to confirm or N to decline");
            return false; // using the boolean returns to break out the while loops of validation
        }else{
            return true;
        }

    }
    
    public boolean dBirthValidation(String dateBirth){
    
        String dBirth[] = dateBirth.split("/"); // using the splitString function to create an array with the Strings typped, the / will determine how to separate the indexes
        int indexCounter = 0; // counting loop interation again to make sure we dont have more than 3 indexes   Day Month Year
        for(int i = 0; i < dBirth.length; i++){ // for to run through our array and validate each index separatly
        
            if((i == 0 || i == 1) && dBirth[i].length()!= 2 || !dBirth[i].matches("[0-9]+")){ // using regex to validate if the user input is correspond to certain characters
                System.out.println("Wrong input format, please type again following the given example DD/MM/YYYY !"); // this will check if the input match with the select numbers ranging from 0 to 9
                return false;
            }else if(i == 2 && dBirth[i].length() != 4 || !dBirth[i].matches("[0-9]+")){ // using regex to validate if the user input is correspond to certain characters
                System.out.println("Wrong input format, please type again following the given example DD/MM/YYYY !");
                return false;
            }else if(i == 2 && dBirth[i].compareTo("1920") < 0){ // using the compareTo function where if the input capture is bigger than what we compare the value returned will greater and if smaller the result will be lower
                System.out.println("Sorry, are you sure about your year of birth ???"); // this way we can stablish an year of birth cap
                return false;
            }else if(dBirth[i].compareTo("00") == 0){ // also make sure the user dindt type zero on our function since it would pass the validation of index size
                System.out.println("Wrong input format, please type again following the given example DD/MM/YYYY !");
                return false;
            }
            indexCounter++;
        }
        if(indexCounter != 3){ // using the counter to valide the size of the array
            System.out.println("Wrong input format, please type again following the given example DD/MM/YYYY !");
            return false; // again using boolean as a form to break out the validations while loop
        }
        return true;
        
    }
    
    public boolean onlyLettersValidation(String name){
    
        if(name.matches("[a-zA-Z]+")){ // using regex to validate if the user input is correspond to certain characters, in this the raging from a-z. needs to be specified in capital letters too.
            return true;
        }
        System.out.println("Please type a valid input. Letters only");
        return false;// again using boolean as a form to break out the validations while loop
    }
    
    public boolean genderValidation(char gender){
    
        if(gender != 'M' && gender != 'F'){ // same method used with Y and N but this time with char on the gender field where we needed to check if the filed was empty first due to the commandt char.at(0)
            System.out.println("Invalid input, please type M for Male or F for Female");
            return false;// again using boolean as a form to break out the validations while loop
        }
        return true;
    }
}


