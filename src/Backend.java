import java.io.*;
import java.net.URL;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
public class Backend {
    static final int MAX_SEARCH_TERM = 20;
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
    public static void downloadFile(String fileURL){

    }

    public static void displayAppsFromSearchHits(String[] searchHits){

    }



    //database
    static HashMap<String, String[]> database  = new HashMap<String, String[]>();
    //might have to hardcode putting items into database on run...


}
