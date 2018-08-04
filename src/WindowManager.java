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
    private final static String PROGRAM_TITLE = "Contacts v2.0.3";

    /**
     * creates the first window of the program, allowing the user to edit or view the contact list
     */
    public static void entryWindow(){
        // preloading group if selected
        if(ContactRuntime.getContactGroupManager().isShowOnStart()){
            groupWindow(ContactRuntime.getContactGroupManager().getIndexOfStart());
            return;
        }

        // creating all the widgets
        JFrame mainFrame = commonFrame(PROGRAM_TITLE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel selectionPane = new JPanel();
        JPanel actionPane = new JPanel();
        JLabel listLabel = new JLabel("Groups: ");
        JLabel alwaysLoadLabel = new JLabel("Always Load Group:    ");
        JComboBox<String> groupList = new JComboBox<String>();
        JCheckBox[] alwaysLoad = {new JCheckBox()};
        JButton open = new JButton("Open");
        JButton save = new JButton("Save");
        JButton newGroup = new JButton("New Group");
        JButton editGroup = new JButton("Edit Group");
        ArrayList<ContactGroup> groups = ContactRuntime.getContactGroupManager().getGroups();
        int index[] = {0};

        groupList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                index[0] = groupList.getSelectedIndex();
                ContactRuntime.getContactGroupManager().setIndexOfStart(index[0]);
            }
        });

        alwaysLoad[0].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                alwaysLoad[0] = (JCheckBox)itemEvent.getItem();
                if(alwaysLoad[0].isSelected()){
                    ContactRuntime.getContactGroupManager().setShowOnStart(true);
                    ContactRuntime.getContactGroupManager().setIndexOfStart(index[0]);
                }else{
                    ContactRuntime.getContactGroupManager().setShowOnStart(false);
                }

                ContactRuntime.save();
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent){
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                ContactRuntime.setIndex(index[0]);
                groupWindow(index[0]);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ContactRuntime.save();
            }
        });

        newGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                createGroupWindow();
            }
        });

        editGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                ContactRuntime.setIndex(index[0]);
                editGroupWindow(index[0]);
            }
        });

        for(ContactGroup cg : groups){
            groupList.addItem(cg.getName());
        }

        // filling the selection pane
        selectionPane.setLayout(new GridLayout(2,1));
        selectionPane.add(listLabel);
        selectionPane.add(groupList);
        selectionPane.add(alwaysLoadLabel);
        selectionPane.add(alwaysLoad[0]);

        // filling the the action pane
        actionPane.add(open);
        actionPane.add(save);
        actionPane.add(newGroup);
        actionPane.add(editGroup);

        mainFrame.add(selectionPane);
        mainFrame.add(actionPane);
        mainFrame.setVisible(true);
    }

    /**
     * creates a new group
     */
    public static void createGroupWindow(){
        JFrame mainFrame = commonFrame(PROGRAM_TITLE);
        JPanel fieldPane = new JPanel();
        JPanel actionPane = new JPanel();
        JTextField groupName = new JTextField("Enter group name", 10);
        JButton create = new JButton("Create");
        JButton cancel = new JButton("Cancel");

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ContactRuntime.getContactGroupManager().addContactGroup(new ContactGroup(groupName.getText()));
                ContactRuntime.save();
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                entryWindow();
            }
        });

        fieldPane.add(groupName);
        actionPane.add(cancel);
        actionPane.add(create);

        mainFrame.add(fieldPane);
        mainFrame.add(actionPane);
        mainFrame.setVisible(true);
    }

    public static void editGroupWindow(int i){
        JFrame mainFrame = commonFrame(PROGRAM_TITLE);
        JPanel fieldPane = new JPanel();
        JPanel actionPane = new JPanel();
        ContactGroup cg = ContactRuntime.getContactGroupManager().getGroups().get(i);
        JTextField groupName = new JTextField(cg.getName(), 10);
        JButton cancel = new JButton("Cancel");
        JButton remove = new JButton("Remove");
        JButton save = new JButton("Save");

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                entryWindow();
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame confirmationFrame = commonFrame("Delete Group");
                JPanel warningPane = new JPanel();
                JPanel actionPane = new JPanel();
                JLabel warning = new JLabel("<html><body><h4> Warning you are about to delete a group. <br/> type in group name to confirm </h4></body></html>");
                JTextField nameField = new JTextField(10);
                JButton cancel = new JButton("Cancel");
                JButton confirm = new JButton("Confirm");

                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        confirmationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        confirmationFrame.dispose();
                    }
                });

                confirm.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(nameField.getText().equals(cg.getName())){
                            ContactRuntime.getContactGroupManager().removeContactGroup(i);
                            confirmationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            confirmationFrame.dispose();
                            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            mainFrame.dispose();
                            entryWindow();
                        }else{
                            warning.setText("<html><body><h4> Name doesn't match group, try again</h4></body></html>");
                            nameField.setText("");
                        }
                    }
                });

                warningPane.add(warning);
                actionPane.add(nameField);
                actionPane.add(cancel);
                actionPane.add(confirm);

                confirmationFrame.add(warningPane);
                confirmationFrame.add(actionPane);
                confirmationFrame.setVisible(true);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                ContactRuntime.getContactGroupManager().getGroups().get(ContactRuntime.getIndex()).setName(groupName.getText());
                ContactRuntime.save();
                entryWindow();
            }
        });

        fieldPane.add(groupName);

        actionPane.add(cancel);
        actionPane.add(remove);
        actionPane.add(save);

        mainFrame.add(fieldPane);
        mainFrame.add(actionPane);

        mainFrame.setVisible(true);


    }

    public static void groupWindow(int i){
        // creating widgets
        ArrayList<Contact> contacts = ContactRuntime.getContactGroupManager().getGroups().get(i).getManager().getContacts();
        JFrame mainFrame = commonFrame(PROGRAM_TITLE);
        mainFrame.setSize(500, 400);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel titlePane = new JPanel();
        JPanelIndexKeeper info = createListPanel(contacts);
        JPanel actionPane = new JPanel();
        JLabel groupLabel = new JLabel("Group: " + ContactRuntime.getContactGroupManager().getGroups().get(i).getName());
        JButton back = new JButton("Back");
        JButton remove = new JButton("Remove");
        JButton view = new JButton("View");
        JButton edit = new JButton("Edit");
        JButton add = new JButton("Add");

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                entryWindow();
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                JFrame confirmBox = commonFrame("Delete Contact?");
                JPanel warningPane = new JPanel();
                JPanel actionPane = new JPanel();
                JLabel warning = new JLabel("<html><body><h4>Warning:<br/> You are about to delete contact \"" + contacts.get(info.getIndex()).getName() + "\". <br/>Are you sure you want to do this </h4> </body></html>");
                JButton cancel = new JButton("Cancel");
                JButton confirm = new JButton("Confirm");

                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        mainFrame.dispose();
                        groupWindow(ContactRuntime.getIndex());
                    }
                });

                confirm.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        contacts.remove(info.getIndex());
                        ContactRuntime.getContactGroupManager().getGroups().get(ContactRuntime.getIndex()).setManager(new ContactManager(contacts));
                        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        mainFrame.dispose();
                        groupWindow(ContactRuntime.getIndex());
                        ContactRuntime.save();
                    }
                });

                warningPane.add(warning);
                actionPane.add(cancel);
                actionPane.add(confirm);

                confirmBox.add(warningPane);
                confirmBox.add(actionPane);

                confirmBox.setVisible(true);
            }
        });

        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                viewContact(info.getIndex());
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.dispose();
                editContactWindow(info.getIndex());
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



        titlePane.add(groupLabel);

        actionPane.setLayout(new GridLayout(1, 5));
        actionPane.add(back);
        actionPane.add(remove);
        actionPane.add(view);
        actionPane.add(edit);
        actionPane.add(add);



        mainFrame.add(titlePane);
        mainFrame.add(info);
        mainFrame.add(actionPane);

        if(ContactRuntime.getContactGroupManager().isShowOnStart()){
            JLabel message = new JLabel("Uncheck to be able to go back ");
            JPanel checkboxPane = new JPanel();
            JCheckBox[] showOnStart = {new JCheckBox()};
            showOnStart[0].setSelected(ContactRuntime.getContactGroupManager().isShowOnStart());

            showOnStart[0].addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent itemEvent) {
                    showOnStart[0] = (JCheckBox)itemEvent.getItem();
                    ContactRuntime.getContactGroupManager().setShowOnStart(showOnStart[0].isSelected());
                }
            });

            checkboxPane.add(message);
            checkboxPane.add(showOnStart[0]);
            mainFrame.add(checkboxPane);

        }

        mainFrame.setVisible(true);



    }


    /**
     * opens window with contact information printed out
     * @param i the index of the contact we want to view
     */
    public static void viewContact(int i){
        JFrame mainFrame = commonFrame(PROGRAM_TITLE);
        JButton back = new JButton("Back");
        JPanel viewPanel = new JPanel();
        JPanel controlPanel = new JPanel();
        Contact c = ContactRuntime.getContactGroupManager().getGroups().get(ContactRuntime.getIndex()).getManager().getContacts().get(i);
        JLabel out = new JLabel();

        // setting all the basic text
        out.setText("<html><body>Name: " + c.getName() + "<br/><br/>Phone Number: " + c.getPhoneNumber() + "<br/<br/>Email: " + c.getEmail() + "<br/><br/>Notes: ");

        // generating the notes text
        for(int j = 0; j < c.getNotes().length(); j++){
            if(c.getNotes().charAt(j) == '\n') out.setText(out.getText() + "<br/>");
            else out.setText(out.getText() + c.getNotes().charAt(j));
        }

        // closing out the html
        out.setText(out.getText() + "</body></html>");

        JScrollPane scrollPane = new JScrollPane(out); // adding scroll pane in case notes are long

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                groupWindow(ContactRuntime.getIndex());
            }
        });

        viewPanel.add(scrollPane);
        controlPanel.add(back);

        mainFrame.add(out);
        mainFrame.add(back);
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
                groupWindow(ContactRuntime.getIndex());
            }
        });

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.dispose();
                ArrayList<Contact> contacts = ContactRuntime.getContactGroupManager().getGroups().get(ContactRuntime.getIndex()).getManager().getContacts();

                // get text information
                String name = nameField.getText(), phoneNumber = phoneNumberField.getText(), email = emailField.getText(), extra = extraArea.getText();
                contacts.add(new Contact(name, phoneNumber, email, extra));
                ContactRuntime.getContactGroupManager().getGroups().get(ContactRuntime.getIndex()).setManager(new ContactManager(contacts));
                groupWindow(ContactRuntime.getIndex());
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
        ArrayList<Contact> contacts = ContactRuntime.getContactGroupManager().getGroups().get(ContactRuntime.getIndex()).getManager().getContacts();
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
                groupWindow(ContactRuntime.getIndex());
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
                ContactRuntime.getContactGroupManager().getGroups().get(ContactRuntime.getIndex()).setManager(new ContactManager(contacts));
                groupWindow(ContactRuntime.getIndex());
                ContactRuntime.save();
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
        ContactRuntime.save();
        JFrame frame = new JFrame(n);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        return frame;
    }

    /**
     * creates a JPanel which contains a JList of the contacts
     * @return the completed JPanelIndex keeper
     */
    private static JPanelIndexKeeper createListPanel(ArrayList<Contact> c){
        JPanelIndexKeeper out = new JPanelIndexKeeper(0);
        ArrayList<Contact> contacts = c;
        JComponent output;                                                                  // will be used for JList or JLabel depending on if there are contacts or not, both JLabel and JList are children of JComponent
        int [] nameSelectedIndex = {0};

        // creating the right type of output for the amount of contacts we have
        if(contacts.size() == 0){
            output = new JLabel("No contacts");                                          // if there are no contacts, than that'll mean that we simple tell the user that
            out.add(output);
        }else {
            // creating an array of strings for JList to use
            String contactNames[] = new String[contacts.size()];                            // if there is at least one contact, than we create a JList which the user can use to scroll through there contacts

            // filling that array with contact names
            for (int i = 0; i < contacts.size(); i++) contactNames[i] = contacts.get(i).getName();

            // create the JList and JScrollPane for the list
            output = new JList<String>(contactNames);
            JScrollPane scrollPane = new JScrollPane(output);
            scrollPane.setPreferredSize(new Dimension(200, 90));
            ((JList) output).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);         // makes it so we can only select one name at a time
            ((JList) output).addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent listSelectionEvent) {
                    int index = ((JList) output).getSelectedIndex();                        // get the what we have selected
                    out.setIndex(index);

                }

            });

            out.add(scrollPane);
        }

        return out;
    }
}