import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/* This class opens windows and fills it in with information from the ContactManager */
public class WindowManager{

	private static JButton setCenterAlignJButton(JButton component, JFrame frame){
		component.setAlignmentX(frame.CENTER_ALIGNMENT);
		component.setAlignmentY(frame.CENTER_ALIGNMENT);
		return component;
	}

	public static void entryWindow(){
		JFrame mainFrame = new JFrame("Contacts");
		JButton edit = new JButton("Edit");
		JButton view = new JButton("View");

		mainFrame.setLocationRelativeTo(null);
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new FlowLayout());
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				mainFrame.dispose();
				editWindow();
			}
		});

		view.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				mainFrame.dispose();
				viewWindow();
			}
		});

		mainFrame.add(edit);
		mainFrame.add(view);
		mainFrame.setVisible(true);
	}

	// edit tree
	public static void editWindow(){
		JFrame mainFrame = new JFrame("Contact Edit");
		JPanel viewPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		JButton add = new JButton("Add");
		JButton remove = new JButton("Remove");
		JButton back = new JButton("Back");
		JLabel out = new JLabel();
		int nameListLength;

		mainFrame.setSize(400, 400);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new FlowLayout());
		mainFrame.setLocationRelativeTo(null);


		try {
			// generating string of names
			String names[] = new String[500];
			int ni = 0;
			for(Contact c : ContactRuntime.cm.getContactList()){
				names[ni] = c.getName();
				ni++;
			}

			JList<String> nameList = new JList<String>(names);
			JScrollPane scrollPane = new JScrollPane(nameList);
			scrollPane.setPreferredSize(new Dimension(300, 120));

			viewPanel.add(scrollPane);
			mainFrame.add(viewPanel);
			mainFrame.setVisible(true);
		} catch(NullPointerException npe){
			// odds are there are no people on the contact list of this block is ran
			// so print just that and allow them to add contact or go back
			out.setText("No contacts");
			add.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					mainFrame.dispose();
					add();
				}
			});

			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					mainFrame.dispose();
					back();
				}
			});

			viewPanel.add(out);
			controlPanel.add(add);
			controlPanel.add(back);

			mainFrame.add(viewPanel);
			mainFrame.add(controlPanel);
			mainFrame.setVisible(true);
		}
	}

	public static void add(){
		JFrame mainFrame = new JFrame("Contact Add");
		JTextField name = new JTextField("Name", 16);
		JTextField email = new JTextField("Email",16);
		JTextField phoneNumber = new JTextField("Phone Number",16);
		JButton back = new JButton("Back");
		JButton submit = new JButton("Submit");
		JPanel outPanel = new JPanel();
		JPanel controlPanel = new JPanel();

		mainFrame.setLayout(new GridLayout(5,1));
		mainFrame.setSize(400, 400);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String nameTemp = name.getText();
				String emailTemp = email.getText();
				String phoneNumberTemp = phoneNumber.getText();

				ContactRuntime.cm.addContact(nameTemp, emailTemp, phoneNumberTemp);
				mainFrame.dispose();
				entryWindow();
			}
		});

		outPanel.add(name);
		outPanel.add(email);
		outPanel.add(phoneNumber);
		controlPanel.add(back);
		controlPanel.add(submit);

		mainFrame.add(outPanel);
		mainFrame.add(controlPanel);
		mainFrame.setVisible(true);
	}

	public static void remove(){

	}

	public static void back(){

	}

	// view tree
	public static void viewWindow(){
		System.out.println("VIEW WINDOW");
	}
}
