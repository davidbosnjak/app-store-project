import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class Main implements ActionListener, FocusListener{
    static JButton sideLabel1 = new JButton();
    static JButton sideLabel2 = new JButton();
    static JButton sideLabel3 = new JButton();
    static  JButton sideLabel4 = new JButton();
    static int startX = 500;
    static int currX = 500;
    static int currY = 350;
    static int xIncrement = 250;
    static int yIncrement = 250;
    static JLayeredPane windowLayer = new JLayeredPane();
    static JLayeredPane appPane = new JLayeredPane();
    static JPanel newPane = new JPanel();
    static JTextField searchInput = new JTextField("Search....");


    public static void main(String[] args) {

        mainWindow();


    }

    public static void mainWindow(){
        JPanel mainPanel = new JPanel();

        JViewport viewport = new JViewport();

        newPane.setBounds(0,0,1280,720);
        newPane.setLayout(null);

        windowLayer.setBackground(Color.LIGHT_GRAY);
        windowLayer.setBounds(0, 0, 1280, 720);
        appPane.setBounds(0,0,1280,720);

        JLayeredPane scrollableStuff = new JLayeredPane();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("App Store");
        frame.setSize(1280,720);
        frame.setResizable(true);

        JLabel titleMessage = new JLabel("App Store Pro");
        titleMessage.setFont(new Font("TimesRoman", Font.BOLD,30));
        titleMessage.setBounds(500,20,500,35);



//      Search bar need the search function to be added to it
        searchInput.addActionListener(new Main());
        searchInput.addFocusListener(new Main());
        searchInput.setBounds(500, 60, 500, 50);
        // need to change the base text so when input is added it will remove "search..."

//      this is the part where we add the sidebar and the features to it
        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.blue);
        sideBar.setBounds(0, 0, 300, 720);


        sideLabel1.setBounds(10, 150, 280, 50);
        sideLabel1.setText("Featured");
        sideLabel1.setFont(new Font("TimesRoman", Font.BOLD, 25 ));
        sideLabel1.addActionListener(new Main());


        sideLabel2.setBounds(10, 225, 280, 50);
        sideLabel2.setText("Programming");
        sideLabel2.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        sideLabel2.addActionListener(new Main());



        sideLabel3.setBounds(10, 300, 280, 50);
        sideLabel3.setText("Communication");
        sideLabel3.setFont(new Font("ComicSans", Font.BOLD, 25 ));
        sideLabel3.addActionListener(new Main());



        sideLabel4.setBounds(10, 375, 280, 50);
        sideLabel4.setText("Browsers");
        sideLabel4.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        sideLabel4.addActionListener(new Main());

        //calls
        ArrayList<String> apps = Backend.getAppNames();




        displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);



//      Needs to be programmed but it is the scrollbar
        JScrollBar sideSlider = new JScrollBar();
        sideSlider.setBounds(1260, 0, 20, 720);

//      This is where the layers are added
        windowLayer.add(titleMessage, Integer.valueOf(1));
        windowLayer.add(searchInput, Integer.valueOf(1));
        windowLayer.add(sideBar, Integer.valueOf(1));

        windowLayer.add(sideSlider, Integer.valueOf(2));
        windowLayer.add(sideLabel1, Integer.valueOf(2));
        windowLayer.add(sideLabel2, Integer.valueOf(2));
        windowLayer.add(sideLabel3, Integer.valueOf(2));
        windowLayer.add(sideLabel4, Integer.valueOf(2));
        scrollableStuff.setBounds(0,0,1280,720);
        windowLayer.add(scrollableStuff, Integer.valueOf(1));
        windowLayer.add(newPane);
        //windowLayer.add(appPane);
        viewport.add(scrollableStuff);
        viewport.setBounds(0,0,1280,720);



        frame.setVisible(true);
        frame.add(windowLayer);
    }

    public static void displayApp(String name, int cordX, int cordY, short starRating,  String image, JPanel pane){
        JLayeredPane layPane = new JLayeredPane();
        layPane.setBounds(0,0,1280,2000);
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
        layPane.add(thirdAppSlot,0);
        layPane.add(thirdAppName,Integer.valueOf(1));
        layPane.add(thirdAppDownload,Integer.valueOf(1));
        layPane.add(stars);
        pane.add(layPane);



    }

    public static void displayAppsFromString(ArrayList<String> apps, int currX, int currY, int startX, int xIncrement, int yIncrement, JPanel scrollableStuff) {

        int i = 0;
        for (String app : apps) {
            System.out.println(app);
            if (currX == startX) {
                if (i == 0) {
                    displayApp(app, currX, currY, (short) 5, "test", scrollableStuff);
                } else {
                    currX += xIncrement;
                    displayApp(app, currX, currY, (short) 5, "test", scrollableStuff);
                }

            } else {
                currX -= xIncrement;
                currY += yIncrement;
                displayApp(app, currX, currY, (short) 5, "test", scrollableStuff);
            }
            i++;

        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        windowLayer.remove(newPane);
        newPane.removeAll();

        if(actionEvent.getSource() == searchInput){
            System.out.println("something happened in search");
            String userEntry = searchInput.getText();
            HashSet<String> apps= Backend.Search(userEntry);
            ArrayList<String> appList  = new ArrayList<>();
            for(String app : apps){
                appList.add(app);
            }
            displayAppsFromString(appList, currX,currY,startX,xIncrement,yIncrement,newPane);
        }

        if(actionEvent.getSource() == sideLabel1){
            ArrayList<String> apps = Backend.getAppsForButton("featured");
            displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);
            System.out.println("Featured");


        }
        if(actionEvent.getSource() == sideLabel2){
            ArrayList<String> apps = Backend.getAppsForButton("programming");
            displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);

            System.out.println("Programming");
        }
        if(actionEvent.getSource() == sideLabel3){
            ArrayList<String> apps = Backend.getAppsForButton("communication");
            displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);

            System.out.println("Communication");

        }
        if(actionEvent.getSource()== sideLabel4){
            ArrayList<String> apps = Backend.getAppsForButton("browser");
            displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);

            System.out.println("Browsers");
        }

        windowLayer.add(newPane);


    }

    @Override
    public void focusGained(FocusEvent e) {
        System.out.println("Focus gained");
        searchInput.setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
        searchInput.setText("Search...");

    }
}