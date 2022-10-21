import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Main implements ActionListener, FocusListener, AdjustmentListener, MouseWheelListener {
    //all buttons that involve actions must be static class variables because the actionEvent method needs to be able to access them
    static JButton featuredButton = new JButton();
    static JButton programmingButton = new JButton();
    static JButton communicationButton = new JButton();
    static  JButton browserButton = new JButton();
    //starting dimensions and increments for generating panels
    final static int startX = 500;
    final static int currX = 500;
    final static int currY = 200;
    final static int xIncrement = 250;
    final static int yIncrement = 250;
    static JLayeredPane windowLayer = new JLayeredPane();
    static JPanel newPane = new JPanel();
    static JTextField searchInput = new JTextField("Search....");

    static JScrollBar sideSlider = new JScrollBar();
    static JButton allButton = new JButton();
    static JPanel portPanel = new JPanel();
    static JPanel mainPanel = new JPanel();

    //window dimensions
    final static int WINDOW_HEIGHT = 720;
    final static int WINDOW_WIDTH = 1280;

    //colors im using
    final static String buttonColorGrey = "#bdc3c7";
    final static String backGroundGray = "#7f8c8d";
    final static String sideBarBlue = "#3498db";
    final static String appPanelBlue = "#2980b9";

    public static void main(String[] args) {
        mainWindow();
    }

    public static void mainWindow(){



        mainPanel.setBounds(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        mainPanel.setLayout(null);


        //set to 5000 pixels because the pane needs to extend far enough to contain 28 apps
        newPane.setBounds(0,0,WINDOW_WIDTH,5000);
        newPane.setLayout(null);

        portPanel.setLayout(null);
        portPanel.setBounds(0,150,WINDOW_WIDTH,WINDOW_HEIGHT);

        windowLayer.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        //setting up the frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("App Store");
        frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        frame.setResizable(false);

        //title message of the window
        JLabel titleMessage = new JLabel("App Store Pro");
        titleMessage.setFont(new Font("TimesRoman", Font.BOLD,40));
        titleMessage.setBounds(600,20,500,35);


//      Search bar need the search function to be added to it
        searchInput.addActionListener(new Main());
        searchInput.addFocusListener(new Main());
        searchInput.setBounds(500, 80, 500, 50);
        // need to change the base text so when input is added it will remove "search..."

//      this is the part where we add the sidebar and the features to it
        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.decode(sideBarBlue));
        sideBar.setBounds(0, 0, 300,WINDOW_HEIGHT);

        //setting up button for all apps
        allButton.setBounds(10,75,280,50);
        allButton.setText("All Apps");
        allButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        allButton.setBackground(Color.decode(buttonColorGrey));
        allButton.addActionListener(new Main());

        //setting up button for featured apps
        featuredButton.setBounds(10, 150, 280, 50);
        featuredButton.setText("Featured");
        featuredButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        featuredButton.setBackground(Color.decode(buttonColorGrey));
        featuredButton.addActionListener(new Main());

        //setting up button for programming apps
        programmingButton.setBounds(10, 225, 280, 50);
        programmingButton.setText("Programming");
        programmingButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        programmingButton.setBackground(Color.decode(buttonColorGrey));
        programmingButton.addActionListener(new Main());


        //setting up button for communication apps
        communicationButton.setBounds(10, 300, 280, 50);
        communicationButton.setText("Communication");
        communicationButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        communicationButton.setBackground(Color.decode(buttonColorGrey));
        communicationButton.addActionListener(new Main());

        //setting up button for browser apps
        browserButton.setBounds(10, 375, 280, 50);
        browserButton.setText("Browsers");
        browserButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        browserButton.setBackground(Color.decode(buttonColorGrey));
        browserButton.addActionListener(new Main());

        //getting all apps names into a list
        ArrayList<String> apps = Backend.getAppNames();

        //displaying all the apps from the list
        try {
            displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //setting the max value of the scrollbar to the list size *3
        //*3 is arbitrary, but it worked well with everything i tried
        sideSlider.setMaximum((apps.size()-1)*3);
        sideSlider.setBounds(1250, 0, 30, 700);

        //adding an adjustment listener which will be called whenever the scrollbar is moved
        sideSlider.addAdjustmentListener(new Main());
        sideSlider.setUnitIncrement(1);

        //adding everything to the windowLayer
//      This is where the layers are added
        windowLayer.add(titleMessage, Integer.valueOf(1));
        windowLayer.add(searchInput, Integer.valueOf(1));
        windowLayer.add(sideBar, Integer.valueOf(1));

        windowLayer.add(sideSlider, Integer.valueOf(2));
        windowLayer.add(featuredButton, Integer.valueOf(2));
        windowLayer.add(programmingButton, Integer.valueOf(2));
        windowLayer.add(communicationButton, Integer.valueOf(2));
        windowLayer.add(browserButton, Integer.valueOf(2));
        windowLayer.add(allButton, Integer.valueOf(2));

        //setting the backgrounds and adding panels to panels.
        //i need 3 panels because one of the panels must have a large height to accomodate all the apps
        //one panel must serve as a viewport for the large panel so when scrolling the apps dont go on the searchbar
        //one panel contains that viewport as well as the panel containing the searchbar
        newPane.setBackground(Color.decode(backGroundGray));
        portPanel.add(newPane);
        windowLayer.setBackground(Color.decode(backGroundGray));
        mainPanel.add(portPanel);
        mainPanel.setBackground(Color.decode(backGroundGray));
        windowLayer.add(mainPanel);

        //added this for scrolling purposes. works well with a real mouse but questionable with a trackpad
        newPane.addMouseWheelListener(new Main());

        //setting visible to true and adding the windowLayer which contains everything to the frame
        frame.setVisible(true);
        frame.add(windowLayer);
    }

    public static void displayApp(String name, int cordX, int cordY, int starRating,  JPanel pane) throws IOException {
        //using a layeredPane so i can add different panels and components to the app
        JLayeredPane layPane = new JLayeredPane();
        layPane.setBounds(0,0,WINDOW_WIDTH,4000);

        //panel that holds the app, using this to have a background color around the app panel
        JPanel appPanel = new JPanel();
        appPanel.setBackground(Color.decode(appPanelBlue));
        appPanel.setBounds(cordX, cordY-150, 200, 200);

        //label for the app name
        JLabel appNameLabel = new JLabel();
        appNameLabel.setBounds(cordX+10, cordY-120, 200, 30);
        appNameLabel.setText(name);
        appNameLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));

        //getting an icon for the appropriate app. paths are specified relatively and are in the git project
        //all images exist in assets folder
        BufferedImage appImg= ImageIO.read(new File("assets/"+name+".png"));
        //scaling image to 100 by 100 pixels
        Image scaledImg = appImg.getScaledInstance(100,100,Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaledImg);
        //adding the image to a label and displaying it
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(icon);
        imageLabel.setBounds(cordX+100,cordY-125,100,100);

        //doing same operations with download arrow
        BufferedImage downImg= ImageIO.read(new File("assets/downloadArrow.png"));
        Image scaledDownImg = downImg.getScaledInstance(45,45,Image.SCALE_DEFAULT);
        ImageIcon downIcon = new ImageIcon(scaledDownImg);
        JButton downloadButton = new JButton(downIcon);

        downloadButton.setBounds(cordX+150, cordY, 45, 45);
        downloadButton.setBackground(Color.decode(appPanelBlue));
        downloadButton.setFont(new Font("TimesRoman", Font.BOLD, 10));
        //using lambda for this because name and mode can easily be accessed
        //mode means which link it will get from the database. it can either get the download link or the website link
        //1 means download and 0 means website
        downloadButton.addActionListener(e -> {
            String url = Backend.getLinkFromDatabase(name, 1);
            Backend.openWebsite(url);
        });
        //same image operations on website representing image
        BufferedImage webImg= ImageIO.read(new File("assets/website.png"));
        Image scaledWebImg = webImg.getScaledInstance(45,45,Image.SCALE_DEFAULT);
        ImageIcon webIcon = new ImageIcon(scaledWebImg);
        JButton websiteButton = new JButton(webIcon);
        //same lambda but with mode 0
        websiteButton.addActionListener(e -> {
            String url = Backend.getLinkFromDatabase(name,0);
            Backend.openWebsite(url);
        });
        websiteButton.setBounds(cordX+80, cordY, 45,45);
        websiteButton.setBackground(Color.decode(appPanelBlue));

        //repeating the star character a certain amount of times based on the rating
        String stringStarRating = "\u2605".repeat(starRating);
        String categoryText = Backend.getCategoryForApp(name);

        //adding category text under the app name
        JLabel category = new JLabel(categoryText);
        category.setBounds(cordX+10, cordY-95, 100,30);

        JLabel stars = new JLabel(stringStarRating);
        stars.setBounds(cordX+10, cordY-70, 200,30);
        stars.setBackground(Color.LIGHT_GRAY);
        layPane.add(appPanel,0);
        layPane.add(category, Integer.valueOf(2));

        //adding stuff to layered pane and adding the layered pane to the panel
        layPane.add(appNameLabel,Integer.valueOf(1));
        layPane.add(downloadButton,Integer.valueOf(1));
        layPane.add(websiteButton, Integer.valueOf(1));
        layPane.add(stars,Integer.valueOf(2));
        layPane.add(imageLabel,Integer.valueOf(2));
        pane.add(layPane);

    }

    public static void displayAppsFromString(ArrayList<String> apps, int currX, int currY, int startX, int xIncrement, int yIncrement, JPanel pane) throws IOException {
        //this function calculates the position that an app should be based on the previous position
        //it will call the displayApp function based on the coordinates
        int i = 0;
        for (String app : apps) {
            int stars = Backend.getRatingForApp(app);
            System.out.println(app);
            if (currX == startX) {
                if (i == 0) {

                    displayApp(app, currX, currY, stars,  pane);
                } else {
                    currX += xIncrement;
                    displayApp(app, currX, currY, stars,  pane);
                }

            } else {
                currX -= xIncrement;
                currY += yIncrement;
                displayApp(app, currX, currY, stars,  pane);
            }
            i++;

        }
    }


    //this function gets called whenever some sort of action is performed like a button press or a search being executed
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //remove everything first, this is required because it won't really update correctly if you don't
        windowLayer.remove(mainPanel);
        mainPanel.remove(portPanel);
        portPanel.remove(newPane);

        //this boolean only exists if nothing is entered into the search bar
        boolean refresh = false;
        newPane.removeAll();
        if(actionEvent.getSource() == searchInput){

            String userEntry = searchInput.getText();
            if(searchInput.getText().equals("")){
                //if nothing is searched, don't bother looking in the database because that will crash the program, instead just refresh all the apps
                refresh = true;
            }
            else{
                //if the search was not nothing, look in the database and use the search method to find matching apps
                LinkedHashSet<String> apps= Backend.Search(userEntry);
                ArrayList<String> appList  = new ArrayList<>();
                int numOfApps = 0;
                for(String app : apps){
                    appList.add(app);
                    numOfApps++;
                }
                //once matching apps are found, display them
                try {
                    displayAppsFromString(appList, currX,currY,startX,xIncrement,yIncrement,newPane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //update scroll bar in regard to how many apps there are
                sideSlider.setMaximum(numOfApps*3);
        }}

        if(actionEvent.getSource() == featuredButton){
            //get apps associated with button
            ArrayList<String> apps = Backend.getAppsForButton("featured");
            //display apps
            try {
                displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sideSlider.setMaximum(apps.size()*3);


        }
        //same as previous
        if(actionEvent.getSource() == programmingButton){
            ArrayList<String> apps = Backend.getAppsForButton("programming");
            try {
                displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sideSlider.setMaximum(apps.size()*3);

        }
        //same as previous

        if(actionEvent.getSource() == communicationButton){
            ArrayList<String> apps = Backend.getAppsForButton("communication");
            try {
                displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sideSlider.setMaximum(apps.size()*3);


        }
        //same as previous

        if(actionEvent.getSource()== browserButton){
            ArrayList<String> apps = Backend.getAppsForButton("browser");
            try {
                displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sideSlider.setMaximum(apps.size()*3);

        }
        //same as previous

        if(actionEvent.getSource()== allButton || refresh){
            try {
                ArrayList<String> allApps = Backend.getAppNames();
                displayAppsFromString(allApps, currX, currY,startX,xIncrement,yIncrement,newPane);
                sideSlider.setMaximum((allApps.size()-1)*3);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //adding stuff back to update it
        windowLayer.add(mainPanel);
        mainPanel.add(portPanel);
        portPanel.add(newPane);


    }

    //if the user clicked into the search bar get rid of the "search..."
    @Override
    public void focusGained(FocusEvent e) {
        searchInput.setText("");
    }

    //if user clicked out of search bar, bring back "search..."
    @Override
    public void focusLost(FocusEvent e) {
        searchInput.setText("Search...");

    }

    //is the scroll bar was changed, update the panel
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        newPane.setBounds(0,sideSlider.getValue()*-50,10000,7000);
    }

    //is the mouse wheel was moved, move the slider to reflect that
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if(notches<0){
            sideSlider.setValue(sideSlider.getValue()-1);

        }
        else{
            sideSlider.setValue(sideSlider.getValue()+1);

        }
    }
}