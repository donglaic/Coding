import java.io.*;
import nu.xom.*;

public class WeatherStation2 {
    int[] highTemp = new int[6];
    int[] lowTemp = new int[6];
    String[] conditions = new String[6];
    String[] skies = new String[6];

    public WeatherStation2() throws ParsingException, IOException {
        // get the XML document
        Builder builder = new Builder();
        Document doc = builder.build("http://www.wunderground.com/auto/wui/geo/ForecastXML/index.xml?query=Wasilla,AK");
        // get the root element, <forecast>
        Element root = doc.getRootElement();
        // get the <simpleforecast> element
        Element simple = root.getFirstChildElement("simpleforecast");
        // get the <forecastday> elements
        Elements days = simple.getChildElements("forecastday");
        for (int current = 0; current < days.size(); current++) {
            // get current <forecastday>
            Element day = days.get(current);
            // get current <high>
            Element high = day.getFirstChildElement("high");
            Element highF = high.getFirstChildElement("fahrenheit");
            // get current <low>
            Element low = day.getFirstChildElement("low");
            Element lowF = low.getFirstChildElement("fahrenheit");
            // get current <icon>
            Element icon = day.getFirstChildElement("icon");
            // get sky icon
            Element sky = day.getFirstChildElement("skyicon");
            // store values in object variables
            lowTemp[current] = -1;
            highTemp[current] = -1;
            try {
                lowTemp[current] = Integer.parseInt(lowF.getValue());
                highTemp[current] =
                Integer.parseInt(highF.getValue());
            } catch (NumberFormatException nfe) {
                // do nothing
            }
            conditions[current] = icon.getValue();
            skies[current] = sky.getValue();
        }
    }

    public void display() {
        for (int i = 0; i < conditions.length; i++) {
            System.out.println("Period " + i);
            System.out.println("\tConditions: " + conditions[i]);
            System.out.println("\tHigh: " + highTemp[i]);
            System.out.println("\tLow: " + lowTemp[i]);
            System.out.println("\tSky: " + skies[i]);
        }
    }

    public static void main(String[] arguments) {
        try {
            WeatherStation2 station = new WeatherStation2();
            station.display();
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
}