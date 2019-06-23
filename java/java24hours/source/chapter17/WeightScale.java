import java.awt.*;

public class WeightScale extends javax.swing.JApplet {
    float lbs = 0F;
    float ozs;
    float kgs;
    float metricTons;
    String name = "somebody";
	int x = 55;

    public void init() {
        String lbsValue = getParameter("weight");
        if (lbsValue != null) {
            lbs = Float.valueOf(lbsValue);
        }
        String personValue = getParameter("person");
        if (personValue != null) {
            name = personValue;
        }
        ozs = (float) (lbs * 16);
        kgs = (float) (lbs / 2.204623);
        metricTons = (float) (lbs / 2204.623);
    }

	public void paint(Graphics screen) {
        Graphics2D screen2D = (Graphics2D) screen;
        screen2D.drawString("Studying the weight of " + name, 5, 30);
        screen2D.drawString("In pounds: " + lbs, 55, 50);
        screen2D.drawString("In ounces: " + ozs, 55, 70);
        screen2D.drawString("In kilograms: " + kgs, 55, 90);
        screen2D.drawString("In metric tons: " + metricTons, 55, 110);
    }

    public void repaint(Graphics screen) {
        Graphics2D screen2D = (Graphics2D) screen;
        screen2D.drawString("Studying the weight of " + name, 5, 30);
        screen2D.drawString("In pounds: " + lbs, x, 50);
        screen2D.drawString("In ounces: " + ozs, x, 70);
        screen2D.drawString("In kilograms: " + kgs, x, 90);
        screen2D.drawString("In metric tons: " + metricTons, x, 110);
		x++;
    }
}
