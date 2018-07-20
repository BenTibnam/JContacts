import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

/**
 * creates the windows for each function of the program
 * all of the classes methods are static, that way we can
 * call them without needing to create in instance of the class
 * as the program contains no data which is important enough
 */
public class WindowManager {
    private final static String PROGRAM_TITLE = "Contacts v0.0.1";

    /**
     * creates the first window of the program, allowing the user to edit or view the contact list
     */
    public static void entryWindow(){
        JFrame mainFrame = commonFrame(PROGRAM_TITLE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        JButton edit = new JButton("Edit");
        JButton view = new JButton("View");

        // setting it so the buttons create the right window
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                editWindow();
            }
        });

        buttonPanel.add(edit);
        buttonPanel.add(view);
        mainFrame.add(buttonPanel);
        mainFrame.setVisible(true);
    }

    /**
     * creates the edit window, allowing user to add or remove contacts
     */
    public static void editWindow(){
        JFrame mainFrame = commonFrame(PROGRAM_TITLE);
        mainFrame.setLayout(new GridLayout(2, 1));
        JButton back = new JButton("Back");                                             // sends back to the last frame
        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton edit = new JButton("Edit");
        JPanel info = new JPanel();
        JPanel control = new JPanel();
        ArrayList<Contact> contacts = ContactRuntime.getContactManager().getContacts();
        JComponent output;                                                                  // will be used for JList or JLabel depending on if there are contacts or not, both JLabel and JList are children of JComponent
        int [] nameSelectedIndex = {0};                                                     // to access this variable in a different class, it must be in an array first

        // creating the right type of output for the amount of contacts we have
        if(contacts.size() == 0){
            output = new JLabel("No contacts");                                          // if there are no contacts, than that'll mean that we simple tell the user that
            info.add(output);
        }else{
            // creating an array of strings for JList to use
            String contactNames[] = new String[contacts.size()];                            // if there is at least one contact, than we create a JList which the user can use to scroll through there contacts

            // filling that array with contact names
            for(int i = 0; i < contacts.size(); i++) contactNames[i] = contacts.get(i).getName();

            // create the JList and JScrollPane for the list
            output = new JList<String>(contactNames);
            JScrollPane scrollPane = new JScrollPane(output);
            scrollPane.setPreferredSize(new Dimension(200, 90));
            ((JList) output).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);         // makes it so we can only select one name at a time
            ((JList) output).addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent listSelectionEvent) {
                    int index = ((JList) output).getSelectedIndex();                        // get the what we have selected
                    nameSelectedIndex[0] = index;                                                // set the string so we know what name is selected, that way we can look it up later
                }
            });

            info.add(scrollPane);
        }

        // adding functionality to the buttons
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                entryWindow();
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                addWindow();
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                editContactWindow(nameSelectedIndex[0]);
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                contacts.remove(nameSelectedIndex[0]);
                ContactRuntime.setContactManager(new ContactManager(contacts));
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                editWindow();
            }
        });

        control.add(back);
        control.add(add);
        control.add(edit);
        control.add(remove);

        mainFrame.add(info);
        mainFrame.add(control);
        mainFrame.setVisible(true);

    }

    /**
     * creates window that allows us to add contacts to the list, once contact is added, we reopen edit window
     */
    public static  void addWindow(){
        JFrame mainFrame = commonFrame(PROGRAM_TITLE);
        mainFrame.setLayout(new GridLayout(2,1));
        JTextField nameField = new JTextField("Name",15);
        JTextField phoneNumberField = new JTextField("Phone Number",10);
        JTextField emailField = new JTextField("Email",10);
        JTextArea extraArea = new JTextArea("Extra Information",10, 25);
        JButton back = new JButton("Back");
        JButton submit = new JButton("Submit");
        JPanel fieldPanel = new JPanel();
        JPanel controlPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(extraArea);
        extraArea.setLineWrap(true);
        extraArea.setWrapStyleWord(true);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                editWindow();
            }
        });

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                ArrayList<Contact> contacts = ContactRuntime.getContactManager().getContacts();

                // get text information
                String name = nameField.getText(), phoneNumber = phoneNumberField.getText(), email = emailField.getText(), extra = extraArea.getText();
                contacts.add(new Contact(name, phoneNumber, email, extra));
                ContactRuntime.setContactManager(new ContactManager(contacts));
                editWindow();
            }
        });

        fieldPanel.add(nameField);
        fieldPanel.add(phoneNumberField);
        fieldPanel.add(emailField);
        fieldPanel.add(scrollPane);

        controlPanel.add(back);
        controlPanel.add(submit);

        mainFrame.add(fieldPanel);
        mainFrame.add(controlPanel);

        mainFrame.setVisible(true);

    }

    /**
     * creates a window that allows us to edit a contact that already exists, once we finish editing the contact, we reopen the list editor
     * @param i the index of the contact that we are editing in the ContactManager
     */
    public static void editContactWindow(int i){

    }


    /**
     * creates the most common type of JFrame used in this program
     * @param n the title of the JFrame
     * @return the frame after it has been created
     */
    private static JFrame commonFrame(String n){
        JFrame frame = new JFrame(n);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        return frame;
    }
}

/**
 * TODO: create the add window and the edit window and test out functionality
 */