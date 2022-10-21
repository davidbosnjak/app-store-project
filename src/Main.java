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

    static JScrollBar sideSlider = new JScrollBar();
    static JButton allButton = new JButton();


    public static void main(String[] args) {

        mainWindow();


    }

    public static void mainWindow(){
        JPanel mainPanel = new JPanel();

        JViewport viewport = new JViewport();

        newPane.setBounds(0,0,1280,5000);
        newPane.setLayout(null);

        windowLayer.setBackground(Color.decode("#ecf0f1"));
        windowLayer.setBounds(0, 0, 1280, 3000);
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


        String buttonColor = "#bdc3c7";
//      Search bar need the search function to be added to it
        searchInput.addActionListener(new Main());
        searchInput.addFocusListener(new Main());
        searchInput.setBounds(500, 60, 500, 50);
        // need to change the base text so when input is added it will remove "search..."

//      this is the part where we add the sidebar and the features to it
        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.decode("#3498db"));
        sideBar.setBounds(0, 0, 300, 720);

        allButton.setBounds(10,75,280,50);
        allButton.setText("All Apps");
        allButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        allButton.setBackground(Color.decode(buttonColor));
        allButton.addActionListener(new Main());

        featuredButton.setBounds(10, 150, 280, 50);
        featuredButton.setText("Featured");
        featuredButton.setFont(new Font("TimesRoman", Font.BOLD, 25 ));
        featuredButton.setBackground(Color.decode(buttonColor));
        featuredButton.addActionListener(new Main());


        programmingButton.setBounds(10, 225, 280, 50);
        programmingButton.setText("Programming");
        programmingButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        programmingButton.setBackground(Color.decode(buttonColor));
        programmingButton.addActionListener(new Main());



        communicationButton.setBounds(10, 300, 280, 50);
        communicationButton.setText("Communication");
        communicationButton.setFont(new Font("ComicSans", Font.BOLD, 25 ));
        communicationButton.setBackground(Color.decode(buttonColor));
        communicationButton.addActionListener(new Main());



        browserButton.setBounds(10, 375, 280, 50);
        browserButton.setText("Browsers");
        browserButton.setFont(new Font("SansSerif", Font.BOLD, 25 ));
        browserButton.setBackground(Color.decode(buttonColor));

        browserButton.addActionListener(new Main());

        //calls
        ArrayList<String> apps = Backend.getAppNames();


        try {
            displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sideSlider.setMaximum((apps.size()-1)*3);


//      Needs to be programmed but it is the scrollbar

        sideSlider.setBounds(1260, 0, 20, 720);
        sideSlider.addAdjustmentListener(new Main());
        sideSlider.setUnitIncrement(1);

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
        scrollableStuff.setBounds(0,0,1280,720);
        windowLayer.add(scrollableStuff, Integer.valueOf(1));
        newPane.setBackground(Color.decode("#7f8c8d"));
        windowLayer.add(newPane);

        newPane.addMouseWheelListener(new Main());
        //windowLayer.add(appPane);
        viewport.add(scrollableStuff);
        viewport.setBounds(0,0,1280,720);



        frame.setVisible(true);
        frame.add(windowLayer);
    }

    public static void displayApp(String name, int cordX, int cordY, int starRating,  String image, JPanel pane) throws IOException {
        JLayeredPane layPane = new JLayeredPane();
        layPane.setBounds(0,0,1280,4000);
        JPanel thirdAppSlot = new JPanel();
        thirdAppSlot.setBackground(Color.decode("#2980b9"));
        thirdAppSlot.setBounds(cordX, cordY-150, 200, 200);

        String thirdAppNameString = name;

        JLabel appNameLabel = new JLabel();
        appNameLabel.setBounds(cordX+10, cordY-120, 200, 30);
        appNameLabel.setText(thirdAppNameString);
        appNameLabel.setBackground(Color.lightGray);
        appNameLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
        BufferedImage appImg= ImageIO.read(new File("assets/"+name+".png"));

        Image scaledImg = appImg.getScaledInstance(100,100,Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaledImg);
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(icon);
        imageLabel.setBounds(cordX+100,cordY-125,100,100);

        BufferedImage downImg= ImageIO.read(new File("assets/downloadArrow.png"));
        Image scaledDownImg = downImg.getScaledInstance(45,45,Image.SCALE_DEFAULT);
        ImageIcon downIcon = new ImageIcon(scaledDownImg);
        JButton downloadButton = new JButton(downIcon);
        downloadButton.setText("\u21e3");
        downloadButton.setBounds(cordX+150, cordY, 45, 45);
        downloadButton.setBackground(Color.decode("#bdc3c7"));
        downloadButton.setFont(new Font("TimesRoman", Font.BOLD, 10));
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DOWNLOAD REUESTED");
                String url = Backend.getLinkFromDatabase(name, 1);
                Backend.openWebsite(url);
            }
        });
        BufferedImage webImg= ImageIO.read(new File("assets/website.png"));
        Image scaledWebImg = webImg.getScaledInstance(45,45,Image.SCALE_DEFAULT);
        ImageIcon webIcon = new ImageIcon(scaledWebImg);
        JButton websiteButton = new JButton(webIcon);

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
        websiteButton.setBounds(cordX+80, cordY, 45,45);
        websiteButton.setBackground(Color.decode("#bdc3c7"));


        String stringStarRating = "\u2605".repeat(starRating);
        String categoryText = Backend.getCategoryForApp(name);
        JLabel category = new JLabel(categoryText);
        category.setBounds(cordX+10, cordY-95, 100,30);

        JLabel stars = new JLabel(stringStarRating);
        stars.setBounds(cordX+10, cordY-70, 200,30);
        stars.setBackground(Color.LIGHT_GRAY);
        layPane.add(thirdAppSlot,0);
        layPane.add(category, Integer.valueOf(2));

        layPane.add(appNameLabel,Integer.valueOf(1));
        layPane.add(downloadButton,Integer.valueOf(1));
        layPane.add(websiteButton, Integer.valueOf(1));
        layPane.add(stars,Integer.valueOf(2));
        layPane.add(imageLabel,Integer.valueOf(2));
        pane.add(layPane);



    }

    public static void displayAppsFromString(ArrayList<String> apps, int currX, int currY, int startX, int xIncrement, int yIncrement, JPanel scrollableStuff) throws IOException {

        int i = 0;
        for (String app : apps) {
            int stars = Backend.getRatingForApp(app);
            System.out.println(app);
            if (currX == startX) {
                if (i == 0) {

                    displayApp(app, currX, currY, stars, "test", scrollableStuff);
                } else {
                    currX += xIncrement;
                    displayApp(app, currX, currY, stars, "test", scrollableStuff);
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
            int numOfApps = 0;
            for(String app : apps){
                appList.add(app);
                numOfApps++;
            }
            try {
                displayAppsFromString(appList, currX,currY,startX,xIncrement,yIncrement,newPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sideSlider.setMaximum(numOfApps*3);
        }

        if(actionEvent.getSource() == featuredButton){
            ArrayList<String> apps = Backend.getAppsForButton("featured");

            try {
                displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Featured");
            sideSlider.setMaximum(apps.size()*3);


        }
        if(actionEvent.getSource() == programmingButton){
            ArrayList<String> apps = Backend.getAppsForButton("programming");
            try {
                displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sideSlider.setMaximum(apps.size()*3);

            System.out.println("Programming");
        }
        if(actionEvent.getSource() == communicationButton){
            ArrayList<String> apps = Backend.getAppsForButton("communication");
            try {
                displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sideSlider.setMaximum(apps.size()*3);

            System.out.println("Communication");

        }
        if(actionEvent.getSource()== browserButton){
            ArrayList<String> apps = Backend.getAppsForButton("browser");
            try {
                displayAppsFromString(apps, currX, currY, startX, xIncrement, yIncrement, newPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sideSlider.setMaximum(apps.size()*3);

            System.out.println("Browsers");
        }
        if(refresh){
            try {
                displayAppsFromString(Backend.getAppNames(), currX, currY,startX,xIncrement,yIncrement,newPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        System.out.println("adjustement made");
        newPane.setBounds(0,sideSlider.getValue()*-50,10000,7000);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("mouse wheel moved");
        int notches = e.getWheelRotation();
        if(notches<0){
            sideSlider.setValue(sideSlider.getValue()-5);

        }
        else{
            sideSlider.setValue(sideSlider.getValue()+5);

        }
    }
}