import java.io.*;
import java.util.*;

public class PropertyFileCreator {
		public PropertyFileCreator() {
				Properties prop = new Properties();
				prop.setProperty("username","rcade");
				prop.setProperty("browser","mozilla Firefox");
				prop.setProperty("showEmail","no");
				try {
						File propFile = new File("properties.xml");
						FileOutputStream propStream = new FileOutputStream(propFile);
						Date now = new Date();
						prop.storeToXML(propStream, "Created on " + now);
				} catch (IOException e) {
						System.out.println("Error: " + e.getMessage());
				}
		}

		public static void main(String[] args) {
				PropertyFileCreator pfc = new PropertyFileCreator();
		}
}
