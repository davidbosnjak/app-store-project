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

//public class Values{

//}
public class Backend {
    static final int MAX_SEARCH_TERM = 20;

    public static ArrayList executeStatementAndGetReturn(String sqlStatement){

        ArrayList<String> returnString  = new ArrayList();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/appstoreSchema", "root","davidsam");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from apps");
            int i=0;
            while(resultSet.next()){
                String appName = (resultSet.getString("appName"));
                returnString.add(appName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return returnString;


    }
    public static HashSet<String> Search(String searchTerm, HashMap<String, String[]> database){
        HashSet<String> matchingEntries = new HashSet();

        if(searchTerm.length()>MAX_SEARCH_TERM){
            //not gonna make a search term
            return matchingEntries;
        }


        for(int i =searchTerm.length(); i>0; i--){
            String slicedSearchTerm = searchTerm.substring(0,i);
            for(String key: database.keySet()){
                if(key.indexOf(slicedSearchTerm)!=-1){
                    matchingEntries.add(key);
                }
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





    //database
    static HashMap<String, String[]> database  = new HashMap<String, String[]>();
    //might have to hardcode putting items into database on run...


}
