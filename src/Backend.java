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

    //looks through the database and returns a list of every single app name
    public static ArrayList<String> getAppNames(){

        ArrayList<String> returnList  = new ArrayList<>();

        try {
            //connect to the mysql server thing
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/appstoreSchema", "root","davidsam");
            Statement statement = connection.createStatement();
            //execute this sql statement which just gets every row from every column
            ResultSet resultSet = statement.executeQuery("select * from apps");

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
    //should have made a "get x from database" function instead of making like 4 different ones...
    //works the same as appname function
    public static String getLinkFromDatabase(String item, int mode) {
        System.out.println(item+" getLinkFromDatabase");
        //download link

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/appstoreSchema", "root", "davidsam");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from apps");
                while (resultSet.next()) {

                    if(resultSet.getString("appName").equals(item)){
                        System.out.println("FOUND");
                        if(mode==0){return resultSet.getString("appURL");}
                        else{
                            return resultSet.getString("appDownload");
                        }

                        }
                    else{
                        System.out.println(resultSet.getString("appName")+" does not equal "+item);
                    }

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        return item;

    }
    //works the same as app name function
    public static ArrayList<String> getAppsForButton(String button){
        ArrayList<String> returnString  = new ArrayList<>();
        String appName;

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/appstoreSchema", "root","davidsam");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from apps");
            while(resultSet.next()){
                //if its part of the appropriate column in the database
                if(resultSet.getString(button).equals("y")){
                    appName = resultSet.getString("appName");
                    returnString.add(appName);

                }
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
    //same as getNames
    public static String getCategoryForApp(String app){
        String category = new String();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/appstoreSchema", "root","davidsam");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from apps");
            while(resultSet.next()){
                if(resultSet.getString("appName").equals(app)){
                    return resultSet.getString("category");
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }
    //same as getNames
    public static int getRatingForApp(String app){
        int rating = 1;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/appstoreSchema", "root","davidsam");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from apps");
            while(resultSet.next()){
                if(resultSet.getString("appName").equals(app)){
                    return resultSet.getInt("appRating");
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rating;
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
