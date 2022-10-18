import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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





    //database
    static HashMap<String, String[]> database  = new HashMap<String, String[]>();
    //might have to hardcode putting items into database on run...


}
