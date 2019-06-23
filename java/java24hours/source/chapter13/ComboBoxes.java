import javax.swing.*;
import java.awt.*;

public class ComboBoxes extends JFrame {
    public ComboBoxes() {
        super("ComboBoxes");
        setLookAndFeel();
        setSize(345, 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComboBox profession = new JComboBox();
        FlowLayout flo = new FlowLayout();
        profession.addItem("Butcher");
        profession.addItem("Baker");
        profession.addItem("Candlestick maker");
        profession.addItem("Fletcher");
        profession.addItem("Fighter");
        profession.addItem("Technical writer");
        setLayout(flo);
        add(profession);
        setVisible(true);
    }
    
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
            );
        } catch (Exception exc) {
            // ignore error
        }
    }

    public static void main(String[] arguments) {
        ComboBoxes cb = new ComboBoxes();
    }
}
