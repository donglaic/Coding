import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

public class ScrollCombo extends JFrame {
    public ScrollCombo() {
        super("ScrollCombo");
        setSize(370, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComboBox jcb = new JComboBox();
        jcb.addItem("Modano");
        jcb.addItem("Guerin");
        jcb.addItem("Turco");
        jcb.addItem("Hatcher");
        jcb.addItem("Matvichuk");
        JScrollPane scroll = new JScrollPane(jcb);
        FlowLayout flow = new FlowLayout();
        setLayout(flow);
        add(scroll);
        setVisible(true);
    }

    public static void main(String[] arguments) {
        ScrollCombo frame = new ScrollCombo();
    }
}
