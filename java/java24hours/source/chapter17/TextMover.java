import java.awt.*;

public class TextMover extends javax.swing.JApplet {
    int xPos = 50;

    public void paint(Graphics screen) {
        super.paint(screen);
        Graphics2D screen2D = (Graphics2D)screen;
        screen2D.drawString("Able was I ere I saw Elba",
            5, xPos++);
    }
}