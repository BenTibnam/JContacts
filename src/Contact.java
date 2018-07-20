public class Contact {
    private String name;
    private String phoneNumber;
    private String email;
    private String notes;                   // used for any additional information about contact you may want to add

    /**
     * default Contact constructor
     */
    public Contact(){
        this.name = "Unnamed";
        this.phoneNumber = "undefined";
        this.email = "undefined";
        this.notes = "";
    }

    /**
     * constructor with name, phone number and email arguments
     * @param n the name of contact
     * @param pn the phone number of contact
     * @param e the email of contact
     */
    public Contact(String n, String pn, String e){
        this.name = n;
        this.phoneNumber = pn;
        this.email = e;
        this.notes = "";
    }

    /**
     * constructor with name, phone number, email, and note arguments
     * @param n the name of contact
     * @param pn the phone number of contact
     * @param e the email of contact
     * @param ntes notes about the contact
     */
    public Contact(String n, String pn, String e, String ntes){
        this.name = n;
        this.phoneNumber = pn;
        this.email = e;
        this.notes = ntes;
    }


    // accessors and modifiers
    public String getName(){return this.name;}
    public String getPhoneNumber(){return this.phoneNumber;}
    public String getEmail(){return this.email;}
    public String getNotes(){return this.notes;}
    public void setPhoneNumber(String pn){this.phoneNumber = pn;}
    public void setEmail(String e){this.email = e;}
    public void setNotes(String n){this.notes = n;}

    public String toString(){
        return "name: " + this.name +
                "\nphone number: " + this.phoneNumber +
                "\nemail: " + this.email +
                "\nnotes: " + this.notes;
    }
}
