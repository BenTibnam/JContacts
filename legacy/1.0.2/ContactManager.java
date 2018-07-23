import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class ContactManager implements Serializable{
    private int contactsAdded;
    private ArrayList<Contact> contacts;

    /**
     * default ContactManager constructor
     */
    public ContactManager() {
        this.contactsAdded = 0;
        contacts = new ArrayList<Contact>();
    }

    /**
     * ContactManager constructor that takes in a premade ArrayList, NOTE: may be taken out if addContact never adds a contact
     * @param cs the contact ArrayList
     */
    public ContactManager(ArrayList<Contact> cs){
        this.contactsAdded = cs.size();                 // assume contacts length is the same ame contactsAdded, will be changed once work GUI is added
        this.contacts = cs;
    }


    /**
     * adds a contact to contacts ArrayList
     * @param c the contact we are added
     */
    public void addContact(Contact c){
        contacts.add(c);
    }

    /**
     * adds a contact through user input in the terminal
     */
    public void addContact(){
        Scanner in = new Scanner(System.in);
        String n, pn, e, ntes;

        System.out.print("Enter contact name: ");
        n = in.nextLine();

        System.out.print("Enter contact phone number: ");
        pn = in.nextLine();

        System.out.print("Enter contact email: ");
        e = in.nextLine();

        System.out.print("Enter any additional contact notes: ");
        ntes = in.nextLine();

        Contact c = new Contact(n, pn, e, ntes);
        addContact(c);
    }

    /**
     * overload of addContacts to create contact and than add it to the contacts array
     * @param n the name of contact
     * @param pn the phone number of contact
     * @param e the email of contact
     */
    public void addContact(String n, String pn, String e){
        Contact c = new Contact(n, pn, e);
        addContact(c);
    }

    /**
     * searches through the contacts for an element that matches c and than removes the element in question
     * @param c the contact that we want to remove
     */
    public void remove(Contact c){
        // searching for the contact
        for(int i = 0; i < contacts.size(); i++){
            if(contacts.get(i).toString().equals(c.toString())){
                contacts.remove(i);
            }
        }
    }


    public int getContactsAdded(){return this.contactsAdded;}
    public ArrayList<Contact> getContacts(){return this.contacts;}


}