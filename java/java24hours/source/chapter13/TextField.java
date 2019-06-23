import javax.swing.*;
import java.awt.*;

public class TextField extends JFrame {
    public TextField() {
        super("TextField");
        setLookAndFeel();
        setSize(375, 80);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel pageLabel = new JLabel("Web page address: ", JLabel.RIGHT);
        JTextField pageAddress = new JTextField(20);
        FlowLayout flo = new FlowLayout();
        setLayout(flo);
        add(pageLabel);
        add(pageAddress);
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
        TextField tf = new TextField();
    }
}
