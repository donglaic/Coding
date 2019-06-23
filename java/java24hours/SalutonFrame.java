import javax.swing.*;
import java.awt.*;

public class SalutonFrame extends JFrame {
		public SalutonFrame() {
				super();
				setLookAndFeel();
				setSize(350, 100);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				FlowLayout flo = new FlowLayout();
				setLayout(flo);
				JLabel pageLabel = new JLabel("Saluton!");
				add(pageLabel);
				setVisible(true);
		}

		private void setLookAndFeel() {
				try {
						UIManager.setLookAndFeel(
										"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
										);
				} catch (Exception exc) {
						//ignore error
				}
		}

		public static void main(String[] args) {
				SalutonFrame sal = new SalutonFrame();
		}
}
