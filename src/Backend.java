import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.SortedSet;

import static java.lang.Character.toLowerCase;

//public class Values{

//}
public class Backend {
    static final int MAX_SEARCH_TERM = 20;

    public static ArrayList getAppNames(){

        ArrayList<String> returnString  = new ArrayList();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/appstoreSchema", "root","davidsam");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from apps");
            while(resultSet.next()){
                String appName = (resultSet.getString("appName"));
                returnString.add(appName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return returnString;


    }
    public static ArrayList<String> getAppsForButton(String button){
        ArrayList<String> returnString  = new ArrayList();
        String appName = new String();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/appstoreSchema", "root","davidsam");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from apps");
            while(resultSet.next()){

                if(resultSet.getString(button).equals("y")){
                    appName = resultSet.getString("appName");
                    System.out.println(appName+" example");
                    returnString.add(appName);


                }
                else{
                    String symbol = resultSet.getString(button);

                    appName = resultSet.getString("appName");
                    System.out.println(appName+" is not a "+button+" "+symbol);
                }


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return returnString;


    }
    public static LinkedHashSet<String> Search(String searchTerm){
        LinkedHashSet<String> matchingEntries = new LinkedHashSet<>();

        if(searchTerm.length()>MAX_SEARCH_TERM){
            return matchingEntries;
        }

        ArrayList<String> apps = getAppNames();
        for(String app: apps){
            if(searchTerm.equalsIgnoreCase(app)){
                matchingEntries.add(app);
                System.out.println("found exact match with "+app);
            }
        }
        for(int i =searchTerm.length(); i>0; i--){
            String slicedSearchTerm = searchTerm.substring(0,i);
            for(String app: apps){
                String firstChar = String.valueOf(app.charAt(0));
                String firstCharSearch = String.valueOf(slicedSearchTerm.charAt(0));
                if(app.indexOf(slicedSearchTerm)!=-1 && firstChar.equalsIgnoreCase(firstCharSearch)){
                    matchingEntries.add(app);
                }
            }
        }
        for(String app: apps){
            if(toLowerCase(app.charAt(0)) == toLowerCase(searchTerm.charAt(0))){
                matchingEntries.add(app);

            }

        }

        return matchingEntries;
    }
    public static void downloadFile(URL fileURL, String outputFile) {
        try {
            try (InputStream in = fileURL.openStream();
                 ReadableByteChannel rbc = Channels.newChannel(in);
                 FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }
        } catch (Exception IOException) {
            System.out.println("Something went wrong! Check file location or input URL");
        }
    }

    public static JPanel[] displayAppsFromSearchHits(String[] searchHits, int pos) throws IOException {
        int i = 0;
        JPanel[] panelList = new JPanel[searchHits.length];
        for(String hit : searchHits){
            JPanel panel = new JPanel();
            panel.setLayout(null);
            JLabel appName = new JLabel(hit);
            appName.setBounds(0,0,200,30);
            panel.add(appName);
            JLabel starRating = new JLabel("5 stars");
            starRating.setBounds(0,40,200,30);
            panel.add(starRating);
            /*
            BufferedImage appIcon = ImageIO.read(new File("example"));

            JLabel iconLabel = new JLabel(new ImageIcon(appIcon));
            panel.add(iconLabel);

             */
            panelList[i] = panel;
            i++;


        }
        return panelList;
    }

    public static void displayAppsFromButton(String buttonName){
        //find list of apps based on button press

        String[] appsToDisplay = new String[10000];
        for(String app : appsToDisplay){
            //function call to app displayer

        }

    }








}
