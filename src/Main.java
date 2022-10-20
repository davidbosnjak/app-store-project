import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Main implements ActionListener, FocusListener{
    static JButton featuredButton = new JButton();
    static JButton programmingButton = new JButton();
    static JButton communicationButton = new JButton();
    static  JButton browserButton = new JButton();
    static int startX = 500;
    static int currX = 500;
    static int currY = 350;
    static int xIncrement = 250;
    static int yIncrement = 250;
    static JLayeredPane windowLayer = new JLayeredPane();
    static JLayeredPane appPane = new JLayeredPane();
    static JPanel newPane = new JPanel();
    static JTextField searchInput = new JTextField("Search....");

    static String buttonPressed = new String();


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


        featuredButton.setBounds(10, 150, 280, 50);
        featuredButton.setText("Featured");
        featuredButton.setFont(new Font("TimesRoman", Font.BOLD, 25 ));
        featuredButton.addActionListener(new Main());


        programmingButton.setBounds(10, 225, 280, 50);
        programmingButton.setText("Programming");
        programmingButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        programmingButton.addActionListener(new Main());



        communicationButton.setBounds(10, 300, 280, 50);
        communicationButton.setText("Communication");
        communicationButton.setFont(new Font("ComicSans", Font.BOLD, 25 ));
        communicationButton.addActionListener(new Main());



        browserButton.setBounds(10, 375, 280, 50);
        browserButton.setText("Browsers");
        browserButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        browserButton.addActionListener(new Main());

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
        windowLayer.add(featuredButton, Integer.valueOf(2));
        windowLayer.add(programmingButton, Integer.valueOf(2));
        windowLayer.add(communicationButton, Integer.valueOf(2));
        windowLayer.add(browserButton, Integer.valueOf(2));
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

        JLabel appNameLabel = new JLabel();
        appNameLabel.setBounds(cordX, cordY, 200, 30);
        appNameLabel.setText(thirdAppNameString);
        appNameLabel.setBackground(Color.lightGray);
        appNameLabel.setFont(new Font("TimesRoman", Font.BOLD, 15));

        JButton downloadButton = new JButton();
        downloadButton.setText("\u21e3");
        downloadButton.setBounds(cordX+150, cordY, 45, 45);
        downloadButton.setBackground(Color.LIGHT_GRAY);
        downloadButton.setFont(new Font("TimesRoman", Font.BOLD, 10));
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = Backend.getLinkFromDatabase(name, 1);
                Backend.openWebsite(url);
            }
        });

        JButton websiteButton = new JButton();
        websiteButton.setText("w");
        websiteButton.setFont(new Font("TimesRoman", Font.BOLD, 10));
        websiteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(name);
                String url = Backend.getLinkFromDatabase(name,0);
                System.out.println("this is the url"+url);
                Backend.openWebsite(url);
            }
        });
        websiteButton.setBounds(cordX+110, cordY, 45,45);
        websiteButton.setBackground(Color.LIGHT_GRAY);


        String stringStarRating = "\u2605".repeat(starRating);

        JLabel stars = new JLabel(stringStarRating);
        stars.setBounds(cordX, cordY-100, 200,30);
        stars.setBackground(Color.LIGHT_GRAY);
        layPane.add(thirdAppSlot,0);
        layPane.add(appNameLabel,Integer.valueOf(1));
        layPane.add(downloadButton,Integer.valueOf(1));
        layPane.add(websiteButton, Integer.valueOf(1));
        layPane.add(stars,Integer.valueOf(2));
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
        boolean refresh = false;
       if(actionEvent.getActionCommand().equals("w")){
           System.out.println("requested website");
           Backend.openWebsite("https://youtube.com");
           refresh = true;
       }

       windowLayer.remove(newPane);
       newPane.removeAll();
        if(actionEvent.getSource() == searchInput){
            System.out.println("something happened in search");
            String userEntry = searchInput.getText();
            LinkedHashSet<String> apps= Backend.Search(userEntry);
            ArrayList<String> appList  = new ArrayList<>();
            for(String app : apps){
                appList.add(app);
            }
            displayAppsFromString(appList, currX,currY,startX,xIncrement,yIncrement,newPane);
        }

        if(actionEvent.getSource() == featuredButton){
            ArrayList<String> apps = Backend.getAppsForButton("featured");
            displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);
            System.out.println("Featured");


        }
        if(actionEvent.getSource() == programmingButton){
            ArrayList<String> apps = Backend.getAppsForButton("programming");
            displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);

            System.out.println("Programming");
        }
        if(actionEvent.getSource() == communicationButton){
            ArrayList<String> apps = Backend.getAppsForButton("communication");
            displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);

            System.out.println("Communication");

        }
        if(actionEvent.getSource()== browserButton){
            ArrayList<String> apps = Backend.getAppsForButton("browser");
            displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);

            System.out.println("Browsers");
        }
        if(refresh){
            displayAppsFromString(Backend.getAppNames(), currX, currY,startX,xIncrement,yIncrement,newPane);
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