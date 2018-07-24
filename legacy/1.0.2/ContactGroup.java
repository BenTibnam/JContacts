import java.io.Serializable;

public class ContactGroup implements Serializable {
    private String name;
    private ContactManager manager;

    /**
     * default constructor for ContactGroup
     */
    public ContactGroup(){
        this.name = "UndefinedGroupName";
        this.manager = new ContactManager();
    }

    /**
     * constructor for ContactGroup with name argument
     * @param name the name of the group
     */
    public ContactGroup(String name){
        this.name = name;
        this.manager = new ContactManager();
    }

    /**
     * constructor for ContactGroup with name and manager argument
     * @param name the name of the group
     * @param cm the contact manager for the group
     */
    public ContactGroup(String name, ContactManager cm){
        this.name = name;
        this.manager = cm;
    }

    public String getName(){return this.name;}
    public ContactManager getManager(){return this.manager;}
    public void setName(String name){this.name = name;}
    public void setManager(ContactManager cm){this.manager = cm;}
}
