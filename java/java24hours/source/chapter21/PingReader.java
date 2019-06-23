import java.io.*;
import nu.xom.*;

public class PingReader {
    
    public PingReader() throws ParsingException, IOException {
        // get the XML document
        File pingFile = new File("shortChanges.xml");
        Builder builder = new Builder();
        Document doc = builder.build(pingFile);
        // get the root element, <weblogUpdates>
        Element root = doc.getRootElement();
        // get the <weblog> elements
        Elements entries = root.getChildElements("weblog");
        for (int current = 0; current < entries.size(); current++) {
            // get current <weblog>
            Element entry = entries.get(current);
            // get entry's name attribute
            Attribute name = entry.getAttribute("name");
            String nameValue = name.getValue();
            // get entry's url attribute
            Attribute url = entry.getAttribute("url");
            String urlValue = url.getValue();
            // get entry's when attribute
            Attribute when = entry.getAttribute("when");
            String whenValue = when.getValue();
            // display values
            System.out.println("\n\nName: " + nameValue +
                "\nURL: " + urlValue +
                "\nWhen: " + whenValue);
        }
    }
       
    public static void main(String[] arguments) {
        try {
            PingReader reader = new PingReader();
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
}