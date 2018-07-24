import java.io.Serializable;
import java.util.ArrayList;

public class ContactGroupManager implements Serializable {
    private ArrayList<ContactGroup> groups;

    /**
     * default constructor for ContactGroupManager
     */
    public ContactGroupManager(){
        this.groups = new ArrayList<ContactGroup>();
    }

    /**
     * constructor which takes in preexisting ContactGroup ArrayList
     * @param g a preexisting ContactGroup ArrayList
     */
    public ContactGroupManager(ArrayList<ContactGroup> g){
        this.groups = g;
    }

    /**
     * adds a new ContactGroup to the groups ArrayList
     * @param cg the new ContactGroup being added
     */
    public void addContactGroup(ContactGroup cg){
        this.groups.add(cg);
    }

    /**
     * replace a ContactGroup at index i
     * @param i the index of the group being replaced
     * @param cg the group replacing groups[i]
     */
    public void redefineContactGroup(int i, ContactGroup cg){
        this.groups.set(i, cg);
    }

    /**
     * removes group at i
     * @param i the index of the group that is being removed
     */
    public void removeContactGroup(int i){
        this.groups.remove(i);
    }

    public ArrayList<ContactGroup> getGroups(){return this.groups;}
    public void setGroups(ArrayList<ContactGroup> cg){this.groups = cg;}

}
