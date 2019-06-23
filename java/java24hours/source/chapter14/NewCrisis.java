import java.awt.*;
import javax.swing.*;

public class NewCrisis extends JFrame {
    JButton panicButton;
    JButton dontPanicButton;
    JButton blameButton;
    JButton mediaButton;
    JButton saveButton;

    public NewCrisis() {
        super("NewCrisis");
        setLookAndFeel();
        setSize(348, 128);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        FlowLayout flo = new FlowLayout();
        BorderLayout bord = new BorderLayout();
        setLayout(flo);
        top.setLayout(flo);
        bottom.setLayout(bord);
        panicButton = new JButton("Panic");
        dontPanicButton = new JButton("Don't Panic");
        blameButton = new JButton("Blame Others");
        mediaButton = new JButton("Notify the Media");
        saveButton = new JButton("Save Yourself");
        top.add(panicButton);
        top.add(dontPanicButton);
        bottom.add(blameButton, BorderLayout.NORTH);
        bottom.add(mediaButton, BorderLayout.CENTER);
        bottom.add(saveButton, BorderLayout.SOUTH);
        add(bottom);
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
        NewCrisis cr = new NewCrisis();
    }
}

