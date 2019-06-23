import javax.swing.*;
import java.awt.*;

public class TextArea extends JFrame {
    public TextArea() {
        super("TextArea");
        setLookAndFeel();
        setSize(500, 190);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextArea comments = new JTextArea(8, 40);
        FlowLayout flo = new FlowLayout();
        setLayout(flo);
        add(comments);
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
        TextArea ta = new TextArea();
    }
}
