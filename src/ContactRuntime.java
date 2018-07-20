import java.io.*;

public class ContactRuntime{
    private static ContactManager cm = new ContactManager();    // this is the contact manager which other classes use to change the fields of the program

    public static void main(String[] args) {
        open();
        WindowManager.entryWindow();
    }

    public static void save(){
        try{
            ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream("contacts.ser"));
            save.writeObject(cm);
        }catch(IOException ioe){
            MessageWindow err = new MessageWindow("Unknown error while saving", "Contact eighthofabyte@protonmail.com with the info", 400, 200);
            ioe.printStackTrace();
            err.setUp();
            err.display();
        }
    }

    public static void open(){
        try{
            ObjectInputStream open = new ObjectInputStream(new FileInputStream("contacts.ser"));
            cm = (ContactManager)open.readObject();
        }catch(IOException ioe){
            save();
        }catch(ClassNotFoundException cnf){
            cnf.printStackTrace();
        }
    }

    public static ContactManager getContactManager(){return cm;}
    public static void setContactManager(ContactManager manager){cm = manager;}
}
