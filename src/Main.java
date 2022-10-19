import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        Backend backend  = new Backend();
        //mainWindow();


    }

    public static void mainWindow(){

        JLayeredPane windowLayer = new JLayeredPane();
        windowLayer.setBackground(Color.LIGHT_GRAY);
        windowLayer.setBounds(0, 0, 1280, 720);


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("App Store");
        frame.setSize(1280,720);
        frame.setResizable(false);

        JLabel titleMessage = new JLabel("Welcome to my app store");
        titleMessage.setFont(new Font("TimesRoman", Font.BOLD,30));
        titleMessage.setBounds(500,20,500,35);


//      Search bar need the search function to be added to it
        JTextField searchInput = new JTextField("Search....");
        searchInput.setBounds(500, 60, 500, 50);
        // need to change the base text so when input is added it will remove "search..."

//      this is the part where we add the sidebar and the features to it
        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.blue);
        sideBar.setBounds(0, 0, 300, 720);

        JButton sideLabel1 = new JButton();
        sideLabel1.setBounds(0, 150, 300, 50);
        sideLabel1.setText("featured apps");
        sideLabel1.setFont(new Font("TimesRoman", Font.BOLD, 25 ));

        JButton sideLabel2 = new JButton();
        sideLabel2.setBounds(0, 225, 300, 50);
        sideLabel2.setText("programs");
        sideLabel2.setFont(new Font("SansSerif", Font.BOLD, 25 ));

        JButton sideLabel3 = new JButton();
        sideLabel3.setBounds(0, 300, 300, 50);
        sideLabel3.setText("Third Slot");
        sideLabel3.setFont(new Font("ComicSans", Font.BOLD, 25 ));

        //calls

        displayApp("first app", 500,350 ,(short)5, "test", windowLayer);
        displayApp("second app", 750,350 ,(short)5, "test", windowLayer);
        displayApp("Does this work", 500,600 ,(short)5, "test", windowLayer);
        displayApp("fourth app", 750,600,(short)5,"test",windowLayer);


//      Needs to be programmed but it is the scrollbar
        JScrollBar sideSlider = new JScrollBar();
        sideSlider.setBounds(1260, 0, 20, 720);

//      This is where the layers are added
        windowLayer.add(titleMessage, Integer.valueOf(0));
        windowLayer.add(searchInput, Integer.valueOf(0));
        windowLayer.add(sideBar, Integer.valueOf(0));

        windowLayer.add(sideSlider, Integer.valueOf(0));
        windowLayer.add(sideLabel1, Integer.valueOf(1));
        windowLayer.add(sideLabel2, Integer.valueOf(1));
        windowLayer.add(sideLabel3, Integer.valueOf(1));

        frame.setVisible(true);
        frame.add(windowLayer);
    }

    public static void displayApp(String name, int cordX, int cordY, short starRating,  String image, JLayeredPane pane){
        JPanel thirdAppSlot = new JPanel();
        thirdAppSlot.setBackground(Color.CYAN);
        thirdAppSlot.setBounds(cordX, cordY-150, 200, 200);

        String thirdAppNameString = name;

        JLabel thirdAppName = new JLabel();
        thirdAppName.setBounds(cordX, cordY, 200, 30);
        thirdAppName.setText(thirdAppNameString);
        thirdAppName.setBackground(Color.lightGray);
        thirdAppName.setFont(new Font("TimesRoman", Font.BOLD, 15));

        JButton thirdAppDownload = new JButton("\u21e3");
        thirdAppDownload.setBounds(cordX+150, cordY, 30, 30);
        thirdAppDownload.setBackground(Color.LIGHT_GRAY);
        thirdAppDownload.setFont(new Font("TimesRoman", Font.BOLD, 10));

        String stringStarRating = Short.toString(starRating);
        JLabel stars = new JLabel(stringStarRating);
        stars.setBounds(cordX, cordY-100, 200,30);
        stars.setBackground(Color.LIGHT_GRAY);
        pane.add(thirdAppSlot,0);
        pane.add(thirdAppName,Integer.valueOf(1));
        pane.add(thirdAppDownload,Integer.valueOf(1));
        pane.add(stars);



    }
}