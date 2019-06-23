import java.awt.*;
import javax.swing.*;

public class PieApplet extends JApplet {
    Color uneasyBeingGreen = new Color(0xCC, 0xCC, 0x99);
    Color zuzusPetals = new Color(0xCC, 0x66, 0xFF);
    Color zootSuit = new Color(0x66, 0x66, 0x99);
    Color sweetHomeAvocado = new Color(0x66, 0x99, 0x66);
    Color shrinkingViolet = new Color(0x66, 0x66, 0x99);
    Color miamiNice = new Color(0x33, 0xFF, 0xFF);
    Color inBetweenGreen = new Color(0x00, 0x99, 0x66);
    Color norwegianBlue = new Color(0x33, 0xCC, 0xCC);
    Color purpleRain = new Color(0x66, 0x33, 0x99);
    Color freckle = new Color (0x99, 0x66, 0x33);

    public void init() {
        PiePanel pie = new PiePanel(10);
        pie.addSlice(uneasyBeingGreen, 1338);
        pie.addSlice(zuzusPetals, 1166);
        pie.addSlice(zootSuit, 307);
        pie.addSlice(sweetHomeAvocado, 240);
        pie.addSlice(shrinkingViolet, 198);
        pie.addSlice(miamiNice, 176);
        pie.addSlice(inBetweenGreen, 156);
        pie.addSlice(norwegianBlue, 149);
        pie.addSlice(purpleRain, 140);
        pie.addSlice(freckle, 127);
        add(pie);
    }
}