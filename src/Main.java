import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        System.out.println("Test");

        System.out.println("hello world");
        mainWindow();
    }

    public static void mainWindow(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("App Store");
        frame.setSize(1280,720);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        JLabel titleMessage = new JLabel("Welcome to my app store");
        titleMessage.setFont(new Font("Times", Font.BOLD,20));
        titleMessage.setBounds(50,20,400,25);
        panel.add(titleMessage);
        frame.setVisible(true);



    }
}