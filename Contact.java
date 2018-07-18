public class Contact{
    private String name;
    private String email;
    private String phoneNumber;
    private int index;

    public Contact(String n, String e, String pn, int i){
        this.name = n;
        this.email = e;
        this.phoneNumber = pn;
        this.index = i;
    }

    public String toString(){
	    return "Name: " + this.name +
	           "\nEmail: " + this.email +
                "\nPhone Number: " + this.phoneNumber;
    }

    public String getName(){return this.name;}
    public String getEmail(){return this.email;}
    public String getPhoneNumber(){return this.phoneNumber;}
    public int getIndex(){return this.index;}
    public void setEmail(String e){this.email = e;}
    public void setPhoneNumber(String pn){this.phoneNumber = pn;}
    public void setIndex(int i){this.index = i;}
    
}
