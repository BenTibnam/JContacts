import java.util.Scanner;

public class ContactManager{
    private int friendCount;
    private Contact[] contactList;
    private final int MAX_CONTACTS = 500;
    
    public ContactManager(){
	this.friendCount = 0;
	this.contactList = new Contact[MAX_CONTACTS];
    }

    public void printContactList(){
	for(Contact c : contactList){
	    System.out.println(c);
	}
    }

    /**
     * adds a contact to the contact list
     * @param name the name of the contact being added
     * @param email the email of the contact being added
     * @param phoneNumber the phone number of the contact being added
     * @return early exit out of method
     */
    public void addContact(String name, String email, String phoneNumber){
	friendCount++;
	if(friendCount > MAX_CONTACTS){
	    System.out.println("Cannot add: " + name + " because contact list is full");
	    return;
	}else{
	    this.contactList[friendCount-1] = new Contact(name, email, phoneNumber, friendCount-1);
	}
    }

    /**
     * none argument version of add contact, has user enter contact, calls the argument version
     */
    public void addContact(){
	Scanner in = new Scanner(System.in);
	String tempName, tempEmail, tempNumber;
	
	System.out.print("Enter in name of contact: ");
	tempName = in.nextLine();

	System.out.print("Enter in email of contact: ");
	tempEmail = in.nextLine();

	System.out.print("Enter in phone number of contact: ");
	tempNumber = in.nextLine();

	System.out.println("Submitting contact info...");
	addContact(tempName, tempEmail, tempNumber);
    }

    public Contact[] getContactList(){return this.contactList;}
}
