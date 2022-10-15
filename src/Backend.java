import java.io.*;
import java.net.URL;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
public class Backend {
    public static String[] Search(String searchTerm){
        //temporary so we dont have errors
        String[] matchingEntries = {searchTerm};
       //check if what they searched contains stuff from entries
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
            System.out.println("Something went wrong!");
        }
    }

    public static void displayAppsFromSearchHits(String[] searchHits){

    }





    //database
    static HashMap<String, String[]> database  = new HashMap<String, String[]>();
    //might have to hardcode putting items into database on run...


}
