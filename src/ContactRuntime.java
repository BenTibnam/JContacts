public class ContactRuntime{
    private static ContactManager cm = new ContactManager();    // this is the contact manager which other classes use to change the fields of the program

    public static void main(String[] args) {
        WindowManager.entryWindow();
    }

    public static ContactManager getContactManager(){return cm;}
    public static void setContactManager(ContactManager manager){cm = manager;}
}
