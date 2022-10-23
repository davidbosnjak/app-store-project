import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import static java.lang.Character.toLowerCase;

public class Backend {
    //max search term that shouldn't be exceeded
    static final int MAX_SEARCH_TERM = 20;
    static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/appstoreSchema", "root","davidsam");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //worth noting that most of these function can probably be made into one but i've already rewrote these 800+ lines of code too many times
    //looks through the database and returns a list of every single app name
    public static ArrayList<String> getAppNames(){

        ArrayList<String> returnList  = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select `appName` from apps");

            //while results still exist, find the appName in the row and add it to a list
            while(resultSet.next()){
                String appName = (resultSet.getString("appName"));
                returnList.add(appName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //return the list
        return returnList;
    }
    //this function gets a specific item in the database. It will get either the website link or the download link
    public static String getLinkFromDatabase(String item, int mode) {
            try {
                Statement statement = connection.createStatement();
                if(mode == 0) {
                    //this sql statement gets the appUrl from the database and gets the specific row where the appName matches the one we want
                    ResultSet resultSet = statement.executeQuery("SELECT `appURL`, `appName` FROM apps WHERE `appName` = '" + item + "'");
                    resultSet.next();
                    return resultSet.getString("appURL");
                }
                else{
                    ResultSet resultSet = statement.executeQuery("SELECT `appDownload`, `appName` FROM apps WHERE `appName` = '" + item + "'");
                    resultSet.next();
                    return resultSet.getString("appDownload");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
    //works the same as app name function
    public static ArrayList<String> getAppsForButton(String button){
        ArrayList<String> returnString  = new ArrayList<>();

        try {
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/appstoreSchema", "root","davidsam");
            Statement statement = connection.createStatement();
            //get apps that are part of a certain button
            ResultSet resultSet = statement.executeQuery("select `appName` from apps WHERE `"+button+"` = 'y'");
            while(resultSet.next()){
                //if its part of the appropriate column in the database
                returnString.add(resultSet.getString("appName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return returnString;

    }
    //search logic function

    public static LinkedHashSet<String> Search(String searchTerm){
        //LinkedHasSet is being used because I need a list that doesn't have duplicates but also remains ordered
        LinkedHashSet<String> matchingEntries = new LinkedHashSet<>();

        //don't make a search is its longer than max
        if(searchTerm.length()>MAX_SEARCH_TERM){
            return matchingEntries;
        }

        //getting all app names
        ArrayList<String> apps = getAppNames();
        for(String app: apps){
            //going through apps and seeing if the search term is exactly equal to the name of an app regardless of case
            if(searchTerm.equalsIgnoreCase(app)){
                //if found this will be the first result
                matchingEntries.add(app);
            }
        }
        for(int i =searchTerm.length(); i>0; i--){
            //slice the searchTerm so it keeps getting smaller
            String slicedSearchTerm = searchTerm.substring(0,i);
            for(String app: apps){
                //geting first chars regarldess of case
                String firstChar = String.valueOf(app.charAt(0));
                String firstCharSearch = String.valueOf(slicedSearchTerm.charAt(0));
                slicedSearchTerm = slicedSearchTerm.toLowerCase();
                String appCopy = app.toLowerCase();
                System.out.println(slicedSearchTerm);
                //if the app name contains the slice of the search term as a substring and the first characters are the same add it to the list
                if(appCopy.contains(slicedSearchTerm) && firstChar.equalsIgnoreCase(firstCharSearch)){
                    matchingEntries.add(app);
                }
            }
        }
        for(String app: apps){
            //going through apps and seeing if the app is a substring of the search term
            if(searchTerm.contains(app)){
                matchingEntries.add(app);
            }
        }
        for(String app: apps){
            //seeing if the first characters are the same
            if(toLowerCase(app.charAt(0)) == toLowerCase(searchTerm.charAt(0))){
                matchingEntries.add(app);

            }
        }
        return matchingEntries;
    }
    //same as getLink
    public static String getCategoryForApp(String app){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select `category` from apps WHERE `appName` = '"+app+"'");
            resultSet.next();
            return resultSet.getString("category");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //same as getLink
    public static int getRatingForApp(String app){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select `appRating` from apps WHERE `appName` = '"+app+"'");
            resultSet.next();
            return resultSet.getInt("appRating");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //website opener
    public static void openWebsite(String webURL){
        //make a URI object and open it using the browse method
        try{
            URI url = new URI(webURL);
            java.awt.Desktop.getDesktop().browse(url);

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
