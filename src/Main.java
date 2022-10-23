import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
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
    static JButton browserButton = new JButton();
    static JButton settingsButton = new JButton();

    //starting dimensions and increments for generating panels
    final static int startX = 500;
    final static int currX = 500;
    final static int currY = 200;
    final static int xIncrement = 250;
    final static int yIncrement = 250;
    static JLayeredPane windowLayer = new JLayeredPane();
    static JPanel scrollablePanel = new JPanel();
    static JTextField searchInput = new JTextField("Search....");

    static JScrollBar sideSlider = new JScrollBar();
    static JButton allButton = new JButton();
    static JPanel portPanel = new JPanel();
    static JPanel mainPanel = new JPanel();

    //window dimensions
    final static int WINDOW_HEIGHT = 720;
    final static int WINDOW_WIDTH = 1280;
    static int currentIndexAccent = 0;

    //colors im using
    final static String buttonColorGrey = "#090909";
    final static String backGroundGray = "#1f1f1f";
    final static String sideBarGrey = "#454545";
    static String sideBarColor = sideBarGrey;
    final static String blueAccent = "#2980b9";
    final static String greenAccent = "#00b894";
    final static String purpleAccent ="#a29bfe";
    final static String yellowAccent = "#fdcb6e";
    final static String whiteAccent = "#FFFFFF";
    final static String pinkAccent = "#fd79a8";
    static String accentColor ="#ff4343";
    static String panelColor = sideBarGrey;
    final static String redAccent ="#ff4343";
    static String titleColor = "#000000";
    static String blackColor = "#000000";
    static String sideBarWhite = "#ecf0f1";

    //drop down menus
    static JComboBox<String> accentOptions = new JComboBox<>(new String[]{"Red","Blue","Green","Purple","Yellow","White","Pink"});
    //static JButton settingsSubmit = new JButton();
    static JComboBox<String> themeOptions = new JComboBox<>(new String[]{"Dark","Light"});
    static JComboBox<String> panelColorOptions = new JComboBox<>(new String[]{"Grey","Red","Blue","Green","Purple","Yellow","White","Pink"});

    static JPanel sideBar = new JPanel();
    static JPanel topPanel = new JPanel();
    static JLabel titleMessage = new JLabel("App Store Pro");



    public static void main(String[] args) throws IOException {
        mainWindow();
    }

    //method that displays and opens the settings menu used to change colors and themes
    public static void settings(){
        //new panel
        JPanel settingsPanel = new JPanel();
        settingsPanel.setBounds(0,0,200,300);
        settingsPanel.setLayout(null);
        //labels and dropdowns
        //worth noting that action listeners were not added here, they were added in the main window function because
        JLabel accentColorLabel = new JLabel("Select accent color");
        accentColorLabel.setBounds(10,20,200,20);
        settingsPanel.add(accentColorLabel);
        accentOptions.setBounds(10,40,100,20);

        settingsPanel.add(accentOptions);

        JLabel themeLabel = new JLabel("Select app theme");
        themeLabel.setBounds(10,70,80,20);
        themeOptions.setBounds(10,90,100,20);

        JLabel panelColorLabel = new JLabel("Select panel color");
        panelColorLabel.setBounds(10,120,80,20);

        panelColorOptions.setBounds(10,140,100,20);
        settingsPanel.add(panelColorLabel);
        settingsPanel.add(panelColorOptions);
        settingsPanel.add(themeLabel);
        settingsPanel.add(themeOptions);

        JFrame settingsFrame = new JFrame();
        //exit operation is dispose on close because I want it to just close the settings window and not the entire program
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setTitle("Settings");
        settingsFrame.setSize(200,300);
        settingsFrame.setResizable(false);
        settingsFrame.add(settingsPanel);
        settingsFrame.setVisible(true);

    }
    public static void mainWindow() throws IOException {


        //there are 3 main panels: scrollablePanel, portPanel, and mainPanel.
        //scrollablePanel is the panel that holds all of the apps. this panels bounds will be modifying when the scroll bar is moved
        //portPanel acts as the viewport for the scrollablePanel. i dont want the apps taking up the full height of the screen so the portPanel limits its dimensions
        //mainPanel contains the portPanel and spans all across the window. the point of this panel is to be able to change the background easily
        //the windowLayer contains the mainPanel as well as the sidebar panel and the windowLayer gets added to the frame
        mainPanel.setBounds(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        mainPanel.setLayout(null);


        //set to 5000 pixels because the pane needs to extend far enough to contain 28 apps
        scrollablePanel.setBounds(0,0,WINDOW_WIDTH,5000);
        scrollablePanel.setLayout(null);

        portPanel.setLayout(null);
        portPanel.setBounds(0,150,WINDOW_WIDTH,WINDOW_HEIGHT);

        windowLayer.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        //setting up the frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("App Store");
        frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        frame.setResizable(false);

        //topPanel consisting of the search and the title
        topPanel.setLayout(null);
        topPanel.setBackground(Color.decode(backGroundGray));
        topPanel.setBounds(300,0,1080,200);

        //adding information to the topPanel
        displaySearchAndTitle(topPanel);


        //sideBar and adding information to it
        sideBar.setBounds(0, 0, 300,WINDOW_HEIGHT);
        displaySideBar(sideBar);

        //getting all apps names into a list
        ArrayList<String> apps = Backend.getAppNames();

        //displaying all the apps from the list
        try {
            displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, scrollablePanel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //adding all action listeners
        settingsButton.addActionListener(new Main());
        searchInput.addFocusListener(new Main());
        allButton.addActionListener(new Main());
        featuredButton.addActionListener(new Main());
        programmingButton.addActionListener(new Main());
        communicationButton.addActionListener(new Main());
        browserButton.addActionListener(new Main());
        searchInput.addActionListener(new Main());

        //adding these here instead of settings so we don't end up with multiple action listeners
        accentOptions.addActionListener(new Main());
        panelColorOptions.addActionListener(new Main());
        themeOptions.addActionListener(new Main());



        //setting the max value of the scrollbar to the list size *3
        //*3 is arbitrary, but it worked well with everything i tried
        sideSlider.setMaximum((apps.size()-1)*3);
        sideSlider.setBounds(1250, 0, 30, 700);

        //adding an adjustment listener which will be called whenever the scrollbar is moved
        sideSlider.addAdjustmentListener(new Main());
        sideSlider.setUnitIncrement(1);

        //adding everything to the windowLayer
//      This is where the layers are added
        windowLayer.add(topPanel);
        sideBar.add(featuredButton, Integer.valueOf(2));
        sideBar.add(programmingButton, Integer.valueOf(2));
        sideBar.add(communicationButton, Integer.valueOf(2));
        sideBar.add(browserButton, Integer.valueOf(2));
        sideBar.add(allButton, Integer.valueOf(2));
        sideBar.add(settingsButton, Integer.valueOf(2));
        windowLayer.add(sideBar);
        windowLayer.add(sideSlider, Integer.valueOf(2));

        //setting the backgrounds and adding panels to panels.
        //i need 3 panels because one of the panels must have a large height to accomodate all the apps
        //one panel must serve as a viewport for the large panel so when scrolling the apps dont go on the searchbar
        //one panel contains that viewport as well as the panel containing the searchbar
        scrollablePanel.setBackground(Color.decode(backGroundGray));
        portPanel.add(scrollablePanel);
        windowLayer.setBackground(Color.decode(backGroundGray));
        mainPanel.add(portPanel);
        mainPanel.setBackground(Color.decode(backGroundGray));
        windowLayer.add(mainPanel);

        //added this for scrolling purposes. works well with a real mouse but questionable with a trackpad
        scrollablePanel.addMouseWheelListener(new Main());

        //setting visible to true and adding the windowLayer which contains everything to the frame
        frame.setVisible(true);
        frame.add(windowLayer);
    }
    //function that displays search bar and title. the reason this has to be a function is that everytime the color optins are changed, the searchbar and title has to be redrawn
    public static void displaySearchAndTitle(JPanel topPanel){
        titleMessage.setText("App Store Pro");
        titleMessage.setFont(new Font("TimesRoman", Font.BOLD,40));
        titleMessage.setBounds(300,20,500,35);
        titleMessage.setForeground(Color.decode(titleColor));

        searchInput.setBounds(200, 80, 500, 50);
        searchInput.setBackground(Color.decode(panelColor));

        searchInput.setForeground(Color.decode(titleColor));
        searchInput.setFont(new Font("SansSerif", Font.PLAIN, 20));
        searchInput.setBorder(new LineBorder(Color.decode(accentColor)));
        topPanel.add(titleMessage);

        topPanel.add(searchInput);
    }
    //function for displaying sidebar that needs to exist for color changing purposes
    public static void displaySideBar(JPanel sideBar) throws IOException {
        System.out.println("drawing sidebar");
        //setting background color and border color
        sideBar.setLayout(null);
        sideBar.setBackground(Color.decode(sideBarColor));
        sideBar.setBorder(new LineBorder(Color.decode(accentColor)));

        //setting up the all apps button
        allButton.setBounds(10,75,280,50);
        allButton.setText("All Apps");
        allButton.setForeground(Color.WHITE);
        allButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        allButton.setBackground(Color.decode(buttonColorGrey));
        allButton.setBorder(new LineBorder(Color.decode(accentColor)));

        //setting up the featured apps button
        featuredButton.setBounds(10, 150, 280, 50);
        featuredButton.setText("Featured");
        featuredButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        featuredButton.setBackground(Color.decode(buttonColorGrey));
        featuredButton.setBorder(new LineBorder(Color.decode(accentColor)));
        featuredButton.setForeground(Color.WHITE);

        //setting up the programming apps button
        programmingButton.setBounds(10, 225, 280, 50);
        programmingButton.setText("Programming");
        programmingButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        programmingButton.setBackground(Color.decode(buttonColorGrey));
        programmingButton.setForeground(Color.WHITE);
        programmingButton.setBorder(new LineBorder(Color.decode(accentColor)));

        //setting up the communication apps button
        communicationButton.setBounds(10, 300, 280, 50);
        communicationButton.setText("Communication");
        communicationButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        communicationButton.setBackground(Color.decode(buttonColorGrey));
        communicationButton.setForeground(Color.WHITE);
        communicationButton.setBorder(new LineBorder(Color.decode(accentColor)));

        //setting up the browser apps button
        browserButton.setBounds(10, 375, 280, 50);
        browserButton.setText("Browsers");
        browserButton.setBorder(new LineBorder(Color.decode(accentColor)));
        browserButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        browserButton.setBackground(Color.decode(buttonColorGrey));
        browserButton.setForeground(Color.WHITE);

        //setting up the settings button
        settingsButton.setBounds(10,600,70,70);
        settingsButton.setBackground(Color.decode(sideBarGrey));
        settingsButton.setBorder(new LineBorder(Color.decode(accentColor)));

        //adding an icon to the settings button and scaling it down to 45*45
        BufferedImage settingsImg= ImageIO.read(new File("assets/settings.png"));
        Image scaledDownImg = settingsImg.getScaledInstance(45,45,Image.SCALE_DEFAULT);
        ImageIcon settingsIcon = new ImageIcon(scaledDownImg);
        settingsButton.setIcon(settingsIcon);

        //add everything to the JLayeredPane
        sideBar.add(featuredButton, Integer.valueOf(2));
        sideBar.add(programmingButton, Integer.valueOf(2));
        sideBar.add(communicationButton, Integer.valueOf(2));
        sideBar.add(browserButton, Integer.valueOf(2));
        sideBar.add(allButton, Integer.valueOf(2));
        sideBar.add(settingsButton, Integer.valueOf(2));

    }
    public static void displayApp(String name, int cordX, int cordY, int starRating,  JPanel pane) throws IOException {
        //using a layeredPane so i can add different panels and components to the app
        JLayeredPane layPane = new JLayeredPane();
        layPane.setBounds(0,0,WINDOW_WIDTH,4000);

        //panel that holds the app, using this to have a background color around the app panel
        JPanel appPanel = new JPanel();
        appPanel.setBackground(Color.decode(panelColor));
        appPanel.setBorder(new LineBorder(Color.decode(accentColor)));
        appPanel.setBounds(cordX, cordY-150, 200, 200);

        //label for the app name
        JLabel appNameLabel = new JLabel();
        appNameLabel.setBounds(cordX+10, cordY-120, 200, 30);
        appNameLabel.setText(name);
        appNameLabel.setForeground(Color.BLACK);
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
        JButton downloadButton = new JButton();
        downloadButton.setIcon(downIcon);
        downloadButton.setBounds(cordX+150, cordY, 45, 45);
        downloadButton.setBackground(Color.decode(panelColor));
        downloadButton.setBorderPainted(false);
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
        JButton websiteButton = new JButton();
        websiteButton.setIcon(webIcon);
        //same lambda but with mode 0
        websiteButton.addActionListener(e -> {
            String url = Backend.getLinkFromDatabase(name,0);
            Backend.openWebsite(url);
        });
        websiteButton.setBounds(cordX+80, cordY, 45,45);
        websiteButton.setBackground(Color.decode(panelColor));
        websiteButton.setBorderPainted(false);

        //repeating the star character a certain amount of times based on the rating
        String stringStarRating = "\u2605".repeat(starRating);
        String categoryText = Backend.getCategoryForApp(name);

        //adding category text under the app name
        JLabel category = new JLabel(categoryText);
        category.setForeground(Color.BLACK);

        category.setBounds(cordX+10, cordY-95, 100,30);

        JLabel stars = new JLabel(stringStarRating);
        stars.setBounds(cordX+10, cordY-70, 200,30);
        stars.setBackground(Color.LIGHT_GRAY);
        stars.setForeground(Color.BLACK);

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
        //if the settings button was clicked, open settings
        if(actionEvent.getSource() == settingsButton){
            settings();
            return;
        }
        //if nothing was inputted into the search bar just return and do nothing
        if(actionEvent.getSource() == searchInput && searchInput.getText().equals("")){return;}

        //remove everything first, this is required because it won't really update correctly if you don't
        windowLayer.remove(mainPanel);
        mainPanel.remove(portPanel);
        portPanel.remove(scrollablePanel);
        //boolean indictating whether the app list needs to be refreshed for whatever reason
        boolean refresh = false;

        //remove all components from the panel containing the apps because it will be redrawn
        scrollablePanel.removeAll();
        if(actionEvent.getSource() == accentOptions){
            currentIndexAccent = accentOptions.getSelectedIndex();
            //refresh set to true because apps will need to be redrawn
            refresh = true;
            String newAccentColor = (String) accentOptions.getSelectedItem();
            //change accentColor to whatever the user has picked
            if(newAccentColor == "Red"){
                accentColor = redAccent;
            }
            if(newAccentColor == "Blue"){
                accentColor = blueAccent;
            }
            if(newAccentColor == "Green"){
                accentColor = greenAccent;
            }
            if(newAccentColor == "Purple"){
                accentColor = purpleAccent;
            }
            if(newAccentColor == "Yellow"){
                accentColor = yellowAccent;
            }
            if(newAccentColor == "White"){
                accentColor = whiteAccent;
            }
            if(newAccentColor == "Pink"){
                accentColor = pinkAccent;
            }
            //remove sidebar and toppanel so we can redrawn it with the correct colors
            windowLayer.remove(sideBar);
            sideBar.removeAll();
            windowLayer.remove(topPanel);
            displaySearchAndTitle(topPanel);
            try {
                displaySideBar(sideBar);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //add them back
            windowLayer.add(sideBar);
            windowLayer.add(topPanel);
        }
        if(actionEvent.getSource() == themeOptions){

            refresh = true;
            String theme = (String) themeOptions.getSelectedItem();
            if(theme ==  "Dark"){
                //change all appropriate colors
                sideBarColor = sideBarGrey;
                titleColor = whiteAccent;
                sideBar.setBackground(Color.decode(sideBarGrey));
                scrollablePanel.setBackground(Color.decode(backGroundGray));
                mainPanel.setBackground(Color.decode(backGroundGray));
                topPanel.setBackground(Color.decode(backGroundGray));
                titleMessage.setForeground(Color.WHITE);

            }
            if(theme == "Light"){
                //change all colors to light
                System.out.println("light mode requested");
                sideBarColor = sideBarWhite;
                titleColor = blackColor;
                sideBar.setBackground(Color.decode(sideBarWhite));
                scrollablePanel.setBackground(Color.WHITE);
                mainPanel.setBackground(Color.WHITE);
                topPanel.setBackground(Color.WHITE);
                titleMessage.setForeground(Color.BLACK);


            }
        }
        if(actionEvent.getSource() == panelColorOptions){
            refresh = true;
            String localPanelColor = (String) panelColorOptions.getSelectedItem();
            //set the panelColor to the users selection
            if(localPanelColor == "Grey"){
                panelColor = sideBarGrey;
            }
            if(localPanelColor == "Red"){
                panelColor = redAccent;
            }
            if(localPanelColor == "Blue"){
                panelColor = blueAccent;
            }
            if(localPanelColor == "Green"){
                panelColor = greenAccent;
            }
            if(localPanelColor == "Purple"){
                panelColor = purpleAccent;
            }
            if(localPanelColor == "Yellow"){
                panelColor = yellowAccent;
            }
            if(localPanelColor == "White"){
                panelColor = whiteAccent;
            }
            if(localPanelColor == "Pink"){
                panelColor = pinkAccent;
            }
            displaySearchAndTitle(topPanel);
        }



        if (actionEvent.getSource() == searchInput) {

            String userEntry = searchInput.getText();
            //turn the LinkedHashSet to a list
            LinkedHashSet<String> apps = Backend.Search(userEntry);
            ArrayList<String> appList = new ArrayList<>();
            int numOfApps = 0;
            for (String app : apps) {
                appList.add(app);
                numOfApps++;
            }

            //once matching apps are found, display them
            try {
                displayAppsFromString(appList, currX, currY, startX, xIncrement, yIncrement, scrollablePanel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //update scroll bar in regard to how many apps there are
            sideSlider.setMaximum(numOfApps * 3);

        }

        if (actionEvent.getSource() == featuredButton || refresh) {
            //get apps associated with button
            ArrayList<String> apps = Backend.getAppsForButton("featured");
            //display apps
            try {
                displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, scrollablePanel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sideSlider.setMaximum(apps.size() * 3);
        }
        //same as previous
        if (actionEvent.getSource() == programmingButton) {
            ArrayList<String> apps = Backend.getAppsForButton("programming");
            try {
                displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, scrollablePanel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sideSlider.setMaximum((apps.size()-2) * 3);

        }
        //same as previous

        if (actionEvent.getSource() == communicationButton) {
            ArrayList<String> apps = Backend.getAppsForButton("communication");
            try {
                displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, scrollablePanel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sideSlider.setMaximum((apps.size()-2) * 3);


        }
        //same as previous

        if (actionEvent.getSource() == browserButton) {
            ArrayList<String> apps = Backend.getAppsForButton("browser");
            try {
                displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, scrollablePanel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sideSlider.setMaximum((apps.size()-1) * 3);

        }
        //same as previous

        if (actionEvent.getSource() == allButton ) {
            try {
                ArrayList<String> allApps = Backend.getAppNames();
                displayAppsFromString(allApps, currX, currY, startX, xIncrement, yIncrement, scrollablePanel);
                sideSlider.setMaximum((allApps.size() - 2) * 3);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //adding stuff back to update it
        windowLayer.add(mainPanel);
        mainPanel.add(portPanel);
        portPanel.add(scrollablePanel);
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
        scrollablePanel.setBounds(0,sideSlider.getValue()*-50,10000,7000);
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