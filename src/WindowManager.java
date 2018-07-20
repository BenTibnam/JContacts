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
    private final static String PROGRAM_TITLE = "Contacts v1.0.0";

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

        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.dispose();
                viewWindow();
            }
        });

        buttonPanel.add(edit);
        buttonPanel.add(view);
        mainFrame.add(buttonPanel);
        mainFrame.setVisible(true);
    }


    /**
     *
     */
    public static void viewWindow(){
        JFrame mainFrame = commonFrame(PROGRAM_TITLE);
        mainFrame.setLayout(new GridLayout(2, 1));
        JButton back = new JButton("Back");                                             // sends back to the last frame
        JButton view = new JButton("View");
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

        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                viewContact(nameSelectedIndex[0]);

            }
        });

        control.add(back);
        control.add(view);
        mainFrame.add(info);
        mainFrame.add(control);
        mainFrame.setVisible(true);
    }

    public static void viewContact(int i){
        JFrame mainFrame = commonFrame(PROGRAM_TITLE);
        JButton back = new JButton("Back");
        JPanel viewPanel = new JPanel();
        JPanel controlPanel = new JPanel();
        Contact c = ContactRuntime.getContactManager().getContacts().get(i);
        JLabel out = new JLabel("<html><body><h4>Name: " + c.getName() +"</h4><br/><h4>Phone Number: " + c.getPhoneNumber() +"</h4><br/><h4>Email: " + c.getEmail() + "</h4><br/><h4>Extra: " + c.getNotes() + "</h4><br/></body></html>");

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewWindow();
            }
        });

        viewPanel.add(out);
        controlPanel.add(back);

        mainFrame.add(out);
        mainFrame.add(back);
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
                try {
                    editContactWindow(nameSelectedIndex[0]);
                    mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    mainFrame.dispose();
                }catch (IndexOutOfBoundsException iobe){
                    MessageWindow err = new MessageWindow("Error: No contacts", "Error: cannot edit when there are no contacts in list", 400, 200);
                    err.setUp();
                    err.display();

                }
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
                ContactRuntime.save();
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
        ArrayList<Contact> contacts = ContactRuntime.getContactManager().getContacts();
        Contact target = contacts.get(i);
        JFrame mainFrame = commonFrame(PROGRAM_TITLE);
        mainFrame.setLayout(new GridLayout(2,1));
        JTextField nameField = new JTextField(target.getName(),15);
        JTextField phoneNumberField = new JTextField(target.getPhoneNumber(),10);
        JTextField emailField = new JTextField(target.getEmail(),10);
        JTextArea extraArea = new JTextArea(target.getNotes(),10, 25);
        JButton back = new JButton("Back");
        JButton save = new JButton("Save");
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

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();


                // get text information
                String name = nameField.getText(), phoneNumber = phoneNumberField.getText(), email = emailField.getText(), extra = extraArea.getText();
                contacts.set(i, new Contact(name, phoneNumber, email, extra));
                ContactRuntime.setContactManager(new ContactManager(contacts));
                editWindow();
            }
        });

        fieldPanel.add(nameField);
        fieldPanel.add(phoneNumberField);
        fieldPanel.add(emailField);
        fieldPanel.add(scrollPane);

        controlPanel.add(back);
        controlPanel.add(save);

        mainFrame.add(fieldPanel);
        mainFrame.add(controlPanel);

        mainFrame.setVisible(true);
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