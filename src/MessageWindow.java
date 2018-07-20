import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MessageWindow{
    private JFrame window;
    private JPanel container;
    private JLabel message;
    private int wWidth;
    private int wHeight;

    public MessageWindow(){
        this.window = new JFrame("Default Message Title");
        this.container = new JPanel();
        this.message = new JLabel("Default Message");
        this.wWidth = 300;
        this.wHeight = 100;

        this.window.setLayout(new FlowLayout());
        this.container.setAlignmentX(this.window.CENTER_ALIGNMENT);
        this.container.setAlignmentY(this.window.CENTER_ALIGNMENT);
    }

    public MessageWindow(String title, String message){
        this.window = new JFrame(title);
        this.container = new JPanel();
        this.message = new JLabel(message);
        this.wWidth = 300;
        this.wHeight = 100;

        this.window.setLayout(new FlowLayout());
        this.container.setAlignmentX(this.window.CENTER_ALIGNMENT);
        this.container.setAlignmentY(this.window.CENTER_ALIGNMENT);
    }

    public MessageWindow(String title, String message, int width, int height){
        this.window = new JFrame(title);
        this.container = new JPanel();
        this.message = new JLabel(message);
        this.wWidth = width;
        this.wHeight = height;

        this.window.setLayout(new FlowLayout());
        this.container.setAlignmentX(this.window.CENTER_ALIGNMENT);
        this.container.setAlignmentY(this.window.CENTER_ALIGNMENT);
    }

    public void setUp(){
        this.window.setSize(wWidth, wHeight);
        this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.container.add(this.message);
        this.window.setLocationRelativeTo(null);
        this.window.add(container);
    }

    public void display(){
        if(this.window.isVisible()) this.window.setVisible(false);
        else this.window.setVisible(true);
    }

    public JFrame getWindow(){return this.window;}
    public JPanel getContainer(){return this.container;}
    public JLabel getMessage(){return this.message;}
    public int getwWidth(){return this.wWidth;}
    public int getwHeight(){return this.wHeight;}

    public void setText(String s){
        this.message.setText(s);
    }
}