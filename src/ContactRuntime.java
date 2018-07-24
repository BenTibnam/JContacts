import java.io.*;

public class ContactRuntime{
    private static ContactGroupManager cgm = new ContactGroupManager();    // this is the contact group manager which other classes use to change the fields of the program
    private static int curGroupIndex;
    public static void main(String[] args) {
        open();                                                         // this will open up users contact manager, but create a new one if a contact manager does not exist
        WindowManager.entryWindow();                                    // create the first window
    }

    /**
     * saves the current state of the contact manager
     */
    public static void save(){
        try{
            ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream("group-contacts.ser"));
            save.writeObject(cgm);
        }catch(IOException ioe){
            MessageWindow err = new MessageWindow("Unknown error while saving", "Contact eighthofabyte@protonmail.com with the info", 400, 200);
            ioe.printStackTrace();
            err.setUp();
            err.display();
        }
    }

    /**
     * opens up a contact manager, if a contact manager does not exist, we save a new one
     */
    public static void open(){
        try{
            ObjectInputStream open = new ObjectInputStream(new FileInputStream("group-contacts.ser"));
            cgm = (ContactGroupManager)open.readObject();

        }catch(IOException ioe){
            // this is called if there is no contacts.ser, so we save a new one
            cgm.addContactGroup(new ContactGroup("Default"));
            save();
        }catch(ClassNotFoundException cnf){
            cnf.printStackTrace();
        }
    }

    public static ContactGroupManager getContactGroupManager(){return cgm;}
    public static void setContactGroupManager(ContactGroupManager manager){cgm = manager;}
    public static int getIndex(){return curGroupIndex;}
    public static void setIndex(int i){curGroupIndex = i;}
}
